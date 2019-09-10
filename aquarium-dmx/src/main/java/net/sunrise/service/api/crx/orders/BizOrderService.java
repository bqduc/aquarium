package net.sunrise.service.api.crx.orders;

import org.springframework.data.domain.Page;

import net.sunrise.domain.entity.crx.BizOrder;
import net.sunrise.exceptions.ObjectNotFoundException;
import net.sunrise.framework.model.SearchParameter;
import net.sunrise.framework.service.GenericService;

public interface BizOrderService extends GenericService<BizOrder, Long> {

	/**
	 * Get one Order with the provided code.
	 * 
	 * @param code
	 *            The Order code
	 * @return The Order
	 * @throws ObjectNotFoundException
	 *             If no such Order exists.
	 */
	BizOrder getOne(String code) throws ObjectNotFoundException;

	/**
	 * Get one Enterprises with the provided search parameters.
	 * 
	 * @param searchParameter
	 *            The search parameter
	 * @return The pageable Enterprises
	 */
	Page<BizOrder> getObjects(SearchParameter searchParameter);
}
