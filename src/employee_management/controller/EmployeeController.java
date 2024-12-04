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

import employee_management.bean.Employee;
import employee_management.bean.EmployeeSubmitItem;
import employee_management.service.EmployeeService;

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
	@RequestMapping(value = "/submit-detail", method = RequestMethod.GET)
	@ResponseBody
	public EmployeeSubmitItem getSubmitDetail(@RequestParam("itemId") Integer itemId, 
											@RequestParam("itemType") String itemType) {
		System.out.println("Searching submit item with id: " + itemId + ", type:" + itemType);
		EmployeeSubmitItem item = employeeService.getSubmitItemDetail(itemId, itemType);
		System.out.println("Found Submit Item: " + item);
	    return item; // Return the employee as JSON
	}

	// Redirect /employee to the dashboard page
    @RequestMapping("/")
    public String redirectToDashboard(@RequestParam("id") int employeeId, RedirectAttributes attributes) {
        // Redirect to the dashboard page, passing the employee id
        return "redirect:employee/dashboard?id=" + employeeId;
    }
	
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
    @RequestMapping(value = "/delete-submit", method = RequestMethod.POST)
    @ResponseBody
	public Map<String, Object> deleteEmployee(@RequestParam("itemId") Integer itemId, 
			@RequestParam("itemType") String itemType) {
    	Map<String, Object> response = new HashMap<>();
		System.out.println("Deleting submit ID: " + itemId + ",type: " + itemType);
		try {
	        boolean success = employeeService.deleteSubmit(itemId, itemType);;

	        if (success) {
	            response.put("success", true);
	            response.put("message", "Item delete successfully.");
	        } else {
	            response.put("failed", false);
	            response.put("message", "Failed to delete the item.");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.put("failed", false);
	        response.put("message", "Error occurred while  the item's status.");
	    }
	    return response;
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
	
	@RequestMapping(value = "/create-item", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createSubmitItem(HttpServletRequest request,
	        @RequestParam("title") String title,
	        @RequestParam("description") String description,
	        @RequestParam("employee_id") int employeeId,
	        @RequestParam("type") String type,
	        @RequestParam(value = "file", required = false) MultipartFile file) {
	    
	    Map<String, Object> response = new HashMap<>();

	    // Create a new EmployeeSubmitItem object to save in the database
	    EmployeeSubmitItem item = new EmployeeSubmitItem();
	    item.setTitle(title);
	    item.setDescription(description);
	    item.setEmployeeId(employeeId);
	    item.setType(type);
	   
	    // If the type is 'Report' and a file is uploaded
	    if ("Report".equalsIgnoreCase(type) && file != null && !file.isEmpty()) {
	        try {
	            // Save the file to the server directory (uploads/reports)
	        	String dataDirectory = "D:/Java/employee_management/WebContent/WEB-INF/uploads/reports";
	            String fileName = file.getOriginalFilename();
	            Path path = Paths.get(dataDirectory, fileName);
	            System.out.println("Save file directory:" + dataDirectory);
	            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

	            // Save the file path to the item (this is stored in the database)
	            item.setFilePath("/uploads/reports/" + fileName);

	            // Save the item to the database (using EmployeeService)
	            boolean success = employeeService.createSubmitItem(item);
	            if (success) {
	                response.put("success", true);
	                response.put("message", "Report created successfully.");
	            } else {
	                response.put("success", false);
	                response.put("message", "Failed to create the report.");
	            }

	        } catch (IOException e) {
	            response.put("success", false);
	            response.put("message", "File upload failed: " + e.getMessage());
	            e.printStackTrace();
	        }
	    } else {
	    	System.out.println("Item ready for story in DTB:" + item);
	        // If no file, just create the item without file path
	        boolean success = employeeService.createSubmitItem(item);
	        if (success) {
	            response.put("success", true);
	            response.put("message", "Item created successfully.");
	        } else {
	            response.put("success", false);
	            response.put("message", "Failed to create the item.");
	        }
	    }

	    return response;
	}

		
}