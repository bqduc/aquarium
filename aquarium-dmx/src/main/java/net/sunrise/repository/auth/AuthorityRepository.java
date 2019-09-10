/**
 * 
 */
package net.sunrise.repository.auth;

import org.springframework.stereotype.Repository;

import net.sunrise.domain.entity.admin.Authority;
import net.sunrise.framework.repository.JBaseRepository;

/**
 * @author bqduc
 *
 */
@Repository
public interface AuthorityRepository extends JBaseRepository<Authority, Long> {
	Authority findByName(String name);
}
