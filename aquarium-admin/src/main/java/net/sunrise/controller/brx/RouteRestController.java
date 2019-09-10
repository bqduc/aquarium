package net.sunrise.controller.brx;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import net.sunrise.common.CommonUtility;
import net.sunrise.controller.ControllerConstants;
import net.sunrise.controller.base.BaseRestController;
import net.sunrise.domain.entity.Route;
import net.sunrise.framework.model.SearchParameter;
import net.sunrise.service.RouteService;

@Slf4j
@RequestMapping(ControllerConstants.REST_API + ControllerConstants.URI_BRX_ROUTE)
@RestController
public class RouteRestController extends BaseRestController<Route>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3044051313217509954L;
	private final static String CACHE_OBJECTS_KEY = "cache.bizPackages";
	@Inject 
	private RouteService businessService;

	
	@RequestMapping(path = "/list", method = RequestMethod.GET)
	public List<Route> onListRoutes(HttpServletRequest request, HttpServletResponse response, Model model) {
		log.info("RestController::Come to enterprise data listing ...>>>>>>");
		List<Route> results = null;
		Object cachedValue = super.cacheGet(CACHE_OBJECTS_KEY);
		PageRequest pageRequest = null;
		SearchParameter searchParameter = null;
		Page<Route> objects = null;
		if (CommonUtility.isNotEmpty(cachedValue)){
			results = (List<Route>)cachedValue;
		} else {
			pageRequest = PageRequest.of(0, 500, Sort.Direction.ASC, "id");
			searchParameter = SearchParameter.builder()
					.pageable(pageRequest)
					.build();
			objects = businessService.getObjects(searchParameter);
			results = objects.getContent();
			super.cachePut(CACHE_OBJECTS_KEY, results);
		}
		log.info("Route data is loaded. >>>>>>");

		return results;
	}

	@Override
	protected void doUpdateBusinessObject(Route updatedClientObject) {
		super.doUpdateBusinessObject(updatedClientObject);
	}

	@Override
	protected Page<Route> doFetchBusinessObjects(Integer page, Integer size) {
		Page<Route> fetchedBusinessObjects = businessService.getObjects(page, size);
		log.info("Route Rest::FetchBusinessObjects: " + fetchedBusinessObjects.getTotalElements());
		return fetchedBusinessObjects;
	}

	@Override
	protected Route doFetchBusinessObject(Long id) {
		Route fetchedBusinessObject = businessService.getObject(id);
		log.info("Route Rest::FetchBusinessObject: " + fetchedBusinessObject.getName());
		return fetchedBusinessObject;
	}

	@Override
	protected void doDeleteBusinessObject(Long id) {
		log.info("Route Rest::DeleteBusinessObject: " + id);
		businessService.remove(id);
		log.info("Route Rest::DeleteBusinessObject is done");
	}

	@Override
	protected void doCreateBusinessObject(Route businessObject) {
		log.info("Route Rest::CreateBusinessObject: " + businessObject.getName());
		businessService.saveOrUpdate((Route)businessObject);
		log.info("Route Rest::CreateBusinessObject is done");
	}
}
