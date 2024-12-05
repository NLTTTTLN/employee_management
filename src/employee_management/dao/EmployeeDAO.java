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


import employee_management.bean.Employee;
import employee_management.bean.EmployeeSubmitItem;

@Repository
public class EmployeeDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/employee_management"; // Nhập tên Schema database
    private static final String USER = "root"; // Nhập tên user truy cập database
    private static final String PASSWORD = "Thang0366"; // Nhập mật khẩu truy cập database

    public EmployeeDAO() {
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
    public List<EmployeeSubmitItem> getReportsAndRequests(Integer id) throws SQLException {
    	System.out.println("Start packaging employee submits with employee's ID:" + id);
        String query = """
            SELECT id, 'Report' AS type, title, e.name AS submittedBy, r.status, r.created_at
            FROM Reports r
            JOIN Employee e ON r.employee_id = e.employee_id
            WHERE r.employee_id = ?  -- Filter by the specific employee's ID
            UNION
            SELECT id, 'Absence Request' AS type, title, e.name AS submittedBy, ar.status, ar.created_at
            FROM AbsenceRequests ar
            JOIN Employee e ON ar.employee_id = e.employee_id
            WHERE ar.employee_id = ?  -- Filter by the specific employee's ID
            ORDER BY created_at DESC;
        """;

        List<EmployeeSubmitItem> items = new ArrayList<>();
        try (Connection conn = getConnection();  // Get connection using your DB utility
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set the employeeId parameter for both parts of the UNION query
            stmt.setInt(1, id);  // Set the employeeId for the Reports table
            stmt.setInt(2, id);  // Set the employeeId for the AbsenceRequests table

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    EmployeeSubmitItem item = new EmployeeSubmitItem();
                    item.setId(rs.getInt("id"));
                    item.setType(rs.getString("type"));
                    item.setTitle(rs.getString("title"));
                    item.setStatus(rs.getString("status"));  // Add status (Pending, Approved, Rejected)

                    // Convert timestamp to a readable date format
                    Timestamp timestamp = rs.getTimestamp("created_at");
                    item.setDate(new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp));

                    items.add(item);
                }
            }
        }
        return items;
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
    
 // Method to delete a submission by itemId and itemType (either "Report" or "Absence Request")
    public boolean deleteSubmit(Integer itemId, String itemType) {
        // Initialize SQL query variable
        String sql = "";
        String username = null;

        // Set the SQL query based on the itemType (either "Report" or "Absence Request")
        if ("Report".equalsIgnoreCase(itemType)) {
            sql = "DELETE FROM Reports WHERE id = ?";
        } else if ("Absence Request".equalsIgnoreCase(itemType)) {
            sql = "DELETE FROM AbsenceRequests WHERE id = ?";
        } else {
            // Invalid item type, so return false
            return false;
        }

        // First, get the username of the employee who submitted the item
        try (Connection conn = getConnection()) {
            // Fetch employee's username based on the item ID
            String usernameQuery = "SELECT e.username FROM employee e " +
                                   "JOIN Reports r ON r.employee_id = e.employee_id " + 
                                   "WHERE r.id = ? LIMIT 1";  // Or adjust the query for AbsenceRequests if needed

            // For Absence Requests, use a different query
            if ("Absence Request".equalsIgnoreCase(itemType)) {
                usernameQuery = "SELECT e.username FROM employee e " +
                                "JOIN AbsenceRequests ar ON ar.employee_id = e.employee_id " +
                                "WHERE ar.id = ? LIMIT 1";
            }

            try (PreparedStatement stmt = conn.prepareStatement(usernameQuery)) {
                stmt.setInt(1, itemId);  // Set the item ID to fetch the username
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        username = rs.getString("username");
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Now execute the deletion and log the activity
        try (Connection conn = getConnection();  // Get database connection
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the itemId in the query
            stmt.setInt(1, itemId);

            // Execute the update and check the number of affected rows
            int rowsAffected = stmt.executeUpdate();

            // If one row was affected, deletion was successful
            if (rowsAffected > 0 && username != null) {
                logActivity(username, "employee", "Delete " + itemType, itemType + " với ID " + itemId + " đã bị xóa.");
            }

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();  // Log the exception (consider using a logger in production)
            return false;  // Return false if any error occurs
        }
    }

    
 // Method to save submit item (either Report or Absence Request)
    public boolean saveSubmitItem(EmployeeSubmitItem item) {
        // Check the type of the item and insert into the correct table
        String sql = "";
        String username = null;

        if ("Report".equalsIgnoreCase(item.getType())) {
            sql = "INSERT INTO Reports (title, description, employee_id, file_path) VALUES (?, ?, ?, ?)";
        } else if ("Absence Request".equalsIgnoreCase(item.getType())) {
            sql = "INSERT INTO AbsenceRequests (title, description, employee_id) VALUES (?, ?, ?)";
        } else {
            // Invalid item type
            return false;
        }

        // First, get the username of the employee who is submitting the item
        try (Connection conn = getConnection()) {
            // Fetch employee's username based on the employee_id
            String usernameQuery = "SELECT e.username FROM employee e " +
                                   "WHERE e.employee_id = ? LIMIT 1";  // Fetch username for both Reports and Absence Requests

            try (PreparedStatement stmt = conn.prepareStatement(usernameQuery)) {
                stmt.setInt(1, item.getEmployeeId());  // Set the employee_id to fetch the username
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        username = rs.getString("username");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Insert data into the appropriate table
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            System.out.println("Start storing item:" + item);

            // Set the parameters
            stmt.setString(1, item.getTitle());
            stmt.setString(2, item.getDescription());
            stmt.setInt(3, item.getEmployeeId());

            // If it's a Report, add the file_path
            if ("Report".equalsIgnoreCase(item.getType())) {
                stmt.setString(4, item.getFilePath());  // Set file path for Report
            }

            // Execute the query
            int rowsAffected = stmt.executeUpdate();

            // If rows are affected, insertion is successful
            if (rowsAffected > 0 && username != null) {
                logActivity(username, "employee", "Create " + item.getType(), 
                            item.getType() + " với tiêu đề '" + item.getTitle() + "' đã được tạo.");
            }

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception (consider using a logger)
            return false;
        }
    }

    
    public boolean updateEmployee(String username, String name, String gender, java.sql.Date dob, String email, String phone_num, String address) {
        String sql = "UPDATE employee SET name = ?, gender = ?, dob = ?, email = ?, phone_num = ?, address = ? WHERE username = ?";

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
         // Log the activity
            if (rowsUpdated > 0) {
                logActivity(username, "employee", "Update Profile", "Nhân viên " + username + "cập nhật thông tin cá nhân.");
            }

            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // If an error occurred, return false
        }
    }
    
    public boolean changeEmployeePassword(String username, String oldPassword, String newPassword) {
        String sql = "UPDATE account SET password = ? WHERE username = ? AND password = ?";
        String employeeName = null;

        System.out.println("Start checking for username: " + username + ", old password: " + oldPassword + ", newPassword: " + newPassword);

        try (Connection conn = getConnection()) {
            // Fetch employee's name based on the username (to include it in the log)
            String usernameQuery = "SELECT name FROM employee WHERE username = ? LIMIT 1";  // Fetch employee name

            try (PreparedStatement stmt = conn.prepareStatement(usernameQuery)) {
                stmt.setString(1, username);  // Set the username to fetch the employee name
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        employeeName = rs.getString("name");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Update password in the account table
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set parameters for the PreparedStatement
            stmt.setString(1, newPassword);
            stmt.setString(2, username);
            stmt.setString(3, oldPassword); // Ensure username is the last parameter (where the condition is)

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0 && employeeName != null) {
                // Log the password change activity
                logActivity(username, "employee", "Change password", 
                            "Mật khẩu của nhân viên " + employeeName + " (" + username + ") đã được thay đổi.");
            }

            return rowsUpdated > 0; // If at least one row was updated, return true

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // If an error occurred, return false
        }
    }

}
