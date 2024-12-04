package employee_management.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import employee_management.bean.Manager;
import employee_management.bean.Account;
import employee_management.bean.Employee;
import employee_management.bean.EmployeeSubmitItem;
import employee_management.service.ManagerService;



@Controller
@RequestMapping("/manager")
public class ManagerController {

	@Autowired
	private ManagerService managerService;
	

	@RequestMapping(value = "/download/{fileName:.+}", method = RequestMethod.GET)
	public void downloadFile(HttpServletRequest request, HttpServletResponse response,
							@PathVariable String fileName) {
		String dataDirectory = request.getServletContext().getRealPath("/WEB-INF/uploads/reports");
		Path file = Paths.get(dataDirectory,fileName);
		if (Files.exists(file)) {
			response.setContentType("application//pdf");
			response.addHeader("Content-Disposition", "attachment; filename="+fileName);
			try
			{
				Files.copy(file, response.getOutputStream());
				response.getOutputStream().flush();
			}
			catch(IOException ex) {
				ex.printStackTrace();
			}
		}
	}


    
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
	public EmployeeSubmitItem getSubmitDetail(@RequestParam("itemId") Integer itemId, 
											@RequestParam("itemType") String itemType) {
		System.out.println("Searching submit item with id: " + itemId + ", type:" + itemType);
		EmployeeSubmitItem item = managerService.getSubmitItemDetail(itemId, itemType);
		System.out.println("Found Submit Item: " + item);
	    return item; // Return the employee as JSON
	}
	@RequestMapping(value = "/handleApproval", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> handleApproval(@RequestParam Integer itemId,
	                                          @RequestParam String itemType,
	                                          @RequestParam String action) {
	    Map<String, Object> response = new HashMap<>();
	    try {
	        boolean success = managerService.handleApproval(itemId, itemType, action);

	        if (success) {
	            response.put("success", true);
	            response.put("message", "Item " + action.toLowerCase() + " successfully.");
	        } else {
	            response.put("failed", false);
	            response.put("message", "Failed to update the item's status.");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.put("failed", false);
	        response.put("message", "Error occurred while updating the item's status.");
	    }
	    return response;
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
		System.out.println("EmployeeFullData:" + employeeFullData);
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

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String showManagerProfile(HttpSession session, Model model) {
		System.out.println("Entering showManagerProfile method");
	    // Retrieve the username from the session
	    Account loggedInUser = (Account) session.getAttribute("user");
	    if (loggedInUser == null) {
	        System.out.println("User not found in session!");
	    } else {
	        System.out.println("User found in session: " + loggedInUser.getUsername());
	    }
	    if (loggedInUser != null && "manager".equals(loggedInUser.getRole())) {
	        String username = loggedInUser.getUsername();  // Get the username from the session

	        // Assuming the service method to get manager ID by username
	        int managerId = managerService.getManagerIdByUsername(username);
            System.out.println("Manager ID: " + managerId);

	        // Fetch manager profile using the managerId
	        Manager managerProfile = managerService.getManagerById(managerId);
            System.out.println("Manager Profile: " + managerProfile);

	        if (managerProfile != null) {
	            // Add manager data to the model for rendering in the view
	            model.addAttribute("ManagerId", managerId);
	            model.addAttribute("Manager", managerProfile);


	        } else {
	            // Handle case when manager profile is not found (optional)
	            model.addAttribute("errorMessage", "Manager profile not found.");
	        }
	    } else {
	        // Handle the case where the user is not logged in or not a manager
	        model.addAttribute("errorMessage", "You are not authorized to view this page.");
	    }

	    return "manager/profile";
	}

	
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody  // This annotation will return the result as JSON
	public Map<String, Object> updateManager(@RequestParam String username,
	                                        @RequestParam String name,
	                                        @RequestParam String gender,
	                                        @RequestParam String dob,
	                                        @RequestParam String email,
	                                        @RequestParam String phone_num,
	                                        @RequestParam String address) {

	    Map<String, Object> response = new HashMap<>();
	    try {
	        boolean success = managerService.updateManager(username, name, gender, dob, email, phone_num, address);

	        if (success) {
	            response.put("success", true);
	            response.put("message", "Manager updated successfully.");
	        } else {
	            response.put("success", false);
	            response.put("message", "Failed to update manager.");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.put("success", false);
	        response.put("message", "Error while updating manager.");
	    }
	    return response;
	}
	
	@RequestMapping(value = "/change-password", method = RequestMethod.POST)
	@ResponseBody  // This annotation will return the result as JSON
	public Map<String, Object> changeManagerPassword(@RequestParam String username,
											@RequestParam String oldPassword,
	                                        @RequestParam String newPassword
	                                     ) {

	    Map<String, Object> response = new HashMap<>();
	    try {
	    	System.out.println("Receiving username:" + username +", old password:" + oldPassword + ", newPassword: " + newPassword);
	        boolean success = managerService.changeManagerPassword(username, oldPassword, newPassword);

	        if (success) {
	            response.put("success", true);
	            response.put("message", "Manager updated successfully.");
	        } else {
	            response.put("success", false);
	            response.put("message", "Failed to update manager.");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.put("success", false);
	        response.put("message", "Error while updating manager.");
	    }
	    System.out.println("Respone change password:" + response);
	    return response;
	}
}