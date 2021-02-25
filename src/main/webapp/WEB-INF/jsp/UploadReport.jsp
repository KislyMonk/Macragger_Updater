<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="<c:url value="main.css" />" rel="stylesheet">
    <title>Анализ по городам</title>
</head>
<body>
<form method="POST" enctype="multipart/form-data"
      action="/">
    Загрузи файлик:  <input type="file" name="file"><br />
    <input type="submit" value="Upload">
</form>
Сообщение от сервера: ${message}
</body>
</html>