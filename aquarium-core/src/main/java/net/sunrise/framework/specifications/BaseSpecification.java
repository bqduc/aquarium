/**
 * 
 */
package net.sunrise.framework.specifications;

import org.springframework.data.jpa.domain.Specification;

import net.sunrise.common.CommonUtility;
import net.sunrise.framework.component.RootComponent;
import net.sunrise.framework.model.SearchParameter;

/**
 * @author bqduc
 *
 */
public abstract class BaseSpecification<UserType, UserRequest> extends RootComponent {
  public abstract Specification<UserType> getFilter(UserRequest request);

  protected String containsLowerCase(String searchField) {
  	return new StringBuilder()
  	.append(CommonUtility.STRING_WILDCARD)
  	.append(CommonUtility.getApplicableString(searchField))
  	.append(CommonUtility.STRING_WILDCARD)
  	.toString();
  }

	protected String containsWildcard(String searchField) {
		if (CommonUtility.isEmpty(searchField))
			return CommonUtility.STRING_BLANK;

		return CommonUtility.STRING_WILDCARD + searchField + CommonUtility.STRING_WILDCARD;
  }

	protected abstract Specification<UserType> buildSpecifications(final SearchParameter searchParameter);
}