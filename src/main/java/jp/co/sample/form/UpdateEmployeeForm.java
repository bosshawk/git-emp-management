package jp.co.sample.form;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import jp.co.sample.domain.Employee;

/**
 * 更新する従業員情報のフォーム.
 * 
 * @author taka
 *
 */
public class UpdateEmployeeForm {
	
	/** ID */
	private String id;
	/** 名前 */
	@NotBlank(message = "名前を入力してください")
	private String name;
	/** 性別 */
	@NotEmpty(message = "性別を入力してください")
	private String gender;
	/** 入社日 */
	@NotBlank(message = "入社日を入力してください")
	private String hireDate;
	/** メールアドレス */
	@NotBlank(message = "メールアドレスを入力してください")
	@Email(message = "メードアドレスの形式で入力してください")
	private String mailAddress;
	/** 郵便番号 */
	@NotBlank(message = "郵便番号を入力してください")
	private String zipCode;
	/** 住所 */
	@NotBlank(message = "住所を入力してください")
	private String address;
	/** 電話番号 */
	@NotBlank(message = "電話番号を入力してください")
	private String telephone;
	/** 給料 */
	@Pattern(regexp = "[0-9]+", message = "数字を入力してください")
	private String salary;
	/** 特性 */
	@NotBlank(message = "特性を入力してください")
	private String characteristics;
	/** 扶養人数 */
	@NotBlank(message = "扶養人数を入力してください")
	@Pattern(regexp = "[0-9]+", message = "数字を入力してください")
	private String dependentsCount;

	
	/**
	 * 従業員情報をformのフィールド変数で更新.
	 * 
	 * @param employee 更新される従業員情報
	 * @return 更新された従業員情報
	 */
	public Employee update(Employee employee) {
		employee.setName(name);
		employee.setGender(gender);
		employee.setHireDate(getDateHireDate());
		employee.setMailAddress(mailAddress);
		employee.setZipCode(zipCode);
		employee.setAddress(address);
		employee.setTelephone(telephone);
		employee.setSalary(getIntSalary());
		employee.setCharacteristics(characteristics);
		employee.setDependentsCount(getIntDependentsCount());
		return employee;
	}
	
	/**
	 * 取得:Date型の入社日
	 * @return Date型の入社日
	 */
	public Date getDateHireDate() {
		return java.sql.Date.valueOf(hireDate);
	}
	
	/**
	 * 取得:int型に給与(salary).
	 * @return int型の給与
	 */
	public int getIntSalary() {
		return Integer.parseInt(salary);
	}
	
	/**
	 * 取得:int型に扶養人数(dependentsCount)
	 * @return int型の扶養人数
	 */
	public int getIntDependentsCount() {
		return Integer.parseInt(dependentsCount);
	}
	
	@Override
	public String toString() {
		return "UpdateEmployeeForm [id=" + id + ", name=" + name + ", gender=" + gender + ", hireDate=" + hireDate
				+ ", mailAddress=" + mailAddress + ", zipCode=" + zipCode + ", address=" + address + ", telephone="
				+ telephone + ", salary=" + salary + ", characteristics=" + characteristics + ", dependentsCount="
				+ dependentsCount + "]";
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getHireDate() {
		return hireDate;
	}

	public void setHireDate(String hireDate) {
		this.hireDate = hireDate;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getCharacteristics() {
		return characteristics;
	}

	public void setCharacteristics(String characteristics) {
		this.characteristics = characteristics;
	}

	public String getDependentsCount() {
		return dependentsCount;
	}

	public void setDependentsCount(String dependentsCount) {
		this.dependentsCount = dependentsCount;
	}

}
