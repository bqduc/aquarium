/**
 * 
 */
package net.sunrise.repository.dmx;

import org.springframework.stereotype.Repository;

import net.sunrise.domain.entity.audit.AuditObject;
import net.sunrise.framework.repository.BaseRepository;

/**
 * @author bqduc
 *
 */
@Repository
public interface AuditObjectRepository extends BaseRepository<AuditObject, Long> {
}
