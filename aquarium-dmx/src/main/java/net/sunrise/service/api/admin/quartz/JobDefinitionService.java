package net.sunrise.service.api.admin.quartz;

import org.springframework.data.domain.Page;

import net.sunrise.domain.entity.schedule.JobDefinition;
import net.sunrise.exceptions.ObjectNotFoundException;
import net.sunrise.framework.model.SearchParameter;
import net.sunrise.framework.service.GenericService;

public interface JobDefinitionService extends GenericService<JobDefinition, Long> {

	/**
	 * Get one JobDefinition with the provided name.
	 * 
	 * @param name
	 *            The JobDefinition name
	 * @return The JobDefinition
	 * @throws ObjectNotFoundException
	 *             If no such JobDefinition exists.
	 */
	JobDefinition getOne(String name) throws ObjectNotFoundException;
}
