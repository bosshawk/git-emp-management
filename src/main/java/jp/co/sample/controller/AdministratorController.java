package jp.co.sample.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Administrator;
import jp.co.sample.form.InsertAdministratorForm;
import jp.co.sample.form.LoginForm;
import jp.co.sample.service.AdministratorService;

@Controller
@RequestMapping("/administrator")
public class AdministratorController {
	
	@Autowired
	private AdministratorService service;
	
	@Autowired
	private HttpSession session;
	
	@ModelAttribute
	public LoginForm setUpLoginForm() {
		return new LoginForm();
	}
	
	@ModelAttribute
	public InsertAdministratorForm setUpInsertForm() {
		return new InsertAdministratorForm();
	}
	
	/**
	 * @param model : リクエストスコープ
	 * @return ログイン処理へ
	 */
	@RequestMapping("/")
	public String toLogin(Model model) {
		return "administrator/login";
	}
	
	/**
	 * @param model : リクエストスコープ
	 * @return 管理者アカウント追加処理へ
	 */
	@RequestMapping("/toInsert")
	public String toInsert(Model model) {
		return "administrator/insert";
	}
	
	/**
	 * @return ログイン画面へ
	 */
	@RequestMapping("/logout")
	public String logout() {
		session.removeAttribute("administratorName");
		return "redirect:/administrator/";
	}
	
	/**
	 * 管理者ログイン.
	 * 
	 * @param form : Loginフォーム
	 * @param result : エラー処理へ
	 * @param model : リクエストスコープ
	 * @return ログイン成功時従業員情報リストにフォワード
	 */
	@RequestMapping("/login")
	public String login(
			@Validated LoginForm form,
			BindingResult result,
			Model model) {
		
		if(result.hasErrors()) {
			return toLogin(model);
		}
		
		Administrator administrator = new Administrator();
		BeanUtils.copyProperties(form, administrator);
		administrator = service.login(administrator);
		if(administrator != null) {
			session.setAttribute("administratorName", administrator.getName());
			return "forward:/employee/showList";
		}else {
			FieldError fieldError 
			= new FieldError(result.getObjectName(),"mailAddress","メールアドレスまたはパスワードが間違っています。");
			result.addError(fieldError);
			return toLogin(model);
		}
	}
	
	/**
	 * 管理者アカウント作成.
	 * 
	 * @param form
	 * @param result:
	 * @param model:リクエストスコープ
	 * @return ログイン画面にフォワード
	 */
	@RequestMapping("/insert")
	public String insert(
			@Validated InsertAdministratorForm form,
			BindingResult result,
			Model model
			) {
		if(result.hasErrors()) {
			return toInsert(model);
		}
		
		Administrator administrator = new Administrator();
		BeanUtils.copyProperties(form, administrator);
		
		administrator = service.insert(administrator);
		if(administrator != null) {
			return toLogin(model);
		}else {
			FieldError fieldError
			= new FieldError(result.getObjectName(),"name","すでに存在するアカウントです。");
			result.addError(fieldError);
			return toInsert(model);
		}
	}
	

}
