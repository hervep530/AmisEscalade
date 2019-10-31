<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<c:set var="footerAuthor" value="${action == 'r' ? true : false}" scope="page" />
<c:set var="footerPagination" value="${action == 'l' ? true : false}" scope="page" />
<c:if test="${delivry.parameters.parsedUrl.id > 0}">
	<c:set var="pageId" value="${delivry.parameters.parsedUrl.id}"></c:set>
</c:if>
<c:if test="${delivry.attributes.listsCount > 0}">
	<c:set var="pageCount" value="${delivry.attributes.listsCount}"></c:set>
</c:if>
<footer>
	<c:if test="${footerAuthor == true}">
		<p class="card-text col-lg-8 ml-4 mb-4">
			<small class="text-muted"> Mis à jour par ${ topo.author.username },
				il y a ${topo.modifiedElapsedTime} </small>
		</p>
	</c:if>
	<c:if test="${footerPagination == true}">
		<nav class="px-3" aria-label="Page navigation example">
			<ul class="pagination justify-content-end">
				<c:if test="${pageId > 1}">
					<li class="page-item"><a class="page-link link-help"
						href="${contextPath}/${service}/l/${pageId - 1}">Previous</a></li>
				</c:if>
				<c:forEach begin="0" end="8" varStatus="loop">
					<c:set var="linkId" value="${pageId - 3 + loop.index }"></c:set>
					<c:if test="${linkId > 0 && linkId <= pageCount}">
						<li class="page-item"><a
							href="${contextPath}/${service}/l/${linkId}"
							class="page-link link-help">${linkId}</a></li>
					</c:if>
				</c:forEach>
				<c:if test="${pageCount > pageId}">
					<li class="page-item"><a class="page-link link-help"
						href="${contextPath}/${service}/l/${pageId + 1}">Next</a></li>
				</c:if>
			</ul>
		</nav>
	</c:if>
</footer>
