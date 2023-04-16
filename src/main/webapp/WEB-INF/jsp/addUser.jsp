<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Photoblog User Management</title></head>
<body>
<c:url var="logoutUrl" value="/logout"/>
<form action="${logoutUrl}" method="post">
    <input type="submit" value="Log out" />
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
<form action="<c:url value="/"/>">
    <input type="submit" value="Homepage" />
</form>
<form action="<c:url value="/myprofile"/>">
    <input type="submit" value="My Profile" />
</form>
<form action="<c:url value="/image/upload"/>">
    <input type="submit" value="Upload" />
</form>

<h2>Create a User</h2>
<form:form method="POST" modelAttribute="imageUser">
    <form:label path="username">Username</form:label><br/>
    <form:input type="text" path="username"/><br/><br/>
    <form:label path="password">Password</form:label><br/>
    <form:input type="text" path="password"/><br/><br/>
    <form:label path="phone">Phone number</form:label><br/>
    <form:input type="text" path="phone"/><br/><br/>
    <form:label path="email">Email address</form:label><br/>
    <form:input type="email" path="email"/><br/><br/>
    <form:label path="roles">Roles</form:label><br/>
    <form:checkbox path="roles" value="ROLE_USER"/>ROLE_USER
    <form:checkbox path="roles" value="ROLE_ADMIN"/>ROLE_ADMIN
    <br/><br/>
    <input type="submit" value="Add User"/>
</form:form>
</body>
</html>