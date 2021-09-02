<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="messages" var="messages"/>
<nav aria-label="...">
    <ul class="pagination">
        <li class="page-item disabled">
            <a class="page-link" href="#" tabindex="-1"><fmt:message key="pages" bundle="${messages}"/></a>
        </li>
        <c:choose>
            <c:when test="${requestScope.pager.totalSize==0}">
                <li class="page-item active">
                    <a class="page-link"
                       href="?page=1&${param.sortParam}">1</a>
                </li>
            </c:when>
            <c:otherwise>
                <c:forEach begin="1" end="${requestScope.pager.totalSize}" var="i">
                    <c:choose>
                        <c:when test="${i==requestScope.pager.currentPage}">
                            <li class="page-item active">
                                <a class="page-link"
                                   href="?page=${i}&${param.sortParam}">${i}</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item">
                                <a class="page-link"
                                   href="?page=${i}&${param.sortParam}">${i}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </ul>
</nav>