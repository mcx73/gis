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
    <form method="POST" enctype="multipart/form-data"
          action="/upload">
        File to upload: <input type="file" name="file"><br /> Name: <input
            type="text" name="name"><br /> <br /> <input type="submit" value="Upload"> Press here to upload the file!
    </form>
    <a href="/">Главная</a>
</div>
</body>

</html>
