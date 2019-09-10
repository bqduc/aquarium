/**
 * 
 */
package net.sunrise.repository.agricultural;

import org.springframework.stereotype.Repository;

import net.sunrise.domain.entity.aquacultural.Pond;
import net.sunrise.framework.repository.CodeBaseRepository;

/**
 * @author bqduc
 *
 */
@Repository
public interface PondRepository extends CodeBaseRepository<Pond, Long> {
}
