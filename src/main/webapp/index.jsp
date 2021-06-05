<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"></fmt:setLocale>
<fmt:setBundle basename="i18n.lang"></fmt:setBundle>

<html>
<title>Main Page</title>
<body>
<h2>
    <fmt:message key="label.welcome"></fmt:message>
<ul>
    <li><a href="?sessionLocale=en">EN</a></li>
    <li><a href="?sessionLocale=ru">RU</a></li>
</ul>
</h2>

<c:if test="${user!=null}">
    <jsp:useBean id="user" scope="session" type="com.example.Periodicals.model.entity.User"/>
    <c:set var="role" value="${user.role}">
    </c:set>
    <c:if test="${role eq 'ADMIN' }">
        <a href="${pageContext.request.contextPath}/app/admin">
            <fmt:message key="user_cabinet">
            </fmt:message></a>
    </c:if>
    <c:if test="${role eq 'USER' }">
        <a href="${pageContext.request.contextPath}/app/getUserCabinet">
            <fmt:message key="user_cabinet">

            </fmt:message></a>
    </c:if>

</c:if>
<c:if test="${user==null}">
    <br/>
    <a href="${pageContext.request.contextPath}/app/getLogInPage">
        <fmt:message key="login">

    </fmt:message></a>
</c:if>
<br/>
<a href="${pageContext.request.contextPath}/app/regPage">
    <fmt:message key="registrationLink">

    </fmt:message>
</a>
<br>
<a href="${pageContext.request.contextPath}/app/logOut">
    <fmt:message key="logOut">

    </fmt:message>
</a>
<br>
<a href="${pageContext.request.contextPath}/app/getCatalog">
    <fmt:message key="catalog">

    </fmt:message>
</a>
</body>
</html>