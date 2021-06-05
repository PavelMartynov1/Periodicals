<%--
  Created by IntelliJ IDEA.
  User: marty
  Date: 30.05.2021
  Time: 10:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"></fmt:setLocale>
<fmt:setBundle basename="i18n.lang"></fmt:setBundle>
<html>
<head>
    <title>Subscribe Page</title>
</head>
<body>
<c:if test="${notEnoughFunds!=null}">
    <fmt:message key="notEnoughFunds"></fmt:message>

</c:if>
<c:if test="${alreadySubscribed!=null}">
    <fmt:message key="alreadySubscribed"></fmt:message>

</c:if>
<hr>
<p>
    <fmt:message key="aboutToSubscribe"></fmt:message>: <c:out value="${param.name}"></c:out>
</p>
<hr>
<p>
    <hr>
<fmt:message key="thatCosts"></fmt:message>: <c:out value="${param.price}"></c:out>
</p>
<hr>
<p>
    <hr>
<fmt:message key="userBalance"></fmt:message> :<c:out value="${user.balance}"></c:out>
</p>
</body>
<form action="subscribe" id="magazine"  name="magazine" >
    <input type="hidden" name="publisher" value="${param.publisher}">
    <input type="hidden" name="name" value="${param.name}">
    <input type="hidden" name="id" value="${param.id}">
    <input type="hidden" name="price" value="${param.price}">
    <button form="magazine" type="submit">
        <fmt:message key="pay"></fmt:message>
    </button>
</form>

</html>
