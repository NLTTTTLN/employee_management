package employee_management.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import employee_management.bean.Account;

@Repository
public class AccountDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/employee_management"; // Nhập tên Schema database
    private static final String USER = "root"; // Nhập tên user truy cập database
    private static final String PASSWORD = "Thang0366"; //Nhập mật khẩu truy cập database
    
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
        try {
            Connection conn = getConnection() ;// Tiến hành kết nối database
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?"; // Chọn Table account 
            PreparedStatement stmt = conn.prepareStatement(sql);// Chuẩn bị truy vấn 
            stmt.setString(1, username); // Gán username cần authenticate vào truy vấn
            stmt.setString(2, password);// Gán password cần authenticate vào truy vấn
            System.out.println("Executing query: " + sql + " with username: " + username + " and password: " + password);
            ResultSet rs = stmt.executeQuery(); // Tạo biến rs chứa kết quả sau khi truy vấn
            //Tiến hành truy vấn từng Row trong Table
            if (rs.next()) {
            	// Nếu có Row trùng với truy vấn, hàm if trả về true, tiến hành gán thông tin của user tương ứng vào Object account
                account = new Account(rs.getString("username"), rs.getString("password"), rs.getString("role"));
            }
            else {
            	// Nếu không có Row trùng với truy vấn, hàm if trả về false,, authenticate thất bại
            	 System.out.println("Authentication failed for username: " + username);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
    }
}
