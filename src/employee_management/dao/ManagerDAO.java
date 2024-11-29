package employee_management.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import employee_management.bean.Account;
import employee_management.bean.Employee;
import employee_management.bean.EmployeeSubmitItem;

@Repository
public class ManagerDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/employee_management"; // Schema database
    private static final String USER = "root"; // User for database access
    private static final String PASSWORD = "Thang0366"; // Password for database access

    public ManagerDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Kết nối với database
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Authenticate 
    public Account authenticate(String username, String password) {
        Account account = null;
        String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    account = new Account(rs.getString("username"), rs.getString("password"), rs.getString("role"));
                } else {
                    System.out.println("Authentication failed for username: " + username);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
    }

    // Count employee accounts
    public int countEmployees() {
        String sql = "SELECT COUNT(*) AS count FROM account WHERE role = 'employee'";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    //Get Employee's submit items
    public List<EmployeeSubmitItem> getReportsAndRequests() throws SQLException {
        String query = """
            SELECT id, 'Report' AS type, title, (SELECT name FROM Employee WHERE id = employee_id) AS submittedBy, created_at
            FROM Reports
            UNION
            SELECT id, 'Absence Request' AS type, title, (SELECT name FROM Employee WHERE id = employee_id) AS submittedBy, created_at
            FROM AbsenceRequests
            ORDER BY created_at DESC;
        """;

        List<EmployeeSubmitItem> items = new ArrayList<>();
        try (Connection conn = getConnection();
        	 PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
            	EmployeeSubmitItem item = new EmployeeSubmitItem();
                item.setId(rs.getInt("id"));
                item.setType(rs.getString("type"));
                item.setTitle(rs.getString("title"));
                item.setSubmittedBy(rs.getString("submittedBy"));
                item.setDate(rs.getString("created_at"));
                items.add(item);
            }
        }
        System.out.println("Items:" + items);
        return items;
    }
    
    // Fetch all employees
    public List<Employee> getAllEmployees() {
        String sql = "SELECT * FROM employee";
        List<Employee> employees = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                employees.add(new Employee(
                        rs.getString("username"), 
                        rs.getString("name"), 
                        rs.getString("gender"),
                        rs.getDate("dob"), 
                        rs.getString("email"), 
                        rs.getString("phone_num"),
                        rs.getString("address"), 
                        rs.getString("department"), 
                        rs.getDouble("salary")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employees;
    }
    
    // Add a user
    public void addEmployee(String username, String password, String name, String gender, java.sql.Date dob, String email, String phone_num, String address, String department, double salary) {
        String accountSql = "INSERT INTO account (username, password, role) VALUES (?, ?, 'employee')";
        String employeeSql = "INSERT INTO employee (username, name, gender, dob, email, phone_num, address, department, salary) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection()) {
            // Start a transaction
            conn.setAutoCommit(false);
            
            // Insert into account table
            try (PreparedStatement stmt = conn.prepareStatement(accountSql)) {
                stmt.setString(1, username);
                stmt.setString(2, password); // Password should be passed to this method
                stmt.executeUpdate();
            }

            // Insert into employee table
            try (PreparedStatement stmt = conn.prepareStatement(employeeSql)) {
                stmt.setString(1, username);
                stmt.setString(2, name);
                stmt.setString(3, gender);
                stmt.setDate(4, dob);
                stmt.setString(5, email);
                stmt.setString(6, phone_num);
                stmt.setString(7, address);
                stmt.setString(8, department);
                stmt.setDouble(9, salary);
                stmt.executeUpdate();
            }

            // Commit transaction
            conn.commit();
            System.out.println("Added employee and account for username: " + username);
        } catch (SQLException e) {
            // Rollback transaction on error
            try (Connection conn = getConnection()) {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    // Delete a user
    public void deleteEmployee(String username) {
        String deleteEmployeeSql = "DELETE FROM employee WHERE username = ?";
        String deleteAccountSql = "DELETE FROM account WHERE username = ?";
        
        try (Connection conn = getConnection()) {
            // Start a transaction
            conn.setAutoCommit(false);
            
            // Delete from employee table
            try (PreparedStatement stmt = conn.prepareStatement(deleteEmployeeSql)) {
                stmt.setString(1, username);
                stmt.executeUpdate();
            }

            // Delete from account table
            try (PreparedStatement stmt = conn.prepareStatement(deleteAccountSql)) {
                stmt.setString(1, username);
                stmt.executeUpdate();
            }

            // Commit transaction
            conn.commit();
            System.out.println("Deleted employee and account for username: " + username);
        } catch (SQLException e) {
            // Rollback transaction on error
            try (Connection conn = getConnection()) {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        }
    }
    
    // Fetch employee by ID
    public Employee getEmployeeByUsername(String username) {
        String sql = "SELECT * FROM employee WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1,username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Employee employee = new Employee();
                    employee.setUsername(rs.getString("username"));
                    employee.setName(rs.getString("name"));
                    employee.setGender(rs.getString("gender"));
                    employee.setDob(rs.getDate("dob"));
                    employee.setEmail(rs.getString("email"));
                    employee.setphone_num(rs.getString("phone_num"));
                    employee.setAddress(rs.getString("address"));
                    employee.setDepartment(rs.getString("department"));
                    employee.setSalary(rs.getDouble("salary"));
                    return employee;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean updateEmployee(String username, String name, String gender, java.sql.Date dob, String email, String phone_num, String address, String department, double salary) {
        String sql = "UPDATE employee SET name = ?, gender = ?, dob = ?, email = ?, phone_num = ?, address = ?, department = ?, salary = ? WHERE username = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set parameters for the PreparedStatement
            stmt.setString(1, name);
            stmt.setString(2, gender);
            stmt.setDate(3, dob);
            stmt.setString(4, email);
            stmt.setString(5, phone_num);
            stmt.setString(6, address);
            stmt.setString(7, department);
            stmt.setDouble(8, salary);
            stmt.setString(9, username); // Ensure username is the last parameter (where the condition is)

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0; // If at least one row was updated, return true

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // If an error occurred, return false
        }
    }

}
