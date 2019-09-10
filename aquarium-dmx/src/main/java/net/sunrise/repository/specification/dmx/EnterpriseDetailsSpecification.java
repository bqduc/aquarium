/**
 * 
 */
package net.sunrise.repository.specification.dmx;

import org.springframework.data.jpa.domain.Specification;

import lombok.Builder;
import net.sunrise.domain.entity.dmx.EnterpriseDetail;
import net.sunrise.framework.model.SearchParameter;
import net.sunrise.framework.model.specifications.SearchRequest;
import net.sunrise.framework.specifications.BrillianceSpecifications;

/**
 * @author bqduc
 *
 */
@Builder
public class EnterpriseDetailsSpecification extends BrillianceSpecifications<EnterpriseDetail, SearchRequest>{
	public static Specification<EnterpriseDetail> buildSpecification(final SearchParameter searchParameter) {
		return EnterpriseDetailsSpecification
				.builder()
				.build()
				.buildSpecifications(searchParameter);
	}
}
