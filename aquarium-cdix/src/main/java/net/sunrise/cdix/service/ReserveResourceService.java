package net.sunrise.cdix.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import net.sunrise.cdix.entity.ReserveResource;
import net.sunrise.exceptions.ObjectNotFoundException;
import net.sunrise.framework.model.SearchParameter;
import net.sunrise.framework.service.GenericService;

public interface ReserveResourceService extends GenericService<ReserveResource, Long> {

	/**
	 * Get one PersistenceFile with the provided code.
	 * 
	 * @param name
	 *            The PersistenceFile name
	 * @return The PersistenceFile
	 * @throws ObjectNotFoundException
	 *             If no such PersistenceFile exists.
	 */
	Optional<ReserveResource> getByName(String name) throws ObjectNotFoundException;

	/**
	 * Get one PersistenceFiles with the provided search parameters.
	 * 
	 * @param searchParameter
	 *            The search parameter
	 * @return The pageable PersistenceFiles
	 */
	Page<ReserveResource> getObjects(SearchParameter searchParameter);
}
