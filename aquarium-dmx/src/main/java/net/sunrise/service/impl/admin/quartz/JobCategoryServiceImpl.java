package net.sunrise.service.impl.admin.quartz;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.sunrise.domain.entity.schedule.JobCategory;
import net.sunrise.exceptions.ObjectNotFoundException;
import net.sunrise.framework.repository.BaseRepository;
import net.sunrise.framework.service.GenericServiceImpl;
import net.sunrise.framework.specifications.predicator.BrilliancePredicator;
import net.sunrise.repository.schedule.JobCategoryRepository;
import net.sunrise.repository.specification.quartz.JobCategoryPredicator;
import net.sunrise.service.api.admin.quartz.JobCategoryService;

@Service
public class JobCategoryServiceImpl extends GenericServiceImpl<JobCategory, Long> implements JobCategoryService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4779262410553662634L;

	@Inject 
	private JobCategoryRepository repository;
	
	protected BaseRepository<JobCategory, Long> getRepository() {
		return this.repository;
	}

	@Override
	protected BrilliancePredicator<JobCategory> getRepositoryPredicator() {
		return JobCategoryPredicator.builder().build();
	}

	@Override
	public Optional<JobCategory> getOne(String name) throws ObjectNotFoundException {
		return repository.findByName(name);
	}

	@Override
	protected Page<JobCategory> performSearch(String keyword, Pageable pageable) {
		return repository.search(keyword, pageable);
	}
}
