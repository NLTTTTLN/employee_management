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


}
