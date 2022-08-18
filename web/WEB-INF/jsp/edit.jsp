<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>ФИО:</dt>
            <dd><input type="text" name="fullName" size="50"
                       pattern="[А-Яа-яA-Za-z]*?\s[А-Яа-яA-Za-z]*?\s[А-Яа-яA-Za-z]*"
                       value="${resume.fullName}" required placeholder="Фамилия Имя Отчество"></dd>
        </dl>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <h3>Секции:</h3>
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSection(type)}"/>
            <dl>
                <c:choose>
                    <c:when test="${type.name() == 'OBJECTIVE'}">
                        <dt>${type.title}</dt>
                        <dd><textarea rows="10" cols="45" name="${type.name()}">${resume.getSection(type)}</textarea>
                        </dd>
                    </c:when>
                    <c:when test="${type.name() == 'PERSONAL'}">
                        <dt>${type.title}</dt>
                        <dd><textarea rows="10" cols="45" name="${type.name()}">${resume.getSection(type)}</textarea>
                        </dd>
                    </c:when>
                    <c:when test="${type.name() == 'ACHIEVEMENT'}">
                        <dt>${type.title}</dt>
                        <dd><textarea rows="10" cols="45"
                                      name="${type.name()}">${resume.getSection(type).toString()}</textarea></dd>
                    </c:when>
                    <c:when test="${type.name() == 'QUALIFICATIONS'}">
                        <dt>${type.title}</dt>
                        <dd><textarea rows="10" cols="45"
                                      name="${type.name()}">${resume.getSection(type).toString()}</textarea></dd>
                    </c:when>
                    <c:when test="${type.name() == 'EXPERIENCE' || type.name() == 'EDUCATION'}">
                        <h3>${type.title}</h3>
                        <c:forEach var="organization" items="${section.getOrganizations()}" varStatus="counter">
                            <dl>
                                <dt>Название организации</dt>
                                <dd><input type="text" name="${type.name()}" size="30" value="${organization.name}">
                                </dd>
                            </dl>
                            <dl>
                                <dt>Сайт организации</dt>
                                <dd><input type="text" name="${type.name()}url" size="30"
                                           value="${organization.homePage.url}"></dd>
                            </dl>
                            <c:forEach var="period" items="${organization.getPeriods()}">
                                <dl>
                                    <dt>Начало работы</dt>
                                    <dd><input type="date" name="${type.name()}dateFrom${counter.index}" size="30"
                                               value="${period.getDateFrom()}"></dd>
                                </dl>
                                <dl>
                                    <dt>Конец работы</dt>
                                    <dd><input type="date" name="${type.name()}dateTo${counter.index}" size="30"
                                               value="${period.getDateTo()}"></dd>
                                </dl>
                                <dl>
                                    <dt>Должность</dt>
                                    <dd><input type="text" name="${type.name()}title${counter.index}" size="30"
                                               value="${period.getTitle()}"></dd>
                                </dl>
                                <dl>
                                    <dt>Обязанности</dt>
                                    <dd><textarea rows="10" cols="30"
                                                  name="${type.name()}description${counter.index}">${period.getDescription()}</textarea>
                                    </dd>
                                </dl>
                            </c:forEach>
                        </c:forEach>
                    </c:when>
                </c:choose>
            </dl>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button type="button" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
