<%--
  Created by IntelliJ IDEA.
  User: K00252722
  Date: 05/11/2022
  Time: 14:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<jsp:include page="header.jsp"/>
<div class="container">
    <div class="row marginTop">
        <shiro:hasRole name="user">
            <br><h2><shiro:principal/></h2>
        </shiro:hasRole>
        <div class="col-md-8 mx-auto">
            <div class="card border-primary text-white bg-primary mb-3 maxWidth">
                <div class="card-header">Edit Details Form</div>
                <div class="card-body">
                    <form action="EditProfileServlet" method="post">
                        <fieldset>
                            <div class="form-group">
                                <label for="firstName" class="form-label mt-2">First Name</label>
                                <input type="text" class="form-control" id="firstName" name="firstName" value="<c:out value="${user.firstName}"/>" required/>
                            </div>
                            <div class="form-group">
                                <label for="lastName" class="form-label mt-2">Last Name</label>
                                <input type="text" class="form-control" id="lastName" name="lastName" value="<c:out value="${user.lastName}"/>" required/>
                            </div>
                            <div class="form-group">
                                <label for="phone" class="form-label mt-2">Phone</label>
                                <input type="text" class="form-control" id="phone" name="phone" value="<c:out value="${user.phone}"/>" required/>
                            </div>
                            <div class="form-group">
                                <label for="email" class="form-label mt-2">Email</label>
                                <input type="text" class="form-control" id="email" name="email" value="<c:out value="${user.email}"/>" required/>
                            </div>
                            <div class="form-group">
                                <label for="userName" class="form-label mt-2">Username</label>
                                <input type="text" class="form-control" id="userName" name="userName" value="<c:out value="${user.username}"/>" readonly/>
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
