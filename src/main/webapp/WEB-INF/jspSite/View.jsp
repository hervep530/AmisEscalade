<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="main" class="container mt-2 mb-2">
	<h1 class="h2 text-center text-classic text-xxl py-2">
		<c:out value="${ delivry.attributes.title }" />
	</h1>

	<div class="my-1 pt-1 px-3 bg-white rounded box-shadow">
		<div class="row border-bottom border-gray">
			<h2 class="col-md-9 mb-0 py-2 text-l text-soft">Du plus récent
				au plus ancien</h2>
			<div class="col-md-3">
				<%@ include file="NavView.jsp"%>
			</div>
		</div>
		<c:if test="${fn:length(delivry.attributes.sites) == 0}">
			<p class="text-center text-soft mt-5">
				<em>Aucun élément à afficher dans la vue.</em>
			</p>
		</c:if>
		<c:if test="${fn:length(delivry.attributes.sites) gt 0}">
			<c:forEach items="${delivry.attributes.sites}" var="site">
				<div class="media text-muted border-bottom border-gray pt-1 row">
					<div class="col-md-2 px-4 py-1">
						<img
							src="${pageContext.request.contextPath}/medias/site/${site.slug}.jpg"
							alt="Kerlouan thumb" width="100%" class="mr-2 rounded" />
					</div>
					<div class="col-md-7 media-body pt-2 mb-0 small lh-125">
						<h3 class="row text-normal">
							<span class="d-block text-gray-dark col-md-8"> <a
								class="pr-3 link-help"
								href="${pageContext.request.contextPath}/site/r/${site.id}/${site.slug}">
									<strong><c:out value="${site.name}"></c:out></strong>
							</a>
							<c:out value="${site.country} - ${site.department}"></c:out></span> <span
								class="col-md-4"> ${site.pathsNumber} voies de
								${site.cotationMin.label} à ${site.cotationMax.label} </span>
						</h3>
						<p>
							<c:out value="${site.summary}"></c:out>
						</p>
					</div>
					<div class="col-sm-3">
						<%@ include file="NavViewEntity.jsp"%>
					</div>
				</div>
			</c:forEach>
		</c:if>
	</div>
</div>