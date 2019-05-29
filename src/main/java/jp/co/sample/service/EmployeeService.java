package jp.co.sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.domain.Employee;
import jp.co.sample.repository.EmployeeRepository;

/**
 * 管理者情報のサービス.
 * 
 * @author taka
 *
 */
@Service
@Transactional
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository repository;
	
	/**
	 * 検索:従業員情報(ID).
	 * 
	 * @param id : 検索するID
	 * @return 検索された従業員情報
	 */
	public Employee load(Integer id) {
		return repository.load(id);
	}
	
	/**
	 * 検索:全従業員リスト.
	 * 
	 * @return 従業員リスト or null
	 */
	public List<Employee> findAll(){
		return repository.findAll();
	}
	
	/**
	 * 更新:従業員情報.
	 * 
	 * @param employee : 更新する従業員情報
	 * @return 更新された従業員情報
	 */
	public Employee update(Employee employee) {
		return repository.save(employee);
	}

}
