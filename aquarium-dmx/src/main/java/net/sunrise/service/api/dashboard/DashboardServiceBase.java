package net.sunrise.service.api.dashboard;

import java.io.Serializable;

import org.springframework.data.domain.Page;

import net.sunrise.exceptions.ObjectNotFoundException;
import net.sunrise.framework.entity.BizObjectBase;
import net.sunrise.framework.model.SearchParameter;
import net.sunrise.framework.service.GenericService;

public interface DashboardServiceBase<T extends BizObjectBase, K extends Serializable> extends GenericService<T, K> {

	/**
	 * Get one T with the provided code.
	 * 
	 * @param name
	 *            The T name
	 * @return The T
	 * @throws ObjectNotFoundException
	 *             If no such T exists.
	 */
	T getOne(String name) throws ObjectNotFoundException;

	/**
	 * Get one Ts with the provided search parameters.
	 * 
	 * @param searchParameter
	 *            The search parameter
	 * @return The pageable Ts
	 */
	Page<T> getObjects(SearchParameter searchParameter);
}
