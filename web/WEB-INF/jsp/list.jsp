<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Список всех резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <div class="add-resume">
        <a class="no-underline-anchor" href="resume?action=add"><img src="img/add-person.svg"></a>
        <a class="text-anchor" href="resume?action=add"><p class="add-resume-title">Добавить резюме</p></a>
    </div>
    <table>
        <tr>
            <th>Имя</th>
            <th>Контакты</th>
            <th>Редактировать</th>
            <th>Удалить</th>
        </tr>
        <c:forEach items="${resumes}" var="resume">
            <jsp:useBean id="resume" type="com.urise.webapp.model.Resume"/>
            <tr>
                <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                <td><%=ContactType.E_MAIL.toHtml(resume.getContact(ContactType.E_MAIL))%>
                </td>
                <td><a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/edit.svg"></a></td>
                <td><a href="resume?uuid=${resume.uuid}&action=delete"><img src="img/remove.svg"></a></td>
            </tr>
        </c:forEach>

    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
