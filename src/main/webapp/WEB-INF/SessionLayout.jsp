<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@ include file="jspCommon/PageVar.jsp" %>
	<head>
		<meta charset="UTF-8">
<%@ include file="jspCommon/meta/Style.jsp" %>
		<title>${delivry.attributes.title}</title>
	</head>
	<body>
<%@ include file="jspCommon/Header.jsp" %>
		<div id="container">
			<div class="row">
				<section id="main" class="col-xl-9">
<c:if test="${ not empty delivry.notifications }">
	<%@ include file="jspCommon/Notification.jsp" %>
</c:if>

<!-- l f r c u uac umc ut utt utf upt upf d -->
<c:set var="a" value="${delivry.parameters.parsedUrl.action}" scope="page"/>
<c:choose>
    <c:when test="${action == 'connexion'}"><%@ include file="jspSession/ConnexionForm.jsp" %></c:when>
    <c:when test="${action == 'inscription'}"><%@ include file="jspSession/InscriptionForm.jsp" %></c:when>
    <c:when test="${action == 'd'}"><%@ include file="jspSession/Account.jsp" %></c:when>
    <c:when test="${action == 'pass'}"><%@ include file="jspSession/UpdatePasswordForm.jsp" %></c:when>
    <c:otherwise><%@ include file="jspSession/ConnexionForm.jsp" %></c:otherwise>
</c:choose>
				</section>
				<section id="proposal" class="col-xl-3">
<%@ include file="jspCommon/SocialLinks.jsp" %>
<%@ include file="jspCommon/Identity.jsp" %>
<%@ include file="jspCommon/Help.jsp" %>
<%@ include file="jspCommon/Legals.jsp" %>
				</section>
			</div>
		</div>
<%@ include file="jspCommon/meta/Script.jsp" %>
	</body>
</html>