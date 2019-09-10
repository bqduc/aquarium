package net.sunrise.crsx.service;

import org.springframework.data.domain.Page;

import net.sunrise.crsx.domain.entity.Region;
import net.sunrise.exceptions.ObjectNotFoundException;
import net.sunrise.framework.model.SearchParameter;
import net.sunrise.framework.service.GenericService;

public interface RegionService extends GenericService<Region, Long> {

	/**
	 * Get one Region with the provided name.
	 * 
	 * @param name
	 *            The Region name
	 * @return The Region
	 * @throws ObjectNotFoundException
	 *             If no such Region exists.
	 */
	Region getOne(String name) throws ObjectNotFoundException;

	/**
	 * Get one Regions with the provided search parameters.
	 * 
	 * @param searchParameter
	 *            The search parameter
	 * @return The pageable Regions
	 */
	Page<Region> getObjects(SearchParameter searchParameter);
}
