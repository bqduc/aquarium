/**
 * 
 */
package net.sunrise.repository.auth;

import org.springframework.stereotype.Repository;

import net.sunrise.domain.entity.security.AccessRight;
import net.sunrise.framework.repository.JBaseRepository;

/**
 * @author bqduc
 *
 */
@Repository
public interface AccessRightRepository extends JBaseRepository<AccessRight, Long> {
	AccessRight findByName(String name);
}
