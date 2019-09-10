/**
 * 
 */
package net.sunrise.repository.specification.admin;

import org.springframework.data.jpa.domain.Specification;

import lombok.Builder;
import net.sunrise.domain.entity.admin.ClientUserAccount;
import net.sunrise.framework.model.SearchParameter;
import net.sunrise.framework.model.specifications.SearchRequest;
import net.sunrise.framework.specifications.BrillianceSpecifications;

/**
 * @author bqduc
 *
 */
@Builder
public class ClientUserAccountSpecification extends BrillianceSpecifications<ClientUserAccount, SearchRequest>{
	public static Specification<ClientUserAccount> buildSpecification(final SearchParameter searchParameter) {
		return ClientUserAccountSpecification
				.builder()
				.build()
				.buildSpecifications(searchParameter);
	}
}
