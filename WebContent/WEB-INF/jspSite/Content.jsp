<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    	<c:set var="site" value="${ delivry.attributes.site}" scope="page" />
		<article id="acceuil">
			<div id="summary" class="mx-4">
				<div class="row">
					<h1 class="h2 text-center col-md-9">
						${ delivry.attributes.site.friendTag ? '<span class="badge badge-pill badge-warning">Ami !</span> ' : ''}
						<c:out value="${ delivry.attributes.site.name }" />
					</h1>
					<div class="col-md-3">
<%@ include file="AuthorNavContent.jsp" %>
<%@ include file="MemberNavContent.jsp" %>
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