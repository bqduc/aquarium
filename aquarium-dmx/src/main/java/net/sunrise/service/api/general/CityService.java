package net.sunrise.service.api.general;

import net.sunrise.domain.entity.general.City;
import net.sunrise.exceptions.ObjectNotFoundException;
import net.sunrise.framework.service.GenericService;

public interface CityService extends GenericService<City, Long> {

	/**
	 * Get one City with the provided name.
	 * 
	 * @param name
	 *            The City name
	 * @return The City
	 * @throws ObjectNotFoundException
	 *             If no such City exists.
	 */
	City getOne(String name) throws ObjectNotFoundException;
}
