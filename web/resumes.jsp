<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: op
  Date: 01.03.18
  Time: 14:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<hr/>
<table border="1" cellpadding="8" cellspacing="0">
    <thead>
    <tr>
        <th>Резюме</th>
    </tr>
    <c:forEach items="${resumes}" var="resume">
        <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume"/>
    <tr>
        <td>${resume.fullName}</td>
    </tr>
    </c:forEach>
</table>
</body>
</html>
