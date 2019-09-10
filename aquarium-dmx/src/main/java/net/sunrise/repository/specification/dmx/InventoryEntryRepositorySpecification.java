/**
 * 
 */
package net.sunrise.repository.specification.dmx;

import org.springframework.data.jpa.domain.Specification;

import lombok.Builder;
import net.sunrise.domain.entity.dmx.Inventory;
import net.sunrise.framework.model.SearchParameter;
import net.sunrise.framework.model.specifications.SearchRequest;
import net.sunrise.framework.specifications.BrillianceSpecifications;

/**
 * @author bqduc
 *
 */
@Builder
public class InventoryEntryRepositorySpecification extends BrillianceSpecifications<Inventory, SearchRequest>{
	public static Specification<Inventory> buildSpecification(final SearchParameter searchParameter) {
		return InventoryEntryRepositorySpecification
				.builder()
				.build()
				.buildSpecifications(searchParameter);
	}
}
