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

/**
 * 管理者情報を操作するコントローラ.
 * 
 * @author taka
 *
 */
@Controller
@RequestMapping("/administrator")
public class AdministratorController {
	
	@Autowired
	private AdministratorService service;
	
	@Autowired
	private HttpSession session;
	
	/** ログインする管理者情報のフォームのインスタンス化 */
	@ModelAttribute
	public LoginForm setUpLoginForm() {
		return new LoginForm();
	}
	
	/** 追加する管理者情報のフォームのインスタンス化 */
	@ModelAttribute
	public InsertAdministratorForm setUpInsertForm() {
		return new InsertAdministratorForm();
	}
	
	/**
	 * 遷移:ログインビュー.
	 * 
	 * @param model : リクエストスコープ
	 * @return ログイン処理へ
	 */
	@RequestMapping("/")
	public String toLogin(Model model) {
		return "administrator/login";
	}
	
	/**
	 * 遷移:管理者情報追加ビュー
	 * 
	 * @param model : リクエストスコープ
	 * @return 管理者アカウント追加処理へ
	 */
	@RequestMapping("/toInsert")
	public String toInsert(Model model) {
		return "administrator/insert";
	}
	
	/**
	 * ログアウト処理.
	 * 
	 * session内の管理者.名前を削除し
	 * ログインビューへフォワード
	 * 
	 * @return ログインビューへフォワード
	 */
	@RequestMapping("/logout")
	public String logout() {
		session.removeAttribute("administratorName");
		return "redirect:/administrator/";
	}
	
	/**
	 * ログイン:管理者情報.
	 * 
	 * 入力したメールアドレスとパスワードを検索し
	 * データベースにあればログインする
	 * 
	 * ログイン
	 * 成功：従業員情報リストにフォワード
	 * 失敗：エラーを表示しログインviewへ
	 * 
	 * @param form : ログインする管理者情報
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
			//result.rejectValue(field, errorCode, defaultMessage);
			//errorCode=null
			result.addError(fieldError);
			return toLogin(model);
		}
	}
	
	/**
	 * 作成:管理者アカウント.
	 * 
	 * 管理者アカウントを追加する
	 * 
	 * @param form : 追加する管理者情報
	 * @param result : エラーメッセージ
	 * @param model : リクエストスコープ
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
