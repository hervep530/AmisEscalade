<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
		<nav id="navBar" class="navbar navbar-expand-lg navbar-dark bg-dark py-0">
			<ul class="navbar-nav ml-3 mr-auto">
				<li class="nav-item active">
					<a class="nav-link px-4 py-3" href="${pageContext.request.contextPath}/site/l/1">Sites</a>
				</li>
				<li class="nav-item">
					<a class="nav-link px-4 py-3" href="${pageContext.request.contextPath}/topo/l/1">Topos</a>
					</li>
				<li class="nav-item">
					<a class="nav-link px-4 py-3" href="${pageContext.request.contextPath}/site/f">Recherche</a>
				</li>
				<li class="nav-item dropdown">
					<a class="nav-link px-4 py-3 dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
						Idenfication
					</a>
					<div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
						<a class="dropdown-item" href="${pageContext.request.contextPath}/session/connexion">Connexion</a>
						<a class="dropdown-item" href="${pageContext.request.contextPath}/session/deconnexion">Deconnexion</a>
						<a class="dropdown-item" href="${pageContext.request.contextPath}/session/inscription">Inscription</a>
						<a class="dropdown-item" href="${pageContext.request.contextPath}/session/d">Mon Compte</a>
					</div>
				</li>
			</ul>
  		</nav>

 