/**
 * 
 */
package net.sunrise.repository.agricultural;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import net.sunrise.domain.entity.aquacultural.Aquafeed;
import net.sunrise.framework.repository.CodeBaseRepository;

/**
 * @author bqduc
 *
 */
@Repository
public interface AquafeedRepository extends CodeBaseRepository<Aquafeed, Long> {
	Optional<Aquafeed> findByName(String name);
}
