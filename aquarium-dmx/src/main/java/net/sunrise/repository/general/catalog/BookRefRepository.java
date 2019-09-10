/**
 * 
 */
package net.sunrise.repository.general.catalog;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import net.sunrise.domain.entity.general.RefBook;
import net.sunrise.framework.repository.JBaseRepository;

/**
 * @author bqduc
 *
 */
@Repository
public interface BookRefRepository extends JBaseRepository<RefBook, Long> {
	Optional<RefBook> findByIsbn(String license);
	Optional<RefBook> findByName(String name);
}
