/**
 * 
 */
package net.sunrise.repository.specification.admin;

import org.springframework.data.jpa.domain.Specification;

import lombok.Builder;
import net.sunrise.domain.entity.admin.Office;
import net.sunrise.framework.model.SearchParameter;
import net.sunrise.framework.model.specifications.SearchRequest;
import net.sunrise.framework.specifications.BrillianceSpecifications;

/**
 * @author bqduc
 *
 */
@Builder
public class OfficeRepositorySpec extends BrillianceSpecifications<Office, SearchRequest> {
	public static Specification<Office> buildSpecification(final SearchParameter searchParameter) {
		return OfficeRepositorySpec
				.builder()
				.build()
				.buildSpecifications(searchParameter);
	}
}
