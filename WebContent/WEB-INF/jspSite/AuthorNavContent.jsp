<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<c:set var="authorId" value="${ delivry.attributes.site.author.id }" scope="page"></c:set>
<c:if test="${ (roleId > 1 && userId == authorId) || roleId > 2 }">
							<li class="nav-item">
								<a href="${contextPath}/site/u/${delivry.attributes.site.id}/${staticToken}" class="p-2">
									<img alt="Icon Edit" src="${contextPath}/images/edit_1.png" 
										title="Modifier le site" width="32" height="32">
								</a>
							</li>
</c:if>