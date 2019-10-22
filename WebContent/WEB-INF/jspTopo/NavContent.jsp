<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<c:set var="authorId" value="${ topo.author.id }" scope="page"></c:set>
<c:if test="${ topo.available && roleId > 1 && userId != authorId }">
							<li class="nav-item">
								<a href="${contextPath}/message/cft/${topo.id}/${staticToken}" class="p-2">
									<img alt="Icon Booking" src="${contextPath}/images/booking.png" 
										title="Reserver le topo" width="32" height="32">
								</a>
							</li>
</c:if>
<c:if test="${ (roleId > 1 && userId == authorId) || roleId > 2 }">
							<li class="nav-item">
								<a href="${contextPath}/topo/u/${topo.id}/${staticToken}" class="p-2">
									<img alt="Icon Edit" src="${contextPath}/images/edit_1.png" 
										title="Modifier le site" width="32" height="32">
								</a>
							</li>
</c:if>
<c:if test="${ (roleId > 1 && userId == authorId) || roleId > 2 }">
	<c:set var="uaAction" value="${ topo.available ? 'uaf' : 'uat' }" scope="page"></c:set>
	<c:set var="uaIcon" value="${ topo.available ? 'unavailable' : 'available' }" scope="page"></c:set>
	<c:set var="uaDisplayStatus" value="${ topo.available ? 'disponible' : 'réservé' }" scope="page"></c:set>
							<li class="nav-item">
								<a href="${contextPath}/topo/${uaAction}/${topo.id}/${token}" class="p-2">
									<img alt="Icon ${uaIcon}" src="${contextPath}/images/make_${uaIcon}.png" 
										title="Rendre le topo ${uaDisplayStatus}" width="32" height="32">
								</a>
							</li>
</c:if>
<c:if test="${ roleId > 5 }">
							<li class="nav-item">
								<a href="${contextPath}/site/${action}/${topo.id}/${token}" class="p-2">
									<img alt="Icon nom action" src="${contextPath}/images/${actionImage}" 
										title="Nom de l'action" width="32" height="32">
								</a>
							</li>
</c:if>