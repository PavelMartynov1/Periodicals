
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="magazine" scope="request" type="com.example.Periodicals.model.entity.Magazine"/>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"></fmt:setLocale>
<fmt:setBundle basename="i18n.lang"></fmt:setBundle>
<html>
<head>
    <title><fmt:message key="deleteMag">

    </fmt:message></title>
</head>
<body>
<p>
    <c:if test="${Deleted!=null}">
        <fmt:message key="deletionStatus">

        </fmt:message>
    </c:if>
</p>
<p>You are about to delete
    <br>
<c:out value="${magazine.name}"></c:out> </p>
<form action="delete_magazine" name="magazine" id="magazine">
    <input type="hidden" value="${magazine.id}" name="magazine_id">
</form>
<button form="magazine">
    <fmt:message key="delete">

    </fmt:message>
</button>
<br>
</body>
</html>
