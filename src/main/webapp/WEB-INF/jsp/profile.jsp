<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Профиль пользователя</title>
</head>

<body>
<sec:authorize access="!isAuthenticated()">
    <% response.sendRedirect("/"); %>
</sec:authorize>
<div>
    <form method="POST" action="/profile/${userId}">
        <h2>Профиль пользователя</h2>
        <div>
            <input name="username" type="text" placeholder="Username"
                   autofocus="true"/>
            <input name="password" type="password" placeholder="Password"/>
            <input name="passwordConfirm" type="password" placeholder="passwordConfirm"/>
            <input name="email" type="text" placeholder="Email"
                   autofocus="true"/>
            <select name="roleslist">
                <c:forEach items="${roleList}" var="listrole">
                    <option value="${listrole}"><c:out value="${listrole}" /></option>
                </c:forEach>
            </select>

        </div>

        <button type="submit">Сохранить</button>
        <a href="/">Главная</a>
    </form>
</div>
</body>
</html>
