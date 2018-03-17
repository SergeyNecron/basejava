<%@ page import="ru.javawebinar.basejava.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ru.javawebinar.basejava.model.ListSection" %>
<%@ page import="ru.javawebinar.basejava.model.OrganizationSection" %>
<%@ page import="ru.javawebinar.basejava.model.SectionType" %>
<%@ page import="ru.javawebinar.basejava.util.DateUtil" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="ru.javawebinar.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <hr>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSection(type)}"/>
            <jsp:useBean id="section" type="ru.javawebinar.basejava.model.Section"/>
            <h2><a>${type.title}</a></h2>
            <c:choose>
                <c:when test="${type=='OBJECTIVE' || type=='PERSONAL'}">
                    <input type='text' name='${type}' size=100 value='<%=section%>'>
                </c:when>
                <c:when test="${type=='QUALIFICATIONS' || type=='ACHIEVEMENT'}">
                    <textarea name='${type}' cols=100
                              rows=7><%=String.join("\n", ((ListSection) section).getItems())%></textarea>
                </c:when>
                <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                    <c:if test="${type=='EXPERIENCE'}">
                        <button type="submit" name="action" value="addEXPERIENCE">Добавить организацию</button>
                        <button type="submit" name="action" value="addPositionEXPERIENCE">Добавить должность</button>
                        <br>
                    </c:if>

                    <c:if test="${type=='EDUCATION'}">
                        <button type="submit" name="action" value="addEDUCATION">Добавить организацию</button>
                        <button type="submit" name="action" value="addPositionEDUCATION">Добавить должность</button>
                        <br><br>
                    </c:if>

                    <c:forEach var="organizations" items="<%=((OrganizationSection) section).getOrganizations()%>"
                               varStatus="counter">
                        <dl>
                            <dt>Название учереждения:</dt>
                            <dd><input type="text" name='${type}' size=100 value="${organizations.homePage.name}"></dd>
                        </dl>
                        <dl>
                            <dt>Сайт учереждения:</dt>
                            <dd><input type="text" name='${type}url' size=100 value="${organizations.homePage.url}">
                            </dd>
                            </dd>
                        </dl>

                        <div style="margin-left: 40px">
                            <c:forEach var="positions" items="${organizations.positions}">
                                <jsp:useBean id="positions" type="ru.javawebinar.basejava.model.Organization.Position"/>
                                <dl>
                                    <dt>Начальная дата:</dt>
                                    <dd>
                                        <input type="text" name="${type}${counter.index}startDate" size=10
                                               value="<%=DateUtil.format(positions.getStartDate())%>"
                                               placeholder="m/yyyy">
                                    </dd>
                                </dl>
                                <dl>
                                    <dt>Конечная дата:</dt>
                                    <dd>
                                        <input type="text" name="${type}${counter.index}endDate" size=10
                                               value="<%=DateUtil.format(positions.getEndDate())%>"
                                               placeholder="m/yyyy">
                                </dl>
                                <dl>
                                    <dt>Должность:</dt>
                                    <dd><input type="text" name='${type}${counter.index}title' size=100
                                               value="${positions.title}">
                                </dl>
                                <dl>
                                    <dt>Описание:</dt>
                                    <dd><textarea name="${type}${counter.index}description" rows=7
                                                  cols=100>${positions.description}</textarea></dd>
                                </dl>
                            </c:forEach>
                        </div>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
        <button type="submit">Сохранить</button>
    </form>
    <button onclick="window.history.back()">Отменить</button>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

