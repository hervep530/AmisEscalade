<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<c:if test="${ roleId > 2 }">
							<li class="nav-item">
								<a href="${pageContext.request.contextPath}/comment/d/${comment.id}" class="p-2">
									<img alt="Icon Remove" src="${pageContext.request.contextPath}/images/remove.png" 
										title="Supprimer le commentaire" width="24" height="24">
								</a>
							</li>
</c:if>
