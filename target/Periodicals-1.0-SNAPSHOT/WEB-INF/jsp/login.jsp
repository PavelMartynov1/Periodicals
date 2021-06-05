<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fmr" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"></fmt:setLocale>

<fmt:setBundle basename="i18n.lang" ></fmt:setBundle>
<fmt:setBundle basename="i18n.errors" var="err" ></fmt:setBundle>
<html>
<head>
    <title>LogIn Page</title>
</head>
<body>
<c:if test="${userLoggedIn!=null}">
    <p></p>
    <fmt:message key="userLoggedIn"></fmt:message>
    </p>
</c:if>
<c:forEach var="error" items="${errors}">
    <fmt:message key="${error}" bundle="${err}"></fmt:message>
</c:forEach>
<form action="login" method="post">
    <c:if test="${emailNotFound!=null}">
        <fmt:message key="emailNotFound"></fmt:message>
    </c:if>
<p>
    <label for="email"><b>Email</b></label>
    <input type="text" placeholder=" <fmt:message key="emailPlaceholder"></fmt:message>" name="email" id="email" required>
</p>
    <c:if test="${passErr!=null}">
        <fmt:message key="passErr"></fmt:message>
    </c:if>
<p>
    <label for="password"><b><fmt:message key="password" ></fmt:message></b></label>
    <input type="password" placeholder="<fmt:message key="passwordPlaceholder"></fmt:message>" name="password" id="password" required>
</p>
    <button type="submit"><fmt:message key="login"></fmt:message></button>
</form>
<p><fmr:message key="doNotHaveAnAccount"></fmr:message> <a href="${pageContext.request.contextPath}/app/regPage"><fmt:message key="registrationLink"></fmt:message> </a></p>
</body>
</html>
