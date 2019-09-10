package net.sunrise.service.api.invt;

import java.util.List;

import net.sunrise.domain.entity.config.Item;
import net.sunrise.domain.entity.config.Language;
import net.sunrise.domain.entity.config.LocalizedItem;
import net.sunrise.exceptions.ObjectNotFoundException;
import net.sunrise.framework.service.GenericService;

public interface ItemService extends GenericService<Item, Long>{

  /**
   * Get one item with the provided code.
   * 
   * @param code The item code
   * @return The item
   * @throws ObjectNotFoundException If no such account exists.
   */
	Item getOne(String code) throws ObjectNotFoundException;

	LocalizedItem getLocalizedItem(Item item, Language language);

	LocalizedItem saveLocalizedItem(LocalizedItem localizedItem);
	
	List<LocalizedItem> getLocalizedItems(String subtype, Language language);
}
