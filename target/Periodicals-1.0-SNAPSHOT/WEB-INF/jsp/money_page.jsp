<%--
  Created by IntelliJ IDEA.
  User: marty
  Date: 30.05.2021
  Time: 13:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"></fmt:setLocale>
<fmt:setBundle basename="i18n.lang"></fmt:setBundle>
<fmt:setBundle basename="i18n.errors" var="err"></fmt:setBundle>
<html>
<head>
    <title>
        <fmt:message key="addMoney"></fmt:message></a>
    </title>
</head>
<body>
<c:forEach var="error" items="${errors}">
    <fmt:message key="${error}" bundle="${err}"></fmt:message>
</c:forEach>
<p>
    <c:if test="${transfer_status!=null}">

        <c:out value="${transfer_status}"></c:out>
    </c:if>
</p>
<form action="add_money" name="money" id="money">
    <input type="text" name="money_amount" id="money_amount" required>
    <p>
    <button form ="money" type="submit">
        <fmt:message key="add">

        </fmt:message>
    </button>
    </p>
</form>
<a href="${pageContext.request.contextPath}/app/getUserCabinet">
    <fmt:message key="user_cabinet">
    </fmt:message></a>
</a>
</body>
</html>
