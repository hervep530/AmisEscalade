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
<%@ include file="jspCommon/Header.jsp" %>
		<div id="container">
			<div class="row">
				<section id="main" class="col-xl-9">
<%@ include file="jspCommon/Notification.jsp" %>
<%@ include file="jspDefault/Content.jsp" %>
<%@ include file="jspDebug/DebugDelivry.jsp" %>
<%@ include file="jspCommon/Footer.jsp" %>
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