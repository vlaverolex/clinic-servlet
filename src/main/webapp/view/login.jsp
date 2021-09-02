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

    <title>Clinic</title>
</head>
<body onload="noBack();" onpageshow="if (event.persisted) noBack();" onunload="">
<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <div class="panel panel-default" style="margin-top:45px">
                <div class="panel-heading">
                    <h3 class="panel-title"><fmt:message key="login" bundle="${messages}"/></h3>
                    <div class="lang-choose">
                        <a href="?lang=en">EN</a>
                        <span class="lang-choose__separator">/</span>
                        <a href="?lang=uk">UA</a>
                    </div>
                </div>
                <div class="panel-body">
                    <c:if test="${requestScope.errorMessage != null}">
                        <div class="alert alert-danger"
                             role="alert"><fmt:message key="${requestScope.errorMessage}" bundle="${messages}"/></div>
                    </c:if>
                    <c:if test="${requestScope.logoutMessage != null}">
                        <div class="alert alert-info"
                             role="alert"><fmt:message key="${requestScope.logoutMessage}" bundle="${messages}"/></div>
                    </c:if>

                    <form action="${pageContext.request.contextPath}/app/login" method="post">
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
                        <button type="submit" class="btn btn-primary">
                            <fmt:message key="LogIn" bundle="${messages}"/>
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    window.history.forward();
    function noBack() {
        window.history.forward();
    }
</script>
</body>
</html>