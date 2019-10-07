<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
							  <button type="button" class="btn btn-default">
									<img alt="Icone Filtre" src="${contextPath}/images/filter.png" 
										title="Menu de filtre" width="20" height="20">
							  </button>
							  <button type="button" class="btn btn-default">
									<img alt="Icone Tri" src="${contextPath}/images/sort.png" 
										title="Tri (Ascendant / Descendant)" width="20" height="20">
							  </button>
<c:if test="${ roleId > 2 }">
							  <button type="button" class="btn btn-default"
							  		onclick="location='${contextPath}/topo/c/0/${token}'">
									<img alt="Icone Ajouter" src="${contextPath}/images/add.png" 
										title="Créer un nouveau topo" width="20" height="20">
							  </button>
</c:if>
<c:if test="${ roleId > 5 }">
							  <!-- Button trigger modal -->
							  <button type="button" class="btn" data-toggle="modal" data-target="#confirmModal" 
							  	data-location="${contextPath}/session/d/0/${token}"
							  	data-title="Confirmer la déconnexion"
							  	data-action="Deconnecter"
							  	data-body="rien">
								<img alt="Icone Tri" src="${contextPath}/images/sort.png" 
										title="Tri (Ascendant / Descendant)" width="20" height="20">
							  </button>
</c:if>
							  