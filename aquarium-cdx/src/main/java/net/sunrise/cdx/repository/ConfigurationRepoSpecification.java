/**
 * 
 */
package net.sunrise.cdx.repository;

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
public class ConfigurationRepoSpecification extends BrillianceSpecifications<Configuration, SearchRequest>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4115991455691287486L;

	public static Specification<Configuration> buildSpecification(final SearchParameter searchParameter) {
		return ConfigurationRepoSpecification
				.builder()
				.build()
				.buildSpecifications(searchParameter);
	}
}
