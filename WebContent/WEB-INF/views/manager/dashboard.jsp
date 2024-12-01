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
					<div class="dashboard">
					<h2>Dashboard</h2>
					    <!-- Top Section with counts -->
					    <div class="dashboard-header">
					      <div class="employee-count">
					        <h3>Employee Count: <span id="employee-count">120</span></h3>
					      </div>
					      <div class="top-right">
					        <div class="pending-absence">
					          <h3>Pending Absences: <span id="pending-absence-count">5</span></h3>
					        </div>
					        <div class="pending-report">
					          <h3>Pending Report Requests: <span id="pending-report-count">3</span></h3>
					        </div>
					      </div>
					    </div>
					
					    <!-- Table Section -->
					    <div class="table-section">
					      <table id="pending-table">
					        <thead>
					          <tr>
					            <th>Type</th>
					            <th>Title</th>
					            <th>Submitted By</th>
					            <th>Date</th>
					          </tr>
					        </thead>
					        <tbody>
					          <tr class="pending-item" onclick="openModal('1')">
					            <td>Report</td>
					            <td>Annual Report</td>
					            <td>John Doe</td>
					            <td>2024-11-01</td>
					          </tr>
					          <tr class="pending-item" onclick="openModal('2')">
					            <td>Absence</td>
					            <td>Medical Leave</td>
					            <td>Jane Smith</td>
					            <td>2024-11-02</td>
					          </tr>
					          <!-- Add more rows here -->
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
 
    <script src="<c:url value='/js/manager.js'/>"></script> <!-- Reusable JS file -->
</body>
</html>
