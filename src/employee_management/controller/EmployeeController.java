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
import employee_management.service.AccountService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/employee")
public class EmployeeController{
	
	@Autowired
    private AccountService accountService;
	
	@RequestMapping(value="/dashboard123", method = RequestMethod.GET)
	public String showAdminHomePage() {
	return "employee/dashboard";
}
	// GET /home cho trang chính của admin
	@RequestMapping(value="/dashboard", method = RequestMethod.GET)
	public String showEmployeeHomePage(@RequestParam("id") int employeeId, Model model) {
	    // Fetch employee data based on the ID
	    Employee employeeAccount = accountService.getEmployeeById(employeeId);
	    if (employeeAccount != null) {
	        // Add employee data to the model for rendering in the view
	        model.addAttribute("employee", employeeAccount);
	    } else {
	        // If the employee ID is not found, redirect to the login page
	        return "redirect:/login";
	    }
	    return "employee/dashboard";
	}


    // Redirect /employee to the dashboard page
	// Redirect /employee to the dashboard page
    @RequestMapping("/")
    public String redirectToDashboard(@RequestParam("id") int employeeId, RedirectAttributes attributes) {
        // Redirect to the dashboard page, passing the employee id
        return "redirect:/employee_management/employee/dashboard?id=" + employeeId;
    }
	@RequestMapping(value="/dashboard-info",method = RequestMethod.GET)
	    @ResponseBody
	    public Map<String, Object> getDashboardData() {
	        // Assuming these methods exist in your AccountService
	        int managerCount = accountService.getManagerCount(); // Get manager count
	        int employeeCount = accountService.getEmployeeCount(); // Get employee count
	        List<String> managerActivities = accountService.getRecentManagerActivities(); // Recent manager activities
	        List<String> employeeActivities = accountService.getRecentEmployeeActivities(); // Recent employee activities

	        // Prepare response data
	        Map<String, Object> dashboardData = new HashMap<>();
	        dashboardData.put("managerCount", managerCount);
	        dashboardData.put("employeeCount", employeeCount);
	        dashboardData.put("managerActivities", managerActivities);
	        dashboardData.put("employeeActivities", employeeActivities);
	        
	        System.out.println("Manager Count: " + managerCount);
	        System.out.println("Employee Count: " + employeeCount);

	        return dashboardData; // This will be returned as JSON
	    }
		
}