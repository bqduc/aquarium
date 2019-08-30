/**
 * 
 */
package net.brilliance.repository.specification.dmx;

import org.springframework.data.jpa.domain.Specification;

import lombok.Builder;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.model.specifications.SearchRequest;
import net.brilliance.framework.specifications.BrillianceSpecifications;
import net.sunrise.domain.entity.dmx.Enterprise;

/**
 * @author bqduc
 *
 */
@Builder
public class EnterpriseSpecification extends BrillianceSpecifications<Enterprise, SearchRequest>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4351535957683794972L;

	public static Specification<Enterprise> buildSpecification(final SearchParameter searchParameter) {
		return EnterpriseSpecification
				.builder()
				.build()
				.buildSpecifications(searchParameter);
	}
}
