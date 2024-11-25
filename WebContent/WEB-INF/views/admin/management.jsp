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
                <h2>Quản lý nhân sự</h2>
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
                        <button class="action-btn" id="deleteBtn">Delete</button>
                        <button class="action-btn" id="promoteBtn">Promote</button>
                    </div>

                    <div class="table-container">
                        <div class="table-box">
                            <h3>Managers</h3>
                            <div class="scrollable-table">
                                <table border="1">
                                    <thead>
                                    <tr>
                                        <th>Username</th>
                                        <th>Role</th>
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
                            <h3>Employees</h3>
                            <div class="scrollable-table">
                                <table border="1">
                                    <thead>
                                    <tr>
                                        <th>Username</th>
                                        <th>Role</th>
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

                    <!-- Modal for Delete or Promote Action -->
                    <div id="actionModal" class="modal">
                        <div class="modal-content">
                            <span class="close-btn" id="closeBtn">&times;</span>
                            <h3>Choose a user</h3>
                            <select id="userSelect">
                                <!-- Dynamic options will be inserted here -->
                            </select>
                            <br>
                            <button id="confirmActionBtn">Confirm</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="<c:url value='/js/admin.js'/>"></script> <!-- Reusable JS file -->
</body>
</html>
