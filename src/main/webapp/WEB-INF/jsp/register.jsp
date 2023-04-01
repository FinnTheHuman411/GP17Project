<!DOCTYPE html>
<html>
<head><title>Photoblog Register</title></head>
<body>

<h2>Register an account</h2>
<form:form method="POST" modelAttribute="photoUser">
    <form:label path="username">Username</form:label><br/>
    <form:input type="text" path="username"/><br/><br/>
    <form:label path="password">Password</form:label><br/>
    <form:input type="text" path="password"/><br/><br/>
    <form:label path="phone">Phone number</form:label><br/>
    <form:input type="text" path="phone"/><br/><br/>
    <form:label path="email">Email address</form:label><br/>
    <form:input type="email" path="email"/><br/><br/>
    <br/><br/>
    <input type="submit" value="Register"/>
</form:form><br/>
<a href="<c:url value="/login" />">Login</a><br/>
<a href="<c:url value="/photo" />">Return to homepage</a>
</body>
</html>