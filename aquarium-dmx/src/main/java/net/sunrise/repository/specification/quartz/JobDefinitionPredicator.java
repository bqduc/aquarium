/**
 * 
 */
package net.sunrise.repository.specification.quartz;

import lombok.Builder;
import net.sunrise.domain.entity.schedule.JobDefinition;
import net.sunrise.framework.specifications.predicator.BrilliancePredicator;

/**
 * @author bqduc
 *
 */
@Builder
public class JobDefinitionPredicator extends BrilliancePredicator<JobDefinition>/*BrillianceSpecifications<JobDefinition, DefaultSearchRequest>*/{
}
