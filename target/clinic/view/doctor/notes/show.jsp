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
  <title>Note</title>

  <link rel="stylesheet" href="<c:url value="/styles/global.css"/>">
  <link rel="stylesheet" href="<c:url value="/styles/header.css"/>">
</head>
<body>

<jsp:include page="/view/parts/header.jsp">
  <jsp:param name="user" value="doctor"/>
  <jsp:param name="page" value="page=${requestScope.pager.currentPage}
  &column=${requestScope.pager.sorting.column}&direction=${requestScope.pager.sorting.direction}
  &patientId=${requestScope.patient.id}&noteId=${requestScope.note.id}"/>
</jsp:include>


<section class="section section-show-all">
  <div class="container">
    <div class="section__inner section-show-all">
      <div>
        <a style="text-transform: capitalize !important;" class="btn btn-info back-button" href="${pageContext.request.contextPath}/app/doctor/patients/medical-book?patientId=${requestScope.patient.id}"><fmt:message key="back" bundle="${messages}"/></a>
      </div>

      <h2 class="section__title section-show-all__title"><fmt:message key="note" bundle="${messages}"/></h2>

      <div class="note-item"><fmt:message key="patient" bundle="${messages}"/>: <span>${requestScope.patient.username}</span></div>
      <div class="note-item"><fmt:message key="doctor" bundle="${messages}"/>: <span>${requestScope.doctorWhoCreateNote.username}</span></div>
      <div class="note-item"><fmt:message key="diagnosis" bundle="${messages}"/>: <span>${requestScope.note.diagnosis}</span></div>

      <div class="procedures-title"><fmt:message key="procedures" bundle="${messages}"/></div>
      <div style="margin-bottom: 15px">${requestScope.note.procedures}</div>
      <form method="post"
            action="${pageContext.request.contextPath}/app/doctor/patients/medical-book/note/make-procedures?noteId=${requestScope.note.id}&patientId=${requestScope.note.patientId}&executorId=${requestScope.currentUser.id}">
        <input name="${_csrf.parameterName}" value="${_csrf.token}" type="hidden">
        <c:choose>
          <c:when test="${requestScope.note.proceduresDone==true}">
            <button type="submit" class="btn btn-sm btn-success" disabled><fmt:message key="performed" bundle="${messages}"/></button>
            <div>${requestScope.personWhoMadeProcedures.username}</div>
          </c:when>
          <c:otherwise>
            <button type="submit" class="btn btn-success"><fmt:message key="perform" bundle="${messages}"/></button>
          </c:otherwise>
        </c:choose>
      </form>

      <c:if test="${requestScope.note.surgery!=null}">
        <div class="surgery-title">Surgery</div>
        <div style="margin-bottom: 15px">${requestScope.note.surgery}</div>
        <form method="post"
              action="${pageContext.request.contextPath}/app/doctor/patients/medical-book/note/make-surgery?noteId=${requestScope.note.id}&patientId=${requestScope.note.patientId}">
          <c:choose>
            <c:when test="${requestScope.note.surgeryDone}">
              <button type="submit" class="btn btn-sm btn-success" disabled>Performed</button>
            </c:when>
            <c:otherwise>
              <button type="submit"
                      class="btn btn-success">Perform
              </button>
            </c:otherwise>
          </c:choose>
        </form>
      </c:if>
    </div>
  </div>
</section>
</body>
</html>