<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Page</title>
    <script src="<c:url value='/js/login.js'/>" defer></script>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/loginstyle.css'/>">
</head>
<body>
    <div class="login-container">
        <h1>Đăng nhập</h1>
        <form id="loginForm" method="post">
            <div class="form-group">
                <label for="username">Tài khoản:</label>
                <input type="text" id="username" name="username" required>
            </div>
            <div class="form-group">
                <label for="password">Mật khẩu:</label>
                <input type="password" id="password" name="password" required>
            </div>
            <button type="submit">Đăng nhập</button>
        </form>
        <div id="message" class="message"></div>
    </div>

</body>
</html>
