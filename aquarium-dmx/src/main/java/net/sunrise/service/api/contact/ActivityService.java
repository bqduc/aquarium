package net.sunrise.service.api.contact;

import org.springframework.data.domain.Page;

import net.sunrise.domain.entity.general.Activity;
import net.sunrise.exceptions.ObjectNotFoundException;
import net.sunrise.framework.model.SearchParameter;
import net.sunrise.framework.service.GenericService;

public interface ActivityService extends GenericService<Activity, Long>{

  /**
   * Get one contact with the provided code.
   * 
   * @param code The activity name
   * @return The Activity
   * @throws ObjectNotFoundException If no such account exists.
   */
	Activity getName(String name) throws ObjectNotFoundException;

  /**
   * Get one activities with the provided search parameters.
   * 
   * @param searchParameter The search parameter
   * @return The pageable activities
   */
	Page<Activity> getObjects(SearchParameter searchParameter);
}
