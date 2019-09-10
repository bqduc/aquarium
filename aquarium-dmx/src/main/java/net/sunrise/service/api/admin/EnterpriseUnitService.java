package net.sunrise.service.api.admin;

import java.util.List;

import org.springframework.data.domain.Page;

import net.sunrise.domain.entity.dmx.EnterpriseUnit;
import net.sunrise.exceptions.ObjectNotFoundException;
import net.sunrise.framework.model.SearchParameter;
import net.sunrise.framework.service.GenericService;

public interface EnterpriseUnitService extends GenericService<EnterpriseUnit, Long>{

  /**
   * Get one EnterpriseUnit with the provided code.
   * 
   * @param code The EnterpriseUnit code
   * @return The EnterpriseUnit
   * @throws ObjectNotFoundException If no such EnterpriseUnit exists.
   */
	EnterpriseUnit getOne(String code) throws ObjectNotFoundException;

	String deployObjects(List<List<String>> dataStrings);

  /**
   * Get one EnterpriseUnits with the provided search parameters.
   * 
   * @param searchParameter The search parameter
   * @return The pageable EnterpriseUnits
   */
	Page<EnterpriseUnit> getObjects(SearchParameter searchParameter);
}
