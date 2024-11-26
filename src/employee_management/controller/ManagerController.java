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
import employee_management.service.ManagerService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manager")
public class ManagerController{
	
	@Autowired
    private ManagerService managerService;
	
	// GET /home cho trang chính của admin
	@RequestMapping(value="/dashboard", method = RequestMethod.GET)
		public String showManagerHomePage() {
		return "manager/dashboard";
	}
	// Redirect /admin về trang chính của admin
	@RequestMapping("/")
	public String redirectToHome(RedirectAttributes attributes) {
			return "redirect:manager/dashboard";
		}
	@RequestMapping(value="/dashboard-info",method = RequestMethod.GET)
	    @ResponseBody
	    public Map<String, Object> getDashboardData() {
	        // Assuming these methods exist in your AccountService
	        int employeeCount = managerService.getEmployeeCount(); // Get employee count

	        // Prepare response data
	        Map<String, Object> dashboardData = new HashMap<>();
	        dashboardData.put("employeeCount", employeeCount);
	        
	        System.out.println("Employee Count: " + employeeCount);

	        return dashboardData; // This will be returned as JSON
	    }
		@RequestMapping(value="/management", method = RequestMethod.GET)
		public String showManagerManagementPage(Model model) {

		return "manager/management";
		}
		@RequestMapping(value="/management-data", method = RequestMethod.GET)
		@ResponseBody
		public Map<String, Object> getEmployeeData(Model model) {
			 List<Employee> employeeData = managerService.getAllEmployees();
			 System.out.println("Employees: " + employeeData);
			 
			 Map<String, Object> employeeFullData = new HashMap<>();
		     employeeFullData.put("employeeData", employeeData);
		return employeeFullData;
		}
		@RequestMapping(value = "/add", method = RequestMethod.POST)
		public String addEmployee(
		    @RequestParam("username") String username, 
		    @RequestParam("name") String name, 
		    @RequestParam("gender") String gender,
		    @RequestParam("dob") java.sql.Date dob, 
		    @RequestParam("email") String email, 
		    @RequestParam("phone_num") String phone_num,
		    @RequestParam("address") String address, 
		    @RequestParam("department") String department, 
		    @RequestParam("salary") Double salary) {
		    
		    managerService.addEmployee(username, name, gender, dob, email, phone_num, address, department, salary);
		    System.out.println("Adding employee: " + username);
		    return "redirect:/manager/management";
		}
		   @RequestMapping(value="/delete",method = RequestMethod.POST)
		    public String deleteEmployee(@RequestParam("username") String username) {
		        managerService.deleteEmployee(username);
		        System.out.println("Deleting employee: " + username);
		        return "redirect:/manager/management";
		    }


}