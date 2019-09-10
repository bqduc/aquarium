package net.sunrise.service.smt;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.sunrise.domain.entity.smt.Request;
import net.sunrise.exceptions.ObjectNotFoundException;
import net.sunrise.framework.model.SearchParameter;
import net.sunrise.framework.repository.BaseRepository;
import net.sunrise.framework.service.GenericServiceImpl;
import net.sunrise.repository.smt.RequestRepository;
import net.sunrise.repository.specification.smt.RequestRepoSpec;
import net.sunrise.service.api.smt.RequestService;

@Service
public class RequestServiceImpl extends GenericServiceImpl<Request, Long> implements RequestService{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2369656282587595766L;

	@Inject 
	private RequestRepository repository;
	
	protected BaseRepository<Request, Long> getRepository() {
		return this.repository;
	}

	@Override
	public Request getOne(String code) throws ObjectNotFoundException {
		return (Request)super.getOptionalObject(repository.findByNumber(code));
	}

	@Override
	protected Page<Request> performSearch(String keyword, Pageable pageable) {
		return repository.search(keyword, pageable);
	}

	@Override
	public Page<Request> getObjects(SearchParameter searchParameter) {
		Page<Request> pagedProducts = this.repository.findAll(RequestRepoSpec.buildSpecification(searchParameter), searchParameter.getPageable());
		//Perform additional operations here
		return pagedProducts;
	}
}
