package net.sunrise.service.api.admin;

import java.util.List;

import org.springframework.data.domain.Page;

import net.sunrise.domain.entity.admin.Office;
import net.sunrise.exceptions.ObjectNotFoundException;
import net.sunrise.framework.model.SearchParameter;
import net.sunrise.framework.service.GenericService;

public interface OfficeService extends GenericService<Office, Long>{

  /**
   * Get one office with the provided code.
   * 
   * @param code The office code
   * @return The office
   * @throws ObjectNotFoundException If no such office exists.
   */
	Office getOne(String code) throws ObjectNotFoundException;

	String deployObjects(List<List<String>> dataStrings);

  /**
   * Get one offices with the provided search parameters.
   * 
   * @param searchParameter The search parameter
   * @return The pageable offices
   */
	Page<Office> getObjects(SearchParameter searchParameter);
}
