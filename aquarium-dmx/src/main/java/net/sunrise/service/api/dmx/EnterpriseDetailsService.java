package net.sunrise.service.api.dmx;

import java.util.List;

import org.springframework.data.domain.Page;

import net.sunrise.domain.entity.dmx.Enterprise;
import net.sunrise.domain.entity.dmx.EnterpriseDetail;
import net.sunrise.framework.model.SearchParameter;
import net.sunrise.framework.service.GenericService;

public interface EnterpriseDetailsService extends GenericService<EnterpriseDetail, Long> {

	/**
	 * Get one EnterpriseDetails with the provided enterprise.
	 * 
	 * @param enterprise
	 *            The Enterprise
	 * @return The pageable EnterpriseDetails
	 */
	List<EnterpriseDetail> getByEnterprise(Enterprise enterprise);

	/**
	 * Get one EnterpriseDetails with the provided search parameters.
	 * 
	 * @param searchParameter
	 *            The search parameter
	 * @return The pageable EnterpriseDetails
	 */
	Page<EnterpriseDetail> getObjects(SearchParameter searchParameter);
}
