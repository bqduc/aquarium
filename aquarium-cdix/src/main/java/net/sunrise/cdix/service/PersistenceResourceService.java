package net.sunrise.cdix.service;

import org.springframework.data.domain.Page;

import net.brilliance.exceptions.ObjectNotFoundException;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.service.GenericService;
import net.sunrise.cdix.entity.PersistenceResource;

public interface PersistenceResourceService extends GenericService<PersistenceResource, Long> {

	/**
	 * Get one PersistenceFile with the provided code.
	 * 
	 * @param name
	 *            The PersistenceFile name
	 * @return The PersistenceFile
	 * @throws ObjectNotFoundException
	 *             If no such PersistenceFile exists.
	 */
	PersistenceResource getByName(String name) throws ObjectNotFoundException;

	/**
	 * Get one PersistenceFiles with the provided search parameters.
	 * 
	 * @param searchParameter
	 *            The search parameter
	 * @return The pageable PersistenceFiles
	 */
	Page<PersistenceResource> getObjects(SearchParameter searchParameter);
}
