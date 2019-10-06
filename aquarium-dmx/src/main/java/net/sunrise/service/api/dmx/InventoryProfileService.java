package net.sunrise.service.api.dmx;

import org.springframework.data.domain.Page;

import net.sunrise.domain.entity.dmx.InventoryProfile;
import net.sunrise.exceptions.ObjectNotFoundException;
import net.sunrise.framework.model.SearchParameter;
import net.sunrise.framework.service.GenericService;

public interface InventoryProfileService extends GenericService<InventoryProfile, Long> {

	/**
	 * Get one InventoryProfile with the provided code.
	 * 
	 * @param code
	 *            The InventoryProfile code
	 * @return The InventoryProfile
	 * @throws ObjectNotFoundException
	 *             If no such InventoryProfile exists.
	 */
	InventoryProfile getByCode(String code) throws ObjectNotFoundException;

	/**
	 * Get one InventoryProfile with the provided code.
	 * 
	 * @param isbn
	 *            The InventoryProfile isbn
	 * @return The InventoryProfile
	 * @throws ObjectNotFoundException
	 *             If no such InventoryProfile exists.
	 */
	InventoryProfile getOne(String isbn) throws ObjectNotFoundException;

	/**
	 * Get one Enterprises with the provided search parameters.
	 * 
	 * @param searchParameter
	 *            The search parameter
	 * @return The pageable Inventory profiles
	 */
	Page<InventoryProfile> getObjects(SearchParameter searchParameter);
}
