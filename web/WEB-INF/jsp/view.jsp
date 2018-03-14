<%@ page import="ru.javawebinar.basejava.model.ListSection" %>
<%@ page import="ru.javawebinar.basejava.model.OrganizationSection" %>
<%@ page import="ru.javawebinar.basejava.model.TextSection" %>
<%@ page import="ru.javawebinar.basejava.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<ru.javawebinar.basejava.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    <p>
    <hr>
    <c:forEach var="sectionEntry" items="${resume.sections}">
        <jsp:useBean id="sectionEntry"
                     type="java.util.Map.Entry<ru.javawebinar.basejava.model.SectionType, ru.javawebinar.basejava.model.Section>"/>
        <c:set var="type" value="${sectionEntry.key}"/>
        <c:set var="section" value="${sectionEntry.value}"/>
        <jsp:useBean id="section" type="ru.javawebinar.basejava.model.Section"/>
        <h2><a name="type.name">${type.title}</a></h2>

        <c:if test="${type=='OBJECTIVE' || type=='PERSONAL'}">
            <%=((TextSection) section).getContent()%>
        </c:if>
        <c:if test="${type=='QUALIFICATIONS' || type=='ACHIEVEMENT'}">
            <ul>
                <c:forEach var="item" items="<%=((ListSection) section).getItems()%>">
                    <li>${item}</li>
                </c:forEach>
            </ul>
        </c:if>
        <c:if test="${type=='EXPERIENCE' || type=='EDUCATION'}">
            <c:forEach var="organizations" items="<%=((OrganizationSection) section).getOrganizations()%>">
                <h3><a href="${organizations.homePage.url}">${organizations.homePage.name}</a></h3>
                <table cellpadding="2">
                    <c:forEach var="position" items="${organizations.positions}">
                        <jsp:useBean id="position" type="ru.javawebinar.basejava.model.Organization.Position"/>
                        <tr>
                            <td width="20%" style="vertical-align: top">
                                <c:if test="${type=='EXPERIENCE'}">
                                    <%=DateUtil.format(position.getStartDate()) + " - " + DateUtil.format(position.getEndDate()) + ":"%>
                                </c:if>
                                <c:if test="${ type=='EDUCATION'}">
                                    <%=DateUtil.format(position.getStartDate().getYear()) + " - " + DateUtil.format(position.getEndDate().getYear()) + ":"%>
                                </c:if>
                            </td>
                            <td><b>${position.title}</b><br>${position.description}</td>
                        </tr>

                    </c:forEach>
                </table>
            </c:forEach>
        </c:if>
    </c:forEach>
    <button onclick="window.history.back()">Назад</button>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
