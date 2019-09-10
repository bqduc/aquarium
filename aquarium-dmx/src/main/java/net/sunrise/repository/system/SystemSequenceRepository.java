/**
 * 
 */
package net.sunrise.repository.system;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.sunrise.domain.entity.system.SystemSequence;
import net.sunrise.framework.repository.BaseRepository;

/**
 * @author bqduc
 *
 */
@Repository
public interface SystemSequenceRepository extends BaseRepository<SystemSequence, Long> {
	Page<SystemSequence> findAll(Pageable pageable);
	Page<SystemSequence> findAllByOrderByIdAsc(Pageable pageable);
	Optional<SystemSequence> findByName(String name);

	@Query("SELECT entity FROM #{#entityName} entity WHERE ("
			+ " LOWER(entity.name) like LOWER(CONCAT('%',:keyword,'%'))"
			+ ")"
	)
	Page<SystemSequence> search(@Param("keyword") String keyword, Pageable pageable);
}
