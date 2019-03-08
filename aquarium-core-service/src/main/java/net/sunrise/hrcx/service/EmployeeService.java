package net.sunrise.hrcx.service;

import java.util.List;

import net.brilliance.domain.entity.hc.Employee;

public interface EmployeeService {
	
	public List<Employee> getAllEmployees();
	public Employee getEmployeeById(long id);

	public List<Employee> getEmployees();
}
