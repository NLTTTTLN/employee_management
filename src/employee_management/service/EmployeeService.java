package employee_management.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import employee_management.bean.Employee;
import employee_management.bean.EmployeeSubmitItem;
import employee_management.dao.EmployeeDAO;

@Service
public class EmployeeService {
	private final EmployeeDAO employeeDAO;//Khởi tạo AccountDAO 

    @Autowired
    public EmployeeService(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO; 
    }
 
    public Employee getEmployeeById(Integer id) {
    	return employeeDAO.getEmployeeById(id);
    }
 // Get the details of all pending reports and absence requests
    public List<EmployeeSubmitItem> getReportsAndRequests(Integer id) {
        try {
            return employeeDAO.getReportsAndRequests(id);
        } catch (SQLException e) {
            // Handle exception
            throw new RuntimeException("Failed to fetch reports and requests", e);
        }
    }
    public EmployeeSubmitItem getSubmitItemDetail(Integer itemId, String itemType) {
        if ("Report".equalsIgnoreCase(itemType)) {
            return employeeDAO.getReportDetail(itemId);  // Fetch report detail
        } else if ("Absence Request".equalsIgnoreCase(itemType)) {
            return employeeDAO.getAbsenceRequestDetail(itemId);  // Fetch absence request detail
        }
        return null;  // If invalid type, return null or handle accordingly
    }
    public boolean deleteSubmit(Integer itemId, String itemType) {
    	System.out.println("ManagerService deleting submit ID: " + itemId + ", type: " + itemType);
        
        try {
            // Determine the status based on action
            boolean status = employeeDAO.deleteSubmit(itemId,itemType);
            
            // Pass the status to DAO layer
            return status;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean createSubmitItem(EmployeeSubmitItem item) {
        try {
            employeeDAO.saveSubmitItem(item);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean updateEmployee(String username, String name, String gender, String dob, String email, String phone_num, String address) {
        try {
            java.sql.Date dateOfBirth = java.sql.Date.valueOf(dob);

            // Ensure we don't modify the username, just the other fields
            return employeeDAO.updateEmployee(username, name, gender, dateOfBirth, email, phone_num, address);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean changeEmployeePassword(String username, String oldPassword, String newPassword) {
        try {
            

            // Ensure we don't modify the username, just the other fields
            return employeeDAO.changeEmployeePassword(username, oldPassword, newPassword);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}