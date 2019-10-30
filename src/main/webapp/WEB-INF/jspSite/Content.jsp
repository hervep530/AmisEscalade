<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    	<c:set var="site" value="${ delivry.attributes.site}" scope="page" />
		<article id="siteContent">
			<div class="mx-4">
				<div class="row">
					<h1 class="h2 text-center text-classic text-xxl py-2 col-md-9">
						<c:out value="Site d'escalade - ${ delivry.attributes.site.name }" />
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
				<p class="text-soft"><em><c:out value="${ delivry.attributes.site.summary }" /></em></p>
			</div>
			<div id="detail" class="container mx-4">
				<section id="teaser" class="row">
					<div id="medias" class="col-lg-8 mb-2">
						<img alt="${site.slug}" width="100%" height="100%"
							src="${pageContext.request.contextPath}/medias/site/${site.slug}.jpg"/>
					</div>
					<div id="technicalSheet" class="col-lg-4 mb-2">
<c:if test="${ delivry.attributes.site.friendTag }">
						<p class="jcm-tag-box"><span class="jcm-tag jcm-tag-warning text-warning">
							<img alt="Icone certification" src="${pageContext.request.contextPath}/images/certifying.png"
							 width="16" height="16"> Officiel : Ami de l'escalade
						</span></p>
</c:if>


						<div class="monkey-box monkey-box-classic">
							<div class="monkey-hat monkey-hat-classic"></div>
							<p class="monkey-drawer"><strong><c:out value="${ delivry.attributes.site.country }" /> - 
							<c:out value="${ delivry.attributes.site.department }" /></strong></p>
							<div class="monkey-separator"></div>
							<p class="monkey-drawer"><c:out value="${ site.block ? 'Bloc' : ''}"/>
								<c:out value="${ site.block && ( site.cliff || site.wall ) ? ' / ' : ''}"/>
								<c:out value="${ site.cliff ? 'Falaise' : ''}"/>
								<c:out value="${ ( site.block || site.cliff ) && site.wall ? ' / ' : ''}"/>
								<c:out value="${ site.wall ? 'Mur' : ''}"/></p>
							<div class="monkey-separator"></div>
							<p class="monkey-drawer row">de <c:out value="${ site.minHeight }" />m à 
							<c:out value="${ site.maxHeight }"/>m</p>
							<div class="monkey-separator"></div>
							<p class="monkey-drawer">De <c:out value="${ site.cotationMin.label }" /> à 
							<c:out value="${ site.cotationMax.label }"/></p>
							<div class="monkey-separator"></div>
							<p class="monkey-drawer"><c:out value="${ site.orientation }" /></p>
						</div>
					</div>
				</section>
				<section id="content" class="pt-2">
					<h3 class="desc-title">Description détaillée</h3>
					<div class="desc-separator"></div>
					<p class="desc-content"><c:out value="${ site.content }" /></p>					
				</section>
			</div>
		</article>