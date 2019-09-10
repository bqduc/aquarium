/**
 * 
 */
package net.sunrise.framework.specifications.predicator;

import org.springframework.data.jpa.domain.Specification;

import net.sunrise.framework.model.SearchParameter;
import net.sunrise.framework.specifications.predicator.base.RepositoryPredicator;

/**
 * @author bqduc
 *
 */
public abstract class BrilliancePredicator<T> extends RepositoryPredicator<T>{
	public Specification<T> buildSpecification(final SearchParameter searchParameter){
		return predicateSpecification(searchParameter);
	}
}
