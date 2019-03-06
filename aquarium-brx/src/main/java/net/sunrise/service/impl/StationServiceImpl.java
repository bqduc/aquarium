package net.sunrise.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import net.brilliance.framework.repository.BaseRepository;
import net.brilliance.framework.service.GenericServiceImpl;
import net.sunrise.domain.entity.Station;
import net.sunrise.repository.StationRepository;
import net.sunrise.service.StationService;

@Service
public class StationServiceImpl extends GenericServiceImpl<Station, Long> implements StationService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4800075642286878168L;

	@Inject 
	private StationRepository repository;
	
	protected BaseRepository<Station, Long> getRepository() {
		return this.repository;
	}
}
