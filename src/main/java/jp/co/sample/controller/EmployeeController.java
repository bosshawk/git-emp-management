package jp.co.sample.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sample.domain.Employee;
import jp.co.sample.form.UpdateEmployeeForm;
import jp.co.sample.service.EmployeeService;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
	
	@ModelAttribute
	private UpdateEmployeeForm setUpEmployeeForm() {
		return new UpdateEmployeeForm();
	}
	
	@Autowired
	private EmployeeService service;
	
	@Autowired
	private HttpSession session;
	
	/**
	 * 表示:全従業員リスト.
	 * 
	 * @param model : リクエストスコープ
	 * @return 従業員リストビューにフォワード
	 */
	@RequestMapping("/showList")
	public String showList(Model model) {
		if(session.getAttribute("administratorName") == null ) {
			return "redirect:/administrator/";
		}
		List<Employee> employeeList = service.findAll();
		model.addAttribute("employeeList", employeeList);
		return "employee/list";
	}
	
	/**
	 * 表示:従業員の詳細
	 * 
	 * @param id : 表示する従業員情報のID
	 * @param model : 従業員情報のリクエストスコープ
	 * @return 従業員詳細情報のビューへ遷移
	 */
	@RequestMapping("/showDetail")
	public String showDetail(String id,Model model,UpdateEmployeeForm form){
		if(session.getAttribute("administratorName") == null ) {
			return "redirect:/administrator/";
		}
		Employee employee = new Employee();
		employee = service.load(Integer.parseInt(id));
		model.addAttribute("employee", employee);
		form.copy(employee);
		return "employee/detail";
	}
	
	/**
	 * 更新:従業員情報.
	 * 
	 * @param form : 更新する従業員情報
	 * @param result : エラーメッセージ
	 * @param model : リクエストスコープ
	 * @return 更新後:従業員リストへredirect,エラー時:詳細従業員表示へredirect
	 */
	
	@RequestMapping("/update")
	public String update(
			@Validated UpdateEmployeeForm form,
			BindingResult result,
			Model model
			) {
		
		if(result.hasErrors()) {
			return showDetail(form.getId(),model,form);
		}
		
		Employee employee = service.load(Integer.parseInt(form.getId()));
		employee = form.update(employee);
		
		employee = service.update(employee);
		
		return "redirect:/employee/showList";
//		if(employee != null) {
//			return showList(model);
//		}else {
//			return "finished";
//		}
	}
	
	
	

}
