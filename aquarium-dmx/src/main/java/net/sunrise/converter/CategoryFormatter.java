/**
 * 
 */
package net.sunrise.converter;

import java.text.ParseException;
import java.util.Locale;

import javax.inject.Inject;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import net.sunrise.common.CommonConstants;
import net.sunrise.common.CommonUtility;
import net.sunrise.domain.entity.general.Category;
import net.sunrise.manager.catalog.CategoryManager;

/**
 * @author bqduc
 *
 */
@Component
public class CategoryFormatter implements Formatter<Category> {
	@Inject 
	private CategoryManager categoryService;

	public CategoryFormatter(CategoryManager categoryService){
		this.categoryService = categoryService;
	}

	@Override
	public Category parse(String id, Locale locale) throws ParseException {
		Category result = categoryService.getById(Long.valueOf(id));
		return CommonUtility.isNotEmpty(result)?result:new Category();
	}

	@Override
	public String print(Category department, Locale locale) {
		return (department != null) ? String.valueOf(department.getId()):CommonConstants.STRING_BLANK; 
	}
}
