/**
 * 
 */
package net.sunrise.repository.specification.dmx;

import org.springframework.data.jpa.domain.Specification;

import lombok.Builder;
import net.sunrise.cdx.domain.entity.Configuration;
import net.sunrise.framework.model.SearchParameter;
import net.sunrise.framework.model.specifications.SearchRequest;
import net.sunrise.framework.specifications.BrillianceSpecifications;

/**
 * @author bqduc
 *
 */
@Builder
public class BpmProcessRepoSpecification extends BrillianceSpecifications<Configuration, SearchRequest>{
	public static Specification<Configuration> buildSpecification(final SearchParameter searchParameter) {
		return BpmProcessRepoSpecification
				.builder()
				.build()
				.buildSpecifications(searchParameter);
	}
}
