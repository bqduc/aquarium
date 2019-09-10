package net.sunrise.service;

import org.springframework.data.domain.Page;

import net.sunrise.domain.entity.Booking;
import net.sunrise.framework.model.SearchParameter;
import net.sunrise.framework.service.GenericService;

public interface BookingService extends GenericService<Booking, Long> {
	/**
	 * Get one Regions with the provided search parameters.
	 * 
	 * @param searchParameter
	 *            The search parameter
	 * @return The pageable Regions
	 */
	Page<Booking> getObjects(SearchParameter searchParameter);
}
