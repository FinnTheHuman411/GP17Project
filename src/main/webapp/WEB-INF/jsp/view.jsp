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

<h2><c:out value="${image.title}"/></h2>
<security:authorize access="hasRole('ADMIN') or
                          (hasRole('USER') and principal.username=='${image.username}')">
    [<a href="<c:url value="/image/edit/${image.id}" />">Edit</a>]
</security:authorize>
<security:authorize access="hasRole('ADMIN')">
    [<a href="<c:url value="/image/delete/${image.id}" />">Delete</a>]
</security:authorize>
<br/>
<c:if test="${!empty image.attachments}">
    <c:forEach items="${image.attachments}" var="attachment" varStatus="status">
        <img src="data:image/png;base64,${fn:escapeXml(Base64.getEncoder().encodeToString(attachment.contents))}" alt="${attachment.name}" width="75%"/>
        <br/>
        <security:authorize access="hasRole('ADMIN') or
                          (hasRole('USER') and principal.username=='${image.username}')">
            <c:if test="${fn:length(image.attachments) > 1}">
                [<a href="<c:url value="/image/${image.id}/delete/${attachment.id}" />">Delete</a>]
            </c:if>
        </security:authorize>
        <br/>
    </c:forEach>
</c:if>
<br/>
<i>Uploader: @</i><a href="<c:url value="/profile/${image.username}" />"><c:out value="${image.username}"/></a><br/><br/>
<i>Description: </i><c:out value="${image.description}"/><br/><br/>
<i>Upload time: </i><c:out value="${image.uploadTime}"/><br/>
<i>Last updated: </i><c:out value="${image.updateTime}"/><br/><br/>

<hr>
<h3>Comments</h3>
<security:authorize access="hasRole('ADMIN') or hasRole('USER')">
    [<a href="<c:url value="/view/${image.id}/comment" />">Add Comment</a>]
    <br/>
</security:authorize>
<c:choose>
    <c:when test="${empty comment}">
        <i>There are no comments in the post.</i>
    </c:when>
    <c:otherwise>
        <ul>
            <c:forEach items="${comment}" var="comment">
                <li><i>@<a href="<c:url value="/profile/${comment.username}" />">${comment.username}</a></i>: ${comment.text} [Post: ${comment.uploadTime}, Update: ${comment.updateTime}]
                <security:authorize access="hasRole('ADMIN') or (hasRole('USER') and principal.username=='${comment.username}')">
                    [<a href="<c:url value="/view/${image.id}/deleteComment/${comment.id}" />">Delete</a>]
                </security:authorize>
                <security:authorize access="hasRole('USER') and principal.username=='${comment.username}'">
                    [<a href="<c:url value="/view/${image.id}/editComment/${comment.id}" />">Edit</a>]
                </security:authorize>
                </li>
            </c:forEach>
        </ul>
    </c:otherwise>
</c:choose>
<br/><br/><br/><br/><br/>
</body>
</html>
