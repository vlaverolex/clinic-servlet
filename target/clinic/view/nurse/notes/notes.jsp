<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages" var="messages"/>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <title>Medical book</title>

    <link rel="stylesheet" href="<c:url value="/styles/global.css"/>">
    <link rel="stylesheet" href="<c:url value="/styles/header.css"/>">

    <script src="https://kit.fontawesome.com/dd59a6bb17.js" crossorigin="anonymous"></script>
</head>
<body>

<jsp:include page="/view/parts/header.jsp">
    <jsp:param name="user" value="nurse"/>
    <jsp:param name="page" value="page=${requestScope.pager.currentPage}&column=${requestScope.pager.sorting.column}&direction=${requestScope.pager.sorting.direction}&patientId=${requestScope.patient.id}"/>
</jsp:include>


<section class="section section-show-all">
    <div class="container">
        <div>
            <a style="text-transform: capitalize !important;" class="btn btn-info back-button" href="${pageContext.request.contextPath}/app/nurse"><fmt:message key="back" bundle="${messages}"/></a>
        </div>

        <div class="section__inner section-show-all">
            <h2 class="section__title section-show-all__title"><fmt:message key="medicalBook" bundle="${messages}"/></h2>

            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col">ID
                        <a href="?column=id&direction=DESC&patientId=${requestScope.patient.id}"><i class="fa fa-solid fa-arrow-down"></i></a>
                        <a href="?column=id&direction=ASC&patientId=${requestScope.patient.id}"><i class="fa fa-solid fa-arrow-up"></i></a>
                    </th>
                    <th scope="col"><fmt:message key="date" bundle="${messages}"/>
                        <a href="?column=creation_date&direction=DESC&patientId=${requestScope.patient.id}"><i class="fa fa-solid fa-arrow-down"></i></a>
                        <a href="?column=creation_date&direction=ASC&patientId=${requestScope.patient.id}"><i class="fa fa-solid fa-arrow-up"></i></a>
                    </th>
                    <th scope="col"><fmt:message key="view" bundle="${messages}"/></th>
                </tr>
                </thead>
                <tbody>

                <c:forEach items="${requestScope.notes}" var="note">
                    <tr>
                        <th scope="row">${note.id}</th>
                        <th scope="row">${note.creationDate}</th>
                        <th scope="row">
                            <a class="btn btn-success medical-note-view-link"
                               href="${pageContext.request.contextPath}/app/nurse/patients/medical-book/note?patientId=${note.patientId}&noteId=${note.id}"><fmt:message key="view" bundle="${messages}"/>
                                <c:if test="${note.procedures!=null && note.proceduresDone==false}">
                                    <i class="fa fa-solid fa-eye"></i>
                                </c:if>
                            </a>
                        </th>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <jsp:include page="/view/parts/pager.jsp">
            <jsp:param name="sortParam" value="column=${requestScope.pager.sorting.column}&direction=${requestScope.pager.sorting.direction}&patientId=${requestScope.patient.id}"/>
        </jsp:include>
    </div>
</section>
</body>
</html>