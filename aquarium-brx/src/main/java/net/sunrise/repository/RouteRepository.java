/**
 * 
 */
package net.sunrise.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.brilliance.framework.repository.BaseRepository;
import net.sunrise.domain.entity.Route;

/**
 * @author ducbq
 *
 */
@Repository
public interface RouteRepository extends BaseRepository<Route, Long> {
	Optional<Route> findByName(String name);
	@Query("SELECT entity FROM #{#entityName} entity WHERE ("
			+ " LOWER(entity.name) like LOWER(CONCAT('%',:keyword,'%'))"
			+ " or LOWER(entity.description) like LOWER(CONCAT('%',:keyword,'%'))"
			+ ")"
	)
	Page<Route> search(@Param("keyword") String keyword, Pageable pageable);
}
