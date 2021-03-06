<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<c:set var="authorId" value="${ delivry.attributes.site.author.id }" scope="page"></c:set>
<c:set var="ut" value="${ delivry.attributes.site.friendTag ? 'utf' : 'utt' }" scope="page"></c:set>
<c:set var="utImage" value="${ delivry.attributes.site.friendTag ? 'unfavorite.png' : 'favorite.png' }" scope="page"></c:set>
<c:set var="utDesc" value="${ delivry.attributes.site.friendTag ? 'Dé-Taguer ami' : 'Taguer ami' }" scope="page"></c:set>
<c:if test="${ (roleId > 1 && userId == authorId) || roleId > 2 }">
							<li class="nav-item">
								<a href="${contextPath}/site/u/${delivry.attributes.site.id}/${staticToken}" class="p-2">
									<img alt="Icon Edit" src="${contextPath}/images/edit_1.png" 
										title="Modifier le site" width="32" height="32">
								</a>
							</li>
</c:if>
<c:if test="${ roleId > 2 }">
							<li class="nav-item">
								<a href="${contextPath}/site/${ut}/${delivry.attributes.site.id}/${token}" class="p-2">
									<img alt="Icon tag ami" src="${contextPath}/images/${utImage}" 
										title="${utDesc}" width="32" height="32">
								</a>
							</li>
</c:if>