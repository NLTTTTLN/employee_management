package employee_management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import employee_management.bean.Account;
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


    // Fetch all employees
    public List<Account> getAllEmployees() {
        return managerDAO.getAllEmployees();
    }
    
    public void addEmployee(String username, String password, String role) {
    	System.out.println("ManagerService adding employee: " + username);
        managerDAO.addEmployee(username,password,role);
    }

    // Delete a user
    public void deleteEmployee(String username) {
    	System.out.println("ManagerService deleting employee: " + username);
        managerDAO.deleteEmployee(username);
    }

}