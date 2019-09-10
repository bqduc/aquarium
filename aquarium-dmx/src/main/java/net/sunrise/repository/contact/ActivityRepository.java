package net.sunrise.repository.contact;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import net.sunrise.domain.entity.general.Activity;
import net.sunrise.framework.repository.BaseRepository;

@Repository
public interface ActivityRepository extends BaseRepository<Activity, Long>{
	Optional<Activity> findOneById(Long id);

	Activity findByName(String name);
}
