package net.sunrise.crsx.service.impl;

import java.io.InputStream;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.sunrise.common.CommonUtility;
import net.sunrise.crsx.repository.OpportunityRepository;
import net.sunrise.deplyment.DeploymentSpecification;
import net.sunrise.domain.DataInterfaceModel;
import net.sunrise.domain.DataSourceType;
import net.sunrise.domain.entity.crx.Opportunity;
import net.sunrise.exceptions.ExecutionContextException;
import net.sunrise.exceptions.ObjectNotFoundException;
import net.sunrise.framework.model.ExecutionContext;
import net.sunrise.framework.repository.BaseRepository;
import net.sunrise.framework.service.GenericServiceImpl;
import net.sunrise.helper.GlobalDataServicesRepository;
import net.sunrise.model.base.IDataContainer;
import net.sunrise.service.api.crx.OpportunityService;


@Service
public class OpportunityServiceImpl extends GenericServiceImpl<Opportunity, Long> implements OpportunityService{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7412053874038570241L;

	@Inject 
	private OpportunityRepository repository;
	
	protected BaseRepository<Opportunity, Long> getRepository() {
		return this.repository;
	}

	@Override
	public Opportunity getOne(String name) throws ObjectNotFoundException {
		return (Opportunity)super.getOptionalObject(repository.findByName(name));
	}

	@Override
	public ExecutionContext load(ExecutionContext executionContext) throws ExecutionContextException {
		GlobalDataServicesRepository globalDataServicesRepository = null;
		Object projectContextData = null;
		DataInterfaceModel dataInterfaceModel = null;
		IDataContainer<String> dataContainer = null;
		try {
			if (!(executionContext.containKey(DeploymentSpecification.DEPLOYMENT_DATA_KEY) || 
					executionContext.containKey(DeploymentSpecification.DEPLOYMENT_DATA_MODEL_KEY))){
				executionContext.setExecutionStage("There is not enough deployment specification for enterprise. ");
				log.info(executionContext.getExecutionStage());
				return executionContext;
			}

			globalDataServicesRepository = GlobalDataServicesRepository.builder().build();
			projectContextData = executionContext.getContextData(DeploymentSpecification.DEPLOYMENT_DATA_KEY);
			dataInterfaceModel = (DataInterfaceModel)executionContext.getContextData(DeploymentSpecification.DEPLOYMENT_DATA_MODEL_KEY);
			if (DataSourceType.CSV.equals(dataInterfaceModel.getDataSourceType())){
				dataContainer = globalDataServicesRepository.readCsvFile(
						(InputStream)projectContextData, 
						dataInterfaceModel.isProcessColumnHeaders(), 
						dataInterfaceModel.getComponentSeparator());
			} else if (DataSourceType.EXCEL.equals(dataInterfaceModel.getDataSourceType())) {
			}

			if (CommonUtility.isEmpty(dataContainer)){
				executionContext.setExecutionStage("The data container is empty. Please recheck data source. ");
				log.info(executionContext.getExecutionStage());
				return executionContext;
			}
			log.info("Start to deploy enterprise data ......");
			
		} catch (Exception e) {
			throw new ExecutionContextException(e);
		}

		executionContext.setExecutionStage("The data deployment for project is done. ");
		log.info(executionContext.getExecutionStage());
		return executionContext;
	}
}
