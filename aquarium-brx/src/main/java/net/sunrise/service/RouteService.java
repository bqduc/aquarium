package net.sunrise.service;

import org.springframework.data.domain.Page;

import net.sunrise.domain.entity.Route;
import net.sunrise.framework.model.SearchParameter;
import net.sunrise.framework.service.GenericService;

public interface RouteService extends GenericService<Route, Long> {
	/**
	 * Get one Regions with the provided search parameters.
	 * 
	 * @param searchParameter
	 *            The search parameter
	 * @return The pageable Regions
	 */
	Page<Route> getObjects(SearchParameter searchParameter);
}
