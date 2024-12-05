-- Create database
CREATE DATABASE employee_management;
-- Use the database
USE employee_management;

-- Create the account table
CREATE TABLE account (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    role ENUM('admin', 'manager', 'employee') NOT NULL
);

-- Create the employee table
CREATE TABLE employee (
    employee_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    gender ENUM('Nam', 'Nữ') NOT NULL,
    dob DATE NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone_num VARCHAR(15),
    address TEXT,
    department VARCHAR(50),
    salary DECIMAL(10, 2),
    FOREIGN KEY (username) REFERENCES account(username) ON DELETE CASCADE
);

-- Create the manager table
CREATE TABLE manager (
    manager_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    gender ENUM('Nam', 'Nữ') NOT NULL,
    dob DATE NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone_num VARCHAR(15),
    address TEXT,
    FOREIGN KEY (username) REFERENCES account(username) ON DELETE CASCADE
);


INSERT INTO account (username, password, role)
VALUES ('admin', 'admin', 'admin');


-- Insert managers into the account table
INSERT INTO account (username, password, role) VALUES
('manager1', 'password1', 'manager'),
('manager2', 'password2', 'manager'),
('manager3', 'password3', 'manager'),
('manager4', 'password4', 'manager'),
('manager5', 'password5', 'manager');

INSERT INTO manager (username, name, gender, dob, email, phone_num, address) VALUES
('manager1', 'Nguyễn Thị Lan', 'Nữ', '1980-01-15', 'lan.nguyen@example.com', '0912345678', '123 Đường Lê Lợi, Quận 1, TP.HCM'),
('manager2', 'Trần Văn Hùng', 'Nam', '1975-05-20', 'hung.tran@example.com', '0987654321', '456 Đường Nguyễn Huệ, Quận 1, TP.HCM'),
('manager3', 'Lê Thị Kim', 'Nữ', '1985-03-10', 'kim.le@example.com', '0933333333', '789 Đường Phan Đình Phùng, Quận Phú Nhuận, TP.HCM'),
('manager4', 'Phạm Minh Tuấn', 'Nam', '1990-07-25', 'tuan.pham@example.com', '0944444444', '321 Đường Cộng Hòa, Quận Tân Bình, TP.HCM'),
('manager5', 'Võ Thị Mai', 'Nữ', '1988-12-05', 'mai.vo@example.com', '0977777777', '654 Đường Bạch Đằng, Quận Bình Thạnh, TP.HCM');


-- Insert employees into the account table
INSERT INTO account (username, password, role) VALUES
('employee1', 'password1', 'employee'),
('employee2', 'password2', 'employee'),
('employee3', 'password3', 'employee'),
('employee4', 'password4', 'employee'),
('employee5', 'password5', 'employee'),
('employee6', 'password6', 'employee'),
('employee7', 'password7', 'employee'),
('employee8', 'password8', 'employee'),
('employee9', 'password9', 'employee'),
('employee10', 'password10', 'employee'),
('employee11', 'password11', 'employee'),
('employee12', 'password12', 'employee'),
('employee13', 'password13', 'employee'),
('employee14', 'password14', 'employee'),
('employee15', 'password15', 'employee'),
('employee16', 'password16', 'employee'),
('employee17', 'password17', 'employee'),
('employee18', 'password18', 'employee'),
('employee19', 'password19', 'employee'),
('employee20', 'password20', 'employee');

-- Insert employees into the employee table
INSERT INTO employee (username, name, gender, dob, email, phone_num, address, department, salary) VALUES
('employee1', 'Nguyễn Văn Anh', 'Nam', '1992-04-12', 'nguyen.anh@example.com', '0912345678', '101 Đường Lê Lợi, Quận 1, TP.HCM', 'HR', 50000.00),
('employee2', 'Trần Thị Lan', 'Nữ', '1989-06-15', 'tran.lan@example.com', '0922222222', '202 Đường Nguyễn Huệ, Quận 1, TP.HCM', 'IT', 60000.00),
('employee3', 'Lê Minh Tuấn', 'Nam', '1995-08-25', 'le.tuan@example.com', '0933333333', '303 Đường Phan Đình Phùng, Quận Phú Nhuận, TP.HCM', 'Finance', 55000.00),
('employee4', 'Phạm Thị Mai', 'Nữ', '1990-09-10', 'pham.mai@example.com', '0944444444', '404 Đường Cộng Hòa, Quận Tân Bình, TP.HCM', 'Sales', 52000.00),
('employee5', 'Võ Minh Hùng', 'Nam', '1991-11-20', 'vo.hung@example.com', '0955555555', '505 Đường Nguyễn Đình Chiểu, Quận 3, TP.HCM', 'Marketing', 48000.00),
('employee6', 'Đặng Thị Lan', 'Nữ', '1993-02-14', 'dang.lan@example.com', '0966666666', '606 Đường Bạch Đằng, Quận Bình Thạnh, TP.HCM', 'IT', 59000.00),
('employee7', 'Bùi Nam Anh', 'Nam', '1994-05-18', 'bui.anh@example.com', '0977777777', '707 Đường Cách Mạng Tháng 8, Quận 10, TP.HCM', 'HR', 47000.00),
('employee8', 'Ngô Thị Hạnh', 'Nữ', '1992-07-22', 'ngo.hanh@example.com', '0988888888', '808 Đường Xô Viết Nghệ Tĩnh, Quận Bình Thạnh, TP.HCM', 'Finance', 56000.00),
('employee9', 'Lý Minh Hùng', 'Nam', '1987-10-30', 'ly.hung@example.com', '0999999999', '909 Đường Phan Văn Trị, Quận Gò Vấp, TP.HCM', 'Sales', 51000.00),
('employee10', 'Trần Thị Lan', 'Nữ', '1986-12-05', 'tran.lan@example.com', '1012121212', '1010 Đường Tân Kỳ Tân Quý, Quận Tân Phú, TP.HCM', 'Marketing', 53000.00),
('employee11', 'Nguyễn Văn Sơn', 'Nam', '1993-03-08', 'nguyen.son@example.com', '1023232323', '1111 Đường Sài Gòn, Quận 2, TP.HCM', 'IT', 57000.00),
('employee12', 'Vũ Thị Nhung', 'Nữ', '1992-01-19', 'vu.nhung@example.com', '1034343434', '1212 Đường Lý Thái Tổ, Quận 4, TP.HCM', 'HR', 49000.00),
('employee13', 'Phan Minh Huy', 'Nam', '1991-04-21', 'phan.huy@example.com', '1045454545', '1313 Đường Nguyễn Thị Minh Khai, Quận 5, TP.HCM', 'Finance', 54000.00),
('employee14', 'Hoàng Thị Thu', 'Nữ', '1990-07-25', 'hoang.thu@example.com', '1056565656', '1414 Đường Lê Thị Riêng, Quận 12, TP.HCM', 'Sales', 52000.00),
('employee15', 'Lê Minh Chí', 'Nam', '1988-09-10', 'le.chi@example.com', '1067676767', '1515 Đường Hồng Bàng, Quận 6, TP.HCM', 'Marketing', 58000.00),
('employee16', 'Phạm Thị Linh', 'Nữ', '1994-10-30', 'pham.linh@example.com', '1078787878', '1616 Đường Cao Thắng, Quận 3, TP.HCM', 'IT', 60000.00),
('employee17', 'Đặng Minh Hùng', 'Nam', '1992-06-15', 'dang.hung@example.com', '1089898989', '1717 Đường Lý Tự Trọng, Quận 1, TP.HCM', 'HR', 45000.00),
('employee18', 'Nguyễn Thị Hoa', 'Nữ', '1993-08-25', 'nguyen.hoa@example.com', '1090909090', '1818 Đường Trường Chinh, Quận Tân Phú, TP.HCM', 'Finance', 56000.00),
('employee19', 'Trần Minh Hoàng', 'Nam', '1995-11-20', 'tran.hoang@example.com', '1010101010', '1919 Đường Cộng Hòa, Quận 10, TP.HCM', 'Sales', 47000.00),
('employee20', 'Lê Thị Mai', 'Nữ', '1993-12-05', 'le.mai@example.com', '1011111111', '2020 Đường Nguyễn Văn Cừ, Quận 5, TP.HCM', 'Marketing', 59000.00);


-- Create table reports
CREATE TABLE Reports (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    file_path VARCHAR(255),
    employee_id INT NOT NULL,
    status ENUM('Đang chờ', 'Chấp thuận', 'Từ chối') DEFAULT 'Đang chờ',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
-- Create table AbsenceRequest
CREATE TABLE AbsenceRequests (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    employee_id INT NOT NULL,
     status ENUM('Đang chờ', 'Chấp thuận', 'Từ chối') DEFAULT 'Đang chờ',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
-- Inserting 10 realistic Reports with file paths (correct column order)
INSERT INTO Reports (title, description, file_path, employee_id,  status)
VALUES
('Báo cáo công việc tuần 1: Khởi động dự án mới', 'Mô tả các công việc đã hoàn thành trong tuần 1 của dự án khởi động, bao gồm các cuộc họp ban đầu và phân công công việc.', '/uploads/reports/week1_project_launch.pdf', 1, 'Đang chờ'),
('Báo cáo công việc tuần 2: Phát triển phần mềm', 'Tóm tắt tiến độ phát triển phần mềm, bao gồm việc hoàn thành chức năng đăng nhập và cơ sở dữ liệu người dùng.', '/uploads/reports/week2_software_development.pdf', 2, 'Đang chờ'),
('Báo cáo công việc tuần 3: Xây dựng giao diện người dùng', 'Các hoạt động thiết kế và triển khai giao diện người dùng cho ứng dụng. Đảm bảo giao diện phù hợp với yêu cầu của khách hàng.', '/uploads/reports/week3_ui_design.pdf', 3, 'Đang chờ'),
('Báo cáo công việc tuần 4: Tối ưu hóa hiệu suất hệ thống', 'Đánh giá và tối ưu hóa các điểm nghẽn về hiệu suất của hệ thống, bao gồm cả việc cải thiện thời gian phản hồi của ứng dụng.', '/uploads/reports/week4_performance_optimization.pdf', 4, 'Đang chờ'),
('Báo cáo công việc tuần 5: Kiểm thử và phát hành', 'Tổng quan về quá trình kiểm thử phần mềm và việc chuẩn bị phát hành sản phẩm. Bao gồm cả các lỗi được phát hiện và các cải tiến được thực hiện.', '/uploads/reports/week5_testing_and_release.pdf', 5, 'Đang chờ'),
('Báo cáo công việc tuần 6: Hỗ trợ khách hàng', 'Tổng hợp các yêu cầu và phản hồi của khách hàng trong tuần qua. Đánh giá sự hài lòng và các vấn đề còn tồn đọng cần giải quyết.', '/uploads/reports/week6_customer_support.pdf', 6, 'Đang chờ'),
('Báo cáo công việc tuần 7: Cập nhật và bảo trì hệ thống', 'Cập nhật phần mềm và bảo trì hệ thống, bao gồm việc triển khai bản vá bảo mật và kiểm tra tính tương thích của hệ thống với các phiên bản mới.', '/uploads/reports/week7_system_update_maintenance.pdf', 7, 'Đang chờ'),
('Báo cáo công việc tuần 8: Đào tạo nhân viên', 'Tổng kết các buổi đào tạo và hướng dẫn cho nhân viên mới về quy trình công việc và công cụ phần mềm.', '/uploads/reports/week8_employee_training.pdf', 8, 'Đang chờ'),
('Báo cáo công việc tuần 9: Quản lý dự án và báo cáo tiến độ', 'Báo cáo về tiến độ thực hiện dự án, các mốc quan trọng đã hoàn thành và kế hoạch cho các tuần tiếp theo.', '/uploads/reports/week9_project_management_progress.pdf', 9, 'Đang chờ'),
('Báo cáo công việc tuần 10: Phân tích dữ liệu và báo cáo kết quả', 'Phân tích dữ liệu thu thập được từ các cuộc khảo sát nội bộ, báo cáo kết quả và đề xuất cải tiến cho quy trình làm việc.', '/uploads/reports/week10_data_analysis_results.pdf', 10, 'Đang chờ');

-- Inserting 10 realistic Absence Requests
INSERT INTO AbsenceRequests (title, description, employee_id,  status)
VALUES
('Đơn xin nghỉ phép ốm', 'Tôi bị sốt và đau họng, cần nghỉ ngơi để hồi phục sức khỏe. Dự kiến nghỉ 3 ngày từ 1/10 đến 3/10.', 1, 'Đang chờ'),
('Đơn xin nghỉ phép thăm gia đình', 'Lý do: Cần về thăm gia đình và hỗ trợ việc cưới của em gái. Dự kiến nghỉ 2 ngày từ 5/10 đến 6/10.', 2, 'Đang chờ'),
('Đơn xin nghỉ phép vì lý do cá nhân', 'Tôi có việc cần phải giải quyết cá nhân, không thể đi làm vào ngày 10/10. Mong được duyệt nghỉ.', 3, 'Đang chờ'),
('Đơn xin nghỉ phép để chăm sóc con nhỏ', 'Con tôi bị bệnh nên tôi cần nghỉ phép để chăm sóc. Dự kiến nghỉ 3 ngày từ 8/10 đến 10/10.', 4, 'Đang chờ'),
('Đơn xin nghỉ phép mừng cưới', 'Lý do nghỉ: Tôi sẽ tổ chức đám cưới vào ngày 12/10, xin nghỉ phép 2 ngày để chuẩn bị và tham dự sự kiện.', 5, 'Đang chờ'),
('Đơn xin nghỉ phép thăm gia đình', 'Tôi cần về quê thăm ông bà ốm. Dự kiến nghỉ phép 1 ngày vào 15/10.', 6, 'Đang chờ'),
('Đơn xin nghỉ phép thăm bà con', 'Tôi cần nghỉ phép để tham gia đám tang của bà con trong gia đình. Xin nghỉ 2 ngày từ 18/10 đến 19/10.', 7, 'Đang chờ'),
('Đơn xin nghỉ phép vì lý do cá nhân', 'Lý do nghỉ: Tôi cần nghỉ để đi khám sức khỏe định kỳ vào ngày 20/10. Dự kiến nghỉ 1 ngày.', 8, 'Đang chờ'),
('Đơn xin nghỉ phép đi du lịch', 'Xin nghỉ phép 3 ngày từ 22/10 đến 24/10 để đi du lịch cùng gia đình. Mong được duyệt.', 9, 'Đang chờ'),
('Đơn xin nghỉ phép để học lớp đào tạo', 'Lý do nghỉ: Tôi tham gia lớp đào tạo nâng cao kỹ năng nghề nghiệp, xin nghỉ 2 ngày từ 25/10 đến 26/10.', 10, 'Đang chờ');


CREATE TABLE ActivityLog (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    userType VARCHAR(50) NOT NULL,  -- 'employee' or 'manager'
    activityType VARCHAR(255) NOT NULL,  -- Type of activity (e.g., "Login", "Update Profile", "Delete Request")
    description TEXT,  -- Details about the activity
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
