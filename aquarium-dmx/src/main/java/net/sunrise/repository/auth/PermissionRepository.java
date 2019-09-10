/**
 * 
 */
package net.sunrise.repository.auth;

import org.springframework.stereotype.Repository;

import net.sunrise.domain.entity.security.Permission;
import net.sunrise.framework.repository.JBaseRepository;

/**
 * @author bqduc
 *
 */
@Repository
public interface PermissionRepository extends JBaseRepository<Permission, Long> {
}
