<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages" var="messages"/>
<html>
<head>
    <title>Clinic</title>
    <link rel="stylesheet" href="<c:url value="/styles/global.css"/>">
    <link rel="stylesheet" href="<c:url value="/styles/header.css"/>">
</head>
<body onload="noBack();" onpageshow="if (event.persisted) noBack();" onunload="">

<jsp:include page="/view/parts/header.jsp" >
    <jsp:param name="user" value="admin" />
</jsp:include>

<section class="section section-index">
    <div class="container">
        <div class="section__inner section-index__inner">
            <div class="section__inner-item">
                <a href="${pageContext.request.contextPath}/app/admin/doctors"><fmt:message key="doctors" bundle="${messages}"/></a>
            </div>
            <div class="section__inner-item">
                <a href="${pageContext.request.contextPath}/app/admin/nurses"><fmt:message key="nurses" bundle="${messages}"/></a>
            </div>
            <div class="section__inner-item">
                <a href="${pageContext.request.contextPath}/app/admin/patients"><fmt:message key="patients" bundle="${messages}"/></a>
            </div>
        </div>
    </div>
</section>

<script type="text/javascript">
    window.history.forward();
    function noBack() {
        window.history.forward();
    }
</script>
</body>
</html>

