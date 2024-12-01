package employee_management.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import employee_management.bean.Account;
import employee_management.bean.Employee;
import employee_management.bean.EmployeeSubmitItem;
import employee_management.dao.ManagerDAO;

@Service
public class ManagerService {
	private final ManagerDAO managerDAO;//Khởi tạo managerDAO 

    @Autowired
    public ManagerService(ManagerDAO managerDAO) {
        this.managerDAO = managerDAO; 
    }

    public int getEmployeeCount() {
        return managerDAO.countEmployees(); 
    }
    
 // Get the count of pending absence requests
    public int getPendingAbsenceCount() {
        try {
            return managerDAO.getPendingAbsenceCount();
        } catch (SQLException e) {
            // Handle exception, perhaps log the error or throw a custom exception
            throw new RuntimeException("Failed to fetch pending absence count", e);
        }
    }

    // Get the count of pending report requests
    public int getPendingReportCount() {
        try {
            return managerDAO.getPendingReportCount();
        } catch (SQLException e) {
            // Handle exception, perhaps log the error or throw a custom exception
            throw new RuntimeException("Failed to fetch pending report count", e);
        }
    }

    // Get the details of all pending reports and absence requests
    public List<EmployeeSubmitItem> getPendingReportsAndRequests() {
        try {
            return managerDAO.getPendingReportsAndRequests();
        } catch (SQLException e) {
            // Handle exception
            throw new RuntimeException("Failed to fetch pending reports and requests", e);
        }
    }
    
    public EmployeeSubmitItem getSubmitItemDetail(Integer itemId, String itemType) {
        if ("Report".equalsIgnoreCase(itemType)) {
            return managerDAO.getReportDetail(itemId);  // Fetch report detail
        } else if ("Absence Request".equalsIgnoreCase(itemType)) {
            return managerDAO.getAbsenceRequestDetail(itemId);  // Fetch absence request detail
        }
        return null;  // If invalid type, return null or handle accordingly
    }

    // Fetch all employees
    public List<Employee> getAllEmployees() {
        return managerDAO.getAllEmployees();
    }
    
    public void addEmployee(String username, String password, String name, String gender, java.sql.Date dob, 
            String email, String phone_num, String address, String department, double salary) {
    		System.out.println("ManagerService adding employee: " + username);
    		managerDAO.addEmployee(username,password, name, gender, dob, email, phone_num, address, department, salary);
    }

    // Delete a user
    public void deleteEmployee(String username) {
    	System.out.println("ManagerService deleting employee: " + username);
        managerDAO.deleteEmployee(username);
    }
    
    public Employee getEmployeeByUsername(String username) {
        return managerDAO.getEmployeeByUsername(username); // Call the DAO to fetch the employee by ID
    }public boolean editEmployee(String username, String name, String gender, String dob, String email, String phone_num, String address, String department, double salary) {
        try {
            java.sql.Date dateOfBirth = java.sql.Date.valueOf(dob);

            // Ensure we don't modify the username, just the other fields
            return managerDAO.updateEmployee(username, name, gender, dateOfBirth, email, phone_num, address, department, salary);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    


}