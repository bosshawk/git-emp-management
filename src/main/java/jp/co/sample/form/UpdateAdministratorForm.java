package jp.co.sample.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.beans.BeanUtils;

import jp.co.sample.domain.Administrator;

/**
 * 更新する管理者情報のフォーム
 * 
 * @author taka
 *
 */
public class UpdateAdministratorForm {

	/** ID */
	private String id;
	/** 名前 */
	@NotBlank(message = "氏名を入力してください")
	private String name;
	/** メールアドレス */
	@NotBlank(message = "メールアドレスを入力してください")
	@Email(message = "メールアドレスの形式で入力してください")
	private String mailAddress;
	/** パスワード */
	@NotBlank(message = "パスワードを入力してください")
	@Size(min = 1, max = 127, message = "1文字以上127文字以下で入力してください")
	private String password;

	/**
	 * 管理者IDをint型で返す.
	 * 
	 * @return 管理者IDをint型で返す
	 */
	public Integer getIntId() {
		return Integer.parseInt(id);
	}

	/**
	 * 管理者情報をコピー.
	 * 
	 * @param administrator : コピー元の管理者情報
	 * @return コピー後の管理者情報
	 */
	public Administrator copy(Administrator administrator) {
		BeanUtils.copyProperties(this, administrator);
		return administrator;
	}

	/**
	 * 管理者情報をformにセットする.
	 * 
	 * @param administrator セットする元の管理者情報
	 */
	public void setForm(Administrator administrator) {
		BeanUtils.copyProperties(administrator, this);
		this.id = ""+administrator.getId();
	}

	@Override
	public String toString() {
		return "UpdateAdministratorForm [id=" + id + ", name=" + name + ", mailAddress=" + mailAddress + ", password="
				+ password + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
