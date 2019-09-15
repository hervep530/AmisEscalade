<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@ include file="jspSite/PageVar.jsp" %>
	<head>
		<meta charset="UTF-8">
<%@ include file="jspCommon/meta/Style.jsp" %>
		<title>Accueil</title>
	</head>
	<body>
		<div id="container">
			<div class="row">
				<section id="main" class="col-xl-9">
<%@ include file="jspCommon/Header.jsp" %>
<%@ include file="jspCommon/NavBar.jsp" %>
<c:if test="${ not empty delivry.errors }">
	<%@ include file="jspCommon/Notification.jsp" %>
</c:if>

<!-- l f r c u uac umc ut utt utf upt upf d -->
<c:set var="a" value="${delivry.parameters.parsedUrl.action}" scope="page"/>
<c:choose>
    <c:when test="${action == 'l'}"><%@ include file="jspSite/View.jsp" %></c:when>
    <c:when test="${action == 'f' && method == 'GET'}"><%@ include file="jspSite/SearchForm.jsp" %></c:when>
    <c:when test="${action == 'f' && method == 'POST'}">
    	<%@ include file="jspSite/View.jsp" %>
    	<%@ include file="jspDebug/TestForm.jsp" %>
    </c:when>
    <c:when test="${action == 'r'}"><%@ include file="jspSite/Content.jsp" %></c:when>
    <c:when test="${action == 'c'}"><%@ include file="jspSite/CreateForm.jsp" %></c:when>
    <c:when test="${action == 'u'}"><%@ include file="jspSite/UpdateForm.jsp" %></c:when>
    <c:otherwise><%@ include file="jspSite/Content.jsp" %></c:otherwise>
</c:choose>



<%@ include file="jspDebug/DebugDelivry.jsp" %>
<%@ include file="jspSite/Footer.jsp" %>
				</section>
				<section id="proposal" class="col-xl-3">
<%@ include file="jspCommon/SocialLinks.jsp" %>
<%@ include file="jspCommon/Identity.jsp" %>
<%@ include file="jspCommon/SiteThread.jsp" %>
<%@ include file="jspCommon/Legals.jsp" %>
				</section>
			</div>
		</div>
<%@ include file="jspCommon/meta/Script.jsp" %>
	</body>
</html>