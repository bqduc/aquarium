/**
 * 
 */
package net.sunrise.dispatch;

import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import net.sunrise.cdx.domain.entity.Configuration;
import net.sunrise.cdx.manager.ConfigurationManager;
import net.sunrise.cdx.service.ConfigurationService;
import net.sunrise.common.CommonConstants;
import net.sunrise.common.ListUtility;
import net.sunrise.constants.CommonManagerConstants;
import net.sunrise.deployment.GlobalDeploymentManager;
import net.sunrise.exceptions.EcosysException;
import net.sunrise.framework.component.BaseComponent;
import net.sunrise.framework.global.GlobalAppConstants;
import net.sunrise.framework.model.ExecutionContext;
import net.sunrise.manager.auth.AuthenticationServiceManager;
import net.sunrise.manager.system.SystemSequenceManager;
import net.sunrise.osx.OfficeSuiteServiceProvider;
import net.sunrise.osx.model.DataBucket;
import net.sunrise.utility.ClassPathResourceUtility;

/**
 * @author bqduc
 *
 */
@Component
public class GlobalDataRepositoryManager extends BaseComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7532241073050763668L;

	/*@Inject 
	private GlobalServicesHelper globalServicesHelper;*/

	@Inject
	private SystemSequenceManager systemSequenceManager;
	
	@Inject 
	private AuthenticationServiceManager authenticationServiceManager;

	@Inject
	private ConfigurationService configurationService;

	@Inject 
	private ConfigurationManager configurationManager;

	@Inject 
	private GlobalDeploymentManager globalDeploymentManager;

	public void initializeGlobalData() throws EcosysException {
		log.info("Enter GlobalDataRepositoryManager::initializeGlobalData()");
		this.init();
		this.performInitializeGlobalMasterData();
		log.info("Leave GlobalDataRepositoryManager::initializeGlobalData()");
	}

	protected void performInitializeGlobalMasterData() throws EcosysException {
		log.info("Enter GlobalServicesHelper::initDefaultComponents(): Initializing the default components....");
		Optional<Configuration> optConfig = null;
		Configuration mdxConfig = null;
		ExecutionContext executionContext = ExecutionContext.builder().build();
		try {
			log.info("Master data for system sequence is in progress. ");
			systemSequenceManager.initializeSystemSequence();

			executionContext.putContextData(CommonManagerConstants.CONFIG_GROUP_DEPLOYMENT, "project");
			globalDeploymentManager.deploy(executionContext);

			optConfig = configurationService.getOne(GlobalAppConstants.GLOBAL_MASTER_DATA_INITIALIZE);
			if (optConfig.isPresent() && CommonConstants.TRUE_STRING.equalsIgnoreCase(optConfig.get().getValue()) ){
				log.info("The global data was initialized already. ");
				return;
			}

			log.info("Master data for configuration is in progress. ");
			configurationManager.initializeMasterData();

			log.info("Master data for authentication is in progress. ");
			authenticationServiceManager.initializeMasterData();

			/*log.info("Master data for system sequence is in progress. ");
			systemSequenceManager.initializeSystemSequence();*/

			//cLog.info("Master data for employee is in progress. ");
			//employeeManager.initDefaultData();
			if (!optConfig.isPresent()){
				mdxConfig = Configuration.builder().build();
			} else {
				mdxConfig = optConfig.get();
			}

			mdxConfig.setValue(CommonConstants.TRUE_STRING);
			mdxConfig.setName(GlobalAppConstants.GLOBAL_MASTER_DATA_INITIALIZE);
			configurationService.saveOrUpdate(mdxConfig);

			log.info("Master data initialization is done. ");
		} catch (Exception e) {
			throw new EcosysException(e);
		}
		log.info("Leave GlobalServicesHelper::initDefaultComponents()");
	}

	public void init() {
		log.info("Start to parse Excel data");
		Map<Object, Object> params = ListUtility.createMap();
		String[] sheetIds = new String[]{"languages", "items", "localized-items"}; 
		DataBucket dataBucket = null;
		try {
			params.put(DataBucket.PARAM_INPUT_STREAM, ClassPathResourceUtility.builder().build().getInputStream("config/data/data-catalog.xlsx"));
			params.put(DataBucket.PARAM_DATA_SHEETS, sheetIds);
			params.put(DataBucket.PARAM_STARTED_ROW_INDEX, new Integer[] {1, 1, 1});
			dataBucket = OfficeSuiteServiceProvider.builder()
			.build()
			.readXlsxData(params);
			System.out.println(dataBucket);
		} catch (Exception e) {
			log.error(e);
		}
		log.info("Finished parse Excel data");
	}
}
