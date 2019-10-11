<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<c:set var="authorId" value="${ site.author.id }" scope="page"></c:set>
<c:if test="${ (userId == site.author.id) || roleId > 2 }">
							  <button type="button" class="btn btn-default"
							  			onclick="location='${contextPath}/site/u/${site.id}/${token}'">
									<img alt="Icone Editer" src="${contextPath}/images/edit_1.png" 
										title="Modifier le site" width="20" height="20">
							  </button>
</c:if>
<c:if test="${ (userId == site.author.id) && roleId > 10}">
							  <button type="button" class="btn btn-default">
									<img alt="Icone Dé-publier" src="${contextPath}/images/unpublish.png" 
										title="Dé-publier le site" width="20" height="20">
							  </button>
</c:if>
<c:if test="${ roleId > 2 }">
							  <button type="button" class="btn btn-default" data-toggle="modal" data-target="#confirmModal" 
							  			data-location="${contextPath}/site/d/${site.id}/${token}"
							  			data-title="Confirmer la suppression du site"
							  			data-action="Supprimer"
							  			data-body="Souhaitez-vous supprimer le site ${site.name}">
									<img alt="Icone Supprimer" src="${contextPath}/images/remove.png" 
										title="Supprimer le topo sélectionné" width="20" height="20">
							  </button>
</c:if>
<c:if test="${ roleId > 10 }">
							<li class="nav-item">
								<a href="${contextPath}/site/${action}/${site.id}/${token}" class="p-2">
									<img alt="Icon nom action" src="${contextPath}/images/${actionImage}" 
										title="Nom de l'action" width="32" height="32">
								</a>
							</li>
</c:if>
