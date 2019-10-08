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
			</div>
			<div id="detail" class="container mx-4">
				<section id="teaser" class="row">
					<div id="medias" class="col-xl-8">
						<img alt="" src="${pageContext.request.contextPath}/medias/topo/${topo.slug}.jpg"
							width="96%" height="96%" />
					</div>
					<div id="" class="col-xl-4">
						<div id="topoStatus">
						</div>
						<p>Auteur : <c:out value="${ topo.writer }" /></p>
						<p>Date d'édition : <c:out value="${ topo.writedAt }" /></p>
						<p id="description"><c:out value="${ delivry.attributes.topo.summary }" /></p>
					</div>
				</section>
				<section id="content" class="row">
					<div id="topoDescription" class="col-xl-8">
						<h3>Description détaillée</h3>
						<p><c:out value="${ topo.content }" /></p>					
					</div>
					<div id="topoLinks" class="col-xl-4">
						<h3>Liste des spots</h3>
						<ul>
<c:forEach items="${ delivry.attributes.topo.sites }" var="site">
							<li>
								<a href="${contextPath}/site/r/${site.id}/${site.slug}">
									${site.name}
								</a>
							</li>
</c:forEach>
						</ul>					
					</div>
				</section>
			</div>
		</article>