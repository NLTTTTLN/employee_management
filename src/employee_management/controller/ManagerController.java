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
import employee_management.service.ManagerService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manager")
public class ManagerController {

	@Autowired
	private ManagerService managerService;

	// GET /home cho trang chính của admin
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String showManagerHomePage() {
		return "manager/dashboard";
	}

	// Redirect /admin về trang chính của admin
	@RequestMapping("/")
	public String redirectToHome(RedirectAttributes attributes) {
		return "redirect:manager/dashboard";
	}

	@RequestMapping(value ="/dashboard-info", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getDashboardInfo() {
        Map<String, Object> response = new HashMap<>();

        // Get counts
        int pendingAbsenceCount = managerService.getPendingAbsenceCount();
        int pendingReportCount = managerService.getPendingReportCount();
        int employeeCount = managerService.getEmployeeCount();

        // Get the list of pending reports and requests
        List<EmployeeSubmitItem> pendingItems = managerService.getPendingReportsAndRequests();

        // Add data to the response map
        response.put("employeeCount", employeeCount);
        response.put("pendingAbsenceCount", pendingAbsenceCount);
        response.put("pendingReportCount", pendingReportCount);
        response.put("pendingItems", pendingItems);

        return response;
    }
	
	@RequestMapping(value = "/submit-detail", method = RequestMethod.GET)
	@ResponseBody
	public EmployeeSubmitItem getSubmitDetail(@RequestParam("itemId") Integer itemId, @RequestParam("itemType") String itemType) {
		System.out.println("Searching submit item with id: " + itemId + ", type:" + itemType);
		EmployeeSubmitItem item = managerService.getSubmitItemDetail(itemId, itemType);
		System.out.println("Found Submit Item: " + item);
	    return item; // Return the employee as JSON
	}

	@RequestMapping(value = "/management", method = RequestMethod.GET)
	public String showManagerManagementPage(Model model) {

		return "manager/management";
	}

	@RequestMapping(value = "/management-data", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getEmployeeData(Model model) {
		List<Employee> employeeData = managerService.getAllEmployees();
		System.out.println("Employees: " + employeeData);

		Map<String, Object> employeeFullData = new HashMap<>();
		employeeFullData.put("employeeData", employeeData);
		return employeeFullData;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addEmployee(@RequestParam("username") String username, @RequestParam("name") String name,
			@RequestParam("gender") String gender, @RequestParam("dob") java.sql.Date dob,
			@RequestParam("email") String email, @RequestParam("phone_num") String phone_num,
			@RequestParam("address") String address, @RequestParam("department") String department,
			@RequestParam("salary") Double salary) {

		// Set password to be the same as the username
		String password = username;

		managerService.addEmployee(username, password, name, gender, dob, email, phone_num, address, department,
				salary);
		System.out.println("Adding employee: " + username);
		return "redirect:/manager/management";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deleteEmployee(@RequestParam("username") String username) {
		managerService.deleteEmployee(username);
		System.out.println("Deleting employee: " + username);
		return "redirect:/manager/management";
	}
	
	@RequestMapping(value = "/get-employee", method = RequestMethod.GET)
	@ResponseBody
	public Employee getEmployeeByUsername(@RequestParam("username") String username) {
	    // Fetch the employee from the service layer using the ID
		 System.out.println("Fetching employee with username: " + username);
		    Employee employee = managerService.getEmployeeByUsername(username);
		    System.out.println("Found employee: " + employee);
	    return employee; // Return the employee as JSON
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String editEmployee(@RequestParam String username,
                               @RequestParam String name,
                               @RequestParam String gender,
                               @RequestParam String dob,
                               @RequestParam String email,
                               @RequestParam String phone_num,
                               @RequestParam String address,
                               @RequestParam String department,
                               @RequestParam double salary) {
        try {
            // Pass the data to the service layer for processing
            boolean success = managerService.editEmployee(username, name, gender, dob, email, phone_num, address, department, salary);

            if (success) {
                System.out.println( username + "update successfully.");
            } else {
            	System.out.println( username + "update failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println( "Error while updating employee: " + e.getMessage());
        }
        return "manager/management";
    }


	/*
	 * @RequestMapping(value = "/update", method = RequestMethod.POST) public String
	 * updateEmployee(
	 * 
	 * @RequestParam("username") String username,
	 * 
	 * @RequestParam("name") String name,
	 * 
	 * @RequestParam("gender") String gender,
	 * 
	 * @RequestParam("dob") java.sql.Date dob,
	 * 
	 * @RequestParam("email") String email,
	 * 
	 * @RequestParam("phone_num") String phone_num,
	 * 
	 * @RequestParam("address") String address,
	 * 
	 * @RequestParam("department") String department,
	 * 
	 * @RequestParam("salary") Double salary) {
	 * 
	 * managerService.updateEmployee(username, name, gender, dob, email, phone_num,
	 * address, department, salary); System.out.println("Updating employee: " +
	 * username); return "redirect:/manager/management"; }
	 */

}