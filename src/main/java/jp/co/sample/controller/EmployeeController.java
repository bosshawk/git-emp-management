package jp.co.sample.controller;

import java.util.ArrayList;
import java.util.List;

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
	
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/showList")
	public String showList(Model model) {
		//List<Employee> employeeList = new ArrayList<>();
		List<Employee> employeeList = service.findAll();
		model.addAttribute("employeeList", employeeList);
		return "employee/list";
	}
	
	@RequestMapping("/showDetail")
	public String showDetail(String id,Model model){
		Employee employee = new Employee();
		employee = service.load(Integer.parseInt(id));
		model.addAttribute("employee", employee);
		return "employee/detail";
	}
	
	@RequestMapping("/update")
	public String update(
			@Validated UpdateEmployeeForm form,
			BindingResult result,
			Model model
			) {
		if(result.hasErrors()) {
			return showDetail(form.getId(),model);
		}
		Employee employee = new Employee();
		employee = service.load(Integer.parseInt(form.getId()));
		Integer dependentsCount = Integer.parseInt(form.getDependentsCount());
		employee.setDependentsCount(dependentsCount);
		employee = service.update(employee);
		if(employee != null) {
			return showList(model);
		}else {
			return "finished";
		}
	}
	
	
	

}
