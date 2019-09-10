/**
 * 
 */
package net.sunrise.repository.general.catalog;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import net.sunrise.domain.entity.general.Project;
import net.sunrise.framework.repository.AdvancedSearchRepository;

/**
 * @author bqduc
 *
 */
@Repository
public interface ProjectRepository extends AdvancedSearchRepository<Project, Long> {
	Optional<Project> findByCode(String license);
	Optional<Project> findByName(String name);
}
