<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Photoblog</title>
</head>
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

<h2>Edit #${image.id}</h2>
<form:form method="POST" enctype="multipart/form-data" modelAttribute="photoForm">
    <b>Add more attachments</b><br/>
    <input type="file" name="attachments" multiple="multiple" accept="image/png, image/jpeg"/><br/><br/>
    <form:label path="title">Title</form:label><br/>
    <form:input type="text" path="title"/><br/><br/>
    <form:label path="description">Description</form:label><br/>
    <form:textarea path="description" rows="5" cols="30"/><br/><br/>
    <input type="submit" value="Save"/>
</form:form>
<br>
<a href="<c:url value="/view/${image.id}" />">Cancel</a>
</body>
</html>
