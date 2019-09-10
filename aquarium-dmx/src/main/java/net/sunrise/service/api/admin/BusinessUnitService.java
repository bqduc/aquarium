package net.sunrise.service.api.admin;

import org.springframework.data.domain.Page;

import net.sunrise.domain.entity.dmx.BusinessUnit;
import net.sunrise.exceptions.ObjectNotFoundException;
import net.sunrise.framework.model.SearchParameter;
import net.sunrise.framework.service.GenericService;

public interface BusinessUnitService extends GenericService<BusinessUnit, Long>{

  /**
   * Get one business unit with the provided code.
   * 
   * @param code The business unit code
   * @return The business unit
   * @throws ObjectNotFoundException If no such business unit exists.
   */
	BusinessUnit getOne(String code) throws ObjectNotFoundException;

  /**
   * Get one business units with the provided search parameters.
   * 
   * @param searchParameter The search parameter
   * @return The pageable business units
   */
	Page<BusinessUnit> getObjects(SearchParameter searchParameter);
}
