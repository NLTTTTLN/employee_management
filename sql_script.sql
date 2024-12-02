-- Create database
CREATE DATABASE employee_management;
-- Use the database
USE employee_management;

-- Create the account table
CREATE TABLE account (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    role ENUM('a0', 'manager', 'employee') NOT NULL
);

-- Create the employee table
CREATE TABLE employee (
    employee_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    gender ENUM('Male', 'Female') NOT NULL,
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
    gender ENUM('Male', 'Female') NOT NULL,
    dob DATE NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone_num VARCHAR(15),
    address TEXT,
    FOREIGN KEY (username) REFERENCES account(username) ON DELETE CASCADE
);

-- Create the admin table
CREATE TABLE admin (
    admin_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    FOREIGN KEY (username) REFERENCES account(username) ON DELETE CASCADE
);

-- Create the leave table
CREATE TABLE employee_leave (
    leave_id INT AUTO_INCREMENT PRIMARY KEY,
    leaver_id INT NOT NULL,
    date DATE NOT NULL,
    reason TEXT NOT NULL,
    FOREIGN KEY (leaver_id) REFERENCES employee(employee_id) ON DELETE CASCADE
);
CREATE TABLE manager_leave (
    leave_id INT AUTO_INCREMENT PRIMARY KEY,
    leaver_id INT NOT NULL,
    date DATE NOT NULL,
    reason TEXT NOT NULL,
    FOREIGN KEY (leaver_id) REFERENCES manager(manager_id) ON DELETE CASCADE
);-- ádasdadasd

-- Step 1: Insert into the account table
ALTER TABLE account MODIFY COLUMN role ENUM ('admin', 'manager', 'employee') NOT NULL;

INSERT INTO account (username, password, role)
VALUES ('admin', 'admin', 'admin');

-- Step 2: Insert into the admin table
INSERT INTO admin (username)
VALUES ('admin');

-- Insert managers into the account table
INSERT INTO account (username, password, role) VALUES
('manager1', 'password1', 'manager'),
('manager2', 'password2', 'manager'),
('manager3', 'password3', 'manager'),
('manager4', 'password4', 'manager'),
('manager5', 'password5', 'manager');

-- Insert managers into the manager table
INSERT INTO manager (username, name, gender, dob, email, phone_num, address) VALUES
('manager1', 'Alice Smith', 'Female', '1980-01-15', 'alice.smith@example.com', '1234567890', '123 Elm Street'),
('manager2', 'Bob Johnson', 'Male', '1975-05-20', 'bob.johnson@example.com', '9876543210', '456 Oak Avenue'),
('manager3', 'Carol Williams', 'Female', '1985-03-10', 'carol.williams@example.com', '5555555555', '789 Pine Road'),
('manager4', 'David Brown', 'Male', '1990-07-25', 'david.brown@example.com', '4444444444', '321 Maple Lane'),
('manager5', 'Eve Davis', 'Female', '1988-12-05', 'eve.davis@example.com', '3333333333', '654 Birch Way');

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
('employee1', 'John Doe', 'Male', '1992-04-12', 'john.doe@example.com', '1111111111', '101 Main St', 'HR', 50000.00),
('employee2', 'Jane Roe', 'Female', '1989-06-15', 'jane.roe@example.com', '2222222222', '202 Broad St', 'IT', 60000.00),
('employee3', 'Mark Lee', 'Male', '1995-08-25', 'mark.lee@example.com', '3333333333', '303 First Ave', 'Finance', 55000.00),
('employee4', 'Lisa Wong', 'Female', '1990-09-10', 'lisa.wong@example.com', '4444444444', '404 High St', 'Sales', 52000.00),
('employee5', 'Tom Clark', 'Male', '1991-11-20', 'tom.clark@example.com', '5555555555', '505 South St', 'Marketing', 48000.00),
('employee6', 'Emma Davis', 'Female', '1993-02-14', 'emma.davis@example.com', '6666666666', '606 East St', 'IT', 59000.00),
('employee7', 'Jake White', 'Male', '1994-05-18', 'jake.white@example.com', '7777777777', '707 West St', 'HR', 47000.00),
('employee8', 'Sophia Green', 'Female', '1992-07-22', 'sophia.green@example.com', '8888888888', '808 Center St', 'Finance', 56000.00),
('employee9', 'Ethan Brown', 'Male', '1987-10-30', 'ethan.brown@example.com', '9999999999', '909 Park St', 'Sales', 51000.00),
('employee10', 'Chloe Black', 'Female', '1986-12-05', 'chloe.black@example.com', '1212121212', '1010 River St', 'Marketing', 53000.00),
('employee11', 'Daniel Gray', 'Male', '1993-03-08', 'daniel.gray@example.com', '2323232323', '1111 Lake St', 'IT', 57000.00),
('employee12', 'Mia Turner', 'Female', '1992-01-19', 'mia.turner@example.com', '3434343434', '1212 Hill St', 'HR', 49000.00),
('employee13', 'Ryan Scott', 'Male', '1991-04-21', 'ryan.scott@example.com', '4545454545', '1313 Field St', 'Finance', 54000.00),
('employee14', 'Isabella Young', 'Female', '1990-07-25', 'isabella.young@example.com', '5656565656', '1414 Forest St', 'Sales', 52000.00),
('employee15', 'Andrew Hall', 'Male', '1988-09-10', 'andrew.hall@example.com', '6767676767', '1515 Sunset St', 'Marketing', 58000.00),
('employee16', 'Olivia Harris', 'Female', '1994-10-30', 'olivia.harris@example.com', '7878787878', '1616 Sunrise St', 'IT', 60000.00),
('employee17', 'Nathan Lewis', 'Male', '1992-06-15', 'nathan.lewis@example.com', '8989898989', '1717 Moon St', 'HR', 45000.00),
('employee18', 'Ella Robinson', 'Female', '1993-08-25', 'ella.robinson@example.com', '9090909090', '1818 Star St', 'Finance', 56000.00),
('employee19', 'James Martin', 'Male', '1995-11-20', 'james.martin@example.com', '1010101010', '1919 Cloud St', 'Sales', 47000.00),
('employee20', 'Charlotte Walker', 'Female', '1993-12-05', 'charlotte.walker@example.com', '1111111111', '2020 Sky St', 'Marketing', 59000.00);
-- Add department colum for manager role
ALTER TABLE Manager
ADD COLUMN department VARCHAR(255) NOT NULL;
-- Create table reports
CREATE TABLE Reports (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    file_path VARCHAR(255),
    employee_id INT NOT NULL,
    manager_id INT,
    status ENUM('Pending', 'Approved', 'Rejected') DEFAULT 'Pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
-- Create table AbsenceRequest
CREATE TABLE AbsenceRequests (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    employee_id INT NOT NULL,
    manager_id INT,
    status ENUM('Pending', 'Approved', 'Rejected') DEFAULT 'Pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
-- Inserting 10 realistic Reports with file paths (correct column order)
INSERT INTO Reports (title, description, file_path, employee_id, manager_id, status)
VALUES
('Báo cáo công việc tuần 1: Khởi động dự án mới', 'Mô tả các công việc đã hoàn thành trong tuần 1 của dự án khởi động, bao gồm các cuộc họp ban đầu và phân công công việc.', '/uploads/reports/week1_project_launch.pdf', 1, 2, 'Pending'),
('Báo cáo công việc tuần 2: Phát triển phần mềm', 'Tóm tắt tiến độ phát triển phần mềm, bao gồm việc hoàn thành chức năng đăng nhập và cơ sở dữ liệu người dùng.', '/uploads/reports/week2_software_development.pdf', 2, 3, 'Pending'),
('Báo cáo công việc tuần 3: Xây dựng giao diện người dùng', 'Các hoạt động thiết kế và triển khai giao diện người dùng cho ứng dụng. Đảm bảo giao diện phù hợp với yêu cầu của khách hàng.', '/uploads/reports/week3_ui_design.pdf', 3, 1, 'Pending'),
('Báo cáo công việc tuần 4: Tối ưu hóa hiệu suất hệ thống', 'Đánh giá và tối ưu hóa các điểm nghẽn về hiệu suất của hệ thống, bao gồm cả việc cải thiện thời gian phản hồi của ứng dụng.', '/uploads/reports/week4_performance_optimization.pdf', 4, 2, 'Pending'),
('Báo cáo công việc tuần 5: Kiểm thử và phát hành', 'Tổng quan về quá trình kiểm thử phần mềm và việc chuẩn bị phát hành sản phẩm. Bao gồm cả các lỗi được phát hiện và các cải tiến được thực hiện.', '/uploads/reports/week5_testing_and_release.pdf', 5, 3, 'Pending'),
('Báo cáo công việc tuần 6: Hỗ trợ khách hàng', 'Tổng hợp các yêu cầu và phản hồi của khách hàng trong tuần qua. Đánh giá sự hài lòng và các vấn đề còn tồn đọng cần giải quyết.', '/uploads/reports/week6_customer_support.pdf', 6, 1, 'Pending'),
('Báo cáo công việc tuần 7: Cập nhật và bảo trì hệ thống', 'Cập nhật phần mềm và bảo trì hệ thống, bao gồm việc triển khai bản vá bảo mật và kiểm tra tính tương thích của hệ thống với các phiên bản mới.', '/uploads/reports/week7_system_update_maintenance.pdf', 7, 2, 'Pending'),
('Báo cáo công việc tuần 8: Đào tạo nhân viên', 'Tổng kết các buổi đào tạo và hướng dẫn cho nhân viên mới về quy trình công việc và công cụ phần mềm.', '/uploads/reports/week8_employee_training.pdf', 8, 3, 'Pending'),
('Báo cáo công việc tuần 9: Quản lý dự án và báo cáo tiến độ', 'Báo cáo về tiến độ thực hiện dự án, các mốc quan trọng đã hoàn thành và kế hoạch cho các tuần tiếp theo.', '/uploads/reports/week9_project_management_progress.pdf', 9, 1, 'Pending'),
('Báo cáo công việc tuần 10: Phân tích dữ liệu và báo cáo kết quả', 'Phân tích dữ liệu thu thập được từ các cuộc khảo sát nội bộ, báo cáo kết quả và đề xuất cải tiến cho quy trình làm việc.', '/uploads/reports/week10_data_analysis_results.pdf', 10, 2, 'Pending');

-- Inserting 10 realistic Absence Requests
INSERT INTO AbsenceRequests (title, description, employee_id, manager_id, status)
VALUES
('Đơn xin nghỉ phép ốm', 'Tôi bị sốt và đau họng, cần nghỉ ngơi để hồi phục sức khỏe. Dự kiến nghỉ 3 ngày từ 1/10 đến 3/10.', 1, 2, 'Pending'),
('Đơn xin nghỉ phép thăm gia đình', 'Lý do: Cần về thăm gia đình và hỗ trợ việc cưới của em gái. Dự kiến nghỉ 2 ngày từ 5/10 đến 6/10.', 2, 3, 'Pending'),
('Đơn xin nghỉ phép vì lý do cá nhân', 'Tôi có việc cần phải giải quyết cá nhân, không thể đi làm vào ngày 10/10. Mong được duyệt nghỉ.', 3, 1, 'Pending'),
('Đơn xin nghỉ phép để chăm sóc con nhỏ', 'Con tôi bị bệnh nên tôi cần nghỉ phép để chăm sóc. Dự kiến nghỉ 3 ngày từ 8/10 đến 10/10.', 4, 2, 'Pending'),
('Đơn xin nghỉ phép mừng cưới', 'Lý do nghỉ: Tôi sẽ tổ chức đám cưới vào ngày 12/10, xin nghỉ phép 2 ngày để chuẩn bị và tham dự sự kiện.', 5, 3, 'Pending'),
('Đơn xin nghỉ phép thăm gia đình', 'Tôi cần về quê thăm ông bà ốm. Dự kiến nghỉ phép 1 ngày vào 15/10.', 6, 1, 'Pending'),
('Đơn xin nghỉ phép thăm bà con', 'Tôi cần nghỉ phép để tham gia đám tang của bà con trong gia đình. Xin nghỉ 2 ngày từ 18/10 đến 19/10.', 7, 2, 'Pending'),
('Đơn xin nghỉ phép vì lý do cá nhân', 'Lý do nghỉ: Tôi cần nghỉ để đi khám sức khỏe định kỳ vào ngày 20/10. Dự kiến nghỉ 1 ngày.', 8, 3, 'Pending'),
('Đơn xin nghỉ phép đi du lịch', 'Xin nghỉ phép 3 ngày từ 22/10 đến 24/10 để đi du lịch cùng gia đình. Mong được duyệt.', 9, 1, 'Pending'),
('Đơn xin nghỉ phép để học lớp đào tạo', 'Lý do nghỉ: Tôi tham gia lớp đào tạo nâng cao kỹ năng nghề nghiệp, xin nghỉ 2 ngày từ 25/10 đến 26/10.', 10, 2, 'Pending');

