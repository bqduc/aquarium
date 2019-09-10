package net.sunrise.service.admin;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.sunrise.domain.entity.admin.ClientUserAccount;
import net.sunrise.domain.entity.admin.UserAccount;
import net.sunrise.exceptions.ObjectNotFoundException;
import net.sunrise.framework.model.SearchParameter;
import net.sunrise.framework.repository.BaseRepository;
import net.sunrise.framework.service.GenericServiceImpl;
import net.sunrise.repository.admin.ClientUserAccountRepository;
import net.sunrise.repository.admin.UserAccountRepository;
import net.sunrise.repository.specification.admin.ClientUserAccountSpecification;
import net.sunrise.service.api.admin.ClientUserAccountService;

@Service
public class ClientUserAccountServiceImpl extends GenericServiceImpl<ClientUserAccount, Long> implements ClientUserAccountService{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1601499409891229800L;
	@Inject 
	private UserAccountRepository userAccountRepository;

	@Inject 
	private ClientUserAccountRepository repository;
	
	protected BaseRepository<ClientUserAccount, Long> getRepository() {
		return this.repository;
	}

	@Override
	public ClientUserAccount getOne(String ssoId) throws ObjectNotFoundException {
		UserAccount userAccount = userAccountRepository.findBySsoId(ssoId);
		return (ClientUserAccount)super.getOptionalObject(repository.findByUserAccount(userAccount));
	}

	@Override
	protected Page<ClientUserAccount> performSearch(String keyword, Pageable pageable) {
		return repository.search(keyword, pageable);
	}

	@Override
	public Page<ClientUserAccount> getObjects(SearchParameter searchParameter) {
		Page<ClientUserAccount> pagedProducts = this.repository.findAll(ClientUserAccountSpecification.buildSpecification(searchParameter), searchParameter.getPageable());
		//Perform additional operations here
		return pagedProducts;
	}
}
