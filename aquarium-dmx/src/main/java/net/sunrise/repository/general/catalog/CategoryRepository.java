/**
 * 
 */
package net.sunrise.repository.general.catalog;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import net.sunrise.domain.entity.general.Category;
import net.sunrise.framework.repository.AdvancedSearchRepository;

/**
 * @author bqduc
 *
 */
@Repository
public interface CategoryRepository extends AdvancedSearchRepository<Category, Long> {
	Optional<Category> findByName(String name);
	Optional<Category> findByCode(String code);
}
