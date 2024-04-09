<%--
  Created by IntelliJ IDEA.
  User: K00252722
  Date: 04/11/2022
  Time: 20:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        <shiro:hasRole name="user">
            <br><h2><shiro:principal/></h2>
        </shiro:hasRole>
        <c:if test="${fn:length(properties) <= 0}">
            <br><h1>There are no properties</h1>
        </c:if>
        <c:if test="${fn:length(properties) > 0}">
            <br><h1>List of all properties (<c:out value="${fn:length(properties)}"/>)</h1>
            <div class="col-md-12 mx-auto">
                <div class="col-md-8 mx-auto">
                    <br><br>
                    <button class="accordion-button" type="button">Search</button>
                    <div class="content">
                        <div class="card border-primary text-white bg-primary mb-3 maxWidth">
                            <div class="card-body">
                                <form action="FilterPropertiesServlet" method="post">
                                    <input type="text" class="form-control" id="city" name="city" placeholder="City name">
                                    <input type="number" class="form-control" id="priceFrom" name="priceFrom" placeholder="Price from">
                                    <input type="number" class="form-control" id="priceTo" name="priceTo" placeholder="Price to">
                                    <input type="number" class="form-control" id="numberOfBedrooms" name="numberOfBedrooms" placeholder="Number of bed rooms">
                                    <input type="number" class="form-control" id="numberOfBathrooms" name="numberOfBathrooms" placeholder="Number of bath rooms">
                                    <br>
                                    <input type="submit" class="btn btn-success" value="Search">
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <br><br>
            </div>
            <shiro:hasRole name="user">
                <c:if test="${fn:length(recentProperties) > 0}">
                    <br><h1>Most Recent 10</h1>
                    <c:forEach items="${recentProperties}" var="property" begin="0" end="9">
                        <div class="col-md-4 mx-auto">
                            <div class="card border-primary mb-3">
                                <h3 class="card-header"> <fmt:formatNumber value="${property.price}" type="currency" currencySymbol="€"/></h3>
                                <img src="<c:out value="assets/images/properties/thumbs/${property.photo}"/>">
                                <ul class="list-group list-group-flush">
                                    <li class="list-group-item">Street: <span class="bold underline"><c:out value="${property.street}"/></span></li>
                                    <li class="list-group-item">City: <span class="bold underline"><c:out value="${property.city}"/></span></li>
                                    <li class="list-group-item">Square Feet: <span class="bold underline"><c:out value="${property.squarefeet}"/></span></li>
                                </ul>
                                <div class="card-body">
                                    <shiro:guest>
                                        <a href="<c:url value="AddPropertyToFavouriteServlet?propertyID=${property.id}"/>">Add To Favourites</a>
                                    </shiro:guest>
                                    <shiro:hasRole name="user">
                                        <a href="<c:url value="AddPropertyToFavouriteServlet?propertyID=${property.id}"/>">Add To Favourites</a>
                                        <a href="<c:url value="GetNotesServlet?propertyID=${property.id}"/>" class="moreDetails">Add Note</a>
                                    </shiro:hasRole>
                                    <a href="<c:url value="MoreDetailsServlet?propertyID=${property.id}"/>" class="moreDetails">More Details</a>
                                </div>
                                <div class="card-footer text-muted"><c:out value="${property.dateAdded}"/></div>
                            </div>
                        </div>
                    </c:forEach>
                </c:if>
                <c:if test="${fn:length(recommendationProperties) > 0}">
                    <br><h1>Recommended Properties</h1>
                    <c:forEach items="${recommendationProperties}" var="property">
                        <div class="col-md-4 mx-auto">
                            <div class="card border-primary mb-3">
                                <h3 class="card-header"> <fmt:formatNumber value="${property.price}" type="currency" currencySymbol="€"/></h3>
                                <img src="<c:out value="assets/images/properties/thumbs/${property.photo}"/>">
                                <ul class="list-group list-group-flush">
                                    <li class="list-group-item">Street: <span class="bold underline"><c:out value="${property.street}"/></span></li>
                                    <li class="list-group-item">City: <span class="bold underline"><c:out value="${property.city}"/></span></li>
                                    <li class="list-group-item">Square Feet: <span class="bold underline"><c:out value="${property.squarefeet}"/></span></li>
                                </ul>
                                <div class="card-body">
                                    <shiro:guest>
                                        <a href="<c:url value="AddPropertyToFavouriteServlet?propertyID=${property.id}"/>">Add To Favourites</a>
                                    </shiro:guest>
                                    <shiro:hasRole name="user">
                                        <a href="<c:url value="AddPropertyToFavouriteServlet?propertyID=${property.id}"/>">Add To Favourites</a>
                                        <a href="<c:url value="GetNotesServlet?propertyID=${property.id}"/>" class="moreDetails">Add Note</a>
                                    </shiro:hasRole>
                                    <a href="<c:url value="MoreDetailsServlet?propertyID=${property.id}"/>" class="moreDetails">More Details</a>
                                </div>
                                <div class="card-footer text-muted"><c:out value="${property.dateAdded}"/></div>
                            </div>
                        </div>
                    </c:forEach>
                    <br><h1>All Properties</h1>
                </c:if>
                <c:if test="${fn:length(recommendationProperties) <= 0}">
                    <br><h1>All Properties</h1>
                </c:if>
            </shiro:hasRole>
            <c:forEach items="${properties}" var="property">
                <div class="col-md-4 mx-auto">
                    <div class="card border-primary mb-3">
                        <h3 class="card-header"> <fmt:formatNumber value="${property.price}" type="currency" currencySymbol="€"/></h3>
                        <img src="<c:out value="assets/images/properties/thumbs/${property.photo}"/>">
                        <ul class="list-group list-group-flush">
                            <li class="list-group-item">Street: <span class="bold underline"><c:out value="${property.street}"/></span></li>
                            <li class="list-group-item">City: <span class="bold underline"><c:out value="${property.city}"/></span></li>
                            <li class="list-group-item">Square Feet: <span class="bold underline"><c:out value="${property.squarefeet}"/></span></li>
                        </ul>
                        <div class="card-body">
                            <shiro:guest>
                                <a href="<c:url value="AddPropertyToFavouriteServlet?propertyID=${property.id}"/>">Add To Favourites</a>
                            </shiro:guest>
                            <shiro:hasRole name="user">
                                <a href="<c:url value="AddPropertyToFavouriteServlet?propertyID=${property.id}"/>">Add To Favourites</a>
                                <a href="<c:url value="GetNotesServlet?propertyID=${property.id}"/>" class="moreDetails">Add Note</a>
                            </shiro:hasRole>
                            <shiro:hasRole name="agent">
                                <c:if test="${property.archived == 0}">
                                    <a href="<c:url value="ArchivePropertyServlet?propertyID=${property.id}"/>">Archive</a>
                                    <a href="<c:url value="Fill_InPropertyDetailsFormServlet?propertyID=${property.id}"/>" class="moreDetails">Edit</a>
                                </c:if>
                                <c:if test="${property.archived == 1}">
                                    <a href="<c:url value="UnarchivePropertiesServlet?propertyID=${property.id}"/>">Unarchive</a>
                                    <a href="<c:url value="Fill_InPropertyDetailsFormServlet?propertyID=${property.id}"/>" class="moreDetails">Edit</a>
                                </c:if>
                            </shiro:hasRole>
                            <shiro:hasRole name="admin">
                                <c:if test="${property.archived == 0}">
                                    <a href="<c:url value="ArchivePropertyServlet?propertyID=${property.id}"/>">Archive</a>
                                    <a href="<c:url value="Fill_InPropertyDetailsFormServlet?propertyID=${property.id}"/>" class="moreDetails">Edit</a>
                                </c:if>
                                <c:if test="${property.archived == 1}">
                                    <a href="<c:url value="UnarchivePropertiesServlet?propertyID=${property.id}"/>">Unarchive</a>
                                    <a href="<c:url value="Fill_InPropertyDetailsFormServlet?propertyID=${property.id}"/>" class="moreDetails">Edit</a>
                                </c:if>
                            </shiro:hasRole>
                            <a href="<c:url value="MoreDetailsServlet?propertyID=${property.id}"/>" class="moreDetails">More Details</a>
                        </div>
                        <div class="card-footer text-muted">
                             <c:out value="${property.dateAdded}"/>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:if>
    </div>
</div>
</body>
</html>

<script>
    var coll = document.getElementsByClassName("accordion-button");
    var i;

    for (i = 0; i < coll.length; i++) {
        coll[i].addEventListener("click", function() {
            this.classList.toggle("active");
            var content = this.nextElementSibling;
            if (content.style.display === "block") {
                content.style.display = "none";
            } else {
                content.style.display = "block";
            }
        });
    }
</script>