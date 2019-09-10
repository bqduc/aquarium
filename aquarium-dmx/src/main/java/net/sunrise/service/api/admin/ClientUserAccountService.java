package net.sunrise.service.api.admin;

import org.springframework.data.domain.Page;

import net.sunrise.domain.entity.admin.ClientUserAccount;
import net.sunrise.exceptions.ObjectNotFoundException;
import net.sunrise.framework.model.SearchParameter;
import net.sunrise.framework.service.GenericService;

public interface ClientUserAccountService extends GenericService<ClientUserAccount, Long> {

	/**
	 * Get one ClientUserAccount with the provided code.
	 * 
	 * @param code
	 *            The ClientUserAccount code
	 * @return The ClientUserAccount
	 * @throws ObjectNotFoundException
	 *             If no such ClientUserAccount exists.
	 */
	ClientUserAccount getOne(String code) throws ObjectNotFoundException;

	/**
	 * Get one ClientUserAccounts with the provided search parameters.
	 * 
	 * @param searchParameter
	 *            The search parameter
	 * @return The pageable ClientUserAccounts
	 */
	Page<ClientUserAccount> getObjects(SearchParameter searchParameter);
}
