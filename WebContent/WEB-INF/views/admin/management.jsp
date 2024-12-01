<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Management</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/style.css'/>"> <!-- Reusable CSS file -->
</head>
<body>
    <div class="wrapper">
        <!-- Sidebar -->
        <div class="sidebar">
            <div class="logo">
                <img src="<c:url value='/images/Logo_PTIT_University.png'/>" alt="Logo" class="logo-img">

            </div>
            <ul class="nav-links">
                <li><button onclick="location.href='<c:url value='/admin/dashboard' />'">
                <img src="<c:url value='/images/dashboard_icon.png'/>" alt="Dashboard Icon" class="icon">Dashboard</button></li>
                <li><button onclick="location.href='<c:url value='/admin/management' />'">
                <img src="<c:url value='/images/management_icon.png'/>" alt="Management Icon" class="icon">Quản lý người dùng</button></li>
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
                <div class="management">
                    <h2>Quản lý người dùng</h2>

                    <!-- Move the buttons inside the management div -->
                    <div class="button-container">
                        <!-- Buttons for Delete/Promote -->
                        <button class="action-btn" id="addBtn">Thêm</button>
                        <button class="action-btn" id="deleteBtn">Xóa</button>
                        <button class="action-btn" id="promoteBtn">Thay đổi chức vụ</button>
                    </div>

                    <div class="table-container">
                        <div class="table-box">
                            <h3>Danh sách quản lý</h3>
                            <div class="scrollable-table">
                                <table class="managers-table" border="1">
                                    <thead>
                                    <tr>
                                        <th>Tên tài khoản</th>
                                        <th>Chức vụ</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="manager" items="${managers}">
                                        <tr>
                                            <td>${manager.username}</td>
                                            <td>${manager.role}</td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>

                        <div class="table-box">
                            <h3>Danh sách nhân viên</h3>
                            <div class="scrollable-table">
                                <table class="employees-table" border="1">
                                    <thead>
                                    <tr>
                                        <th>Tên tài khoản</th>
                                        <th>Chức vụ</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="employee" items="${employees}">
                                        <tr>
                                            <td>${employee.username}</td>
                                            <td>${employee.role}</td>
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
                            <h3>Thêm người dùng mới</h3>

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

                                <button type="button" id="confirmAddBtn">Thêm người dùng</button>
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

    <script src="<c:url value='/js/admin.js'/>"></script> <!-- Reusable JS file -->
</body>
</html>
