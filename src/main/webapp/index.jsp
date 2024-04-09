<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<jsp:include page="header.jsp" />

<div class="container">
    <div class="row marginTop">
        <shiro:guest>
            <br><h1>Welcome to Irish Home Listings!</h1>
        </shiro:guest>
        <shiro:authenticated>
            <br><h2>Welcome <shiro:principal/> to Irish Home Listings!</h2>
        </shiro:authenticated>

        <c:if test="${message != null && message != 'Something went wrong'}">
            <div class="form-group">
                <p><span class="bold">Your username is <mark><c:out value="${message}"/></mark></span></p>
            </div>
        </c:if>
    </div>
</div>
</body>
</html>