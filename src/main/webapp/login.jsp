<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Customer Login</title>
</head>
<body>
    <h1>Customer Login</h1>
    
    <form action="customer_dashboard.jsp" method="post">
        Customer ID: <input type="text" name="cid"><br>
        Password: <input type="password" name="name"><br>
        <input type="submit" value="Login">
    </form>
    
    <p>${error}</p>
</body>
</html>
6