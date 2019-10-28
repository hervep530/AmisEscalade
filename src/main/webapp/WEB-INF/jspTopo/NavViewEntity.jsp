<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<c:set var="authorId" value="${ topo.author.id }" scope="page"></c:set>
<c:if test="${ (userId == topo.author.id) || roleId > 2 }">
							  <button type="button" class="btn btn-default"
							  			onclick="location='${contextPath}/topo/u/${topo.id}/${staticToken}'">
									<img alt="Icone Editer" src="${contextPath}/images/edit_1.png" 
										title="Modifier le topo" width="20" height="20">
							  </button>
</c:if>
<c:if test="${ (userId == topo.author.id) || roleId > 2 }">
	<c:set var="uaAction" value="${ topo.available ? 'uaf' : 'uat' }" scope="page"></c:set>
	<c:set var="uaIcon" value="${ topo.available ? 'unavailable' : 'available' }" scope="page"></c:set>
	<c:set var="uaDisplayStatus" value="${ topo.available ? 'réservé' : 'disponible' }" scope="page"></c:set>
	
							  <button type="button" class="btn btn-default"
							  			onclick="location='${contextPath}/topo/${uaAction}/${topo.id}/${token}'">
									<img alt="Icon ${uaIcon}" src="${contextPath}/images/make_${uaIcon}.png" 
										title="Rendre le topo ${uaDisplayStatus}" width="20" height="20">
							  </button>
</c:if>
<c:if test="${ roleId > 10 }">
							  <button type="button" class="btn btn-default">
									<img alt="Icone Dé-publier" src="${contextPath}/images/unpublish.png" 
										title="Dé-publier le topo" width="20" height="20">
							  </button>
</c:if>
<c:if test="${ (userId == topo.author.id) || roleId > 2 }">
							  <button type="button" class="btn btn-default" data-toggle="modal" data-target="#confirmModal" 
							  			data-location="${contextPath}/topo/d/${topo.id}/${token}"
							  			data-title="Confirmer la suppression du topo"
							  			data-action="Supprimer"
							  			data-body="Souhaitez-vous supprimer le topo ${topo.name}">
									<img alt="Icone Supprimer" src="${contextPath}/images/remove.png" 
										title="Supprimer le topo sélectionné" width="20" height="20">
							  </button>
</c:if>
<c:if test="${ roleId > 5 }">
							<li class="nav-item">
								<a href="${contextPath}/site/${action}/${topo.id}/${staticToken}" class="p-2">
									<img alt="Icon nom action" src="${contextPath}/images/${actionImage}" 
										title="Nom de l'action" width="32" height="32">
								</a>
							</li>
</c:if>
