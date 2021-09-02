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
  <jsp:param name="page" value="page=${requestScope.pager.currentPage}&column=${requestScope.pager.sorting.column}&direction=${requestScope.pager.sorting.direction}&&patientId=${requestScope.patientId}"/>
</jsp:include>


<section class="section section-show-all">
  <div class="container">
    <div>
      <a style="text-transform: capitalize !important;" class="btn btn-info back-button" href="${pageContext.request.contextPath}/app/admin/patients"><fmt:message key="back" bundle="${messages}"/></a>
    </div>

    <div class="section__inner section-show-all">
      <h2 class="section__title section-show-all__title"><fmt:message key="doctors" bundle="${messages}"/></h2>

      <table class="table table-striped">
        <thead>
        <tr>
          <th scope="col">ID</th>
          <th scope="col"><fmt:message key="username" bundle="${messages}"/>
            <a href="?column=username&direction=DESC&patientId=${requestScope.patientId}"><i class="fa fa-solid fa-arrow-down"></i></a>
            <a href="?column=username&direction=ASC&patientId=${requestScope.patientId}"><i class="fa fa-solid fa-arrow-up"></i></a>
          </th>
          <th scope="col"><fmt:message key="birthday" bundle="${messages}"/>
            <a href="?column=birthday&direction=DESC&patientId=${requestScope.patientId}"><i class="fa fa-solid fa-arrow-down"></i></a>
            <a href="?column=birthday&direction=ASC&patientId=${requestScope.patient.id}"><i class="fa fa-solid fa-arrow-up"></i></a>
          </th>
          <th scope="col"><fmt:message key="category" bundle="${messages}"/></th>
          <th scope="col"><fmt:message key="patientVolume" bundle="${messages}"/>
            <a href="?column=patient_volume&direction=DESC&patientId=${requestScope.patientId}"><i class="fa fa-solid fa-arrow-down"></i></a>
            <a href="?column=patient_volume&direction=ASC&patientId=${requestScope.patientId}"><i class="fa fa-solid fa-arrow-up"></i></a>
          </th>
          <th scope="col">Select</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${requestScope.doctors}" var="doctor">
          <tr>
            <th scope="row">${doctor.id}</th>
            <th scope="row">${doctor.username}</th>
            <th scope="row">${doctor.birthday}</th>
            <th scope="row"><fmt:message key="${doctor.category.engName()}" bundle="${messages}"/></th>
            <th scope="row">${doctor.patientVolume}</th>
            <th>
              <form method="post"
                    action="${pageContext.request.contextPath}/app/admin/patients/assign-doctor?patientId=${requestScope.patientId}&doctorId=${doctor.id}">
                <input name="${_csrf.parameterName}" value="${_csrf.token}" type="hidden">
                <button type="submit" class="btn btn-success"><fmt:message key="select" bundle="${messages}"/></button>
              </form>
            </th>
          </tr>
        </c:forEach>
        </tbody>
      </table>
    </div>
    <jsp:include page="/view/parts/pager.jsp">
      <jsp:param name="sortParam" value="column=${requestScope.pager.sorting.column}&direction=${requestScope.pager.sorting.direction}&patientId=${requestScope.patientId}"/>
    </jsp:include>
  </div>
</section>

</body>
</html>