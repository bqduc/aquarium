package net.sunrise.service.api.crx;

import org.springframework.data.domain.Page;

import net.sunrise.domain.entity.crx.Campaign;
import net.sunrise.exceptions.ObjectNotFoundException;
import net.sunrise.framework.model.SearchParameter;
import net.sunrise.framework.service.GenericService;

public interface CampaignService extends GenericService<Campaign, Long> {

	/**
	 * Get one Order with the provided code.
	 * 
	 * @param name
	 *            The Order name
	 * @return The Order
	 * @throws ObjectNotFoundException
	 *             If no such Order exists.
	 */
	Campaign getOne(String name) throws ObjectNotFoundException;

	/**
	 * Get one Enterprises with the provided search parameters.
	 * 
	 * @param searchParameter
	 *            The search parameter
	 * @return The pageable Enterprises
	 */
	Page<Campaign> getObjects(SearchParameter searchParameter);
}
