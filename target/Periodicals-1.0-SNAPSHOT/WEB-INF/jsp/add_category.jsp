<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"></fmt:setLocale>
<fmt:setBundle basename="i18n.lang"></fmt:setBundle>
<fmt:setBundle basename="i18n.errors" var="err"></fmt:setBundle>
<html>
<head>
    <title>Add Category</title>
</head>
<body>
<p>
    <c:forEach var="error" items="${errors}">
        <p>
        <fmt:message key="${error}" bundle="${err}"></fmt:message>
</p>
    </c:forEach>
<a href="${pageContext.request.contextPath}/app/admin">
    <fmt:message key="adminCab"></fmt:message>
</a>
</p>

    <c:if test="${category!=null}">
<p>
    <c:out value="${category.getName()} was added succesfully ">

    </c:out>
    </p>
</c:if>
<form action="add_category" method ="post">
    <p>
        <label for="cat_name"><b><fmt:message key="categoryName"></fmt:message></b></label>
        <input type="text" placeholder="<fmt:message key="enterNamePlaceholder"></fmt:message>" name="cat_name" id="cat_name" required>
    </p>
    <p>
        <label for="description"><b>
            <fmt:message key="description"></fmt:message>
        </b></label>
        <input type="text" placeholder="<fmt:message key="enterDescriptionPlaceholder"></fmt:message>" name="description" id="description" required>
    </p>
    <button type="submit">
        <fmt:message key="add"></fmt:message>
    </button>
</form>
</body>
</html>
