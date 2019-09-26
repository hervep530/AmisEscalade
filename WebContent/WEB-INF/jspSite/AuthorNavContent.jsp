<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<c:set var="roleId" value="${ sessionScope.sessionUser.role.id }" scope="page"></c:set>
<c:set var="userId" value="${ sessionScope.sessionUser.id }" scope="page"></c:set>
<c:set var="authorId" value="${ delivry.attributes.site.author.id }" scope="page"></c:set>
<c:if test="${ (roleId > 1 && userId == authorId) || roleId > 2 }">
							<li class="nav-item">
								<a href="${pageContext.request.contextPath}/site/u/${delivry.attributes.site.id}" class="p-2">
									<img alt="Icon Edit" src="${pageContext.request.contextPath}/images/edit_1.png" 
										title="Modifier le site" width="32" height="32">
								</a>
							</li>
</c:if>