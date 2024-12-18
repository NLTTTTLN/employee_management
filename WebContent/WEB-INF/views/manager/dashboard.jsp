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
                            <h3>Số lượng nhân viên: <span id="employee-count"></span></h3>  <!-- Placeholder for dynamic employee count -->
                        </div>
                        <div class="top-right">
                        	<div class="pending-report">
                                <h3>Báo cáo chờ duyệt: <span id="pending-report-count"></span></h3>
                            </div>
                            <div class="pending-absence">
                                <h3>Đơn xin nghỉ chờ duyệt: <span id="pending-absence-count"></span></h3>
                            </div>
                            
                        </div>
                    </div>
                
                    <!-- Table Section -->
                    <div class="table-container">
	                    
	                    <div class="table-box">
	                    	<h3>Danh sách chờ duyệt</h3>
	                    	<input type="text" class="searchbox" id="myInput1" onkeyup="myFunction1()" placeholder="Search for titles..." title="Type in a name">
				             <script>
									function myFunction1() {
									  // Declare variables
									  var input, filter, table, tr, td, i, txtValue;
									  input = document.getElementById("myInput1");
									  filter = input.value.toUpperCase();
									  table = document.getElementById("pending-table");
									  tr = table.getElementsByTagName("tr");
									
									  // Loop through all table rows, and hide those who don't match the search query
									  for (i = 0; i < tr.length; i++) {
									    td = tr[i].getElementsByTagName("td")[1];
									    if (td) {
									      txtValue = td.textContent || td.innerText;
									      if (txtValue.toUpperCase().indexOf(filter) > -1) {
									        tr[i].style.display = "";
									      } else {
									        tr[i].style.display = "none";
									      }
									    }
									  }
									}
						</script>
	                    	<div class="scrollable-table">
	                    	
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
                    </div>
                </div>

                <!-- Modal for Pending Submit Details -->
                <div id="submit-detail-modal" class="modal">
				    <div class="modal-content">
				    <span class="close-btn" id="closeSubmitModalBtn">&times;</span>
				        <h3>Chi tiết</h3>
				     
				        <p id="modal-type"></p>
				        <p id="modal-title"></p>
				        <p id="modal-submitted-by"></p>
				        <p id="modal-date"></p>
				        <p id="modal-description"></p>
				        <!-- This is the new file path container -->
				        <p id="modal-file-path" style="display: none;"></p>
				        <div class="modal-buttons">
				            <button id="approveBtn" class="submitBtn">Phê duyệt</button>
				            <button id="rejectBtn" class="submitBtn">Từ chối</button>
				        </div>

				    </div>
				</div>
            </div>
        </div>
    </div>

    <script src="<c:url value='/js/manager.js'/>"></script> <!-- Reusable JS file -->
</body>
</html>
