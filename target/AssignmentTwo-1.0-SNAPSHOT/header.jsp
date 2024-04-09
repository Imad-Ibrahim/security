<%--
  Created by IntelliJ IDEA.
  User: K00252722
  Date: 03/11/2022
  Time: 00:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Irish Home Listings</title>
    <link href="assets/css/stylesheet.css" rel="stylesheet"/>
    <link rel="stylesheet" href="assets/css/design.css"/>
</head>
<body>
<header>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container-fluid">
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarColor01" aria-controls="navbarColor01" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarColor01">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item"><a class='nav-link active' href="index.jsp">Home</a><span class="visually-hidden">(current)</span></li>
                    <shiro:guest>
                        <li class="nav-item"><a class='nav-link' href="<c:url value="GetPropertiesServlet"/>">Properties</a></li>
                        <li class="nav-item"><a class='nav-link' href="<c:url value="/GetRemoveFavouritePropertiesServlet"/>">Favourites</a></li>
                        <li class="nav-item"><a class='nav-link' href="<c:url value="SignIn.jsp"/>">Sign in</a></li>
                        <li class="nav-item"><a class='nav-link' href="<c:url value="SignUp.jsp"/>">Sign up</a></li>
                    </shiro:guest>
                    <shiro:hasRole name="user">
                        <li class="nav-item"><a class='nav-link' href="<c:url value="/GetPropertiesServlet"/>">Properties</a></li>
                        <li class="nav-item"><a class='nav-link' href="<c:url value="/GetRemoveFavouritePropertiesServlet"/>">Favourites</a></li>
                        <li class="nav-item"><a class='nav-link' href="<c:url value="/Fill_InUserProfileFormServlet"/>">Edit Profile</a></li>
                        <li class="nav-item"><a class='nav-link' href="<c:url value="ChangePassword.jsp"/>">Change Password</a></li>
                        <li class="nav-item"><a class='nav-link' href="<c:url value="LogOutConfirmation.jsp"/>">Log out</a></li>
                    </shiro:hasRole>
                    <shiro:hasRole name="agent">
                        <li class="nav-item"><a class='nav-link' href="<c:url value="/GetAgentPropertiesServlet"/>">Properties</a></li>
                        <li class="nav-item"><a class='nav-link' href="<c:url value="/Fill_InPropertyDetailsFormServlet"/>">Add Property</a></li>
                        <li class="nav-item"><a class='nav-link' href="<c:url value="/GetArchivedPropertiesServlet"/>">Archived Properties</a></li>
                        <li class="nav-item"><a class='nav-link' href="<c:url value="/GetAllSuspendedUsersServlet"/>">Activate User</a></li>
                        <li class="nav-item"><a class='nav-link' href="<c:url value="/GetAllActivatedUsersServlet"/>">Suspend User</a></li>
                        <li class="nav-item"><a class='nav-link' href="<c:url value="/GetAllVendorsServlet"/>">Vendors</a></li>
                        <li class="nav-item"><a class='nav-link' href="<c:url value="/SignUp.jsp"/>">Sign Up Vendor</a></li>
                        <li class="nav-item"><a class='nav-link' href="<c:url value="ChangePassword.jsp"/>">Change Password</a></li>
                        <li class="nav-item"><a class='nav-link' href="<c:url value="/LogOutConfirmation.jsp"/>">Log out</a></li>
                    </shiro:hasRole>
                    <shiro:hasRole name="admin">
                        <li class="nav-item"><a class='nav-link' href="<c:url value="/GetAgentPropertiesServlet"/>">Properties</a></li>
                        <li class="nav-item"><a class='nav-link' href="<c:url value="/Fill_InPropertyDetailsFormServlet"/>">Add Property</a></li>
                        <li class="nav-item"><a class='nav-link' href="<c:url value="/GetArchivedPropertiesServlet"/>">Archived Properties</a></li>
                        <li class="nav-item"><a class='nav-link' href="<c:url value="/GetAllSuspendedUsersServlet"/>">Activate User</a></li>
                        <li class="nav-item"><a class='nav-link' href="<c:url value="/GetAllActivatedUsersServlet"/>">Suspend User</a></li>
                        <li class="nav-item"><a class='nav-link' href="<c:url value="/GetAllVendorsServlet"/>">Vendors</a></li>
                        <li class="nav-item"><a class='nav-link' href="<c:url value="/GetAllAgentsServlet"/>">Agents</a></li>
                        <li class="nav-item"><a class='nav-link' href="<c:url value="/SignUp.jsp"/>">Sign Up Vendor</a></li>
                        <li class="nav-item"><a class='nav-link' href="<c:url value="/AddAgent.jsp"/>">Sign Up Agent</a></li>
                        <li class="nav-item"><a class='nav-link' href="<c:url value="ChangePassword.jsp"/>">Change Password</a></li>
                        <li class="nav-item"><a class='nav-link' href="<c:url value="/LogOutConfirmation.jsp"/>">Log out</a></li>
                    </shiro:hasRole>
                </ul>
            </div>
        </div>
    </nav>
</header>
