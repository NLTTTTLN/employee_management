package employee_management.service;

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
    
    //Get employee's reports and requets
    public List<EmployeeSubmitItem> getReportsAndRequests() {
        try {
            return managerDAO.getReportsAndRequests();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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