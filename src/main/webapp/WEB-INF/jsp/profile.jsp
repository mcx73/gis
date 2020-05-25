<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
    <form method="POST" action="/profile">
        <h2>Профиль пользователя</h2>
        <div>
            <input name="username" type="text" placeholder="Username"
                   autofocus="true"/>
            <input name="password" type="password" placeholder="Password"/>
            <input name="passwordConfirm" type="password" placeholder="passwordConfirm"/>
            <input name="email" type="text" placeholder="Email"
                   autofocus="true"/>
        </div>
        <button type="submit">Сохранить</button>
        <a href="/">Главная</a>
    </form>
</div>
</body>
</html>
