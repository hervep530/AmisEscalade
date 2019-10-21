<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
		<nav id="navBar" class="navbar navbar-expand-lg navbar-dark bg-dark py-0">
			<ul class="navbar-nav ml-3 mr-auto">
				<li class="nav-item active">
					<a class="nav-link px-4 py-3" href="${contextPath}/site/l/1">Sites</a>
				</li>
				<li class="nav-item">
					<a class="nav-link px-4 py-3" href="${contextPath}/topo/l/1">Topos</a>
					</li>
				<li class="nav-item">
					<a class="nav-link px-4 py-3" href="${contextPath}/site/f">Recherche</a>
				</li>
				<li class="nav-item dropdown">
					<a class="nav-link px-4 py-3 dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						Idenfication
					</a>
					<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
						<a class="dropdown-item" href="${pageContext.request.contextPath}/session/connexion/0/786775566A7674776D7541724E58766B">Connexion</a>
						<a class="dropdown-item" href="${pageContext.request.contextPath}/session/deconnexion/0/${staticToken}">Deconnexion</a>
						<a class="dropdown-item" href="${pageContext.request.contextPath}/session/inscription/0/786775566A7674776D7541724E58766B">Inscription</a>
						<a class="dropdown-item" href="${pageContext.request.contextPath}/session/d/0/${token}">Mon Compte</a>
					</div>
				</li>
			</ul>
  		</nav>

 