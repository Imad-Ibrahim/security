<%--
  Created by IntelliJ IDEA.
  User: K00252722
  Date: 07/12/2022
  Time: 02:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<jsp:include page="header.jsp" />
<div class="container">
    <div class="row marginTop">
        <shiro:hasRole name="admin">
            <div class="col-md-4 mx-auto">
                <div class="card border-primary mb-3">
                    <ul class="list-group list-group-flush">
                        <li class="list-group-item"><shiro:principal/></li>
                    </ul>
                    <img src="<c:out value="assets/images/agents/${admin.agentId}.jpg"/>">
                </div>
            </div>
        </shiro:hasRole>
        <div class="col-md-8 mx-auto">
            <div class="card border-primary text-white bg-primary mb-3 maxWidth">
                <div class="card-header">Edit Agent Form</div>
                <div class="card-body">
                    <form action="EditAgentServlet" method="post">
                        <fieldset>
                            <div class="form-group">
                                <label for="firstName" class="form-label mt-2">First Name</label>
                                <input type="text" class="form-control" id="firstName" name="firstName" value="<c:out value="${agent.firstName}"/>" placeholder="Enter first name" required/>
                                <input type="hidden" class="form-control" id="agentID" name="agentID" value="${agent.agentId}"/>
                            </div>
                            <div class="form-group">
                                <label for="lastName" class="form-label mt-2">Last Name</label>
                                <input type="text" class="form-control" id="lastName" name="lastName" value="<c:out value="${agent.lastName}"/>" placeholder="Enter last name" required/>
                            </div>
                            <div class="form-group">
                                <label for="phone" class="form-label mt-2">Phone</label>
                                <input type="text" class="form-control" id="phone" name="phone" value="<c:out value="${agent.phone}"/>" placeholder="Enter phone number" required/>
                            </div>
                            <div class="form-group">
                                <label for="fax" class="form-label mt-2">Fax</label>
                                <input type="text" class="form-control" id="fax" name="fax" value="<c:out value="${agent.fax}"/>" placeholder="Enter fax" required/>
                            </div>
                            <div class="form-group">
                                <label for="email" class="form-label mt-2">Email</label>
                                <input type="text" class="form-control" id="email" name="email" value="<c:out value="${agent.email}"/>" placeholder="Enter email address" required/>
                            </div>
                            <br>
                            <input type="submit" value="Save" class="btn btn-success">
                            <input class="btn btn-danger" type="button" value="Cancel" onclick="history.back()">
                        </fieldset>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>