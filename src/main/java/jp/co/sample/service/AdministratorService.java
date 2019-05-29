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
	 * 検索:引数(ID)の管理者情報
	 * 
	 * @param id : 検索する管理者情報のID
	 * @return 検索した管理者情報
	 */
	public Administrator load(Integer id) {
		return repository.load(id);
	}
	
	/**
	 * 追加:管理者情報.
	 * 
	 * @param administrator : 追加する管理者情報
	 * @return 追加された時管理者情報 or null
	 * 
	 */
	public Administrator insert(Administrator administrator) {
		if( repository.findByMailAddress(administrator.getMailAddress()) == null) {
			return repository.save(administrator);
		}else {
			return null;
		}
	}
	
	
	/**
	 * 判定:管理者情報ログイン.
	 * 
	 * @param administrator : ログインする管理者情報
	 * @return 管理者情報 or null
	 */
	public Administrator login(Administrator administrator) {		
		return repository.findByMailAddressAndPassword(
				administrator.getMailAddress(),administrator.getPassword());

	}
	
	/**
	 * 更新:管理者情報.
	 * 
	 * @param administrator : 更新する管理者情報
	 * @return 更新した管理者情報 or null
	 */
	public Administrator update(Administrator administrator) {
		if(repository.findByMailAddress(administrator.getMailAddress())==null) {			
			return repository.save(administrator);
		}else {
			return null;
		}
	}

}
