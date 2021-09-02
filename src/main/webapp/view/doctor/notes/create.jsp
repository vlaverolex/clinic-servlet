<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages" var="messages"/>
<html>
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- Latest compiled and minified CSS -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
        integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
  <!-- Optional theme -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
        integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

  <link rel="stylesheet" href="<c:url value="/styles/global.css"/>">
  <link rel="stylesheet" href="<c:url value="/styles/header.css"/>">

  <title>Note</title>

</head>
<body>

<jsp:include page="/view/parts/header.jsp">
  <jsp:param name="user" value="doctor"/>
  <jsp:param name="page" value="patientId=${requestScope.patient.id}"/>
</jsp:include>

<section class="section">
  <div class="container">
    <div>
      <a style="text-transform: capitalize !important;" class="btn btn-info back-button" href="${pageContext.request.contextPath}/app/doctor/patients/medical-book?patientId=${requestScope.patient.id}"><fmt:message key="back" bundle="${messages}"/></a>
    </div>

    <div class="row">
      <div class="col-md-6 col-md-offset-3">
        <div class="panel panel-default" style="margin-top:45px">
          <div class="panel-heading">
            <h3 class="panel-title"><fmt:message key="addingNote" bundle="${messages}"/></h3>
          </div>
          <div class="panel-body">
            <form action="${pageContext.request.contextPath}/app/doctor/patients/medical-book/create?patientId=${requestScope.patient.id}" method="post">
              <input name="${_csrf.parameterName}" value="${_csrf.token}" type="hidden">
              <div class="form-group">
                <label for="diagnosis"><fmt:message key="diagnosis" bundle="${messages}"/></label>
                <input required type="text" class="form-control" id="diagnosis"
                       placeholder="<fmt:message key="diagnosis" bundle="${messages}"/>"
                       name="diagnosis">
              </div>
              <div class="form-group">
                <label for="procedures"><fmt:message key="procedures" bundle="${messages}"/></label>
                <textarea required class="form-control" id="procedures"
                          placeholder="<fmt:message key="procedures" bundle="${messages}"/>"
                          name="procedures"></textarea>
              </div>
              <div class="form-group">
                <label for="surgery"><fmt:message key="surgery" bundle="${messages}"/></label>
                <textarea class="form-control" id="surgery"
                          placeholder="<fmt:message key="surgery" bundle="${messages}"/>"
                          name="surgery"></textarea>
              </div>
              <button type="submit" class="btn btn-success"><fmt:message key="add" bundle="${messages}"/></button>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</section>
</body>
</html>