/**
 * 
 */
package net.brilliance.repository.auth;

import org.springframework.stereotype.Repository;

import net.brilliance.domain.entity.security.Permission;
import net.brilliance.framework.repository.JBaseRepository;

/**
 * @author bqduc
 *
 */
@Repository
public interface PermissionRepository extends JBaseRepository<Permission, Long> {
}
