package net.sunrise.service;

import org.springframework.data.domain.Page;

import net.sunrise.domain.entity.Schedule;
import net.sunrise.framework.model.SearchParameter;
import net.sunrise.framework.service.GenericService;

public interface ScheduleService extends GenericService<Schedule, Long> {
	/**
	 * Get one Regions with the provided search parameters.
	 * 
	 * @param searchParameter
	 *            The search parameter
	 * @return The pageable Regions
	 */
	Page<Schedule> getObjects(SearchParameter searchParameter);
}
