package net.sunrise.cdix.controller;

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
import net.brilliance.common.CommonUtility;
import net.brilliance.framework.model.SearchParameter;
import net.sunrise.cdix.entity.PersistenceResource;
import net.sunrise.cdix.service.PersistenceResourceService;
import net.sunrise.constants.ControllerConstants;
import net.sunrise.controller.base.BaseRestController;

@Slf4j
@RequestMapping(ControllerConstants.REST_API + ControllerConstants.URI_PERSISTENCE_RESOURCE)
@RestController
public class PersistenceResourceRestController extends BaseRestController<PersistenceResource>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4207876465901017247L;
	private final static String CACHE_OBJECTS_KEY = "cache.persistenceResources";
	@Inject 
	private PersistenceResourceService businessService;

	@SuppressWarnings("unchecked")
	@RequestMapping(path = "/list", method = RequestMethod.GET)
	public List<PersistenceResource> onListBusinessObjects(HttpServletRequest request, HttpServletResponse response, Model model) {
		log.info("RestController::Come to persistence resource data listing ...>>>>>>");
		List<PersistenceResource> results = null;
		Object cachedValue = null;//super.cacheGet(CACHE_OBJECTS_KEY);
		PageRequest pageRequest = null;
		SearchParameter searchParameter = null;
		Page<PersistenceResource> objects = null;
		if (CommonUtility.isNotEmpty(cachedValue)){
			results = (List<PersistenceResource>)cachedValue;
		} else {
			pageRequest = PageRequest.of(0, 500, Sort.Direction.ASC, "id");
			searchParameter = SearchParameter.builder()
					.pageable(pageRequest)
					.build();
			objects = businessService.getObjects(searchParameter);
			results = objects.getContent();
			//super.cachePut(CACHE_OBJECTS_KEY, results);
		}
		log.info("PersistenceResource data is loaded. >>>>>>");

		return results;
	}

	@Override
	protected void doUpdateBusinessObject(PersistenceResource updatedClientObject) {
		super.doUpdateBusinessObject(updatedClientObject);
	}

	@Override
	protected Page<PersistenceResource> doFetchBusinessObjects(Integer page, Integer size) {
		Page<PersistenceResource> fetchedBusinessObjects = businessService.getObjects(page, size);
		log.info("PersistenceResource Rest::FetchBusinessObjects: " + fetchedBusinessObjects.getTotalElements());
		return fetchedBusinessObjects;
	}

	@Override
	protected PersistenceResource doFetchBusinessObject(Long id) {
		PersistenceResource fetchedBusinessObject = businessService.getObject(id);
		log.info("PersistenceResource Rest::FetchBusinessObject: " + fetchedBusinessObject.getName());
		return fetchedBusinessObject;
	}

	@Override
	protected void doDeleteBusinessObject(Long id) {
		log.info("PersistenceResource Rest::DeleteBusinessObject: " + id);
		businessService.remove(id);
		log.info("PersistenceResource Rest::DeleteBusinessObject is done");
	}

	@Override
	protected void doCreateBusinessObject(PersistenceResource businessObject) {
		log.info("PersistenceResource Rest::CreateBusinessObject: " + businessObject.getName());
		businessService.saveOrUpdate((PersistenceResource)businessObject);
		log.info("PersistenceResource Rest::CreateBusinessObject is done");
	}
}
