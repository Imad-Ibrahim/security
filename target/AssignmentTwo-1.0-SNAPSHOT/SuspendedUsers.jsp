<%--
  Created by IntelliJ IDEA.
  User: K00252722
  Date: 03/11/2022
  Time: 14:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<jsp:include page="header.jsp" />

<div class="container">
    <div class="row marginTop">
        <shiro:hasRole name="agent">
            <div class="col-md-4 mx-auto">
                <div class="card border-primary mb-3">
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item"><shiro:principal/></li>
                    </ul>
                    <img src="<c:out value="assets/images/agents/${agent.agentId}.jpg"/>">
                </div>
            </div>
        </shiro:hasRole>
        <shiro:hasRole name="admin">
            <div class="col-md-4 mx-auto">
                <div class="card border-primary mb-3">
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item"><shiro:principal/></li>
                    </ul>
                    <img src="<c:out value="assets/images/agents/${agent.agentId}.jpg"/>">
                </div>
            </div>
        </shiro:hasRole>
        <c:if test="${fn:length(suspendedUsers) <= 0}">
            <br><h1>There are no users to activate</h1><br>
        </c:if>
        <c:if test="${fn:length(suspendedUsers) > 0}">
            <br><h1>List of all users that are suspended (<c:out value="${fn:length(suspendedUsers)}"/>) </h1>
            <table class="table marginTop">
                <tbody>
                <tr class="table-primary">
                    <td>ID</td>
                    <td>First Name</td>
                    <td>Last Name</td>
                    <td>Phone</td>
                    <td>Email</td>
                    <td>Action</td>
                </tr>
                <c:forEach items="${suspendedUsers}" var="user">
                    <tr class="table-active">
                        <td><c:out value="${user.id}"/></td>
                        <td><c:out value="${user.firstName}"/></td>
                        <td><c:out value="${user.lastName}"/></td>
                        <td><c:out value="${user.phone}"/></td>
                        <td><c:out value="${user.email}"/></td>
                        <td><a href="<c:url value="ActiveUserServlet?userID=${user.id}"/>" class="btn btn-success">Activate</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</div>
</body>
</html>