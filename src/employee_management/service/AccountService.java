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
        // Your logic to get the count of managers from the database
        return 10; // Placeholder
    }

    public int getEmployeeCount() {
        // Your logic to get the count of employees from the database
        return 50; // Placeholder
    }

    public List<String> getRecentManagerActivities() {
        // Your logic to get recent activities by managers
        return List.of("Manager 1 logged in", "Manager 2 created a report"); // Placeholder
    }

    public List<String> getRecentEmployeeActivities() {
        // Your logic to get recent activities by employees
        return List.of("Employee 1 submitted a request", "Employee 2 updated their profile"); // Placeholder
    }
}