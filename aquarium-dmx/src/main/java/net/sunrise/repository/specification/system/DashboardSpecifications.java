/**
 * 
 */
package net.sunrise.repository.specification.system;

import org.springframework.data.jpa.domain.Specification;

import lombok.Builder;
import net.sunrise.domain.entity.system.DigitalDashboard;
import net.sunrise.framework.model.SearchParameter;
import net.sunrise.framework.model.specifications.SearchRequest;
import net.sunrise.framework.specifications.BrillianceSpecifications;

/**
 * @author bqduc
 *
 */
@Builder
public class DashboardSpecifications extends BrillianceSpecifications<DigitalDashboard, SearchRequest>{
	public static Specification<DigitalDashboard> buildSpecification(final SearchParameter searchParameter) {
		return DashboardSpecifications
				.builder()
				.build()
				.buildSpecifications(searchParameter);
	}
}
