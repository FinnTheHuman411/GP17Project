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
    <title>Photoblog</title>
</head>
<body>
<c:url var="logoutUrl" value="/logout"/>
<security:authorize access="hasRole('ADMIN') or hasRole('USER')" >
    <form action="${logoutUrl}" method="post">
        <input type="submit" value="Log out" />
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</security:authorize>

<security:authorize access="!{hasRole('ADMIN') or hasRole('USER')}" >
    <form action="<c:url value="/login"/>">
        <input type="submit" value="Login" />
    </form>
</security:authorize>


<h2> @${username} </h2>
${user.description}

<h2>Uploaded images</h2>
</body>
</html>
