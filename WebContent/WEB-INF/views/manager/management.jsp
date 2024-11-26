<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
                        <button class="action-btn" id="promoteBtn">Thay đổi chức vụ</button>
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

                                <label for="password">Mật khẩu:</label>
                                <input type="password" id="password" name="password" placeholder="Mật khẩu" required>
                                <br>

                                <label for="role">Vai trò:</label>
                                <select id="role" name="role">
                                    <option value="employee">Nhân viên</option>
                                    <option value="manager">Quản lý</option>
                                </select>
                                <br>

                                <button type="button" id="confirmAddBtn">Thêm nhân viên</button>
                            </form>
                        </div>
                    </div>

                    <!-- Modal for Delete or Promote Action -->
                    <div id="actionModal" class="modal" style="display: none;">
                        <div class="modal-content">
                            <span class="close-btn" id="closeBtn">&times;</span>
                            <h3></h3>
                            <select id="userSelect" style="display: none;">
                                <!-- Dynamic options will be inserted here -->
                            </select>
                            <br>
                            <button id="confirmActionBtn"></button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="<c:url value='/js/manager.js'/>"></script> <!-- Reusable JS file -->
</body>
</html>
