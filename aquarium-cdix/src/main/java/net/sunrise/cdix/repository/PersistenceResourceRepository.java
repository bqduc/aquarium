/**
 * 
 */
package net.sunrise.cdix.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.brilliance.framework.repository.BaseRepository;
import net.sunrise.cdix.entity.PersistenceResource;

/**
 * @author bqduc
 *
 */
@Repository
public interface PersistenceResourceRepository extends BaseRepository<PersistenceResource, Long> {
	Page<PersistenceResource> findAll(Pageable pageable);
	Page<PersistenceResource> findAllByOrderByIdAsc(Pageable pageable);
	Optional<PersistenceResource> findByName(String name);
	Long countByName(String name);

	@Query("SELECT entity FROM #{#entityName} entity WHERE ("
			+ " LOWER(entity.name) like LOWER(CONCAT('%',:keyword,'%'))"
			+ "or LOWER(entity.type) like LOWER(CONCAT('%',:keyword,'%')) "
			+ "or LOWER(entity.description) like LOWER(CONCAT('%',:keyword,'%')) "
			+ ")"
	)
	Page<PersistenceResource> search(@Param("keyword") String keyword, Pageable pageable);
}
