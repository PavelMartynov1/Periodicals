<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.lang}"></fmt:setLocale>
<fmt:setBundle basename="i18n.lang"></fmt:setBundle>
<html>
<head>
    <title><fmt:message key="users"></fmt:message></title>
</head>
<body>
<p>
    <a href="${pageContext.request.contextPath}/app/admin"><fmt:message key="adminCab"></fmt:message></a>
</p>
<form action="getLoggedInUsers" name="users" id="users">
    <table>
    <th><input form="users" type="text" id="name" name="mail" placeholder="<fmt:message key="searchByMail"></fmt:message>"></th>
        <th><button type="submit"><fmt:message key="search"></fmt:message></button></th>
    </table>
    <th><input value="search" form="users" type="hidden" name="command"  id="filter"></th>
</form>
<table border="1" align="center" bgcolor="#adff2f">
    <caption><fmt:message key="users"></fmt:message></caption>
    <tr>
        <th><fmt:message key="firstName"></fmt:message></th>
        <th><fmt:message key="lastName"></fmt:message></th>
        <th><fmt:message key="email"></fmt:message></th>
        <th><fmt:message key="lastTimeWasSeen"></fmt:message></th>
        <th><fmt:message key="status"></fmt:message></th>
    </tr>

    <c:forEach var="user" items="${users}">
    <tr>
        <th><c:out value="${user.getFirstName()}"></c:out> </th>
        <th><c:out value="${user.getLastName()}"></c:out> </th>
        <th><a href="${pageContext.request.contextPath}/app/get_user_info?id=${user.getId()}" >
            <c:out value="${user.getEmail()}"></c:out></a> </th>
        <th><c:out value="${user.getSingInDate()}"></c:out> </th>
        <th><c:out value="${user.getStatus()}"></c:out> </th>
    </tr>
    </c:forEach>

</table>
</body>
</html>
