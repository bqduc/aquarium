/**
 * 
 */
package net.sunrise.cdix.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import lombok.Builder;
import net.sunrise.cdix.entity.ReserveResource;
import net.sunrise.framework.model.SearchParameter;
import net.sunrise.framework.model.specifications.SearchRequest;
import net.sunrise.framework.specifications.BrillianceSpecifications;

/**
 * @author bqduc
 *
 */
@Builder
public class PersistenceResourceRepoSpecification extends BrillianceSpecifications<ReserveResource, SearchRequest>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6974549290535666144L;

	public static Specification<ReserveResource> buildSpecification(final SearchParameter searchParameter) {
		return PersistenceResourceRepoSpecification
				.builder()
				.build()
				.buildSpecifications(searchParameter);
	}
}
