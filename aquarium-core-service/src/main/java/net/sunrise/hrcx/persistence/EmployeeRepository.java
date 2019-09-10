package net.sunrise.hrcx.persistence;

import org.springframework.stereotype.Repository;

import net.sunrise.domain.entity.hc.Employee;
import net.sunrise.framework.repository.BaseRepository;

@Repository
public interface EmployeeRepository extends BaseRepository<Employee, Long>{
	Long countByCode(String code);
}
