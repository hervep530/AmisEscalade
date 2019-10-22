<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<c:if test="${ roleId > 2 }">
							<li class="nav-item">
								<a href="${contextPath}/comment/u/${comment.id}/${staticToken}" class="p-2">
									<img alt="Icon Edit" src="${contextPath}/images/edit_1.png" 
										title="Modifier le commentaire" width="24" height="24">
								</a>
							</li>
</c:if>
<c:if test="${ roleId > 5  && userId == comment.author.id }">
							<li class="nav-item">
								<a href="${contextPath}/comment/upf/${comment.id}/${staticToken}" class="p-2">
									<img alt="Icon Unpublish" src="${ontextPath}/images/unpublish.png" 
										title="Dé-publier le commentaire" width="24" height="24">
								</a>
							</li>
</c:if>
