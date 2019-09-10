package net.sunrise.service.api.admin.quartz;

import java.util.Optional;

import net.sunrise.domain.entity.schedule.JobCategory;
import net.sunrise.exceptions.ObjectNotFoundException;
import net.sunrise.framework.service.GenericService;

public interface JobCategoryService extends GenericService<JobCategory, Long> {

	/**
	 * Get one JobCategory with the provided name.
	 * 
	 * @param name
	 *            The JobCategory name
	 * @return The JobCategory
	 * @throws ObjectNotFoundException
	 *             If no such JobCategory exists.
	 */
	Optional<JobCategory> getOne(String name) throws ObjectNotFoundException;
}
