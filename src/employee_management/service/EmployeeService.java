package employee_management.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import employee_management.bean.Account;
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
}