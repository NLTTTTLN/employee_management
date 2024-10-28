<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Home</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/styles.css'/>"> <!-- Link to your CSS file -->
</head>
<body>
    <div class="admin-container">
        <h1>Welcome to Admin Home</h1>
        
        <div class="admin-options">
            <h2>Admin Options</h2>
            <ul>
                <li><a href="<c:url value='/admin/manageAccounts'/>">Manage Accounts</a></li>
                <li><a href="<c:url value='/admin/viewReports'/>">View Reports</a></li>
                <li><a href="<c:url value='/admin/manageEmployees'/>">Manage Employees</a></li>
                <li><a href="<c:url value='/admin/settings'/>">Settings</a></li>
            </ul>
        </div>

        <div class="logout">
            <a href="<c:url value='/logout'/>">Logout</a>
        </div>
    </div>
</body>
</html>
