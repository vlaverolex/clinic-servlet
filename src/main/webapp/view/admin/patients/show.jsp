<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages" var="messages"/>
<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <title>Clinic</title>

    <link rel="stylesheet" href="<c:url value="/styles/global.css"/>">
    <link rel="stylesheet" href="<c:url value="/styles/header.css"/>">
    <script src="https://kit.fontawesome.com/dd59a6bb17.js" crossorigin="anonymous"></script>
</head>
<body>

<jsp:include page="/view/parts/header.jsp">
    <jsp:param name="user" value="admin"/>
    <jsp:param name="page" value="page=${requestScope.pager.currentPage}&column=${requestScope.pager.sorting.column}&direction=${requestScope.pager.sorting.direction}"/>
</jsp:include>


<section class="section section-show-all">
    <div class="container">
        <div>
            <a style="text-transform: capitalize !important;" class="btn btn-info back-button" href="${pageContext.request.contextPath}/app/admin">
                <fmt:message key="back" bundle="${messages}"/>
            </a>
        </div>

        <div class="section__inner section-show-all">
            <div class="section-show-all__add-button">
                <a class="btn btn-primary"
                   href="${pageContext.request.contextPath}/app/admin/patients/create">
                    <fmt:message key="newPatient" bundle="${messages}"/>
                </a>
            </div>

            <h2 class="section__title section-show-all__title"><fmt:message key="patients" bundle="${messages}"/></h2>

            <table class="table table-striped">
                <thead>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col"><fmt:message key="username" bundle="${messages}"/>
                        <a href="?column=username&direction=DESC"><i class="fa fa-solid fa-arrow-down"></i></a>
                        <a href="?column=username&direction=ASC"><i class="fa fa-solid fa-arrow-up"></i></a>
                    </th>
                    <th scope="col"><fmt:message key="birthday" bundle="${messages}"/>
                        <a href="?column=birthday&direction=DESC"><i class="fa fa-solid fa-arrow-down"></i></a>
                        <a href="?column=birthday&direction=ASC"><i class="fa fa-solid fa-arrow-up"></i></a>
                    </th>
                    <th scope="col"><fmt:message key="doctor" bundle="${messages}"/></th>
                    <th scope="col"><fmt:message key="delete" bundle="${messages}"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${requestScope.patients}" var="patient">
                    <tr>
                        <th scope="row">${patient.id}</th>
                        <th scope="row">${patient.username}</th>
                        <th scope="row">${patient.birthday}</th>
                        <th scope="row">
                            <c:choose>
                                <c:when test="${patient.doctor!=null}">
                                    ${patient.doctor.username}
                                    <form method="post" style="margin-top: 5px"
                                          action="${pageContext.request.contextPath}/app/admin/patients/remove-doctor?patientId=${patient.id}">
                                        <input name="${_csrf.parameterName}" value="${_csrf.token}" type="hidden">
                                        <button type="submit" class="badge badge-danger">
                                            <fmt:message key="delete" bundle="${messages}"/>
                                        </button>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <form method="post"
                                          action="${pageContext.request.contextPath}/app/admin/patients/assign-doctor?patientId=${patient.id}">
                                        <input name="${_csrf.parameterName}" value="${_csrf.token}" type="hidden">
                                        <button type="submit" class="btn btn-success">
                                            <fmt:message key="select" bundle="${messages}"/>
                                        </button>
                                    </form>
                                </c:otherwise>
                            </c:choose>
                        </th>
                        <th scope="row"><fmt:message key="delete" bundle="${messages}"/></th>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <jsp:include page="/view/parts/pager.jsp">
            <jsp:param name="sortParam" value="column=${requestScope.pager.sorting.column}&direction=${requestScope.pager.sorting.direction}"/>
        </jsp:include>
    </div>
</section>

</body>
</html>
