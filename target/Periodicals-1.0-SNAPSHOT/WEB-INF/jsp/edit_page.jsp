<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"></fmt:setLocale>
<fmt:setBundle basename="i18n.lang"></fmt:setBundle>
<html>
<head>
    <title><fmt:message key="magInfo"></fmt:message></title>
</head>
<body>
<c:if test="${status!=null}">
    <fmt:message key="operationStatus"></fmt:message>
    <c:out value="${status}"></c:out>

</c:if>
<form action="edit_magazine" id="edit_magazine"  name="magazine"  method="post" >
    <input form="edit_magazine" type="hidden" name="magazine_id" value="${magazine.id}">
    <hr>
    <p>
    <label for="name">
        <fmt:message key="oldName"></fmt:message>
        <c:out value="${magazine.name}"></c:out> </label>
    <input type="text" placeholder="Enter new Name" name="name" id="name" required>
    </p>
    <p>
        <label for="description">
            <fmt:message key="oldDescription"></fmt:message>
            <c:out value=" ${magazine.description}"></c:out></label>
    <input type="text" placeholder="Enter new description" name="description" id="description" required>
    </p>
    <p>
        <fmt:message key="oldPrice"></fmt:message>
        <label for="price"><c:out value="${magazine.price}"></c:out></label>
    <input type="text" placeholder="Enter new price" name="price" id="price" required>
    </p>
    <p>
        <fmt:message key="oldCategory"></fmt:message>
        <label for="category"><c:out value=" ${magazine.category}"></c:out></label>
        <p>
        <select size="5" form="edit_magazine" name="category_id" id="category">
            <c:forEach var="category" items="${categories}">
                <option value="${category.getId()}">
                    <c:out value="${category.getName()}"></c:out>
                </option>
            </c:forEach>

        </select>
</p>
    <p>
        <fmt:message key="oldCategory"></fmt:message>
        <label for="publisher"><c:out value="${magazine.publisher}"></c:out></label>
    <input type="text" placeholder="Enter new category" name="publisher" id="publisher" required>
    </p>
    <hr>
</form>
<c:set var="role" value="${user.getRole()}"></c:set>
<c:if test="${role.toString().equals('ADMIN')}">
    <button form="edit_magazine">
        <fmt:message key="submit"></fmt:message>
    </button>
</c:if>

</body>
</html>

