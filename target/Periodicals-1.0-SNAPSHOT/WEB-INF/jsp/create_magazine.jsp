<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"></fmt:setLocale>
<fmt:setBundle basename="i18n.lang"></fmt:setBundle>
<fmt:setBundle basename="i18n.errors" var="err"></fmt:setBundle>
<html>
<head>
    <title><fmt:message key="addMagazine"></fmt:message></title>
</head>
<c:forEach var="error" items="${errors}">
    <p>
    <fmt:message key="${error}" bundle="${err}"></fmt:message>
    </p>
</c:forEach>

<a href="${pageContext.request.contextPath}/app/admin"><fmt:message key="adminCab"></fmt:message></a>
<body>
<form action="add_magazine" method="post" id="magazine">
    <p>
        <label for="name"><b><fmt:message key="magazineName"></fmt:message></b></label>
        <input type="text"  name="name" id="name" required>
    </p>
    <p>
        <label for="description"><b><fmt:message key="description"></fmt:message></b></label>
        <input type="text"  name="description" id="description" required>
    </p>
    <p>
        <label for="publisher"><b><fmt:message key="publisher"></fmt:message></b></label>
        <input type="text"  name="publisher" id="publisher" required>
    </p>
    <p>
        <label for="price"><b><fmt:message key="price"></fmt:message></b></label>
        <input type="text"  name="price" id="price" required>
    </p>
    <p>
        <select size="5" form="magazine" name="category_id">
            <c:forEach var="category" items="${categories}">
                <option value="${category.getId()}">
                    <c:out value="${category.getName()}"></c:out>
                </option>
            </c:forEach>

        </select>
    </p>
    <button type="submit"><fmt:message key="addMagazine"></fmt:message></button>
</form>
</body>
</html>
