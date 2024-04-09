<%--
  Created by IntelliJ IDEA.
  User: K00252722
  Date: 09/12/2022
  Time: 01:56
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
                    <form action="ChangePasswordServlet" method="post">
                        <fieldset>
                            <div class="form-group">
                                <label for="newPassword" class="form-label mt-2">New Password</label>
                                <input type="text" class="form-control" id="newPassword" name="newPassword" placeholder="New password" required/>
                            </div>
                            <div class="form-group">
                                <label for="confirmPassword" class="form-label mt-2">Confirm Password</label>
                                <input type="text" class="form-control" id="confirmPassword" name="confirmPassword"  placeholder="Confirm password" required/>
                            </div>
                            <br>
                            <input type="submit" value="Change" class="btn btn-success">
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