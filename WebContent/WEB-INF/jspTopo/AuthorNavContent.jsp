<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<c:set var="authorId" value="${ topo.author.id }" scope="page"></c:set>
<c:if test="${ (roleId > 1 && userId == authorId) || roleId > 2 }">
							<li class="nav-item">
								<a href="${contextPath}/site/u/${topo.id}/${token}" class="p-2">
									<img alt="Icon Edit" src="${contextPath}/images/edit_1.png" 
										title="Modifier le site" width="32" height="32">
								</a>
							</li>
</c:if>