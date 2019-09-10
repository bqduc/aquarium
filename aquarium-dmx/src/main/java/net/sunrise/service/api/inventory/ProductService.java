package net.sunrise.service.api.inventory;

import java.util.List;

import org.springframework.data.domain.Page;

import net.sunrise.domain.entity.stock.Product;
import net.sunrise.exceptions.ObjectNotFoundException;
import net.sunrise.framework.model.SearchParameter;
import net.sunrise.framework.service.GenericService;

public interface ProductService extends GenericService<Product, Long> {

	/**
	 * Get one contact with the provided code.
	 * 
	 * @param code
	 *            The product code
	 * @return The product
	 * @throws ObjectNotFoundException
	 *             If no such account exists.
	 */
	Product getOne(String code) throws ObjectNotFoundException;

	String deployProducts(List<List<String>> dataStrings);

	/**
	 * Get one contacts with the provided search parameters.
	 * 
	 * @param searchParameter
	 *            The search parameter
	 * @return The pageable contacts
	 */
	Page<Product> getObjects(SearchParameter searchParameter);
}
