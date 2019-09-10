/**
 * 
 */
package net.sunrise.repository.dashboard;

import org.springframework.stereotype.Repository;

import net.sunrise.domain.entity.system.DigitalDashboard;

/**
 * @author bqduc
 *
 */
@Repository
public interface DashboardRepository extends DashboardBaseRepository<DigitalDashboard, Long> {
}
