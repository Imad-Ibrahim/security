<%--
  Created by IntelliJ IDEA.
  User: K00252722
  Date: 23/11/2022
  Time: 13:59
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
        <c:if test="${fn:length(vendorsList) <= 0}">
            <br><h1>There are no vendors</h1>
        </c:if>
        <c:if test="${fn:length(vendorsList) > 0}">
            <br><h1>List of all vendors (<c:out value="${fn:length(vendorsList)}"/>)</h1>
            <table class="table marginTop">
                <tbody>
                <tr class="table-primary">
                    <td>ID</td>
                    <td>First Name</td>
                    <td>Last Name</td>
                    <td>Street</td>
                    <td>City</td>
                    <td>Phone</td>
                    <td>Email</td>
                    <td>Action</td>
                </tr>
                <c:forEach items="${vendorsList}" var="vendor">
                    <tr class="table-active">
                        <td><c:out value="${vendor.vendorId}"/></td>
                        <td><c:out value="${vendor.firstName}"/></td>
                        <td><c:out value="${vendor.lastName}"/></td>
                        <td><c:out value="${vendor.street}"/></td>
                        <td><c:out value="${vendor.city}"/></td>
                        <td><c:out value="${vendor.phone}"/></td>
                        <td><c:out value="${vendor.email}"/></td>
                        <td><a href="<c:url value="GetVendorDetailsServlet?vendorID=${vendor.vendorId}"/>" class="btn btn-success">Edit</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</div>
</body>
</html>
