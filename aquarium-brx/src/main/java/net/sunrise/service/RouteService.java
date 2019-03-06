package net.sunrise.service;

import org.springframework.data.domain.Page;

import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.service.GenericService;
import net.sunrise.domain.entity.Route;

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
