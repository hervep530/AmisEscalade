<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
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
<%@ include file="jspCommon/Notification.jsp" %>
<%@ include file="jspSite/Content.jsp" %>
<%@ include file="jspDebug/DebugDelivry.jsp" %>
<%@ include file="jspCommon/Footer.jsp" %>
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