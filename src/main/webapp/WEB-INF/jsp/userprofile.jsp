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


<h2> @${username} </h2>
${user.description}

<h2>Uploaded images</h2>
<div>
    <c:choose>
        <c:when test="${fn:length(photoDatabase) == 0}">
            <i>There are no images in the system.</i>
        </c:when>
        <c:otherwise>
            <c:forEach items="${photoDatabase}" var="image">
                <td>
                    <a href="<c:url value="/user/profile/${image.username}"></c:url>">
                        <img src="data:image/png;base64,${fn:escapeXml(Base64.getEncoder().encodeToString(image.data))}" alt="Image" width="300" height="300"/>
                    </a>
                </td>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
