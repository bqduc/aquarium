package net.sunrise.service.api.system;

import org.springframework.data.domain.Page;

import net.sunrise.domain.entity.system.SystemSequence;
import net.sunrise.exceptions.ObjectNotFoundException;
import net.sunrise.framework.model.SearchParameter;
import net.sunrise.framework.service.GenericService;

public interface SystemSequenceService extends GenericService<SystemSequence, Long> {

	/**
	 * Get one CatalogSubtype with the provided code.
	 * 
	 * @param code
	 *            The SystemSequence name
	 * @return The SystemSequence
	 * @throws ObjectNotFoundException
	 *             If no such SystemSequence exists.
	 */
	SystemSequence getOne(String name) throws ObjectNotFoundException;

	/**
	 * Get one SystemSequences with the provided search parameters.
	 * 
	 * @param searchParameter
	 *            The search parameter
	 * @return The pageable SystemSequences
	 */
	Page<SystemSequence> getObjects(SearchParameter searchParameter);
}
