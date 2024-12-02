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
public class AccountDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/employee_management"; // Nhập tên Schema database
    private static final String USER = "root"; // Nhập tên user truy cập database
    private static final String PASSWORD = "Thang0366"; // Nhập mật khẩu truy cập database

    public AccountDAO() {
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
        Account account = null; // Khởi tạo Object Account trống
        String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username); // Gán username cần authenticate vào truy vấn
            stmt.setString(2, password); // Gán password cần authenticate vào truy vấn
            System.out.println("Executing query: " + sql + " with username: " + username);
            try (ResultSet rs = stmt.executeQuery()) { // Tạo biến rs chứa kết quả sau khi truy vấn
                if (rs.next()) {
                    // Nếu có Row trùng với truy vấn, hàm if trả về true, tiến hành gán thông tin của user tương ứng vào Object account
                    account = new Account(rs.getString("username"), rs.getString("password"), rs.getString("role"));
                } else {
                    // Nếu không có Row trùng với truy vấn, hàm if trả về false, authenticate thất bại
                    System.out.println("Authentication failed for username: " + username);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
    }

    // Count manager accounts
    public int countManagers() {
        String sql = "SELECT COUNT(*) AS count FROM account WHERE role = 'manager'";
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
    // Fetch all managers
    public List<Account> getAllManagers() {
        String sql = "SELECT * FROM account WHERE role = 'manager'";
        List<Account> managers = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                managers.add(new Account(rs.getString("username"), rs.getString("password"), rs.getString("role")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return managers;
    }

    // Fetch all employees
    public List<Account> getAllEmployees() {
        String sql = "SELECT * FROM account WHERE role = 'employee'";
        List<Account> employees = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                employees.add(new Account(rs.getString("username"), rs.getString("password"), rs.getString("role")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employees;
    }
    
    // Add a user
    public void addUser(String username, String password, String role) {
        String sql = "INSERT INTO account (username, password, role) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role);
            stmt.executeUpdate();
            System.out.println("Added user with username: " + username);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Delete a user
    public void deleteUser(String username) {
        String sql = "DELETE FROM account WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
            System.out.println("Executing query: " + sql + " with username: " + username);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Promote an employee to a manager
    public void promoteToManager(String username) {
        String sql = "UPDATE account SET role = 'manager' WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Integer getAccountId(String username) {
        String roleSql = "SELECT role FROM account WHERE username = ?";
        String idSql = null;
        
        try (Connection conn = getConnection();
             PreparedStatement roleStmt = conn.prepareStatement(roleSql)) {

            // Set the username parameter in the query to get the user's role
            roleStmt.setString(1, username);

            // Execute the query to retrieve the user's role
            try (ResultSet roleRs = roleStmt.executeQuery()) {
                if (roleRs.next()) {
                    String role = roleRs.getString("role");
                    
                    // Based on the role, set the appropriate SQL query to fetch the ID
                    if ("employee".equals(role)) {
                        idSql = "SELECT employee_id FROM employee WHERE username = ?";
                    } else if ("manager".equals(role)) {
                        idSql = "SELECT manager_id FROM manager WHERE username = ?";
                    } else {
                        return null;  // If role is not 'employee' or 'manager', return null
                    }
                } else {
                    return null;  // No matching username found
                }
            }

            // Now that we have the correct SQL based on role, fetch the ID
            if (idSql != null) {
                try (PreparedStatement idStmt = conn.prepareStatement(idSql)) {
                    idStmt.setString(1, username);

                    try (ResultSet idRs = idStmt.executeQuery()) {
                        if (idRs.next()) {
                            // Return the appropriate ID based on the role
                            if (idSql.contains("employee_id")) {
                                return idRs.getInt("employee_id");  // If the role is 'employee', return employee_id
                            } else if (idSql.contains("manager_id")) {
                                return idRs.getInt("manager_id");  // If the role is 'manager', return manager_id
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // You should log this in production with a logging framework like SLF4J or Log4j
        }
        
        return null;  // Return null if no ID was found
    }

    
    public Employee getEmployeeById(Integer id) {
        String sql = "SELECT e.employee_id, e.username, e.name, e.gender, e.dob, e.email, e.phone_num, e.address, e.department, e.salary " +
                     "FROM employee e " +
                     "WHERE e.employee_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);  // Set the employee ID parameter

            // Execute the query
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Create a new Employee object and populate it with data from the result set
                    Employee employee = new Employee();
                    employee.setEmployeeId(rs.getInt("employee_id"));
                    employee.setUsername(rs.getString("username"));
                    employee.setName(rs.getString("name"));
                    employee.setGender(rs.getString("gender"));
                    employee.setDob(rs.getDate("dob"));
                    employee.setEmail(rs.getString("email"));
                    employee.setphone_num(rs.getString("phone_num"));
                    employee.setAddress(rs.getString("address"));
                    employee.setDepartment(rs.getString("department"));
                    employee.setSalary(rs.getDouble("salary"));

                    return employee;  // Return the Employee object populated with data
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // You might want to log this in a production environment
        }
        
        return null;  // Return null if no employee is found with the given ID
    }


}
