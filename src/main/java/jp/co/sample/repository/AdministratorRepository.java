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

import jp.co.sample.domain.Administrator;

@Repository
public class AdministratorRepository {
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/** テーブル名 */
	private static final String TABLE_NAME = "administrators";
	
	/** Administrator検索 */
	private static final RowMapper<Administrator> 
	ADMINISTRATOR_ROW_MAPPER = (rs,i) -> {
		Administrator administrator = new Administrator();
		administrator.setId(rs.getInt("id"));
		administrator.setName(rs.getString("name"));
		administrator.setMailAddress(rs.getString("mail_address"));
		administrator.setPassword(rs.getString("password"));
		return administrator;
	};
	
	/** 追加時に主キー取得 */
	private SimpleJdbcInsert insert;
	@PostConstruct
	public void init() {
		SimpleJdbcInsert simpleJdbcInsert
		= new SimpleJdbcInsert((JdbcTemplate)template.getJdbcOperations());
		SimpleJdbcInsert withTableName
		= simpleJdbcInsert.withTableName(TABLE_NAME);
		insert = withTableName.usingGeneratedKeyColumns("id");
	}
	
	/**
	 * 主キー検索.
	 * 
	 * @param id 検索するID
	 * @return 検索された管理者情報
	 */
	public Administrator load(Integer id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT id,name,mail_address,password ");
		sql.append("FROM "+ TABLE_NAME + " WHERE id = :id;");
		SqlParameterSource param
		= new MapSqlParameterSource().addValue("id", id);
		
		return template.queryForObject(sql.toString(), param, ADMINISTRATOR_ROW_MAPPER);
	}
	
	/**
	 * 全件検索.
	 * 
	 * @return 検索された管理者情報
	 */
	public List<Administrator> findAll(){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT id,name,mail_address,password ");
		sql.append("FROM " + TABLE_NAME + " ORDER BY id;");
		return template.query(sql.toString(), ADMINISTRATOR_ROW_MAPPER);
	}
	
	
	/**
	 * 追加,更新する管理者情報を追加.
	 * 
	 * @param administrator 追加,更新する管理者情報
	 * @return 更新した管理者情報
	 */
	public Administrator insert(Administrator administrator) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(administrator);
		if(administrator.getId() == null) {
			Number key = insert.executeAndReturnKey(param);
			administrator.setId(key.intValue());
			return administrator;
		}else {
			return null;
//			StringBuffer updateSql = new StringBuffer();
//			updateSql.append("UPDATE "+TABLE_NAME + " ");
//			updateSql.append("SET name = :name,mail_address = :mailAddress ");
//			updateSql.append("password=:password WHERE id = :id");
//			template.update(updateSql.toString(), param);
		}
	}
	

}
