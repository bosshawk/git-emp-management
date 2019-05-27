package jp.co.sample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.domain.Administrator;
import jp.co.sample.repository.AdministratorRepository;

@Service
@Transactional
public class AdministratorService {
	
	@Autowired
	private AdministratorRepository repository;
	
	public Administrator load(Integer id) {
		return repository.load(id);
	}
	
	public List<Administrator> findAll(){
		return repository.findAll();
	}
	
	/**
	 * 管理者情報を追加.
	 * 
	 * @param administrator
	 * @return 追加時管理者情報を返し、存在する場合nullを返す
	 */
	public Administrator insert(Administrator administrator) {
		return repository.insert(administrator);
	}
	
	/**
	 * ログイン処理.
	 * 
	 * @param administrator
	 * @return ログイン成功時管理者情報を返し、ログイン失敗時nullを返す
	 */
	public Administrator login(Administrator administrator) {
		for(Administrator admin:findAll()) {
			if(administrator.getMailAddress().equals(admin.getMailAddress()) &&
				administrator.getPassword().equals(admin.getPassword())) {
				return admin;
			}
		}
		return null;
	}

}
