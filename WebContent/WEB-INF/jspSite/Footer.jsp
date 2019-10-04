<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<c:set var="footerAuthor" value="${(service == 'topo' || service == 'site') && action == 'r' ? true : false}" scope="page"/>	
<c:set var="footerPagination" value="${(service == 'topo' || service == 'site') && action == 'l' ? true : false}" scope="page"/>	
<c:if test="${delivry.parameters.parsedUrl.id > 0}">
	<c:set var="pageId" value="${delivry.parameters.parsedUrl.id}"></c:set>
</c:if>
<c:if test="${delivry.attributes.listsCount > 0}">
	<c:set var="pageCount" value="${delivry.attributes.listsCount}"></c:set>
</c:if>
		<footer>
<c:if test="${footerAuthor == true}">		
	<jsp:useBean id="now" class="java.util.Date" />
	<c:set var="nowMin" value="${ now.time / (1000*60) }" scope="page"/>
	<c:set var="updateMin" value="${ site.tsModified.time / (1000*60) }" scope="page"/>
	<c:set value="${(nowMin - updateMin)}" var="datePassed"/>
			<div id="updateLabel">
				<p>Mis à jour</p>
			</div>
			<div id="updateInfo">
				<ul>
					<li>le <fmt:formatDate type = "date" dateStyle = "medium" value = "${ site.tsModified }" />
					<li>par <c:out value="${ site.author.username }" /></li>
					<li> il y a 
	<c:choose>
	    <c:when test="${datePassed gt 60}">${Math.round(datePassed / 60)} heure(s)</c:when>
	    <c:when test="${datePassed gt (60*24)}">${Math.round(datePassed / (60*24))} jour(s)</c:when>
	    <c:when test="${datePassed gt (60*24*30)}">${Math.round(datePassed / (60*24*30))} mois</c:when>
	    <c:when test="${datePassed gt (60*24*365)}">${Math.round(datePassed / (60*24*365))} an(s)</c:when>
	    <c:otherwise>${Math.round(datePassed)} minute(s)</c:otherwise>
	</c:choose>
					</li>
				</ul>
			</div>
</c:if>
<c:if test="${footerPagination == true}">
			<nav aria-label="Page navigation example">
				<ul class="pagination justify-content-end">
<c:if test="${pageId > 1}">
					<li class="page-item">
						<a class="page-link" href="${pageContext.request.contextPath}/site/l/${pageId - 1}">Previous</a>
					</li>
</c:if>
<c:forEach begin="0" end="8" varStatus="loop">
	<c:set var="linkId" value="${pageId - 3 + loop.index }"></c:set>
	<c:if test="${linkId > 0 && linkId <= pageCount}">
					<li class="page-item">
						<a href="${pageContext.request.contextPath}/site/l/${linkId}"
							class="page-link">${linkId}</a>
					</li>
	</c:if>
</c:forEach>
<c:if test="${pageCount > pageId}">
					<li class="page-item">
						<a class="page-link" href="${pageContext.request.contextPath}/site/l/${pageId + 1}">Next</a>
					</li>
</c:if>
				</ul>
			</nav>		
</c:if>
		</footer>
