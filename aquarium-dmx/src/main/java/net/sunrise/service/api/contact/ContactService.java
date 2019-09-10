package net.sunrise.service.api.contact;

import java.util.List;

import org.springframework.data.domain.Page;

import net.sunrise.domain.entity.crx.contact.Contact;
import net.sunrise.exceptions.ObjectNotFoundException;
import net.sunrise.framework.model.SearchParameter;
import net.sunrise.framework.service.GenericService;

public interface ContactService extends GenericService<Contact, Long>{
  /**
   * Get one contact with the provided code.
   * 
   * @param code The contact code
   * @return The contact
   * @throws ObjectNotFoundException If no such account exists.
   */
	Contact getOne(String code) throws ObjectNotFoundException;

	String deployContacts(List<List<String>> dataStrings);

	/**
   * Get one contacts with the provided search parameters.
   * 
   * @param searchParameter The search parameter
   * @return The pageable contacts
   */
	Page<Contact> getObjects(SearchParameter searchParameter);
}
