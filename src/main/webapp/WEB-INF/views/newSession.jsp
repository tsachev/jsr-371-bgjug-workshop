<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=utf-8" language="java" %>
<html>
<head>
    <title>Bulgarian JUG</title>
</head>
<body>
<c:forEach items="${errors.messages}" var="msg">
    <div style="color:red"><c:out value="${msg}" /></div>
</c:forEach>
Submit session proposal:
<form method="post">
    <label for="title">Title:</label><input type="text" id="title" name="title"><br>
    <label for="description">Description:</label><textarea id="description" name="description"></textarea><br>
    <input type="submit" value="Submit">
</form>
</body>
</html>
