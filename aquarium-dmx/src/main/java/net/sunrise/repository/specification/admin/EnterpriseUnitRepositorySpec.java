/**
 * 
 */
package net.sunrise.repository.specification.admin;

import org.springframework.data.jpa.domain.Specification;

import lombok.Builder;
import net.sunrise.domain.entity.dmx.EnterpriseUnit;
import net.sunrise.framework.model.SearchParameter;
import net.sunrise.framework.model.specifications.SearchRequest;
import net.sunrise.framework.specifications.BrillianceSpecifications;

/**
 * @author bqduc
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
