<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Photoblog Login</title>
</head>
<body>
<c:if test="${param.error != null}">
    <p>Login failed.</p>
</c:if>
<h2>Login</h2>
<form action="login" method="POST">
    <label for="username">Username:</label><br/>
    <input type="text" id="username" name="username"/><br/><br/>
    <label for="password">Password:</label><br/>
    <input type="password" id="password" name="password"/><br/><br/>
    <input type="checkbox" id="remember-me" name="remember-me"/>
    <label for="remember-me">Remember me</label><br/><br/>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="submit" value="Log In"/>
</form><br/>
If you don't have any accounts:<br/>
<a href="<c:url value="/register" />">Register</a><br/>
<a href="<c:url value="/" />">Return to homepage</a>
</body>
</html>
