/**
 * 
 */
package net.sunrise.cdix.repository.specification;

import org.springframework.data.jpa.domain.Specification;

import lombok.Builder;
import net.brilliance.framework.model.SearchParameter;
import net.brilliance.framework.model.specifications.SearchRequest;
import net.brilliance.framework.specifications.BrillianceSpecifications;
import net.sunrise.cdix.entity.PersistenceResource;

/**
 * @author bqduc
 *
 */
@Builder
public class PersistenceResourceRepoSpecification extends BrillianceSpecifications<PersistenceResource, SearchRequest>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6974549290535666144L;

	public static Specification<PersistenceResource> buildSpecification(final SearchParameter searchParameter) {
		return PersistenceResourceRepoSpecification
				.builder()
				.build()
				.buildSpecifications(searchParameter);
	}
}
