<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: lbens
  Date: 3/31/2023
  Time: 5:48 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Base64" %>
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
    <form action="<c:url value="/image/upload"/>">
        <input type="submit" value="Upload" />
    </form>
</security:authorize>

<security:authorize access="!{hasRole('ADMIN') or hasRole('USER')}" >
    <form action="<c:url value="/login"/>">
        <input type="submit" value="Login" />
    </form>
</security:authorize>

<h2><c:out value="${image.title}"/></h2>
<security:authorize access="hasRole('ADMIN') or
                          (hasRole('USER') and principal.username=='${image.username}')">
    [<a href="<c:url value="/ticket/edit/${image.id}" />">Edit</a>]
</security:authorize>
<security:authorize access="hasRole('ADMIN')">
    [<a href="<c:url value="/ticket/delete/${image.id}" />">Delete</a>]
</security:authorize>
<br/>
<c:if test="${!empty image.attachments}">
    <c:forEach items="${image.attachments}" var="attachment" varStatus="status">
        <img src="data:image/png;base64,${fn:escapeXml(Base64.getEncoder().encodeToString(attachment.contents))}" alt="${attachment.name}" width="75%"/>
        <br/>
    </c:forEach>
</c:if>
<br/>
<i>Uploader: <a href="<c:url value="/profile/${image.username}" />"><c:out value="${image.username}"/></a> </i><br/><br/>
<c:out value="${image.description}"/><br/><br/>
</body>
</html>
