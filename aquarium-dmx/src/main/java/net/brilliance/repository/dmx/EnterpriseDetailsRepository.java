/**
 * 
 */
package net.brilliance.repository.dmx;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.brilliance.framework.repository.BaseRepository;
import net.sunrise.domain.entity.dmx.Enterprise;
import net.sunrise.domain.entity.dmx.EnterpriseDetail;

/**
 * @author bqduc
 *
 */
@Repository
public interface EnterpriseDetailsRepository extends BaseRepository<EnterpriseDetail, Long> {
	List<EnterpriseDetail> findByEnterprise(Enterprise enterprise);

	@Query("SELECT entity FROM #{#entityName} entity WHERE ("
			+ " LOWER(entity.name) like LOWER(CONCAT('%',:keyword,'%'))"
			+ "or LOWER(entity.enterprise.code) like LOWER(CONCAT('%',:keyword,'%')) "
			+ "or LOWER(entity.enterprise.name) like LOWER(CONCAT('%',:keyword,'%')) "
			+ ")"
	)
	Page<EnterpriseDetail> search(@Param("keyword") String keyword, Pageable pageable);
}
