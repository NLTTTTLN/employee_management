<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manager Management</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/style.css'/>"> <!-- Reusable CSS file -->
</head>
<body>
    <div class="wrapper">
        <!-- Sidebar -->
        <div class="sidebar">
            <div class="logo">
                <img src="<c:url value='/images/Logo_PTIT_University.png'/>" alt="Logo" class="logo-img">
                <h2>Quản lý nhân sự</h2>
            </div>
            <ul class="nav-links">
                <li><button onclick="location.href='<c:url value='/manager/dashboard' />'">
                    <img src="<c:url value='/images/dashboard_icon.png'/>" alt="Dashboard Icon" class="icon">Dashboard</button></li>
                <li><button onclick="location.href='<c:url value='/manager/management' />'">
                    <img src="<c:url value='/images/management_icon.png'/>" alt="Management Icon" class="icon">Quản lý người dùng</button></li>
                <li><button onclick="location.href='<c:url value='/manager/profile' />'">
                    <img src="<c:url value='/images/personal_icon.png'/>" alt="Profile Icon" class="icon">Cá nhân</button></li>
            </ul>
            <div class="bottom-link">
                <li><button onclick="location.href='<c:url value='/logout' />'">Đăng xuất</button></li>
            </div>
        </div>

        <!-- Main content -->
        <div class="content">
            <button class="toggle-btn" onclick="toggleSidebar()">☰</button>
            <div class="main-content">
                <!-- Content will load here based on selection -->
                <div class="manager_management">
                    <h2>Quản lý nhân viên</h2>

                    <!-- Move the buttons inside the management div -->
                    <div class="button-container">
                        <!-- Buttons for Delete/Promote -->
                        <button class="action-btn" id="addBtn">Thêm</button>
                        <button class="action-btn" id="deleteBtn">Xóa</button>
                        <button class="action-btn" id="editBtn">Sửa</button>
                    </div>

                    <div class="table-container">
                        <div class="table-box">
                            <h3>Danh sách nhân viên</h3>
                            <div class="scrollable-table">
                                <table class="manager-management-table" border="1">
                                    <thead>
                                        <tr>
                                            <th>Tên tài khoản</th>
                                            <th>Tên</th>
                                            <th>Giới tính</th>
                                            <th>Ngày sinh</th>
                                            <th>Email</th>
                                            <th>Số điện thoại</th>
                                            <th>Địa chỉ</th>
                                            <th>Bộ phận</th>
                                            <th>Mức lương</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="employee" items="${employees}">
                                            <tr>
                                                <td>${employee.username}</td>
                                                <td>${employee.name}</td>
                                                <td>${employee.gender}</td>
                                                <td>${employee.dob}</td>
                                                <td>${employee.email}</td>
                                                <td>${employee.phone_num}</td>
                                                <td>${employee.address}</td>
                                                <td>${employee.department}</td>
                                                <td>${employee.salary}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>

                    <!-- Add Modal -->
                    <div id="addModal" class="modal" style="display: none;">
                        <div class="modal-content">
                            <span class="close-btn" id="closeAddBtn">&times;</span>
                            <h3>Thêm nhân viên mới</h3>
                            <form id="addUserForm">
                                <label for="username">Tên tài khoản:</label>
                                <input type="text" id="username" name="username" placeholder="Tên tài khoản" required>
                                <br>

                                <label for="name">Tên đầy đủ:</label>
                                <input type="text" id="name" name="name" placeholder="Tên đầy đủ" required>
                                <br>

                                <label for="gender">Giới tính:</label>
                                <select id="gender" name="gender" required>
                                    <option value="male">Nam</option>
                                    <option value="female">Nữ</option>
                                    <option value="other">Khác</option>
                                </select>
                                <br>

                                <label for="dob">Ngày sinh:</label>
                                <input type="date" id="dob" name="dob" required>
                                <br>

                                <label for="email">Email:</label>
                                <input type="email" id="email" name="email" placeholder="Email" required>
                                <br>

                                <label for="phone_num">Số điện thoại:</label>
                                <input type="text" id="phone_num" name="phone_num" placeholder="Số điện thoại" required>
                                <br>

                                <label for="address">Địa chỉ:</label>
                                <input type="text" id="address" name="address" placeholder="Địa chỉ" required>
                                <br>

                                <label for="department">Phòng ban:</label>
                                <input type="text" id="department" name="department" placeholder="Phòng ban" required>
                                <br>

                                <label for="salary">Lương:</label>
                                <input type="number" id="salary" name="salary" placeholder="Lương" required>
                                <br>

                                <button type="button" id="confirmAddBtn">Thêm nhân viên</button>
                            </form>
                        </div>
                    </div>

                    <!-- Delete Modal -->
                    <div id="deleteModal" class="modal" style="display: none;">
                        <div class="modal-content">
                            <span class="close-btn" id="closeDeleteBtn">&times;</span>
                            <h3>Xóa nhân viên</h3>
                            
                            <!-- Search Bar for Employees -->
                            <label for="searchEmployee">Tìm kiếm nhân viên:</label>
                            <input type="text" id="searchEmployee" placeholder="Nhập tên tài khoản" oninput="searchEmployee()">
                            <br><br>
                    
                            <!-- Employee List Dropdown -->
                            <select id="employeeList" name="employeeList" size="5" style="width: 100%;">
                                <option value="">Chọn nhân viên</option>
                            </select>
                            <br>
                    
                            <form id="deleteUserForm">
                                <label for="deleteUsername">Nhân viên được chọn:</label>
                                <input type="text" id="deleteUsername" name="deleteUsername" placeholder="Tên tài khoản" readonly>
                                <br>
                                <button type="button" id="confirmDeleteBtn">Xóa</button>
                            </form>
                        </div>
                    </div>

                    <!-- Edit select Modal -->
                    <div id="editSelectModal" class="modal" style="display: none;">
                        <div class="modal-content">
                            <span class="close-btn" id="closeEditSelectBtn">&times;</span>
                            <h3>Chọn nhân viên chỉnh sửa</h3>
                            
                            <!-- Search Bar for Employees -->
                            <label for="searchEditEmployee">Tìm kiếm nhân viên:</label>
                            <input type="text" id="searchEditEmployee" placeholder="Nhập tên tài khoản" oninput="searchEditEmployee()">
                            <br><br>
                    
                            <!-- Employee List Dropdown -->
                            <select id="employeeListEdit" name="employeeListEdit" size="5" style="width: 100%;" onchange="selectEditEmployee()">
                                <option value="">Chọn nhân viên</option>
                            </select>
                            <br>
                    
                            <form id="editUserForm">
                                <label for="selectEditUsername">Nhân viên được chọn:</label>
                                <input type="text" id="selectEditUsername" name="selectEditUsername" placeholder="Tên tài khoản" readonly>
                                <br>
                                <button type="button" id="confirmSelectBtn">Sửa</button>
                            </form>
                        </div>
                    </div>

                    <!-- Edit Modal (same modal as Add, but with pre-filled data for editing) -->
                    <div id="editModal" class="modal" style="display: none;">
                        <div class="modal-content">
                            <span class="close-btn" id="closeEditBtn">&times;</span>
                            <h3>Sửa thông tin nhân viên</h3>
                            <form id="editUserForm">
                                <label for="editUsername">Tên tài khoản:</label>
                                <input type="text" id="editUsername" name="username" placeholder="Tên tài khoản" readonly>
                                <br>
                    
                                <label for="editName">Tên đầy đủ:</label>
                                <input type="text" id="editName" name="name" placeholder="Tên đầy đủ" required>
                                <br>
                    
                                <label for="editGender">Giới tính:</label>
                                <select id="editGender" name="gender" required>
                                    <option value="male">Nam</option>
                                    <option value="female">Nữ</option>
                                    <option value="other">Khác</option>
                                </select>
                                <br>
                    
                                <label for="editDob">Ngày sinh:</label>
                                <input type="date" id="editDob" name="dob" required>
                                <br>
                    
                                <label for="editEmail">Email:</label>
                                <input type="email" id="editEmail" name="email" placeholder="Email" required>
                                <br>
                    
                                <label for="editPhone_num">Số điện thoại:</label>
                                <input type="text" id="editPhone_num" name="phone_num" placeholder="Số điện thoại" required>
                                <br>
                    
                                <label for="editAddress">Địa chỉ:</label>
                                <input type="text" id="editAddress" name="address" placeholder="Địa chỉ" required>
                                <br>
                    
                                <label for="editDepartment">Phòng ban:</label>
                                <input type="text" id="editDepartment" name="department" placeholder="Phòng ban" required>
                                <br>
                    
                                <label for="editSalary">Lương:</label>
                                <input type="number" id="editSalary" name="salary" placeholder="Lương" required>
                                <br>
                    
                                <button type="button" id="confirmEditBtn">Cập nhật thông tin</button>
                            </form>
                        </div>
                    </div>
                    
                    
                </div>
            </div>
        </div>
    </div>

    <script src="<c:url value='/js/manager.js'/>"></script> <!-- Reusable JS file -->
</body>
</html>
