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


<h2> @${username} </h2>
<i>Description: </i>${user.description}
<br>
[<a href="<c:url value="/profile/${username}/comhist" />">Comment History</a>]
<security:authorize access="hasRole('ADMIN') or
                          (hasRole('USER') and principal.username=='${username}')">
    [<a href="<c:url value="/profile/${username}/editdesc" />">Edit</a>]
</security:authorize>

<h2>Uploaded images</h2>
<div>
    <c:choose>
        <c:when test="${fn:length(photoDatabase) == 0}">
            <i>There are no images in the system.</i>
        </c:when>
        <c:otherwise>
            <c:forEach items="${photoDatabase}" var="image">
                <c:forEach items="${image.attachments}" var="attachment" varStatus="status">
                    <c:if test="${status.first}">
                        <td>
                            <a href="<c:url value="/view/${image.id}" />">
                                <img src="data:image/png;base64,${fn:escapeXml(Base64.getEncoder().encodeToString(attachment.contents))}" alt="${attachment.name}" width="300" height="300"/>
                            </a>
                        <td>
                    </c:if>
                </c:forEach>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
