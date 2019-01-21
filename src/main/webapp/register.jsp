<%--
  Created by IntelliJ IDEA.
  User: Darina
  Date: 20.01.2019
  Time: 22:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="post" action="register">
    <label>Name</label>
    <input type="text" name="firstName"/><br>
    <label>Surname</label>
    <input type="text" name="lastName"/><br>
    <label>Login</label>
    <input type="text" name="login"/><br>
    <label>Password</label>
    <input type="password" name="password"/><br>
    <input type="submit" value="register">

</form>
<form method="post" action="login">
    <input type="text" name="login"/><br>
    <label>Password</label>
    <input type="password" name="password"/><br>
    <input type="submit" value="register">
</form>
</body>
</html>
