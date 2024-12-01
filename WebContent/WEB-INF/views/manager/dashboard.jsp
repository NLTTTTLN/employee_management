<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Manager Dashboard</title>
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
                <div class="dashboard">
                    <h2>Dashboard</h2>
                    <!-- Top Section with counts -->
                    <div class="dashboard-header">
                    	<h3>Thống kê</h3>
                        <div class="employee-count">
                            <h3>Số lượng nhân viên: <span id="employee-count">120</span></h3>  <!-- Placeholder for dynamic employee count -->
                        </div>
                        <div class="top-right">
                        	<div class="pending-report">
                                <h3>Báo cáo chờ duyệt: <span id="pending-report-count">3</span></h3>
                            </div>
                            <div class="pending-absence">
                                <h3>Đơn xin nghỉ chờ duyệt: <span id="pending-absence-count">5</span></h3>
                            </div>
                            
                        </div>
                    </div>
                
                    <!-- Table Section -->
                    <div class="table-section">
                    	<h3>Danh sách chờ duyệt</h3>
                        <table id="pending-table">
                            <thead>
                                <tr>
                                    <th>Loại</th>
                                    <th>Tiêu đề</th>
                                    <th>Người gửi</th>
                                    <th>Ngày gửi</th>
                                </tr>
                            </thead>
                            <tbody>
                                <!-- Rows will be dynamically inserted here by JavaScript -->
                            </tbody>
                        </table>
                    </div>
                </div>

                <!-- Modal for Pending Submit Details -->
                <div id="submit-detail-modal" class="modal">
                    <div class="modal-content">
                        <h3>Submit Item Details</h3>
                        <p id="modal-type">Type: </p>
                        <p id="modal-title">Title: </p>
                        <p id="modal-submitted-by">Submitted By: </p>
                        <p id="modal-date">Date: </p>
                        <div class="modal-buttons">
                            <button onclick="approveSubmit()">Approve</button>
                            <button onclick="rejectSubmit()">Reject</button>
                        </div>
                        <button class="close-btn" onclick="closeModal()">Close</button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="<c:url value='/js/manager.js'/>"></script> <!-- Reusable JS file -->
</body>
</html>
