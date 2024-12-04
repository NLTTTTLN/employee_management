<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Employee Dashboard | ${employee.name}</title>
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
                <li><button onclick="location.href='<c:url value='/employee/dashboard?id=${employeeId}' />'">
                <img src="<c:url value='/images/dashboard_icon.png'/>" alt="Dashboard Icon" class="icon">Dashboard</button></li>
              
                <li><button onclick="location.href='<c:url value='/employee/profile?id=${employeeId}' />'">
    			<img src="<c:url value='/images/personal_icon.png'/>" alt="Profile Icon" class="icon">Cá nhân</button></li>
                
            </ul>
            <div class="bottom-link">
                <li><button onclick="location.href='<c:url value='/logout' />'">Đăng xuất</button></li>
            </div>
        </div>

        <!-- Main content -->
        <!-- Main content -->
        <div class="content">
            <button class="toggle-btn" onclick="toggleSidebar()">☰</button>
            <div class="main-content">
                <div class="dashboard">
                    <h2>Dashboard</h2>
					<input type="hidden" id="employeeId" value="${employeeId}" />

                	<div class="button-container">
                        <!-- Buttons for Delete/Promote -->
                        <button class="action-btn" id="addBtn">Tạo +</button>
                    </div>
                    
                    <!-- Table Section -->
                    <div class="table-container">
                    	
                        <div class="table-box">
                            <h3>Danh sách đơn báo cáo</h3>
                        <div class="scrollable-table">

	                        <table id="pending-table">
	                            <thead>
	                                <tr>
	                                    <th>Loại</th>
	                                    <th>Tiêu đề</th>
	                                    <th>Ngày gửi</th>
	                                    <th>Trạng thái</th>
	                                    
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
                
                <!-- Modal 1: Choose Report or Absence Request -->
                <div id="choose-type-modal" class="modal">
                    <div class="modal-content">
                        <span class="close-btn" id="closeChooseModalBtn">&times;</span>
                        <h3>Chọn loại đơn</h3>
                        <p>Bạn muốn tạo:</p>
                        <button id="createReportBtn">Báo cáo</button>
                        <button id="createAbsenceBtn">Yêu cầu vắng mặt</button>
                    </div>
                </div>

                <!-- Modal 2: Create Report or Absence Request -->
                <div id="create-item-modal" class="modal">
                    <div class="modal-content">
                        <span class="close-btn" id="closeCreateItemModalBtn">&times;</span>
                        <h3 id="modal-title">Tạo Đơn</h3>
                        <form id="createItemForm">
                            <input type="hidden" id="itemType" value="" />
                            <label for="itemTitle">Tiêu đề:</label>
                            <input type="text" id="itemTitle" name="itemTitle" required /><br>

                            <label for="itemDescription">Mô tả:</label>
                            <textarea id="itemDescription" name="itemDescription" required></textarea><br>

                            <button type="submit" class="submitBtn">Tạo</button>
                        </form>
                    </div>
                </div>

                <!-- Modal for Pending Submit Details -->
                <div id="submit-detail-modal" class="modal">
				    <div class="modal-content">
				    <span class="close-btn" id="closeSubmitModalBtn">&times;</span>
				        <h3>Thêm</h3>
				     
				        <p id="modal-type"></p>
				        <p id="modal-title"></p>
				        <p id="modal-date"></p>
				        <p id="modal-description"></p>
				        <!-- This is the new file path container -->
				        <p id="modal-file-path" style="display: none;"></p>
				        <div class="modal-buttons">
				            <button id="deleteSubmitBtn" class="submitBtn">Xóa</button>
				            
				        </div>

				    </div>
				</div>
            </div>
        </div>
    </div>

    <script src="<c:url value='/js/employee.js'/>"></script> <!-- Reusable JS file -->
</body>
</html>
