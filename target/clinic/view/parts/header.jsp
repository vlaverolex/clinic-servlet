<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages" var="messages"/>
<header class="header">
    <div class="container">
        <div class="header__inner">
            <div class="header__inner-logo">
                <a href="${pageContext.request.contextPath}/app/${param.user}">
                    <fmt:message key="${param.user}" bundle="${messages}"/>
                </a>
            </div>
            <div class="lang-choose">
                <c:choose>
                    <c:when test="${param.page==null}">
                        <a href="?lang=en">EN</a>
                        <span class="lang-choose__separator">/</span>
                        <a href="?lang=uk">UK</a>
                    </c:when>
                    <c:otherwise>
                        <a href="?lang=en&${param.page}">EN</a>
                        <span class="lang-choose__separator">/</span>
                        <a href="?lang=uk&${param.page}">UK</a>
                    </c:otherwise>
                </c:choose>

            </div>
            <div class="header__inner-menu">
                <a href="${pageContext.request.contextPath}/app/logout">
                    <fmt:message key="logOut" bundle="${messages}"/>
                </a>
            </div>
        </div>
    </div>
</header>