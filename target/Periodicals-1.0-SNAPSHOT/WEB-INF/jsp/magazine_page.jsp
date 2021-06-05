<jsp:useBean id="magazine" scope="request" type="com.example.Periodicals.model.entity.Magazine"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"></fmt:setLocale>
<fmt:setBundle basename="i18n.lang"></fmt:setBundle>
<html>
<head>
    <title><fmt:message key="magInfo"></fmt:message>  </title>
</head>
<body>
<hr>
<p>
        <fmt:message key="magazineName"></fmt:message>:
        <c:out value="${magazine.name}"></c:out> </p>
<hr>
<p>

        <fmt:message key="description"></fmt:message>:
        <c:out value="${magazine.description}"></c:out>
</p>
<hr>
<p>
    <fmt:message key="publisher"></fmt:message>: <c:out value="${magazine.publisher}"></c:out>
</p>
<hr>
<p>
        <fmt:message key="price"></fmt:message>: <c:out value="${magazine.price}"></c:out>
</p>
<hr>
<p>
        <fmt:message key="category"></fmt:message>: <c:out value="${magazine.category.name}"></c:out>
</p>
<form action="get_subscribe_page" id="magazine"  name="magazine" >
    <input type="hidden" name="publisher" value="${magazine.publisher}">
    <input type="hidden" name="name" value="${magazine.name}">
    <input type="hidden" name="id" value="${magazine.id}">
    <input type="hidden" name="price" value="${magazine.price}">
</form>
<button form="magazine" type="submit">
    <fmt:message key="subscribe"></fmt:message>
</button>
<hr>
<c:if test="${!(user eq null)}">
<jsp:useBean id="user" scope="session" type="com.example.Periodicals.model.entity.User"/>
<c:set var="role" value="${user.role}"></c:set>

<form action="getMagDeletePage"  id="magazine_to_delete">
    <input type="hidden" name="magazine_id" value="${magazine.id}">
</form>
<c:if test="${role.toString().equals('ADMIN')}">
    <a href="${pageContext.request.contextPath}/app/get_edit_page?id=${magazine.id}">
        <fmt:message key="edit"></fmt:message>
    </a>
    <button form="magazine_to_delete" >
        <fmt:message key="delete"></fmt:message>
    </button>
</c:if>
</c:if>
</body>
</html>
