<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    	<c:set var="site" value="${ delivry.attributes.site}" scope="page" />
		<article id="siteContent">
			<div id="summary" class="mx-4">
				<div class="row">
					<h1 class="h2 text-center col-md-9">
						<c:out value="${ delivry.attributes.site.name }" />
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
				<p id="description"><c:out value="${ delivry.attributes.site.summary }" /></p>
			</div>
			<div id="detail" class="container mx-4">
				<section id="teaser" class="row">
					<div id="medias" class="col-xl-8">
						<img alt="" src="${pageContext.request.contextPath}/medias/site/${site.slug}.jpg"
							width="96%" height="96%" />
					</div>
					<div id="technicalSheet" class="col-xl-4">
<c:if test="${ delivry.attributes.site.friendTag }">
						<p><span class="jcm-tag jcm-tag-warning text-warning">
							<img alt="Icone certification" src="${pageContext.request.contextPath}/images/certifying.png"
							 width="16" height="16"> Officiel : Ami de l'escalade
						</span></p>
</c:if>
						<p><strong>$<c:out value="${ delivry.attributes.site.country }" /> - 
							<c:out value="${ delivry.attributes.site.department }" /></strong></p>
						<ul>
							<li>
								<c:out value="${ site.block ? 'Bloc' : ''}"/>
								<c:out value="${ site.block && ( site.cliff || site.wall ) ? ' / ' : ''}"/>
								<c:out value="${ site.cliff ? 'Falaise' : ''}"/>
								<c:out value="${ ( site.block || site.cliff ) && site.wall ? ' / ' : ''}"/>
								<c:out value="${ site.wall ? 'Mur' : ''}"/>
							</li>
							<li>Hauteur
								<ul>
									<li>Min :<c:out value="${ site.minHeight }" /></li>
									<li>Max :<c:out value="${ site.maxHeight }" /></li>
								</ul>
							</li>
							<li>Cotation
								<ul>
									<li>Min :<c:out value="${ site.cotationMin.label }" /></li>
									<li>Max :<c:out value="${ site.cotationMax.label }" /></li>
								</ul>
							</li>
							<li>orientation : <c:out value="${ site.orientation }" /></li>
						</ul>
					</div>
				</section>
				<section id="content">
					<p><c:out value="${ site.content }" /></p>					
				</section>
			</div>
		</article>