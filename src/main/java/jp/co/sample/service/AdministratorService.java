package jp.co.sample.service;

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
	
	/**
	 * 追加:管理者情報.
	 * 
	 * @param administrator : 追加する管理者情報
	 * @return 追加された時管理者情報 or null
	 * 
	 */
	public Administrator insert(Administrator administrator) {
		return repository.save(administrator);
	}
	
	
	/**
	 * 判定:管理者情報ログイン.
	 * 
	 * @param administrator : ログインする管理者情報
	 * @return 管理者情報 or null
	 */
	public Administrator login(Administrator administrator) {		
		return repository.findByEmailAddressAndPassword(
				administrator.getMailAddress(),administrator.getPassword());

	}

}
