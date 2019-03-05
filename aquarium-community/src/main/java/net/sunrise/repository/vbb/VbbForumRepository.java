/**
 * 
 */
package net.sunrise.repository.vbb;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.brilliance.framework.repository.BaseRepository;
import net.sunrise.domain.entity.vbb.VbbForum;

/**
 * @author ducbq
 *
 */
@Repository
public interface VbbForumRepository extends BaseRepository<VbbForum, Long> {
	VbbForum findByName(String name);

	@Query("SELECT entity FROM #{#entityName} entity "
			+ "WHERE ("
			+ " LOWER(entity.name) like LOWER(CONCAT('%',:keyword,'%'))"
			+ ")"
	)
	Page<VbbForum> search(@Param("keyword") String keyword, Pageable pageable);
}
