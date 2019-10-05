<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    	<c:set var="topo" value="${ delivry.attributes.topo}" scope="page" />
		<article id="siteContent">
			<div id="summary" class="mx-4">
				<div class="row">
					<h1 class="h2 text-center col-md-9">
						<c:out value="${ delivry.attributes.topo.name }" />
					</h1>
					<div class="col-md-3">
						<nav id="NavContent" class="navbar navbar-expand-lg">
							<ul class="navbar-nav">
<%@ include file="AuthorNavContent.jsp" %>
<%@ include file="MemberNavContent.jsp" %>
							</ul>
						</nav>
					</div>
				</div>
				<p id="description"><c:out value="${ delivry.attributes.topo.summary }" /></p>
			</div>
			<div id="detail" class="container mx-4">
				<section id="teaser" class="row">
					<div id="medias" class="col-xl-8">
						<img alt="" src="${pageContext.request.contextPath}/medias/topo/${topo.slug}.jpg"
							width="96%" height="96%" />
					</div>
					<div id="technicalSheet" class="col-xl-4">
						<p>Auteur : <c:out value="${ topo.writer }" /></p>
						<p>Date d'édition : <c:out value="${ topo.writedAt }" /></p>
					</div>
				</section>
				<section id="content">
					<p><c:out value="${ site.content }" /></p>					
				</section>
			</div>
		</article>