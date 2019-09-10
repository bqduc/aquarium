package net.sunrise.service.general;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.sunrise.domain.entity.general.City;
import net.sunrise.exceptions.ObjectNotFoundException;
import net.sunrise.framework.repository.BaseRepository;
import net.sunrise.framework.service.GenericServiceImpl;
import net.sunrise.repository.general.CityRepository;
import net.sunrise.service.api.general.CityService;

@Service
public class CityServiceImpl extends GenericServiceImpl<City, Long> implements CityService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4761950125978671850L;
	@Inject 
	private CityRepository repository;
	
	protected BaseRepository<City, Long> getRepository() {
		return this.repository;
	}

	@Override
	public City getOne(String name) throws ObjectNotFoundException {
		return (City)super.getOptionalObject(repository.findByName(name));
	}
}
