package jp.co.sample.repository;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import jp.co.sample.domain.Employee;

@Repository
public class EmployeeRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static final String TABLE_NAME = "employees";
	
	/** 従業員情報のRowMapper */
	private static final RowMapper<Employee> 
	EMPLOYEE_ROW_MAPPER = (rs,i) -> {
		Employee employee = new Employee();
		employee.setId(rs.getInt("id"));
		employee.setName(rs.getString("name"));
		employee.setImage(rs.getString("image"));
		employee.setGender(rs.getString("gender"));
		employee.setHireDate(rs.getDate("hire_date"));
		employee.setMailAddress(rs.getString("mail_address"));
		employee.setZipCode(rs.getString("zip_code"));
		employee.setAddress(rs.getString("address"));
		employee.setTelephone(rs.getString("telephone"));
		employee.setSalary(rs.getInt("salary"));
		employee.setCharacteristics(rs.getString("characteristics"));
		employee.setDependentsCount(rs.getInt("dependents_count"));
		return employee;
	};
	
	/** 追加時に主キー取得 */
	private SimpleJdbcInsert insert;
	/** 追加時主キー取得 */
	@PostConstruct
	public void init() {
		SimpleJdbcInsert simpleJdbcInsert
		= new SimpleJdbcInsert((JdbcTemplate)template.getJdbcOperations());
		SimpleJdbcInsert withTableName
		= simpleJdbcInsert.withTableName(TABLE_NAME);
		insert = withTableName.usingGeneratedKeyColumns("id");
	}
	
	/**
	 * 従業員情報の主キー検索.
	 * 
	 * @param id 検索する主キー
	 * @return 検索された従業員情報
	 */
	public Employee load(Integer id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM "+TABLE_NAME +" WHERE id=:id");
		SqlParameterSource param
		= new MapSqlParameterSource().addValue("id", id);
		return template.queryForObject(sql.toString(), param,EMPLOYEE_ROW_MAPPER);
	}
	
	/**
	 * 従業員情報を全件検索.
	 * 
	 * @return 従業員情報一覧
	 */
	public List<Employee> findAll(){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM " + TABLE_NAME + " ");
		sql.append("ORDER BY hire_date DESC; ");
		return template.query(sql.toString(), EMPLOYEE_ROW_MAPPER);
	}
	
	/**
	 * 従業員情報を更新.
	 * 
	 * @param employee 更新する従業員情報
	 * @return 更新された従業員情報or既にある場合null
	 */
	public Employee save(Employee employee) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(employee);
		if(employee.getId() == null) {
			return null;
		}else {
			StringBuffer updateSql = new StringBuffer();
			updateSql.append("UPDATE "+TABLE_NAME+" SET name=:name,");
			updateSql.append("image=:image,gender=:gender,hire_date=:hireDate,");
			updateSql.append("mail_address=:mailAddress,zip_code=:zipCode,");
			updateSql.append("address=:address,telephone=:telephone,salary=:salary,");
			updateSql.append("characteristics=:characteristics,");
			updateSql.append("dependents_count=:dependentsCount ");
			updateSql.append("WHERE id=:id; ");
			template.update(updateSql.toString(), param);
			return employee;
		}
	}
	
	

}
