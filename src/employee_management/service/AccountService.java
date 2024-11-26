package employee_management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import employee_management.bean.Account;
import employee_management.dao.AccountDAO;

@Service
public class AccountService {
	private final AccountDAO accountDAO;//Khởi tạo AccountDAO 

    @Autowired
    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO; 
    }

    public Account authenticate(String username, String password) {
        return accountDAO.authenticate(username, password); //Tiến hành authenticate 
    }
    
    public int getManagerCount() {
        return accountDAO.countManagers(); 
    }

    public int getEmployeeCount() {
        return accountDAO.countEmployees(); 
    }

    public List<String> getRecentManagerActivities() {
        // Your logic to get recent activities by managers
        return List.of("Manager 1 logged in", "Manager 2 created a report"); // Placeholder
    }

    public List<String> getRecentEmployeeActivities() {
        // Your logic to get recent activities by employees
        return List.of("Employee 1 submitted a request", "Employee 2 updated their profile"); // Placeholder
    }
    
    // Fetch all managers
    public List<Account> getAllManagers() {
        return accountDAO.getAllManagers();
    }

    // Fetch all employees
    public List<Account> getAllEmployees() {
        return accountDAO.getAllEmployees();
    }
    
    public void addUser(String username, String password, String role) {
    	System.out.println("AccountService adding user: " + username);
        accountDAO.addUser(username,password,role);
    }

    // Delete a user
    public void deleteUser(String username) {
    	System.out.println("AccountService deleting user: " + username);
        accountDAO.deleteUser(username);
    }

    // Promote an employee to a manager
    public void promoteToManager(String username) {
        accountDAO.promoteToManager(username);
    }
}