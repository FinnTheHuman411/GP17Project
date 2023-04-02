<!DOCTYPE html>
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
  <form action="<c:url value="/user/myprofile"/>">
    <input type="submit" value="My Profile" />
  </form>
</security:authorize>

<security:authorize access="!{hasRole('ADMIN') or hasRole('USER')}" >
  <form action="<c:url value="/login"/>">
    <input type="submit" value="Login" />
  </form>
</security:authorize>

<h2>Error page</h2>
<c:choose>
  <c:when test="${empty message}">
    <p>Something went wrong.</p>
  </c:when>
  <c:otherwise>
    <p>${message}</p>
  </c:otherwise>
</c:choose>
<a href="<c:url value="/photo" />">Return to homepage</a>
</body>
</html>
