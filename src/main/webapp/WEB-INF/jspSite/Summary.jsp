<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="container mt-2 mb-2">
	<div class="media text-muted border-bottom border-gray pt-1 row">
		<div class="col-md-2 px-4 py-1">
			<img
				src="${contextPath}/medias/site/${site.slug}.jpg"
				alt="Kerlouan thumb" width="100%" class="mr-2 rounded" />
		</div>
		<div class="col-md-7 media-body pt-2 mb-0 small lh-125">
			<h3 class="row text-normal">
				<span class="d-block text-gray-dark col-md-8"> <a
					class="pr-3 link-help"
					href="${contextPath}/site/r/${site.id}/${site.slug}">
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
		<div class="col-sm-3 small">
			<p>
				Mis à jour par <strong><c:out
						value="${site.author.username}"></c:out></strong><br> il y a
				<c:out value="${site.modifiedElapsedTime}"></c:out>
			</p>
		</div>
	</div>
</div>