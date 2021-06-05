<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"></fmt:setLocale>
<fmt:setBundle basename="i18n.lang"></fmt:setBundle>
<html>
<head>
    <title><fmt:message key="catalog"></fmt:message></title>
</head>
<body>

<form action="getCatalog" name="magazines" id="magazines" method="get">
    <table border="1" align="center" name="magazines">
        <caption><fmt:message key="chooseMagazine"></fmt:message></caption>
        <tr>
            <th>
                <label for="category"><fmt:message key="chooseYourCategory"></fmt:message></label>
                <select id="category" size="1" form="magazines" name="category">
                    <option  value="all"  >All</option>
                    <c:forEach var="category" items="${categories}">

                        <option value="${category.getId()}">
                            <c:out value="${category.getName()}"></c:out>
                        </option>
                    </c:forEach>
                </select>
            </th>
            <th>
                <label for="records">
                    <fmt:message key="recordsPerPage"></fmt:message>
                </label>
                <select form="magazines" id="records" name="recordsPerPage">
                    <option value="1">1</option>
                    <option value="5">5</option>
                    <option value="10" selected>10</option>
                    <option value="15">15</option>
                </select>
            </th>
            <th>
                <label for="price"><fmt:message key="price"></fmt:message></label>
                <select id="price" size="1" form="magazines" name="price">
                    <option  value="ignore"><fmt:message key="ignore"></fmt:message></option>
                    <option value="ASC"><fmt:message key="asc"></fmt:message></option>
                    <option value="DESC"><fmt:message key="desc"></fmt:message></option>
                </select>
            </th>
            <th><input form="magazines" type="text" id="name" name="name" placeholder="Search By Name"></th>
            <th><button type="submit">
                <fmt:message key="search"></fmt:message>
            </button></th>
        </tr>
    </table>
    <input form="magazines" type="hidden" name="filter" id="filter">
</form>

<c:forEach var="magazine" items="${magazines}">
    <hr>
    <p><fmt:message key="magazineName"></fmt:message>
        <a href="${pageContext.request.contextPath}/app/get_mag_info?id=${magazine.getId()}">
            <c:out value="${magazine.getName()}"></c:out>
        </a>
    </p>
    <p><fmt:message key="description"></fmt:message>
        <c:out value="${magazine.getDescription()}"></c:out>
    </p>
    <p>
        <fmt:message key="price"></fmt:message>
        <c:out value="${magazine.getPrice()}"></c:out>
    </p>
    <p>
        <fmt:message key="publisher"></fmt:message>
        <c:out value="${magazine.getPublisher()}"></c:out>
    </p>
    <p>
        <fmt:message key="category"></fmt:message>
        <c:out value="${magazine.getCategory().getName()}"></c:out>
    </p>
    <hr>
</c:forEach>
<hr>
<c:if  test="${searchParams!=null}">
    <c:set var="order" value="${searchParams.hasPriceOrder()}" scope="page" />
    <c:if test="${searchParams.currentPage!=0}">
        <c:if test="${order}">
            <li >
                <a  href="${pageContext.request.contextPath}/app/getCatalog?name=${searchParams.getName()}&category=${searchParams.getCategory()}&price=${searchParams.getPriceOrder()}&recordsPerPage=${searchParams.getRecordsPerPage()}&currentPage=${searchParams.getCurrentPage()-1}&filter="
                >
                    <fmt:message key="previous"></fmt:message>
                </a>
            </li>
        </c:if>
        <c:if test="${order==false}">
            <a  href="${pageContext.request.contextPath}/app/getCatalog?name=${searchParams.getName()}&category=${searchParams.getCategory()}&recordsPerPage=${searchParams.getRecordsPerPage()}&currentPage=${searchParams.getCurrentPage()-1}&filter="
            >
                <fmt:message key="previous"></fmt:message>
            </a>
        </c:if>
    </c:if>
    <c:if test="${searchParams.getCurrentPage()+1 lt nOfPages}">
    <c:if test="${order}">
        <li >
        <a  href="${pageContext.request.contextPath}/app/getCatalog?name=${searchParams.getName()}&category=${searchParams.getCategory()}&price=${searchParams.getPriceOrder()}&recordsPerPage=${searchParams.getRecordsPerPage()}&currentPage=${searchParams.getCurrentPage()+1}&filter="
        >
            <fmt:message key="next"></fmt:message>
        </a>
    </li>
    </c:if>
    <c:if test="${order==false}">
        <a  href="${pageContext.request.contextPath}/app/getCatalog?name=${searchParams.getName()}&category=${searchParams.getCategory()}&recordsPerPage=${searchParams.getRecordsPerPage()}&currentPage=${searchParams.getCurrentPage()+1}&filter="
        >
            <fmt:message key="next"></fmt:message>
        </a>
    </c:if>
</c:if>
</c:if>
</body>
</html>
