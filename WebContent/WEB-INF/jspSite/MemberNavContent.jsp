<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<c:set var="ut" value="${ delivry.attributes.site.friendTag ? 'utf' : 'utt' }" scope="page"></c:set>
<c:set var="utImage" value="${ delivry.attributes.site.friendTag ? 'unfavorite.png' : 'favorite.png' }" scope="page"></c:set>
<c:if test="${ roleId > 2 }">
							<li class="nav-item">
								<a href="${contextPath}/site/${ut}/${delivry.attributes.site.id}/${token}" class="p-2">
									<img alt="Icon tag ami" src="${contextPath}/images/${utImage}" 
										title="(De-)Taguer ami" width="32" height="32">
								</a>
							</li>
</c:if>