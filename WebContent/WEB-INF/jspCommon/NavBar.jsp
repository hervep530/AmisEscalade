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
					<a class="nav-link px-4 py-3" href="#">A propos</a>
				</li>
				<li class="nav-item">
					<a class="nav-link px-4 py-3" href="${pageContext.request.contextPath}/session/connexion">Connexion</a>
				</li>
			</ul>
			<form class="form-inline my-2 my-lg-0">
				<input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
				<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
			</form>
  		</nav>
