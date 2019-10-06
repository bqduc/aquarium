/**
 * 
 */
package net.sunrise.controller.admin;

import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import net.sunrise.asyn.AsyncDataPackageLoader;
import net.sunrise.asyn.AsyncExtendedDataLoader;
import net.sunrise.asyn.ImportContactsThread;
import net.sunrise.asyn.InventoryDataDeployer;
import net.sunrise.common.CommonUtility;
import net.sunrise.common.ListUtility;
import net.sunrise.controller.ControllerConstants;
import net.sunrise.controller.base.BaseController;
import net.sunrise.dispatch.GlobalDataLoadingManager;
import net.sunrise.dispatch.GlobalDataRepositoryManager;
import net.sunrise.domain.entity.config.Language;
import net.sunrise.domain.entity.config.LocalizedItem;
import net.sunrise.domain.entity.hc.Employee;
import net.sunrise.framework.asyn.Asynchronous;
import net.sunrise.framework.model.ExecutionContext;
import net.sunrise.framework.model.SearchParameter;
import net.sunrise.helper.GlobalDataServiceHelper;
import net.sunrise.manager.ConfigurationServicesHelper;
import net.sunrise.manager.catalog.CatalogRegistryServiceHelper;
import net.sunrise.model.Bucket;
import net.sunrise.osx.OfficeSuiteServiceProvider;
import net.sunrise.service.api.contact.ContactService;
import net.sunrise.service.api.invt.ItemService;
import net.sunrise.service.api.invt.LanguageService;

/**
 * @author bqduc
 */
@Slf4j
@Controller
@RequestMapping(ControllerConstants.REQUEST_URI_DATA_ADMIN)
public class DataAdminController extends BaseController {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6037064426276941676L;

	@Inject
	private GlobalDataLoadingManager globalDataLoadingManager;

	@Inject
	private ConfigurationServicesHelper configurationServicesHelper;

	@Inject
	private CatalogRegistryServiceHelper catalogRegistryServiceHelper;

	@Inject
	private ItemService itemService;

	@Inject
	private LanguageService languageService;

	@Inject
	private InventoryDataDeployer inventoryDataDeployer;

	@Inject
	private ContactService contactService;

	@Inject
	private TaskExecutor asyncExecutor;

	@Inject
	private ApplicationContext applicationContext;

	@Inject
	private GlobalDataServiceHelper globalDataServiceHelper;

	@Inject
	private GlobalDataRepositoryManager globalDataRepositoryManager;

	private static Map<String, Object> executorMap = ListUtility.createMap();

	@Override
	protected String performSearch(SearchParameter params) {
		// TODO Auto-generated method stub
		return null;
	}

	@GetMapping({ "", "/index" })
	public String viewIndex(Model model, HttpServletRequest request) {
		log.info("Rendering data administration index page...");
		globalDataRepositoryManager.init();
		return "pages/system/dataAdminBrowse";
	}

	@GetMapping("/importEmployees")
	public String onImportEmployees(Model model, HttpServletRequest request) {
		log.info("On import employees .........");
		try {
			List<Employee> employees = configurationServicesHelper.importEmployees();
			System.out.println("Imported employees: " + employees.size());
		} catch (Exception e) {
			log.error(CommonUtility.getStackTrace(e));
		}
		return "pages/system/dataAdminBrowse";
	}

	@GetMapping("/importCatalogues")
	public String onImportCatalogues(Model model, HttpServletRequest request) {
		log.info("On import catalogues .........");
		String resourceFileName = "F:/Downloads/DANH-MỤC-HÀNG-HÓA-XUẤT-KHẨU-NHẬP-KHẨU-VIỆT-NAM-2015.xlsx";
		String sheet = "Danh muc-";
		Map<Object, Object> configParams = null;
		try {
			configParams = ListUtility.createMap();
			configParams.put(sheet + Bucket.PARAM_STARTED_ROW_INDEX, Integer.valueOf(2));
			configParams.put(Bucket.PARAM_DATA_SHEETS, new String[] { sheet });
			configParams.put(Bucket.PARAM_INPUT_STREAM, new FileInputStream(resourceFileName));
			configParams.put(Bucket.PARAM_WORK_DATA_SHEET, sheet);

			catalogRegistryServiceHelper.registerCataloguesFromExcel(configParams);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return "pages/system/dataAdminBrowse";
	}

	@GetMapping("/deployData")
	public String deployData(Model model, HttpServletRequest request) {
		log.info("Elog.ploy data.");
		try {
			doListLocalizedItems();
			inventoryDataDeployer.asyncDeployConstructionData(null);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return "pages/system/dataAdminBrowse";
	}

	@GetMapping("/loadExternalData")
	public String loadExternalData(Model model, HttpServletRequest request) {
		log.info("Elog.ploy data.");
		Bucket bucket = null;
		try {
			// bucket = super.doLoadExternalData("/config/data/salesman.xlsx", new String[]{"contact-data"}, new int[]{2});
			bucket = super.doLoadExternalData("/config/data/contact-data.xlsx", new String[] { "contact-data" },
					new int[] { 2 });
			displayData(bucket);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return "pages/system/dataAdminBrowse";
	}

	@GetMapping("/deployContactData")
	public String deployContactData(Model model, HttpServletRequest request) {
		log.info("Elog.ploy data.");
		Bucket bucket = null;
		/*
		if (true==isDeploying)
			return "pages/system/dataAdminBrowse";
		*/

		try {
			ExecutionContext executionContext = ExecutionContext.builder().build();
			executionContext.putContextData("sourceStream", "/config/liquibase/data/online_resources.xlsx");
			executionContext.putContextData("sheetIds", new String[] { "contact-data" });
			executionContext.putContextData("sheetId", "contact-data");
			executionContext.putContextData("startIndexes", new int[] { 2 });
			executionContext.putContextData("contactService", contactService);
			executionContext.putContextData("globalDataServiceHelper", globalDataServiceHelper);
			ImportContactsThread importContactsThread = applicationContext.getBean(ImportContactsThread.class,
					executionContext);
			asyncExecutor.execute(importContactsThread);

			// bucket = super.doLoadExternalData("/config/data/salesman.xlsx", new String[]{"contact-data"}, new int[]{2});
			/*
			long started = System.currentTimeMillis();
			bucket = super.doLoadExternalData("/config/liquibase/data/online_resources.xlsx", new String[]{"contact-data"}, new int[]{2});
			long duration = System.currentTimeMillis()-started;
			System.out.println("Duration: " + duration);
			contactService.deployContacts((List<List<String>>)bucket.get("contact-data"));
			*/
			// displayData(bucket);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return "pages/system/dataAdminBrowse";
	}

	@GetMapping("/loadingExtendedData")
	public String loadingExtendedData(Model model, HttpServletRequest request) {
		log.info("Enter DataAdminController::loadingExtendedData.");
		loadingAsyncData();
		log.info("Leave DataAdminController::loadingExtendedData.");
		return "pages/system/dataAdminBrowse";
	}

	@GetMapping("/stopLoadingExtendedData")
	public String stopLoadingExtendedData(Model model, HttpServletRequest request) {
		log.info("Enter DataAdminController::loadingExtendedData.");
		stopLoadingAsyncData();
		log.info("Leave DataAdminController::loadingExtendedData.");
		return "pages/system/dataAdminBrowse";
	}

	protected void loadingAsyncData() {
		ExecutionContext executionContext = null;
		Asynchronous asyncExtendedDataLoader = null;
		Asynchronous asyncDataPackageLoader = null;
		try {
			executionContext = ExecutionContext.builder().build().context("AA", "xx").context("DD", "ss");

			asyncExtendedDataLoader = applicationContext.getBean(AsyncExtendedDataLoader.class, executionContext);
			this.asyncExecutor.execute(asyncExtendedDataLoader);

			asyncDataPackageLoader = applicationContext.getBean(AsyncDataPackageLoader.class, executionContext);
			this.asyncExecutor.execute(asyncDataPackageLoader);

			executorMap.put("asyncExtendedDataLoader", asyncExtendedDataLoader);
			executorMap.put("asyncDataPackageLoader", asyncDataPackageLoader);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	protected void stopLoadingAsyncData() {
		Asynchronous asyncExtendedDataLoader = null;
		Asynchronous asyncDataPackageLoader = null;
		try {
			asyncExtendedDataLoader = (Asynchronous)executorMap.get("asyncExtendedDataLoader");
			if (null != asyncExtendedDataLoader && !asyncExtendedDataLoader.isInterrupted()) {
				asyncExtendedDataLoader.setRunning(false);
			}

			asyncDataPackageLoader = (Asynchronous)executorMap.get("asyncDataPackageLoader");
			if (null != asyncDataPackageLoader && !asyncDataPackageLoader.isInterrupted()) {
				asyncDataPackageLoader.setRunning(false);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	protected void doListLocalizedItems() {
		Language language = languageService.getByCode("en");
		List<LocalizedItem> localizedItems = itemService.getLocalizedItems("CASE_STATUS", language);
		System.out.println(localizedItems);
	}

	private void displayData(Bucket bucket) {
		List<List<String>> dataEntries = null;
		Map<Object, Object> dataMap = bucket.getBucketData();
		for (Object key : dataMap.keySet()) {
			dataEntries = (List<List<String>>) dataMap.get(key);
			System.out.println(dataEntries);
		}

		System.out.println(bucket);
	}
}
