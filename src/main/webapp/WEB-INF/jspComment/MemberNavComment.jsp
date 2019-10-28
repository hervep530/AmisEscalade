<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<c:if test="${ roleId > 2 }">
							<li class="nav-item">
								<a href="${contextPath}/comment/d/${comment.id}/${token}" class="p-2">
									<img alt="Icon Remove" src="${contextPath}/images/remove.png" 
										title="Supprimer le commentaire" width="24" height="24">
								</a>
							</li>
</c:if>
