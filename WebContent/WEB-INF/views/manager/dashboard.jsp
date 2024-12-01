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
                      <div class="dashboard">
                <h2>Dashboard</h2>
                <div class="dashboard-stats">
        
                    <div class="stat">
                        <h3>Số lượng nhân viên</h3>
                        <p id="employeeCount">0</p>
                    </div>
                </div>

                <h3>Bảng thông báo</h3>
                <div class="recent-activities">
					<div class="submit-container">
                             <table class="submit-table">
						        <thead>
						            <tr>
						                <th>Type</th>
						                <th>Title/Description</th>
						                <th>Submitted By</th>
						                <th>Date</th>
						            </tr>
						        </thead>
						        <tbody>
						            <c:forEach var="item" items="${reportsAndRequests}">
						                <tr class="report-row" data-id="${item.id}" data-type="${item.type}">
						                    <td>${item.type}</td>
						                    <td>${item.title}</td>
						                    <td>${item.submittedBy}</td>
						                    <td>${item.date}</td>
						                </tr>
						            </c:forEach>
						        </tbody>
						    </table>
                
                 <!-- Modal -->
                <div id="modal" class="modal">
                    <div class="modal-content">
                        <span class="close" onclick="closeModal()">&times;</span>
                        <h3 id="modalTitle">Details</h3>
                        <div id="modalBody">
                            <!-- Details will be dynamically loaded here -->
                        </div>
                        <div class="modal-actions">
                            <button class="btn approve" onclick="handleApproval('Approved')">Approve</button>
                            <button class="btn reject" onclick="handleApproval('Rejected')">Reject</button>
                        </div>
                    </div>
                </div>
                
                
                </div>
            </div>
            </div>
        </div>
    </div>
 
    <script src="<c:url value='/js/manager.js'/>"></script> <!-- Reusable JS file -->
</body>
</html>
