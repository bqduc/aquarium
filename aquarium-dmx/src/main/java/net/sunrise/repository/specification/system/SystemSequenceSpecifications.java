/**
 * 
 */
package net.sunrise.repository.specification.system;

import org.springframework.data.jpa.domain.Specification;

import lombok.Builder;
import net.sunrise.domain.entity.system.SystemSequence;
import net.sunrise.framework.model.SearchParameter;
import net.sunrise.framework.model.specifications.SearchRequest;
import net.sunrise.framework.specifications.BrillianceSpecifications;

/**
 * @author bqduc
 *
 */
@Builder
public class SystemSequenceSpecifications extends BrillianceSpecifications<SystemSequence, SearchRequest>{
	public static Specification<SystemSequence> buildSpecification(final SearchParameter searchParameter) {
		return SystemSequenceSpecifications
				.builder()
				.build()
				.buildSpecifications(searchParameter);
	}
}
