package employee_management.dao;

import java.sql.Connection;
import java.sql.Date;
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
    private static final String USER = ""; // Nhập tên user truy cập database
    private static final String PASSWORD = ""; // Nhập mật khẩu truy cập database

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
    
    public List<String> getRecentManagerActivities() {
        List<String> activities = new ArrayList<>();
        String query = "SELECT activityType, description, timestamp FROM ActivityLog WHERE userType = 'manager' ORDER BY timestamp DESC LIMIT 10";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                String activity = rs.getString("activityType") + ": " + rs.getString("description") + " at " + rs.getTimestamp("timestamp");
                activities.add(activity);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions
        }
        
        return activities;
    }

    public List<String> getRecentEmployeeActivities() {
        List<String> activities = new ArrayList<>();
        String query = "SELECT activityType, description, timestamp FROM ActivityLog WHERE userType = 'employee' ORDER BY timestamp DESC LIMIT 10";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                String activity = rs.getString("activityType") + ": " + rs.getString("description") + " at " + rs.getTimestamp("timestamp");
                activities.add(activity);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions
        }
        
        return activities;
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
        String insertRoleSql = "";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            // Insert the user into the 'account' table
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role);
            stmt.executeUpdate();
            System.out.println("Added user with username: " + username);

            // Depending on the role, insert the user into the respective table
            if ("manager".equals(role)) {
                // Insert into 'manager' table
                insertRoleSql = "INSERT INTO manager (username, name, gender, dob, email) VALUES (?,?,?,?,?)";
                try (PreparedStatement stmtManager = conn.prepareStatement(insertRoleSql)) {
                	stmtManager.setString(1, username);
                	stmtManager.setString(2, "Welcome");
                	stmtManager.setString(3, "Nam");
                	stmtManager.setDate(4, Date.valueOf("1990-05-15")); 
                	stmtManager.setString(5, "welcome@welcome.welcome");
                    stmtManager.executeUpdate();
                    System.out.println("Added manager with username: " + username);
                }
            } else if ("employee".equals(role)) {
                // Insert into 'employee' table
            	insertRoleSql = "INSERT INTO employee (username, name, gender, dob, email, department) VALUES (?,?,?,?,?,?)";
                try (PreparedStatement stmtEmployee = conn.prepareStatement(insertRoleSql)) {
                    stmtEmployee.setString(1, username);
                    stmtEmployee.setString(2, "Welcome");
                    stmtEmployee.setString(3, "Nam");
                    stmtEmployee.setDate(4, Date.valueOf("1990-05-15")); 
                    stmtEmployee.setString(5, "welcome@welcome.welcome");
                    stmtEmployee.setString(6, "Welcome");
                    stmtEmployee.executeUpdate();
                    System.out.println("Added employee with username: " + username);
                }
            } else {
                System.out.println("Invalid role: " + role);
            }

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

    public void promoteToManager(String username) {
        String updateAccountSql = "UPDATE account SET role = 'manager' WHERE username = ?";
        String insertManagerSql = "INSERT INTO manager (username, name, gender, dob, email, phone_num, address) " +
                                   "SELECT username, name, gender, dob, email, phone_num, address FROM employee WHERE username = ?";

        try (Connection conn = getConnection()) {
            // Start a transaction
            conn.setAutoCommit(false);

            // Update the account role to 'manager'
            try (PreparedStatement updateStmt = conn.prepareStatement(updateAccountSql)) {
                updateStmt.setString(1, username);
                updateStmt.executeUpdate();
            }

            // Insert the employee's details into the manager table
            try (PreparedStatement insertStmt = conn.prepareStatement(insertManagerSql)) {
                insertStmt.setString(1, username);
                insertStmt.executeUpdate();
            }

            // Commit the transaction
            conn.commit();
            System.out.println("Successfully promoted employee with username '" + username + "' to manager.");

        } catch (Exception e) {
            e.printStackTrace();

            // Rollback transaction on error
            try (Connection conn = getConnection()) {
                conn.rollback();
            } catch (Exception rollbackEx) {
                rollbackEx.printStackTrace();
            }
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
            e.printStackTrace();  // Log exception in production code
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
