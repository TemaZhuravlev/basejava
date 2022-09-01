<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
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
            <c:choose>
                <c:when test="${type.name() == 'OBJECTIVE' || type.name() == 'PERSONAL'}">
                    <dl>
                        <dt>${type.title}</dt>
                        <dd><textarea rows="10" cols="45" name="${type.name()}">${section}</textarea>
                        </dd>
                    </dl>
                </c:when>
                <c:when test="${type.name() == 'ACHIEVEMENT' || type.name() == 'QUALIFICATIONS'}">
                    <dl>
                        <dt>${type.title}</dt>
                        <dd><textarea rows="10" cols="45"
                                      name="${type.name()}">${section.toString()}</textarea></dd>
                    </dl>
                </c:when>
                <c:when test="${type.name() == 'EXPERIENCE' ||  type.name() == 'EDUCATION'}">
                    <h3>${type.title}</h3>
                    <div id="${type.name()}"  class="organization-edit">
                        <c:forEach var="organization" items="${section.getOrganizations()}" varStatus="counter">
                            <c:set var="orgCounter" value="${counter.index}"/>
                            <div id="${type.name()}-chld"  class="chld">
                                <div id="${type.name()}-name" class="name">
                                    <dl>
                                        <dt>Название организации</dt>
                                        <dd><input type="text" name="${type.name()}" size="50"
                                                   value="${organization.name}">
                                        </dd>
                                    </dl>
                                    <dl>
                                        <dt>Сайт организации</dt>
                                        <dd><input type="text" name="${type.name()}url" size="30"
                                                   value="${organization.homePage.url}"></dd>
                                    </dl>
                                </div>
                                <c:forEach var="period" items="${organization.getPeriods()}">
                                    <div id="${type.name()}-period" class="period">
                                        <dl>
                                            <dt>Начало работы</dt>
                                            <dd><input type="date" name="${type.name()}dateFrom${orgCounter}"
                                                       size="30"
                                                       value="${period.getDateFrom()}"></dd>
                                        </dl>
                                        <dl>
                                            <dt>Конец работы</dt>
                                            <dd><input type="date" name="${type.name()}dateTo${orgCounter}" size="30"
                                                       value="${period.getDateTo()}"></dd>
                                        </dl>
                                        <dl>
                                            <dt>Должность</dt>
                                            <dd><input type="text" name="${type.name()}title${orgCounter}" size="30"
                                                       value="${period.getTitle()}"></dd>
                                        </dl>
                                        <dl>
                                            <dt>Обязанности</dt>
                                            <dd><textarea rows="10" cols="30"
                                                          name="${type.name()}description${orgCounter}">${period.getDescription()}</textarea>
                                            </dd>
                                        </dl>
                                        <button type="button" id="add-period" onclick="return addPeriod(this)">Добавить
                                            период
                                        </button>
                                        <button type="button" id="delete-period" onclick="return deletePeriod(this)">Удалить период</button>
                                    </div>
                                </c:forEach>
                                <button type="button" id="delete-org" onclick="return deleteOrg${type.name()}(this)">Удалить организацию</button>
                            </div>
                        </c:forEach>
                    </div>
                    <button type="button" id="add-org" onclick="return addOrg${type.name()}()">Добавить
                        организацию
                    </button>
                    <script type="text/javascript">
                        $countt${type.name()} = ${orgCounter};
                        function addOrg${type.name()}() {
                            $countt${type.name()}++;
                            $("div#${type.name()}").append('<div id="${type.name()}-chld" class="chld"><div id="${type.name()}-name"  class="name">' +
                                '<dl><dt>Название организации</dt><dd><input type="text" name="${type.name()}" size="50" ></dd></dl>' +
                                '<dl><dt>Сайт организации</dt><dd><input type="text" name="${type.name()}url" size="30" ></dd></dl></div>' +
                                '<div id="${type.name()}-period" class="period">' +
                                '<dl><dt>Начало работы</dt><dd><input type="date" name="${type.name()}dateFrom' + $countt${type.name()} + '" size="30"></dd></dl>' +
                                '<dl><dt>Конец работы</dt><dd><input type="date" name="${type.name()}dateTo' + $countt${type.name()} + '" size="30"></dd></dl>' +
                                '<dl><dt>Должность</dt><dd><input type="text" name="${type.name()}title' + $countt${type.name()} + '" size="30"></dd></dl>' +
                                '<dl><dt>Обязанности</dt><dd><textarea rows="10" cols="30" name="${type.name()}description' + $countt${type.name()} + '"></textarea></dd></dl>' +
                                '<button type="button" id="add-period" onclick="return addPeriod(this)">Добавить период</button>' +
                                '<button type="button" id="delete-period" onclick="return deletePeriod(this)">Удалить период</button></div>' +
                                '<button type="button" id="delete-org" onclick="return deleteOrg${type.name()}(this)">Удалить организацию</button></div>');
                        }
                        function deleteOrg${type.name()}(e) {
                            let $parent = $(e).parent();
                            if ($parent.siblings('.chld').length) {
                                $parent.remove();
                            }
                            let $count = 0;
                            for (let i = 0; i <= $countt${type.name()}; i++) {
                                if($("[name='${type.name()}dateFrom"+i+"']").length !== 0){
                                    $("div#${type.name()}").find("[name='${type.name()}dateFrom"+i+"']").attr('name', '${type.name()}dateFrom'+$count);
                                    $("div#${type.name()}").find("[name='${type.name()}dateTo"+i+"']").attr('name', '${type.name()}dateTo'+$count);
                                    $("div#${type.name()}").find("[name='${type.name()}title"+i+"']").attr('name', '${type.name()}title'+$count);
                                    $("div#${type.name()}").find("[name='${type.name()}description"+i+"']").attr('name', '${type.name()}description'+$count);
                                    $count++;
                                }
                            }
                            $countt${type.name()}--;
                        }
                        function addPeriod(e) {
                            let $parent = $(e).parent();
                            let $clone = $parent.clone(true);
                            $parent.after($clone);
                            $clone.find('input').val('');
                            $clone.find('textarea').val('');
                        }
                        function deletePeriod(e) {
                            let $parent = $(e).parent();
                            if ($parent.siblings('.period').length) {
                                $parent.remove();
                            }
                        }
                    </script>
                </c:when>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button type="button" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
