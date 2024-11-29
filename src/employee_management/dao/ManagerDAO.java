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
}
