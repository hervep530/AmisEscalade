<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@ include file="jspCommon/PageVar.jsp" %>
	<head>
		<meta charset="UTF-8">
<%@ include file="jspCommon/meta/Style.jsp" %>
		<title>Accueil</title>
	</head>
	<body>
<%@ include file="jspCommon/ModalBox.jsp" %>
<%@ include file="jspCommon/Header.jsp" %>
		<div id="container">
			<div class="row">
				<section id="main" class="col-xl-9">
<c:if test="${ not empty delivry.notifications }">
	<%@ include file="jspCommon/Notification.jsp" %>
</c:if>
						<div class="message-box">
<%@ include file="jspMessage/Title.jsp" %>
<c:choose>
    <c:when test="${action == 'r' || action == 'lfd' }"><%@ include file="jspMessage/Content.jsp" %></c:when>
    <c:when test="${action == 'c' || action == 'ca' || action == 'cft'}"><%@ include file="jspMessage/CreateForm.jsp" %></c:when>
</c:choose>
<%@ include file="jspMessage/View.jsp" %>
						</div>
<%@ include file="jspSite/Footer.jsp" %>
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