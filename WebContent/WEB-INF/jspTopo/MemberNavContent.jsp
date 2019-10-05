<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<c:set var="action" value="${ topo.name == 'action' ? 'action1' : 'action2' }" scope="page"></c:set>
<c:set var="actionImage" value="${ topo.name == 'action' ? 'image1.png' : 'image2.png' }" scope="page"></c:set>
<c:if test="${ roleId > 5 }">
							<li class="nav-item">
								<a href="${contextPath}/site/${action}/${topo.id}/${token}" class="p-2">
									<img alt="Icon nom action" src="${contextPath}/images/${actionImage}" 
										title="Nom de l'action" width="32" height="32">
								</a>
							</li>
</c:if>