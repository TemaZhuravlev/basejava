<%@ page import="com.urise.webapp.model.Resume" %>
<%@ page import="java.util.List" %>
<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Список всех резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <table>
        <tr>
           <th>Имя</th>
            <th>E-mail</th>
            <th></th>
            <th></th>
        </tr>
        <%
            for (Resume resume : (List<Resume>) request.getAttribute("resumes")){
        %>
        <tr>
            <td><a href="resume?uuid=<%=resume.getUuid()%>"><%=resume.getFullName()%></a></td>
            <td><%=resume.getContact(ContactType.E_MAIL)%></td>
            <td></td>
            <td></td>
        </tr>
        <%
            }
        %>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
