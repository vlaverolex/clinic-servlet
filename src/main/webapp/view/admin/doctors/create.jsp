<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages" var="messages"/>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <title>Clinic</title>

    <link rel="stylesheet" href="<c:url value="/styles/global.css"/>">
    <link rel="stylesheet" href="<c:url value="/styles/header.css"/>">
</head>
<body>

<jsp:include page="/view/parts/header.jsp">
    <jsp:param name="user" value="admin"/>
</jsp:include>


<section class="section">
    <div class="container">
        <div>
            <a style="text-transform: capitalize !important;" class="btn btn-info back-button"
               href="${pageContext.request.contextPath}/app/admin/doctors"><fmt:message key="back"
                                                                                        bundle="${messages}"/></a>
        </div>

        <div class="row">
            <div class="col-md-6 col-md-offset-3">
                <div class="panel panel-default" style="margin-top:45px">
                    <div class="panel-heading">
                        <h3 class="panel-title"><fmt:message key="addingDoctor" bundle="${messages}"/></h3>
                    </div>
                    <div class="panel-body">

                        <c:if test="${requestScope.userExistErrorMessage != null}">
                            <div class="alert alert-danger"
                                 role="alert"><fmt:message key="${requestScope.userExistErrorMessage}"
                                                           bundle="${messages}"/></div>
                        </c:if>
                        <c:if test="${requestScope.usernameLengthErrorMessage != null}">
                            <div class="alert alert-danger"
                                 role="alert"><fmt:message key="${requestScope.usernameLengthErrorMessage}"
                                                           bundle="${messages}"/></div>
                        </c:if>
                        <c:if test="${requestScope.passwordLengthErrorMessage != null}">
                            <div class="alert alert-danger"
                                 role="alert"><fmt:message key="${requestScope.passwordLengthErrorMessage}"
                                                           bundle="${messages}"/></div>
                        </c:if>
                        <c:if test="${requestScope.passwordsDoNotMatch != null}">
                            <div class="alert alert-danger"
                                 role="alert"><fmt:message key="${requestScope.passwordsDoNotMatch}"
                                                           bundle="${messages}"/></div>
                        </c:if>

                        <form  action="${pageContext.request.contextPath}/app/admin/doctors/create" method="post">
                            <input name="${_csrf.parameterName}" value="${_csrf.token}" type="hidden">
                            <div class="form-group">
                                <label for="username"><fmt:message key="username" bundle="${messages}"/></label>
                                <input required type="text" class="form-control" id="username"
                                       placeholder="<fmt:message key="username" bundle="${messages}"/>"
                                       name="username">
                            </div>
                            <div class="form-group">
                                <label for="password"><fmt:message key="password" bundle="${messages}"/></label>
                                <input required type="password" class="form-control" id="password"
                                       placeholder="<fmt:message key="password" bundle="${messages}"/>"
                                       name="password">
                            </div>
                            <div class="form-group">
                                <label for="confirmPassword"><fmt:message key="confirmPassword"
                                                                          bundle="${messages}"/></label>
                                <input required type="password" class="form-control" id="confirmPassword"
                                       placeholder="<fmt:message key="confirmPassword" bundle="${messages}"/>"
                                       name="confirmPassword" >
                            </div>
                            <div class="form-group">
                                <label for="birthday"><fmt:message key="birthday" bundle="${messages}"/></label>
                                <input required type="date" class="form-control" id="birthday"
                                       placeholder="<fmt:message key="birthday" bundle="${messages}"/>"
                                       name="birthday" max="${requestScope.maxDate.toString()}">
                            </div>
                            <div class="form-group">
                                <label for="categoryName"><fmt:message key="category" bundle="${messages}"/></label>
                                <label for="categoryName">
                                    <select name="categoryName" id="categoryName">
                                        <c:forEach items="${requestScope.categories}" var="category">
                                            <option name="categoryName" value="${category.engName()}">
                                              <c:choose>
                                                  <c:when test="${sessionScope.lang=='uk'}">
                                                      ${category.ukrName()}
                                                  </c:when>
                                                  <c:otherwise>
                                                      ${category.engName()}
                                                  </c:otherwise>
                                              </c:choose>
                                            </option>
                                        </c:forEach>
                                    </select>
                                </label>
                            </div>
                            <button type="submit" class="btn btn-success"><fmt:message key="add"
                                                                                       bundle="${messages}"/></button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
</html>
