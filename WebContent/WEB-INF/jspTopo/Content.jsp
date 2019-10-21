<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    	<c:set var="topo" value="${ delivry.attributes.topo}" scope="page" />
		<article id="siteContent">
			<div id="summary" class="mx-4">
				<div class="py-3 row">
					<h1 class="h2 text-center col-md-6">
						<c:out value="${ delivry.attributes.topo.name }" />
					</h1>
					<div class="col-md-3">
<c:set var="tagContent" value="${ delivry.attributes.topo.available ? 'Disponible' : 'Réservé'}" scope="page"></c:set>
<c:set var="tagType" value="${ delivry.attributes.topo.available ? 'success' : 'danger'}" scope="page"></c:set>
						<p><span class="jcm-tag jcm-tag-${tagType} text-${tagType}">${tagContent}</span></p>
					</div>
					<div class="col-md-3">
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
			<div class="bd-callout bd-callout-warning">
				<h5 id="conveying-meaning-to-assistive-technologies">Conveying meaning to assistive technologies</h5>
				
				<p>Using color to add meaning only provides a visual indication, which will not be conveyed to users of assistive technologies – such as screen readers. Ensure that information denoted by the color is either obvious from the content itself (e.g. the visible text), or is included through alternative means, such as additional text hidden with the <code class="highlighter-rouge">.sr-only</code> class.</p>
			</div>
			
		</article>