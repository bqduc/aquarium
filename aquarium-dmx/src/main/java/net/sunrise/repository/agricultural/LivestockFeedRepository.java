/**
 * 
 */
package net.sunrise.repository.agricultural;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import net.sunrise.domain.entity.aquacultural.LivestockFeed;
import net.sunrise.framework.repository.CodeBaseRepository;

/**
 * @author bqduc
 *
 */
@Repository
public interface LivestockFeedRepository extends CodeBaseRepository<LivestockFeed, Long> {
	Optional<LivestockFeed> findByName(String name);
}
