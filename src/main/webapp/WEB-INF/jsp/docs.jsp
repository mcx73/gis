<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Документы</title>
</head>
<body>
<div>
    <h2>Документы <br> Только для залогинившихся пользователей.</h2>
    <form method="POST" enctype="multipart/form-data">
        <input type="file" name="file">
        <button type="submit">добавить</button>
    </form>
            <c:forEach items="${docum}" var="doc">
            <div>
                <b>${doc.id}</b>
                <strong>${doc.authorName}</strong>
                <div>

                <c:if test="${doc.filename!= null}">
                  <!--  <img src="/img/{doc.icon}">
                  -->
                </c:if>
            </div>
        </c:forEach>
</div>
    <a href="/">Главная</a>
</div>
</body>

</html>
