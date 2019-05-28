package jp.co.sample.domain;

/**
 * 管理者情報.
 * 
 * @author taka
 *
 */
public class Administrator {

	/** ID */
	private Integer id;
	/** 名前 */
	private String Name;
	/** メールアドレス */
	private String mailAddress;
	/** パスワード */
	private String password;

	@Override
	public String toString() {
		return "Administrators [id=" + id + ", Name=" + Name + ", mail_addres=" + mailAddress + ", password=" + password
				+ "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
