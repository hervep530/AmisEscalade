<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<c:set var="authorId" value="${ topo.author.id }" scope="page"></c:set>
<c:if test="${ (userId == topo.author.id) || roleId > 2 }">
							  <button type="button" class="btn btn-default">
									<img alt="Icone Editer" src="${contextPath}/images/edit_1.png" 
										title="Modifier le topo" width="20" height="20">
							  </button>
</c:if>
<c:if test="${ (userId == topo.author.id) }">
							  <button type="button" class="btn btn-default">
									<img alt="Icone Dé-publier" src="${contextPath}/images/unpublish.png" 
										title="Dé-publier le topo" width="20" height="20">
							  </button>
</c:if>

