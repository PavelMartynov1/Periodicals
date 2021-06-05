<%--
  Created by IntelliJ IDEA.
  User: marty
  Date: 31.05.2021
  Time: 19:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.lang}"></fmt:setLocale>
<fmt:setBundle basename="i18n.lang"></fmt:setBundle>
<html>
<head>
    <title><fmt:message key="userInfo"></fmt:message></title>
</head>
<body>
<c:if test="${user!=null}">
    <table  border="1" align="center" bgcolor="#d2691e">
        <tr>
            <th><fmt:message key="firstName"></fmt:message></th>
            <th><fmt:message key="lastName"></fmt:message></th>
            <th><fmt:message key="email"></fmt:message></th>
            <th><fmt:message key="lastTimeWasSeen"></fmt:message></th>
            <th><fmt:message key="status"></fmt:message></th>
            <th><fmt:message key="balance"></fmt:message></th>
        </tr>
        <tr>
            <th><c:out value="${user.getFirstName()}"></c:out></th>
            <th><c:out value="${user.getLastName()}"></c:out></th>
            <th><c:out value="${user.getEmail()}"></c:out></th>
            <th><c:out value="${user.getSingInDate()}"></c:out></th>
            <th><c:out value="${user.getStatus()}"></c:out></th>
            <th><c:out value="${user.getBalance()}"></c:out></th>
            <th><c:forEach var="sub" items="${subs}">
                <p>
                    <c:out value="${sub.getMagazine().getName()}"></c:out>
                </p>
            </c:forEach> </th>
        </tr>
    </table>
    <c:set value="${user.getStatus()}" var="status"></c:set>

    </p>
</c:if>
<form action="change_user_status" id="ban_user"  name="ban_user">
    <input type="hidden" name="user_id" value="${user.getId()}">
    <input form="ban_user" value="BANNED"  type="hidden" name="command" >
</form>
<form action="change_user_status" id="unban_user"  name="unban_user">
    <input type="hidden" name="user_id" value="${user.getId()}">
    <input form="unban_user" value="ACTIVE"  type="hidden" name="command" >
</form>
<c:if test="${status.toString().equals('ACTIVE')}">

    <button form="ban_user"><fmt:message key="ban"></fmt:message></button>
</c:if>
<c:if test="${status.toString().equals('BANNED')}">

    <button form="unban_user"><fmt:message key="unban"></fmt:message></button>
</c:if>

</body>
</html>
