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
import employee_management.service.AccountService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController{
	
	@Autowired
    private AccountService accountService;
	
	// GET /home cho trang chính của admin
	@RequestMapping(value="/dashboard", method = RequestMethod.GET)
		public String showAdminHomePage() {
		return "admin/dashboard";
	}
	// Redirect /admin về trang chính của admin
	@RequestMapping("/")
	public String redirectToHome(RedirectAttributes attributes) {
			return "redirect:admin/dashboard";
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
		@RequestMapping(value="/management", method = RequestMethod.GET)
		public String showAdminManagementPage(Model model) {
	        model.addAttribute("managers", accountService.getAllManagers());
	        model.addAttribute("employees", accountService.getAllEmployees());
		return "admin/management";
		}
		   @RequestMapping(value="/delete",method = RequestMethod.POST)
		    public String deleteUser(@RequestParam("username") String username) {
		        accountService.deleteUser(username);
		        return "redirect:/admin/management";
		    }

		   @RequestMapping(value="/promote",method = RequestMethod.POST)
		    public String promoteToManager(@RequestParam("username") String username) {
		        accountService.promoteToManager(username);
		        return "redirect:/admin/management";
		    }
}