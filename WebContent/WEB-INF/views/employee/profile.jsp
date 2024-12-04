<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
<head>
    <meta charset="UTF-8">
    <title>Employee Profile | ${employee.name}</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/style.css'/>">
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
                    <img src="<c:url value='/images/dashboard_icon.png'/>" alt="Dashboard Icon" class="icon">Dashboard
                </button></li>
              
                <li><button onclick="location.href='<c:url value='/employee/profile?id=${employeeId}' />'">
                    <img src="<c:url value='/images/personal_icon.png'/>" alt="Profile Icon" class="icon">Cá nhân
                </button></li>
            </ul>
            <div class="bottom-link">
                <li><button onclick="location.href='<c:url value='/logout' />'">Đăng xuất</button></li>
            </div>
        </div>

        <!-- Main content -->
        <div class="content">
            <button class="toggle-btn" onclick="toggleSidebar()">☰</button>
            <div class="main-content-container">
            	<div class="title">
            		<h2>Cá nhân</h2>
            	</div>
                <div class="main-profile-content">
                <!-- Left Sidebar for options -->
                <div class="update-option-container">
                    <button id="profileBtn">Thông tin cá nhân</button>
                    <button id="changePasswordBtn">Đổi mật khẩu</button>
                </div>

                <!-- Right content for displaying forms -->
                <div class="profile">
                    <h2>Thông tin cá nhân</h2>
					<input type="hidden" id="employeeId" value="${employeeId}" />
                    <!-- Form to display and edit employee data -->
                    <div id="profile-modal">
                        <div>
                        	<input  type="hidden" id="username" value="${employee.username}" />
                        </div>

                        <div>
                            <label for="name">Tên</label>
                            <input  id="name" value="${employee.name}" />
                        </div>

                        <div>
                            <label for="gender">Giới tính</label>
                            <input  id="gender" value="${employee.gender}" />
                        </div>
                        
                        <div>
                            <label for="dob">Ngày sinh</label>
                            <input  id="dob" value="${employee.dob}" />
                        </div>
                        
                        <div>
                            <label for="email">Địa chỉ email</label>
                            <input  id="email" value="${employee.email}" />
                        </div>
                        
                        <div>
                            <label for="phone_num">Số điện thoại</label>
                            <input  id="phone_num" value="${employee.phone_num}" />
                        </div>
                        
                        <div>
                            <label for="address">Địa chỉ</label>
                            <input  id="address" value="${employee.address}" />
                        </div>

                        <!-- Add more fields as necessary -->

                        <button id="submitProfileBtn" type="submit">Lưu thay đổi</button>
                    </div>
                
                </div>
                
			<div class="changePassword">
			    <h2>Đổi mật khẩu</h2>
			
			    <!-- Form to display and edit employee data -->
			        
			        <div class="input-form">
				        <div>
				            <label for="oldPass">Mật khẩu cũ</label>
				            <input type="password"   id="oldPass" type="password" />
				        </div>
				
				        <div>
				            <label for="newPass">Mật khẩu mới</label>
				            <input type="password"  id="newPass" type="password" />
				        </div>
				
				        <div>
				            <label for="confirmPass">Xác nhận mật khẩu</label>
				            <input type="password"  id="confirmPass" type="password" />
				        </div>
				        			
			        <button id="submitChangePwdBtn" type="submit">Cập nhật</button>
			        </div>

			</div>

                  
                </div>
            </div>
        </div>
    </div>

    <script src="<c:url value='/js/employee.js'/>"></script> <!-- Reusable JS file -->


</body>
</html>
