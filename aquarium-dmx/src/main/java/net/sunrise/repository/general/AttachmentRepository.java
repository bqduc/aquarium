/**
 * 
 */
package net.sunrise.repository.general;

import org.springframework.stereotype.Repository;

import net.sunrise.domain.entity.general.Attachment;
import net.sunrise.framework.repository.BaseRepository;

/**
 * @author bqduc
 *
 */
@Repository
public interface AttachmentRepository extends BaseRepository<Attachment, Long> {
}
