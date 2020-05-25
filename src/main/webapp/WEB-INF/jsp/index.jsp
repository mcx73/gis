<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE HTML>
<html>
<head>
    <title>Главная</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" type="text/css" href="${contextPath}/resources/css/style.css">
</head>
<body>
<div>
    <h3>${pageContext.request.userPrincipal.name}</h3>
    <sec:authorize access="!isAuthenticated()">
        <h4><a href="/login">Войти</a></h4>
        <h4><a href="/registration">Зарегистрироваться</a></h4>
    </sec:authorize>
    <sec:authorize access="isAuthenticated()">
        <h4><a href="/logout">Выйти</a></h4>
    </sec:authorize>
    <!--<sec:authorize access="!isAnonymous()">-->
        <sec:authorize access="hasAnyAuthority('USER', 'ADMIN')">
            <h4><a href="/docs">Документы (только пользователь)</a></h4>
        </sec:authorize>
        <sec:authorize access="hasAnyAuthority('MODERATOR', 'ADMIN')">
            <h4><a href="/mfc">МФЦ (только модератор)</a></h4>
        </sec:authorize>
        <sec:authorize access="hasAuthority('ADMIN')">
            <h4><a href="/admin">Пользователи (только админ)</a></h4>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
            <h4><a href="/profile">Профайл</a></h4>
        </sec:authorize>
    </sec:authorize>
</div>
</body>
</html>

<!--
Для скрытия части контента на странице для авторизованных пользователей (ссылка на страницу регистрации и авторизации)
можно использовать тег authorize из библиотеки тегов Spring Security. Параметр access принимает несколько выражений, можно,
например, установить ограничение в зависимости от роли пользователя hasRole('ADMIN')
-->


