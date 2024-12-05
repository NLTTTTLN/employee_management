package employee_management.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import employee_management.bean.Account;
import employee_management.bean.Employee;
import employee_management.bean.EmployeeSubmitItem;
import employee_management.bean.Manager;

@Repository
public class ManagerDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/employee_management"; // Schema database
    private static final String USER = ""; // User for database access
    private static final String PASSWORD = ""; // Password for database access

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
    
    public void logActivity(String username, String userType, String activityType, String description) {
        String sql = "INSERT INTO ActivityLog (username, userType, activityType, description) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, userType);
            stmt.setString(3, activityType);
            stmt.setString(4, description);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
 // Method to get Manager ID by username
    public int findManagerIdByUsername(String username) {
        String sql = "SELECT manager_id FROM manager WHERE username = ?";  // Assuming role is stored in the 'account' table

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the username in the query
            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Return the manager_id if found
                    return rs.getInt("manager_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;  // Return -1 if manager is not found
    }
    
    public Manager getManagerById(Integer id) {
        String sql = "SELECT manager_id, username, name, gender, dob, email, phone_num, address " +
                     "FROM manager " +
                     "WHERE manager_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);  // Set the manager ID parameter

            // Execute the query
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Create a new Manager object and populate it with data from the result set
                    Manager manager = new Manager();
                    manager.setManagerId(rs.getInt("manager_id"));
                    manager.setUsername(rs.getString("username"));
                    manager.setName(rs.getString("name"));
                    manager.setGender(rs.getString("gender"));
                    manager.setDob(rs.getDate("dob"));
                    manager.setEmail(rs.getString("email"));
                    manager.setphone_num(rs.getString("phone_num"));
                    manager.setAddress(rs.getString("address"));

                    return manager;  // Return the Manager object populated with data
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // You might want to log this in a production environment
        }

        return null;  // Return null if no manager is found with the given ID
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
    
    // Method to get the count of pending absence requests
    public int getPendingAbsenceCount() throws SQLException {
        String query = "SELECT COUNT(*) FROM AbsenceRequests WHERE status = 'Đang chờ'";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);  // Return the count
            }
            return 0;
        }
    }

    // Method to get the count of pending report requests
    public int getPendingReportCount() throws SQLException {
        String query = "SELECT COUNT(*) FROM Reports WHERE status = 'Đang chờ'";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);  // Return the count
            }
            return 0;
        }
    }

    // Method to get both the pending reports and pending absence requests
    public List<EmployeeSubmitItem> getPendingReportsAndRequests() throws SQLException {
        String query = """
            SELECT id, 'Report' AS type, title, e.name AS submittedBy, created_at
            FROM Reports r
            JOIN Employee e ON r.employee_id = e.employee_id
            WHERE r.status = 'Đang chờ'
            UNION
            SELECT id, 'Absence Request' AS type, title, e.name AS submittedBy, created_at
            FROM AbsenceRequests ar
            JOIN Employee e ON ar.employee_id = e.employee_id
            WHERE ar.status = 'Đang chờ'
            ORDER BY created_at DESC;
        """;

        List<EmployeeSubmitItem> items = new ArrayList<>();
        try (Connection conn = getConnection();  // Get connection using your DB utility
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                EmployeeSubmitItem item = new EmployeeSubmitItem();
                item.setId(rs.getInt("id"));
                item.setType(rs.getString("type"));
                item.setTitle(rs.getString("title"));
                item.setSubmittedBy(rs.getString("submittedBy"));

                // Convert timestamp to a readable date format
                Timestamp timestamp = rs.getTimestamp("created_at");
                item.setDate(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp));

                items.add(item);
            }
        }
        return items;
    }
    // Handle approval or rejection of an item
    public boolean handleApproval(Integer itemId, String itemType, String status) {
        String sql = "";
        String itemTitle = null;  // To store the item title for logging purposes
        System.out.println("Status: " + status);
        System.out.println("ItemId: " + itemId);
        System.out.println("ItemType: " + itemType);
        // Determine the correct table and column for the item based on its type
        if ("Report".equalsIgnoreCase(itemType)) {
            // Update status for reports
            sql = "UPDATE Reports SET status = ? WHERE id = ?";
            


        } else if ("Absence Request".equalsIgnoreCase(itemType)) {
            // Update status for absence requests
            sql = "UPDATE AbsenceRequests SET status = ? WHERE id = ?";
            
        }

        

        // Proceed with updating the status
        try (Connection conn = getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the parameters for the SQL query
            stmt.setString(1, status);  // Set the new status (Approve/Reject)
            stmt.setInt(2, itemId);     // Set the item ID
            
            // Execute the update
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0 ) {
                // Log the approval/rejection activity
                logActivity(" ", "manager", status + " " + itemType, 
                            itemType + " với tiêu đề \"" + itemTitle + "\" và ID " + itemId + " đã  " + status + " bởi quản lý");
            }

            // If rowsAffected > 0, the update was successful
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    
 // Fetch Report details
    public EmployeeSubmitItem getReportDetail(Integer itemId) {
        String query = """
            SELECT r.id, r.title, r.description, r.file_path, e.name AS submittedBy, r.created_at
            FROM Reports r
            JOIN Employee e ON r.employee_id = e.employee_id
            WHERE r.id = ?;
        """;

        try (Connection conn = getConnection();  // Get connection using your DB utility
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, itemId);  // Set the itemId as the first parameter

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    EmployeeSubmitItem report = new EmployeeSubmitItem();
                    report.setId(rs.getInt("id"));
                    report.setType("Report");  // Set type as "Report"
                    report.setTitle(rs.getString("title"));
                    report.setDescription(rs.getString("description"));
                    report.setFilePath(rs.getString("file_path"));
                    report.setSubmittedBy(rs.getString("submittedBy"));

                    // Convert timestamp to a readable date format
                    Timestamp timestamp = rs.getTimestamp("created_at");
                    report.setDate(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp));

                    return report;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


 // Fetch Absence Request details
    public EmployeeSubmitItem getAbsenceRequestDetail(Integer itemId) {
        String query = """
            SELECT ar.id, ar.title, ar.description, e.name AS submittedBy, ar.created_at
            FROM AbsenceRequests ar
            JOIN Employee e ON ar.employee_id = e.employee_id
            WHERE ar.id = ?;
        """;

        try (Connection conn = getConnection();  // Get connection using your DB utility
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, itemId);  // Set the itemId as the first parameter

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    EmployeeSubmitItem absenceRequest = new EmployeeSubmitItem();
                    absenceRequest.setId(rs.getInt("id"));
                    absenceRequest.setType("Absence Request");  // Set type as "Absence Request"
                    absenceRequest.setTitle(rs.getString("title"));
                    absenceRequest.setDescription(rs.getString("description"));
                    absenceRequest.setSubmittedBy(rs.getString("submittedBy"));

                    // Convert timestamp to a readable date format
                    Timestamp timestamp = rs.getTimestamp("created_at");
                    absenceRequest.setDate(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp));

                    return absenceRequest;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
        String accountSql = "INSERT INTO account (username, password, role) VALUES (?, ?, 'employee')"; // The new employee has role 'employee'
        String employeeSql = "INSERT INTO employee (username, name, gender, dob, email, phone_num, address, department, salary) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection()) {
            // Start a transaction
            conn.setAutoCommit(false);

            // Insert into account table (set role as 'employee')
            try (PreparedStatement stmt = conn.prepareStatement(accountSql)) {
                stmt.setString(1, username);
                stmt.setString(2, password); // Password passed to this method
                stmt.executeUpdate();
            }
            System.out.println("Gender:" + gender);
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

            // Log activity: Manager adding employee
            logActivity(" ", "manager", "Add Employee", "Quản lý '" + "' thêm nhân viên mới với tên tài khoản: '" + username + "'.");

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
            logActivity(" ", "manager", "Delete Employee", "Quản lý '" + "' xóa nhân viên với tên tài khoản: '" + username + "'.");
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
            if(rowsUpdated > 0) {
            	logActivity(" ", "manager", "Update Employee", "Quản lý '" + "' cập nhật thông tin cá nhân của nhân viên: '" + username + "'.");
            }
            return rowsUpdated > 0; // If at least one row was updated, return true

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // If an error occurred, return false
        }
    }
    
    public boolean updateManager(String username, String name, String gender, java.sql.Date dob, String email, String phone_num, String address) {
        String sql = "UPDATE manager SET name = ?, gender = ?, dob = ?, email = ?, phone_num = ?, address = ? WHERE username = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set parameters for the PreparedStatement
            stmt.setString(1, name);
            stmt.setString(2, gender);
            stmt.setDate(3, dob);
            stmt.setString(4, email);
            stmt.setString(5, phone_num);
            stmt.setString(6, address);
            stmt.setString(7, username); // Ensure username is the last parameter (where the condition is)

            int rowsUpdated = stmt.executeUpdate();

            // Log activity if the update is successful
            if (rowsUpdated > 0) {
                logActivity(username, "manager", "Update Manager", "Quản lý '" + username + "' cập nhật thông tin cá nhân '" +  "'.");
            }

            return rowsUpdated > 0; // If at least one row was updated, return true

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // If an error occurred, return false
        }
    }

    
    public boolean changeManagerPassword(String username, String oldPassword, String newPassword) {
    	String sql = "UPDATE account SET password = ? WHERE username = ? AND password = ?";
    	System.out.println("Start checking for username:" + username +", old password:" + oldPassword + ", newPassword: " + newPassword);
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set parameters for the PreparedStatement
            stmt.setString(1, newPassword);
            stmt.setString(2, username);
            stmt.setString(3, oldPassword); // Ensure username is the last parameter (where the condition is)

            int rowsUpdated = stmt.executeUpdate();
            
            if (rowsUpdated > 0) {
                logActivity(username, "manager", "Update Manager", "Quản lý '" + username + "' thay đổi mật khẩu '" +  "'.");
            }
            return rowsUpdated > 0; // If at least one row was updated, return true

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // If an error occurred, return false
        }
    }
}
