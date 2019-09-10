/**
 * 
 */
package net.sunrise.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sunrise.controller.base.BaseController;
import net.sunrise.domain.SelectItem;
import net.sunrise.domain.entity.general.CatalogueSubtype;
import net.sunrise.framework.logging.LogService;
import net.sunrise.framework.model.SearchParameter;
import net.sunrise.manager.catalog.CategoryManager;
import net.sunrise.manager.catalog.impl.DepartmentManager;
import net.sunrise.service.api.inventory.CatalogueSubtypeService;

/**
 * @author bqduc
 *
 */
@Controller
public class SuggestionController extends BaseController {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8903910871628867413L;
	protected static final String PAGE_POSTFIX_BROWSE = "Browse";
	protected static final String PAGE_POSTFIX_SHOW = "Show";
	protected static final String PAGE_POSTFIX_EDIT = "Edit";

	protected static final String REDIRECT =	"redirect:/";

	@Inject 
	protected LogService log;

	@Inject
	protected DepartmentManager departmentServiceManager;

	@Inject
	protected CategoryManager categoryServiceManager;

	@Autowired
	protected MessageSource messageSource;

	@Inject
	private CatalogueSubtypeService catalogueSubtypeService;

	/*@PostMapping(value = "/search/query", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String search(@RequestBody(required = false) SearchParameter params, Model model, Pageable pageable) {
		log.info("Enter search/query");
		return performSearch(
				params
				.setPageable(pageable)
				.setModel(model));
	}

	@RequestMapping(path="/searchex/query", method=RequestMethod.GET)
	public List query(@RequestBody(required = false) SearchParameter params, Model model, Pageable pageable) {
		log.info("Enter search/query");
		return performSearchObjects(
				params
				.setPageable(pageable)
				.setModel(model));
	}

	@RequestMapping(value = "/searchAction", method = RequestMethod.POST)
	public @ResponseBody String onSearch(@RequestBody(required = false) SearchParameter params, Model model, Pageable pageable){
		Gson gson = new GsonBuilder().serializeNulls().create();
		List searchResults = performSearchObjects(
				params
				.setPageable(pageable)
				.setModel(model));
		String jsonBizObjects = gson.toJson(searchResults);
		//String contentJson = "{\"iTotalDisplayRecords\":" + searchResults.size() + "," + "\"data\":" + jsonBizObjects + "}";
		String contentJson = jsonBizObjects;
		return contentJson;
	}

	@RequestMapping(value = "/suggest", method = RequestMethod.GET)
	public @ResponseBody List<SelectItem> suggest(@RequestParam("term") String keyword, HttpServletRequest request) {
		log.info("Enter keyword: " + keyword);
		List<SelectItem> suggestedItems = suggestItems(keyword);
		if (CommonUtility.isNull(suggestedItems)){
			suggestedItems = new ArrayList<>();
		}
		return suggestedItems;
	}*/

	@RequestMapping(value = "/suggestCatalogueSubtype", method = RequestMethod.GET)
	public @ResponseBody List<SelectItem> suggestCatalogueSubtypes(@RequestParam("term") String keyword, HttpServletRequest request) {
		log.info("Enter keyword for suggest catalogue subtype: " + keyword);
		Page<CatalogueSubtype> fetchedSuggestObjects = catalogueSubtypeService.searchObjects(keyword, null);
		List<SelectItem> suggestedItems = buildCategorySelectedItems(fetchedSuggestObjects.getContent());
		return suggestedItems;
	}

	/*@RequestMapping(value = "/suggestDepartment", method = RequestMethod.GET)
	public @ResponseBody List<SelectItem> suggestDepartment(@RequestParam("term") String keyword, HttpServletRequest request) {
		log.info("Enter keyword for category: " + keyword);
		Page<Department> suggestedCategories = departmentServiceManager.search(buildSearchParameters(null, null, keyword));
		return buildCategorySelectedItems(suggestedCategories.getContent());
	}

	@RequestMapping(value = "/suggestCategory", method = RequestMethod.GET)
	public @ResponseBody List<SelectItem> suggestCategory(@RequestParam("term") String keyword, HttpServletRequest request) {
		log.info("Enter keyword for category: " + keyword);
		Page<Category> suggestedCategories = categoryServiceManager.search(buildSearchParameters(null, null, keyword));
		return buildCategorySelectedItems(suggestedCategories.getContent());
	}

	@RequestMapping(value = "/suggestObjects", method = RequestMethod.GET)
	public @ResponseBody List<SelectItem> suggestObject(@RequestParam("keyword") String keyword, HttpServletRequest request) {
		log.info("Enter keyword: " + keyword);
		List<SelectItem> suggestedItems = suggestItems(keyword);
		if (CommonUtility.isNull(suggestedItems)){
			suggestedItems = new ArrayList<>();
		}
		return suggestedItems;
	}*/

	@Override
	protected String performSearch(SearchParameter params) {
		// TODO Auto-generated method stub
		return null;
	}
}