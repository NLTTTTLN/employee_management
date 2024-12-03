package employee_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import employee_management.bean.Account;
import employee_management.bean.Employee;
import employee_management.bean.EmployeeSubmitItem;
import employee_management.service.AccountService;
import employee_management.service.EmployeeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/employee")
public class EmployeeController{
	
	@Autowired
    private EmployeeService employeeService;
	
	
	// GET /home cho trang chính của admin
	@RequestMapping(value="/dashboard", method = RequestMethod.GET)
	public String showEmployeeHomePage(@RequestParam("id") int employeeId, Model model) {
	    // Fetch employee data based on the ID
	    Employee employeeAccount = employeeService.getEmployeeById(employeeId);
	    if (employeeAccount != null) {
	        // Add employee data to the model for rendering in the view
	    	model.addAttribute("employeeId",employeeId);
	        model.addAttribute("employee", employeeAccount);
	    } else {
	        // If the employee ID is not found, redirect to the login page
	        return "redirect:/login";
	    }
	    return "employee/dashboard";
	}
	
	@RequestMapping(value="/dashboard-info", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getEmployeeSubmitItem(@RequestParam("id") int employeeId) {
		System.out.println("Retrieved employee ID:" + employeeId);
		Map<String, Object> response = new HashMap<>();
		
		List<EmployeeSubmitItem> items = employeeService.getReportsAndRequests(employeeId);
		response.put("Submit Items", items);

        return response;
	}


	// Redirect /employee to the dashboard page
    @RequestMapping("/")
    public String redirectToDashboard(@RequestParam("id") int employeeId, RedirectAttributes attributes) {
        // Redirect to the dashboard page, passing the employee id
        return "redirect:/employee_management/employee/dashboard?id=" + employeeId;
    }
	
    
    
    
	
	@RequestMapping(value="/profile", method = RequestMethod.GET)
	public String showEmployeeProfile(@RequestParam("id") int employeeId, Model model) {
	    // Fetch employee data based on the ID
	    Employee employeeProfile = employeeService.getEmployeeById(employeeId);
	    if (employeeProfile != null) {
	        // Add employee data to the model for rendering in the view
	    	model.addAttribute("employeeId",employeeId);
	        model.addAttribute("employee", employeeProfile);
	        System.out.println("Employee ID:"+employeeId);
	        System.out.println("Employee Profile:"+employeeProfile );
	    } else {
	        // If the employee ID is not found, redirect to the login page
	        return "redirect:/login";
	    }
	    return "employee/profile";
	}
		
}