<%--
  Created by IntelliJ IDEA.
  User: K00252722
  Date: 09/12/2022
  Time: 02:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<jsp:include page="header.jsp" />
<div class="container">
    <div class="row marginTop">
        <div class="col-md-8 mx-auto">
            <div class="card border-primary text-white bg-primary mb-3 maxWidth">
                <div class="card-header">Add New Agent Form</div>
                <div class="card-body">
                    <form action="AddAgentServlet" method="post">
                        <fieldset>
                            <div class="form-group">
                                <label for="firstName" class="form-label mt-2">First Name</label>
                                <input type="text" class="form-control" id="firstName" name="firstName" placeholder="Enter first name" required/>
                            </div>
                            <div class="form-group">
                                <label for="lastName" class="form-label mt-2">Last Name</label>
                                <input type="text" class="form-control" id="lastName" name="lastName" placeholder="Enter last name" required/>
                            </div>
                            <div class="form-group">
                                <label for="phone" class="form-label mt-2">Phone</label>
                                <input type="text" class="form-control" id="phone" name="phone" placeholder="Enter phone number" required/>
                            </div>
                            <div class="form-group">
                                <label for="fax" class="form-label mt-2">Fax</label>
                                <input type="text" class="form-control" id="fax" name="fax" placeholder="Enter fax" required/>
                            </div>
                            <div class="form-group">
                                <label for="email" class="form-label mt-2">Email</label>
                                <input type="text" class="form-control" id="email" name="email" placeholder="Enter email address" required/>
                            </div>
                            <div class="form-group">
                                <label for="password" class="form-label mt-2">Password</label>
                                <input type="password" class="form-control" id="password" name="password" placeholder="Enter password" required/>
                            </div>
                            <div class="form-group">
                                <label for="confirmPassword" class="form-label mt-2">Confirm Password</label>
                                <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" placeholder="Confirm password" required/>
                            </div>
                            <br>
                            <input type="submit" value="Add" class="btn btn-success">
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