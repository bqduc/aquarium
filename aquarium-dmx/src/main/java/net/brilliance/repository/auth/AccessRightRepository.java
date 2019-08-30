/**
 * 
 */
package net.brilliance.repository.auth;

import org.springframework.stereotype.Repository;

import net.brilliance.domain.entity.security.AccessRight;
import net.brilliance.framework.repository.JBaseRepository;

/**
 * @author bqduc
 *
 */
@Repository
public interface AccessRightRepository extends JBaseRepository<AccessRight, Long> {
	AccessRight findByName(String name);
}
