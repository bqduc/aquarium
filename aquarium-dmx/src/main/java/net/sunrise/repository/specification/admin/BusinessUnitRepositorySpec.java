/**
 * 
 */
package net.sunrise.repository.specification.admin;

import org.springframework.data.jpa.domain.Specification;

import lombok.Builder;
import net.sunrise.domain.entity.dmx.BusinessUnit;
import net.sunrise.framework.model.SearchParameter;
import net.sunrise.framework.model.specifications.SearchRequest;
import net.sunrise.framework.specifications.BrillianceSpecifications;

/**
 * @author bqduc
 *
 */
@Builder
public class BusinessUnitRepositorySpec extends /*BaseSpecifications*/BrillianceSpecifications<BusinessUnit, SearchRequest>{
	public static Specification<BusinessUnit> buildSpecification(final SearchParameter searchParameter) {
		return BusinessUnitRepositorySpec
				.builder()
				.build()
				.buildSpecifications(searchParameter);
	}
}
