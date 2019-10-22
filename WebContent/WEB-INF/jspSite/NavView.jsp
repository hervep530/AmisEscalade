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
<c:if test="${ roleId > 1 }">
							  <button type="button" class="btn btn-default"
							  		onclick="location='${contextPath}/site/c/0/${staticToken}'">
									<img alt="Icone Ajouter" src="${contextPath}/images/add.png" 
										title="Créer un nouveau topo" width="20" height="20">
							  </button>
</c:if>
							  