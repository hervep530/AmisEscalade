<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<c:if test="${ (roleId == 2 && userId == comment.author.id) || roleId > 2 }">
							<li class="nav-item">
								<a href="${pageContext.request.contextPath}/comment/u/${comment.id}" class="p-2">
									<img alt="Icon Edit" src="${pageContext.request.contextPath}/images/edit_1.png" 
										title="Modifier le commentaire" width="24" height="24">
								</a>
							</li>
</c:if>
<c:if test="${ roleId > 5  && userId == comment.author.id }">
							<li class="nav-item">
								<a href="${pageContext.request.contextPath}/comment/upf/${comment.id}" class="p-2">
									<img alt="Icon Unpublish" src="${pageContext.request.contextPath}/images/unpublish.png" 
										title="Dé-publier le commentaire" width="24" height="24">
								</a>
							</li>
</c:if>
