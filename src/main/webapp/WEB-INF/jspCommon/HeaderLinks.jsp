<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="b-block d-lg-none">
	<ul id="collapsibleNavbar" class="navbar-nav collapse navbar-collapse ml-3 mr-auto">
		<li class="nav-item"><a class="nav-link px-4 py-3"
			href="${contextPath}/site/l/1">Sites</a></li>
		<c:if test="${ userId  > 1 }">
			<li class="nav-item"><a class="nav-link px-4 py-3"
				href="${contextPath}/topo/l/1">Topos</a></li>
		</c:if>
		<li class="nav-item"><a class="nav-link px-4 py-3"
			href="${contextPath}/site/f">Recherche</a></li>
		<c:if test="${ userId == 1 }">
			<li class="nav-item"><a class="nav-link px-4 py-3"
				href="${contextPath}/session/connexion/0/${staticToken}">Connexion</a></li>
		</c:if>
		<c:if test="${ userId  > 1 }">
			<li class="nav-item"><a class="nav-link px-4 py-3"
				href="${contextPath}/session/deconnexion/0/${staticToken}">Deconnexion</a></li>
		</c:if>
		<c:if test="${ userId  == 1 }">
			<li class="nav-item"><a class="nav-link px-4 py-3"
				href="${contextPath}/session/inscription/0/${staticToken}">Inscription</a></li>
		</c:if>
		<c:if test="${ userId  > 1 }">
			<li class="nav-item"><a class="nav-link px-4 py-3"
				href="${contextPath}/session/d/0/${staticToken}">Mon Compte</a></li>
		</c:if>
		<c:if test="${ userId  > 1 }">
			<li class="nav-item"><a class="nav-link px-4 py-3"
				href="${contextPath}/topo/h/${userId}/${staticToken}">Mon Espace</a></li>
		</c:if>
		<c:if test="${ userId  > 1 }">
			<li class="nav-item"><a class="nav-link px-4 py-3"
				href="${contextPath}/message/lmd/1/${staticToken}">Mes Messages</a></li>
		</c:if>
	</ul>
</div>

<div class="d-none d-lg-block col-lg-5 col-xl-5">
	<ul class="navbar-nav nav-justified mx-5 ">
		<li class="nav-item"><a class="nav-link"
			href="${contextPath}/site/l/1">Sites</a></li>
		<c:if test="${ userId  > 1 }">
			<li class="nav-item"><a class="nav-link px-""
				href="${contextPath}/topo/l/1">Topos</a></li>
		</c:if>
		<li class="nav-item"><a class="nav-link px-3"
			href="${contextPath}/site/f">Recherche</a></li>
		<c:if test="${ userId == 1 }">
			<li class="nav-item"><a class="nav-link px-3"
				href="${contextPath}/session/connexion/0/${staticToken}">Connexion</a></li>
		</c:if>
		<c:if test="${ userId  > 1 }">
			<li class="nav-item"><a class="nav-link px-3"
				href="${contextPath}/session/deconnexion/0/${staticToken}">Deconnexion</a></li>
		</c:if>
		<c:if test="${ userId  == 1 }">
			<li class="nav-item"><a class="nav-link px-3"
				href="${contextPath}/session/inscription/0/${staticToken}">Inscription</a></li>
		</c:if>
	</ul>
</div>
