/**
 * 
 */
package net.sunrise.repository.dashboard;

import org.springframework.stereotype.Repository;

import net.sunrise.domain.entity.system.DigitalDashlet;

/**
 * @author bqduc
 *
 */
@Repository
public interface DashletRepository extends DashboardBaseRepository<DigitalDashlet, Long> {
}
