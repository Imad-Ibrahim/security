<%--
  Created by IntelliJ IDEA.
  User: K00252722
  Date: 05/11/2022
  Time: 14:59
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
                    <img src="<c:out value="assets/images/agents/${admin.agentId}.jpg"/>">
                </div>
            </div>
        </shiro:hasRole>
        <shiro:hasRole name="user">
            <br><h2><shiro:principal/></h2>
        </shiro:hasRole>

        <c:if test="${property == null}">
            <br><h1>Something went Wrong</h1>
        </c:if>
        <c:if test="${property != null && style != null && propertyType != null && garageType != null && agent != null}">
            <div class="col-md-10 mx-auto">
                <div class="card border-primary mb-3 slideshow-container">
                    <c:forEach items="${imagesName}" var="imageName">
                        <img class="imagesGallery animation" src="<c:out value="assets/images/properties/large/${property.listingNum}/${imageName}"/>">
                    </c:forEach>
                    <a class="prev" onclick="plusSlides(-1)">❮</a>
                    <a class="next" onclick="plusSlides(1)">❯</a>
                    <ul class="list-group list-group-flush">
                        <h3 class="list-group-item"><fmt:formatNumber value="${property.price}" type="currency" currencySymbol="€"/></h3>
                        <li class="list-group-item"><span class="bold"><c:out value="${property.street}, ${property.city}"/></span></li>
                        <li class="list-group-item">
                            <c:out value="${property.bedrooms} Bed, ${property.bathrooms} Bath, ${property.squarefeet} Square Feet, ${style.PStyle}"/>
                        </li>
                        <li class="list-group-item"><span class="bold propertyDes">Description</span><br><c:out value="${property.description}"/></li>
                        <shiro:hasRole name="user">
                            <c:if test="${propertyList == null}">
                                <li class="list-group-item"><span class="bold propertyDes">Notes</span><br><c:out value="${propertynote.note}"/></li>
                            </c:if>
                        </shiro:hasRole>
                    </ul>
                    <div class="card-body">
                        <shiro:guest>
                            <c:if test="${propertyList == null}">
                                <a href="<c:url value="AddPropertyToFavouriteServlet?propertyID=${property.id}"/>">Add To Favourites</a>
                            </c:if>
                            <c:if test="${propertyList != null}">
                                <c:forEach items="${propertyList}" var="p">
                                    <c:if test="${p.id == property.id}">
                                        <a href="<c:url value="GetRemoveFavouritePropertiesServlet?removePropertyID=${property.id}"/>">Remove From Favourites</a>
                                    </c:if>
                                </c:forEach>
                            </c:if>
                        </shiro:guest>
                        <shiro:hasRole name="user">
                            <c:if test="${propertyList == null}">
                                <a href="<c:url value="AddPropertyToFavouriteServlet?propertyID=${property.id}"/>">Add To Favourites</a>
                                <a href="<c:url value="GetNotesServlet?propertyID=${property.id}"/>" class="moreDetails">Add Note</a>
                            </c:if>
                            <c:if test="${propertyList != null}">
                                <c:forEach items="${propertyList}" var="p">
                                    <c:if test="${p.id == property.id}">
                                        <a href="<c:url value="GetRemoveFavouritePropertiesServlet?removePropertyID=${property.id}"/>">Remove From Favourites</a>
                                        <a href="<c:url value="GetNotesServlet?propertyID=${property.id}"/>" class="moreDetails">Add Note</a>
                                    </c:if>
                                </c:forEach>
                            </c:if>
                        </shiro:hasRole>
                        <shiro:hasRole name="agent">
                            <c:if test="${property.archived == 0}">
                                <a href="<c:url value="ArchivePropertyServlet?propertyID=${property.id}"/>">Archive</a>
                            </c:if>
                            <c:if test="${property.archived == 1}">
                                <a href="<c:url value="UnarchivePropertiesServlet?propertyID=${property.id}"/>">Unarchive</a>
                            </c:if>
                            <a href="<c:url value="Fill_InPropertyDetailsFormServlet?propertyID=${property.id}"/>" class="moreDetails">Edit</a>
                        </shiro:hasRole>
                        <shiro:hasRole name="admin">
                            <c:if test="${property.archived == 0}">
                                <a href="<c:url value="ArchivePropertyServlet?propertyID=${property.id}"/>">Archive</a>
                            </c:if>
                            <c:if test="${property.archived == 1}">
                                <a href="<c:url value="UnarchivePropertiesServlet?propertyID=${property.id}"/>">Unarchive</a>
                            </c:if>
                            <a href="<c:url value="Fill_InPropertyDetailsFormServlet?propertyID=${property.id}"/>" class="moreDetails">Edit</a>
                        </shiro:hasRole>
                    </div>
                </div>
            </div>
            <div class="col-md-4 mx-auto">
                <div class="card border-primary mb-3">
                    <div class="card-body">
                        <h2 class="propertyDetailsHeader">Property Details</h2>
                        <ul>
                            <li class="propertyDetails"><span class="bold">Bed Rooms: </span><c:out value="${property.bedrooms}"/></li>
                            <li class="propertyDetails"><span class="bold">Bath Room: </span><c:out value="${property.bathrooms}"/></li>
                            <li class="propertyDetails"><span class="bold">Square Feet: </span><c:out value="${property.squarefeet}"/></li>
                            <li class="propertyDetails"><span class="bold">Property Style: </span><c:out value="${style.PStyle}"/></li>
                            <li class="propertyDetails"><span class="bold">Property Type: </span><c:out value="${propertyType.PType}"/></li>
                            <li class="propertyDetails"><span class="bold">BER Rating: </span><img src="<c:out value="assets/images/BER/${property.berRating}.png"/>"></li>
                            <li class="propertyDetails"><span class="bold">Lot Size: </span> <c:out value="${property.lotsize}"/></li>
                            <li class="propertyDetails"><span class="bold">Garage Size: </span><c:out value="${property.garagesize}"/></li>
                            <li class="propertyDetails"><span class="bold">Garage Type: </span><c:out value="${garageType.GType}"/></li>
                            <li class="propertyDetails"><span class="bold">Date Added: </span><c:out value="${property.dateAdded}"/></li>
                        </ul>

                    </div>
                </div>
                <shiro:hasRole name="agent">
                    <div class="card border-primary mb-3">
                        <ul class="list-group list-group-flush">
                            <h3 class="list-group-item"><c:out value="${vendor.firstName} ${vendor.lastName}"/></h3>
                            <li class="list-group-item"><span class="bold">Address: </span><c:out value="${vendor.street}, ${vendor.city}"/></li>
                            <li class="list-group-item"><span class="bold">Phone: </span><c:out value="${vendor.phone}"/></li>
                            <li class="list-group-item"><span class="bold">Email: </span><a href="mailto:${vendor.email}"><c:out value="${vendor.email}"/></a></li>
                        </ul>
                    </div>
                </shiro:hasRole>
            </div>
            <shiro:guest>
                <div class="col-md-4 mx-auto">
                    <div class="card border-primary mb-3">
                        <img src="<c:out value="assets/images/agents/${agent.agentId}.jpg"/>">
                        <ul class="list-group list-group-flush">
                            <h3 class="list-group-item"><c:out value="${agent.firstName} ${agent.lastName}"/></h3>
                            <li class="list-group-item">Phone: <span class="bold"><c:out value="${agent.phone}"/></span></li>
                            <li class="list-group-item">Fax: <span class="bold"><c:out value="${agent.fax}"/></span></li>
                            <li class="list-group-item">Email: <span class="bold"><a href="mailto:${agent.email}"><c:out value="${agent.email}"/></a></span></li>
                        </ul>
                    </div>
                </div>
            </shiro:guest>
            <shiro:hasRole name="user">
                <div class="col-md-4 mx-auto">
                    <div class="card border-primary mb-3">
                        <img src="<c:out value="assets/images/agents/${agent.agentId}.jpg"/>">
                        <ul class="list-group list-group-flush">
                            <h3 class="list-group-item"><c:out value="${agent.firstName} ${agent.lastName}"/></h3>
                            <li class="list-group-item">Phone: <span class="bold"><c:out value="${agent.phone}"/></span></li>
                            <li class="list-group-item">Fax: <span class="bold"><c:out value="${agent.fax}"/></span></li>
                            <li class="list-group-item">Email: <span class="bold"><a href="mailto:${agent.email}"><c:out value="${agent.email}"/></a></span></li>
                        </ul>
                    </div>
                </div>
            </shiro:hasRole>
            <shiro:hasRole name="admin">
                <div class="col-md-4 mx-auto">
                    <div class="card border-primary mb-3">
                        <img src="<c:out value="assets/images/agents/${agent.agentId}.jpg"/>">
                        <ul class="list-group list-group-flush">
                            <h3 class="list-group-item"><c:out value="${agent.firstName} ${agent.lastName}"/></h3>
                            <li class="list-group-item">Phone: <span class="bold"><c:out value="${agent.phone}"/></span></li>
                            <li class="list-group-item">Fax: <span class="bold"><c:out value="${agent.fax}"/></span></li>
                            <li class="list-group-item">Email: <span class="bold"><a href="mailto:${agent.email}"><c:out value="${agent.email}"/></a></span></li>
                        </ul>
                    </div>
                    <div class="card border-primary mb-3">
                        <ul class="list-group list-group-flush">
                            <h3 class="list-group-item"><c:out value="${vendor.firstName} ${vendor.lastName}"/></h3>
                            <li class="list-group-item"><span class="bold">Address: </span><c:out value="${vendor.street}, ${vendor.city}"/></li>
                            <li class="list-group-item"><span class="bold">Phone: </span><c:out value="${vendor.phone}"/></li>
                            <li class="list-group-item"><span class="bold">Email: </span><a href="mailto:${vendor.email}"><c:out value="${vendor.email}"/></a></li>
                        </ul>
                    </div>
                </div>
            </shiro:hasRole>
            <shiro:hasRole name="agent">
                <div class="col-md-4 mx-auto">
                    <iframe class="google"
                            src="<c:out value="https://maps.google.com/maps?q=${property.street} ${property.city}&t=&z=13&ie=UTF8&iwloc=&output=embed"/>">
                    </iframe>
                </div>
            </shiro:hasRole>
            <shiro:hasRole name="admin">
                <div class="col-md-4 mx-auto">
                    <iframe class="google"
                            src="<c:out value="https://maps.google.com/maps?q=${property.street} ${property.city}&t=&z=13&ie=UTF8&iwloc=&output=embed"/>">
                    </iframe>
                </div>
            </shiro:hasRole>
            <shiro:hasRole name="user">
                <div class="col-md-10 mx-auto">
                    <iframe class="google"
                            src="<c:out value="https://maps.google.com/maps?q=${property.street} ${property.city}&t=&z=13&ie=UTF8&iwloc=&output=embed"/>">
                    </iframe>
                </div>
            </shiro:hasRole>
            <shiro:guest>
                <div class="col-md-10 mx-auto">
                    <iframe class="google"
                            src="<c:out value="https://maps.google.com/maps?q=${property.street} ${property.city}&t=&z=13&ie=UTF8&iwloc=&output=embed"/>">
                    </iframe>
                </div>
            </shiro:guest>
        </c:if>
    </div>
</div>

<script>
    let slideIndex = 1;
    showSlides(slideIndex);

    function plusSlides(n) {
        showSlides(slideIndex += n);
    }
    function showSlides(n) {
        let i;
        let slides = document.getElementsByClassName("imagesGallery");
        if (n > slides.length) {
            slideIndex = 1
        }
        if (n < 1) {
            slideIndex = slides.length
        }
        for (i = 0; i < slides.length; i++) {
            slides[i].style.display = "none";
        }
        slides[slideIndex-1].style.display = "block";
    }
</script>
</body>
</html>