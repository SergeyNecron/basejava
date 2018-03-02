<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3>Список резюме:</h3>
<table border="1" cellpadding="8" cellspacing="0">
    <thead>
    <tr>
        <th>UUID</th>
        <th>Full Name</th>
    </tr>
    <c:forEach items="${resumes}" var="resume">
        <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume"/>
    <tr>
        <td>${resume.uuid}</td>
        <td>${resume.fullName}</td>
    </tr>
    </c:forEach>
</table>
</body>
</html>
