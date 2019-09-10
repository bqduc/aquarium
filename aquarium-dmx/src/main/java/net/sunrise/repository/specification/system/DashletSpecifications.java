/**
 * 
 */
package net.sunrise.repository.specification.system;

import org.springframework.data.jpa.domain.Specification;

import lombok.Builder;
import net.sunrise.domain.entity.system.DigitalDashlet;
import net.sunrise.framework.model.SearchParameter;
import net.sunrise.framework.model.specifications.SearchRequest;
import net.sunrise.framework.specifications.BrillianceSpecifications;

/**
 * @author bqduc
 *
 */
@Builder
public class DashletSpecifications extends BrillianceSpecifications<DigitalDashlet, SearchRequest>{
	public static Specification<DigitalDashlet> buildSpecification(final SearchParameter searchParameter) {
		return DashletSpecifications
				.builder()
				.build()
				.buildSpecifications(searchParameter);
	}
}
