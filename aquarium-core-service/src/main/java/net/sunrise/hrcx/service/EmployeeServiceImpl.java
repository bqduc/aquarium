package net.sunrise.hrcx.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sunrise.domain.entity.hc.Employee;
import net.sunrise.hrcx.persistence.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@Override
	public Employee getEmployeeById(long id) {
		return employeeRepository.getOne(id);
	}

	@Override
	public List<Employee> getEmployees() {
		return employeeRepository.findAll();
	}

}
