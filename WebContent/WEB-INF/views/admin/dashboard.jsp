<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
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
                      <div class="dashboard">
                <h2>Dashboard</h2>
                <div class="dashboard-stats">
                    <div class="stat">
                        <h3>Số lượng quản lý</h3>
                        <p id="managerCount">0</p>
                    </div>
                    <div class="stat">
                        <h3>Số lượng nhân viên</h3>
                        <p id="employeeCount">0</p>
                    </div>
                </div>

                <h3>Lịch sử hoạt động</h3>
                <div class="recent-activities">
					<div class="activity-container">
					
                            <div class="activity-section">
                            	<input type="text" class="searchbox" id="myInput1" onkeyup="myFunction1()" placeholder="Search for names.." title="Type in a name">
						             <script>
									function myFunction1() {
									    // Declare variables
									    var input, filter, list, li, textContent, i, txtValue;
									    input = document.getElementById("myInput1");
									    filter = input.value.toUpperCase();
									    list = document.getElementById("managerActivities");  // Assuming your list's ID is "employeeActivities"
									    li = list.getElementsByTagName("li");
									
									    // Loop through all list items, and hide those who don't match the search query
									    for (i = 0; i < li.length; i++) {
									        textContent = li[i].textContent || li[i].innerText;
									        if (textContent.toUpperCase().indexOf(filter) > -1) {
									            li[i].style.display = "";  // Show the list item
									        } else {
									            li[i].style.display = "none";  // Hide the list item
									        }
									    }
									}
									</script>
                                <div class="scrollable-table">
                                
                                <table id="managerActivitiesTable">
                                    <thead>
                                        <tr>
                                            <th>Quản lý</th>
                                        </tr>
                                    </thead>
                                    <tbody id="managerActivities">
                                        <!-- Recent activities by managers will be populated here -->
                                    </tbody>
                                </table>
                                </div>
                            </div>
                            <div class="activity-section">
                            <input type="text" class="searchbox" id="myInput2" onkeyup="myFunction2()" placeholder="Search for names.." title="Type in a name">
						             <script>
									function myFunction2() {
									    // Declare variables
									    var input, filter, list, li, textContent, i, txtValue;
									    input = document.getElementById("myInput2");
									    filter = input.value.toUpperCase();
									    list = document.getElementById("employeeActivities");  // Assuming your list's ID is "employeeActivities"
									    li = list.getElementsByTagName("li");
									
									    // Loop through all list items, and hide those who don't match the search query
									    for (i = 0; i < li.length; i++) {
									        textContent = li[i].textContent || li[i].innerText;
									        if (textContent.toUpperCase().indexOf(filter) > -1) {
									            li[i].style.display = "";  // Show the list item
									        } else {
									            li[i].style.display = "none";  // Hide the list item
									        }
									    }
									}
									</script>
                                <div class="scrollable-table">
                                
                                	<table id="employeeActivitiesTable">
                                    <thead>
                                        <tr>
                                            <th>Nhân viên</th>
                                        </tr>
                                    </thead>
                                    <tbody id="employeeActivities">
                                        <!-- Recent activities by employees will be populated here -->
                                    </tbody>
                                </table>
                                </div>
                            </div>	
                </div>
            </div>
            </div>
        </div>
    </div>
 
    <script src="<c:url value='/js/admin.js'/>"></script> <!-- Reusable JS file -->
</body>
</html>
