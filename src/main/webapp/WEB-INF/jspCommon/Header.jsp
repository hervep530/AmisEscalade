<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<header class="bg-classic py-2 mb-0">
	<nav id="navBar"
		class="navbar navbar-expand-lg navbar-classic bg-classic row py-0">
		<div class="col-sm-12 col-md-12 col-lg-4 col-xl-4">
			<a class="navbar-brand" href="${contextPath}/"> <img
				class="align-middle" alt="Logo Amis escalade"
				src="${contextPath}/images/escalade.png" width="36" height="36" />
				<span class="h2 align-middle px-4 text-help">Les Amis de l'escalade</span>
			</a>
			<!-- Toggler/collapsibe Button visible up to md -->
			<button class="navbar-toggler navbar-toggler-classic d-inline d-lg-none" type="button" data-toggle="collapse"
				data-target="#collapsibleNavbar">
				<span class="navbar-toggler-icon navbar-toggler-icon-classic"></span>
			</button>
		</div>
		<%@ include file="HeaderLinks.jsp"%>
		<%@ include file="HeaderIcons.jsp"%>
	</nav>
</header>
