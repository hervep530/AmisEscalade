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
					<div id="error">

						<div class="monkey-box monkey-box-danger">
							<h1 class="monkey-title monkey-title-danger text-white">404 - Une erreur s'est produite</h1>
							<p class="monkey-drawer text-center">
								La page que vous avez demandée est introuvable.<br>
								Cette url est invalide.
							</p>
							<p class="monkey-drawer text-center">
								<button type="button" class="btn btn-danger"
								onclick="location='${contextPath}'">Retour à l'accueil</button>
							</p>
						</div>
					
					</div>
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