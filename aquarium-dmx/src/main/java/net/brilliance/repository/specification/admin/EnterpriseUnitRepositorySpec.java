/**
 * 
 */
package net.brilliance.repository.specification.admin;

import org.springframework.data.jpa.domain.Specification;

import lombok.Builder;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.model.specifications.SearchRequest;
import net.brilliance.framework.specifications.BrillianceSpecifications;
import net.sunrise.domain.entity.dmx.EnterpriseUnit;

/**
 * @author ducbq
 *
 */
@Builder
public class EnterpriseUnitRepositorySpec extends BrillianceSpecifications<EnterpriseUnit, SearchRequest> {
	public static Specification<EnterpriseUnit> buildSpecification(final SearchParameter searchParameter) {
		return EnterpriseUnitRepositorySpec
				.builder()
				.build()
				.buildSpecifications(searchParameter);
	}
}
