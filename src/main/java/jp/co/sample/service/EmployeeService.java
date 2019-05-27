package jp.co.sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.domain.Employee;
import jp.co.sample.repository.EmployeeRepository;

@Service
@Transactional
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository repository;
	
	public Employee load(Integer id) {
		return repository.load(id);
	}
	
	public List<Employee> findAll(){
		return repository.findAll();
	}
	
	public Employee update(Employee employee) {
		return repository.update(employee);
	}

}
