<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"></fmt:setLocale>
<fmt:setBundle basename="i18n.lang"></fmt:setBundle>
<html>
<head>
    <title><fmt:message key="userCabinet"></fmt:message> </title>
</head>
<body>
<c:if test="${user!=null}">
    <hr>
    <p>
        <c:out value="${user.firstName} personal  cabinet"></c:out>
    </p>
    <hr>
    <p><fmt:message key="balance"></fmt:message>:
        <c:out value="${balance} "></c:out>
    </p>
    <c:if test="${!(user.getSubs()).isEmpty()}">
    <hr>
    <p>
        <fmt:message key="userSubs"></fmt:message>:
    </p>
        <c:forEach  var="sub" items="${subscriptions}">
            <hr>
            <p>Name :
                <c:out value="${sub.getMagazine().getName()}"></c:out>
            </p>
            <hr>
        </c:forEach>
</c:if>
</c:if>
<a href="${pageContext.request.contextPath}/app/get_money_page">
    <fmt:message key="addMoney"></fmt:message></a>
</body>
</html>
