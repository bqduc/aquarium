/**
 * 
 */
package net.sunrise.repository.specification.dmx;

import org.springframework.data.jpa.domain.Specification;

import lombok.Builder;
import net.sunrise.domain.entity.general.Project;
import net.sunrise.framework.model.SearchParameter;
import net.sunrise.framework.model.specifications.SearchRequest;
import net.sunrise.framework.specifications.BrillianceSpecifications;

/**
 * @author bqduc
 *
 */
@Builder
public class ProjectRepositorySpec extends BrillianceSpecifications<Project, SearchRequest>{
	public static Specification<Project> buildSpecification(final SearchParameter searchParameter) {
		return ProjectRepositorySpec
				.builder()
				.build()
				.buildSpecifications(searchParameter);
	}
}
