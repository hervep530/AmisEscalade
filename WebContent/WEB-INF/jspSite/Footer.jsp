<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
		<footer>
<jsp:useBean id="now" class="java.util.Date" />
<c:set var="nowMin" value="${ now.time / (1000*60) }" scope="page"/>
<c:set var="updateMin" value="${ site.tsModified.time / (1000*60) }" scope="page"/>
<c:set value="${(nowMin - updateMin)}" var="datePassed"/>
			<div id="updateLabel">
				<p>Mis à jour </p>
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
		</footer>
