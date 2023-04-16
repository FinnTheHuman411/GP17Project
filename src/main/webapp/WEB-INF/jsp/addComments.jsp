<%--
  Created by IntelliJ IDEA.
  User: lbens
  Date: 3/31/2023
  Time: 5:48 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Comment</title>
</head>
<body>
<c:url var="logoutUrl" value="/logout"/>
<security:authorize access="hasRole('ADMIN') or hasRole('USER')" >
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
</security:authorize>

<security:authorize access="!{hasRole('ADMIN') or hasRole('USER')}" >
    <form action="<c:url value="/login"/>">
        <input type="submit" value="Login" />
    </form>
    <form action="<c:url value="/"/>">
        <input type="submit" value="Homepage" />
    </form>
</security:authorize>
<h2>Comment</h2>
<form:form method="POST" enctype="multipart/form-data"
           modelAttribute="commentForm">
    <form:label path="text">Input your comment here:</form:label><br/>
    <form:textarea path="text" rows="5" cols="30" /><br/><br/>
    <input type="submit" value="Submit"/><br/><br/>
</form:form>
</body>
</html>
