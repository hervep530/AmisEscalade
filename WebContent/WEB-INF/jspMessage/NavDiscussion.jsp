<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<c:if test="${ roleId > 1}">
							  <button type="button" class="btn btn-default"
							  			onclick="location='${contextPath}/message/lfd/${discussion.id}/${token}'">
									<img alt="Icon voir la discussion" src="${contextPath}/images/discussion_open.png" 
										title="Afficher toute la discussion" width="20" height="20">
							  </button>
</c:if>
<c:if test="${ roleId > 1 }">
							  <button type="button" class="btn btn-default" data-toggle="modal" data-target="#confirmModal" 
							  			data-location="${contextPath}/message/d/${discussion.id}/${token}"
							  			data-title="Confirmer la suppression de la discussion"
							  			data-action="Supprimer"
							  			data-body="Souhaitez-vous supprimer la discussion ${discussion.title}">
									<img alt="Icone Supprimer" src="${contextPath}/images/mail-delete.png" 
										title="Supprimer la discussion" width="20" height="20">
							  </button>
</c:if>
