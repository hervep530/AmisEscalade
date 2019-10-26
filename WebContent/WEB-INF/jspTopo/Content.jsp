<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    	<c:set var="topo" value="${ delivry.attributes.topo}" scope="page" />
		<article id="siteContent">
			<div id="summary" class="mx-4">
				<div class="py-3 row">
					<h1 class="h2 text-center col-md-8">
						<c:out value="${ delivry.attributes.topo.name }" />
					</h1>
					<div class="col-md-4">
						<nav id="NavContent" class="navbar navbar-expand-lg">
							<ul class="navbar-nav">
<%@ include file="NavContent.jsp" %>
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
<c:set var="tagContent" value="${ delivry.attributes.topo.available ? 'Disponible' : 'Réservé'}" scope="page"></c:set>
<c:set var="tagType" value="${ delivry.attributes.topo.available ? 'success' : 'danger'}" scope="page"></c:set>
						<p class="jcm-tag-box"><span class="jcm-tag jcm-tag-${tagType} text-${tagType}">${tagContent}</span></p>
						<div class="monkey-box monkey-box-${tagType}">
							<div class="monkey-hat monkey-hat-${tagType}"></div>
							<p class="monkey-drawer">Auteur : <c:out value="${ topo.writer }" /></p>
							<div class="monkey-separator"></div>
							<p class="monkey-drawer">Date d'édition : <c:out value="${ topo.writedAt }" /></p>
							<div class="monkey-separator"></div>
							<p id="description" class="monkey-drawer"><c:out value="${ delivry.attributes.topo.summary }" /></p>
						</div>
					</div>
				</section>
				<section id="content" class="row">
					<div id="topoDescription" class="desc-box col-xl-8">
						<h3 class="desc-title">Description détaillée</h3>
						<div class="desc-separator"></div>
						<div class="desc-content"><c:out value="${ topo.content }" /></div>
					</div>
					<div id="topoLinks" class="col-xl-4">
						<div class="monkey-box monkey-box-classic">
							<h3 class="monkey-title monkey-title-classic text-white">Liens vers les spots</h3>
							<ul>
<c:set var="s" value="0" scope="page"></c:set>
<c:forEach items="${ delivry.attributes.topo.sites }" var="site">
	<c:if test="${ s > 0 }">
								<div class="monkey-separator"></div>
	</c:if>
								<li class="monkey-drawer">
									<a class="tex-classic" href="${contextPath}/site/r/${site.id}/${site.slug}">
										${site.name}
									</a>
								</li>
<c:set var="s" value="${s+1}" scope="page"></c:set>
</c:forEach>
							</ul>
						</div>					
					</div>
				</section>
			</div>			
		</article>