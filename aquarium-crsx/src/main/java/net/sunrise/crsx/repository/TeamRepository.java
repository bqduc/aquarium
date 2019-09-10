/**
 * 
 */
package net.sunrise.crsx.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.sunrise.domain.entity.crx.Team;
import net.sunrise.framework.repository.BaseRepository;

/**
 * @author bqduc
 *
 */
@Repository
public interface TeamRepository extends BaseRepository<Team, Long> {
	Optional<Team> findByName(String name);
	@Query("SELECT entity FROM #{#entityName} entity WHERE ("
			+ " LOWER(entity.name) like LOWER(CONCAT('%',:keyword,'%'))"
			+ " or LOWER(entity.description) like LOWER(CONCAT('%',:keyword,'%'))"
			+ ")"
	)
	Page<Team> search(@Param("keyword") String keyword, Pageable pageable);
}
