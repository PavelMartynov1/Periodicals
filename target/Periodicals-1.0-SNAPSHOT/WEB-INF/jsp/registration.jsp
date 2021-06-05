<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"></fmt:setLocale>
<fmt:setBundle basename="i18n.lang"></fmt:setBundle>
<form action="register" method="post">
    <div class="container">
        <h1><fmt:message key="register"></fmt:message> </h1>
        <p><fmt:message key="regMessage"></fmt:message></p>
        <p>
            <label for="first_name"><b><fmt:message key="firstName"></fmt:message></b></label>
            <input type="text" placeholder="<fmt:message key="firstNamePlaceholder"></fmt:message>" name="first_name" id="first_name" required>
        </p>
        <p>
            <label for="last_name"><b><fmt:message key="lastName"></fmt:message></b></label>
            <input type="text" placeholder="<fmt:message key="lastNamePlaceholder"></fmt:message>" name="last_name" id="last_name" required>
        </p>

        <label>
            <strong><fmt:message key="gender"></fmt:message> :</strong>
        </label><br>
        <input type="radio" name="gender" value="male" checked/> <fmt:message key="male"></fmt:message> <br>
        <input type="radio" name="gender" value="female" /><fmt:message key="female"></fmt:message> <br>
        <input type="radio" name="gender" value="other" /> <fmt:message key="other"></fmt:message>
        <br>

            <c:if test="${emailOccupied!=null}"> <fmt:message key="emailOccupied"></fmt:message>  </c:if>

        <c:if test="${emailError!=null}">
            <c:out value="${emailError}"></c:out>
        </c:if>
        <p>
        <label for="email"><b><fmt:message key="email"></fmt:message> </b></label>
        <input type="text" placeholder="<fmt:message key="emailPlaceholder"></fmt:message>" name="email" id="email" required>
</p>
        <c:choose>
            <c:when test="${passwordError!=null}">
                <fmt:message key="passErr"></fmt:message>

            </c:when>
            <c:when test="${passwordError==null and passwordEqualityError!=null}">
                <fmt:message key="passwordEqualityError"></fmt:message>
            </c:when>
        </c:choose>


        <p>
        <label for="password"><b><fmt:message key="password"></fmt:message></b></label>
        <input type="password" placeholder="<fmt:message key="passwordPlaceholder"></fmt:message>" name="password" id="password" required>
        </p>
        <p>
        <label for="password-repeat"><b><fmt:message key="repeatPassword"></fmt:message></b></label>
        <input type="password" placeholder="<fmt:message key="repeatPassword"></fmt:message>" name="password-repeat" id="password-repeat" required>

            <button type="submit" class="registerbtn"><fmt:message key="register"></fmt:message></button>
        </p>
    </div>

    <div class="container signin">
        <p><fmt:message key="alreadyHaveAnAccount"></fmt:message> <a href="${pageContext.request.contextPath}/app/getLogInPage"><fmt:message key="login"></fmt:message></a>.</p>
    </div>
</form>