<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.lang}"></fmt:setLocale>
<fmt:setBundle basename="i18n.lang"></fmt:setBundle>
<html>
<head>
    <title><fmt:message key="adminCab"></fmt:message></title>
</head>
<body>
    <p>
        <a href="${pageContext.request.contextPath}/app/getLoggedInUsers">
            <fmt:message key="users"></fmt:message>
        </a>
    </p>
    <p>
        <a href="${pageContext.request.contextPath}/app/getMagCreationPage">
            <fmt:message key="addMagazine"></fmt:message>
        </a>
    </p>
    <p>
        <a href="${pageContext.request.contextPath}/app/addCategoryPage">
            <fmt:message key="addCategory"></fmt:message>
        </a>
    </p>


</body>
</html>
