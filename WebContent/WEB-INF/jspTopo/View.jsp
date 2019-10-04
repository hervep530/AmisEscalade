<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
				    <div id="main" class="container">
				      <div class="d-flex align-items-center p-3 my-3 text-blue-50 bg-purple rounded box-shadow">
				        <img class="mr-3" src="" alt="site symbol" width="48" height="48">
				        <div class="lh-100">
				          <h6 class="mb-0 text-blue lh-100">Tous les topos</h6>
				        </div>
				      </div>

				      <div class="my-3 p-3 bg-white rounded box-shadow">
				        <h6 class="border-bottom border-gray pb-2 mb-0">Du plus récent au plus ancien</h6>
<c:if test="${fn:length(delivry.attributes.topos) gt 0}">
	<c:forEach items="${delivry.attributes.topos}" var="topo">
				        <div class="media text-muted pt-3">
				        	<img src="${pageContext.request.contextPath}/medias/topo/${topo.slug}.jpg"
								 alt="${topo.slug} thumb" width="32" height="32" class="mr-2 rounded"/>
				        	<p class="media-body pb-3 mb-0 small lh-125 border-bottom border-gray">
				            	<span class="d-block text-gray-dark">
				            		<a href="${pageContext.request.contextPath}/topo/r/${topo.id}/${topo.slug}">
				            			<strong><c:out value="${topo.name}"></c:out></strong>
				            		</a>
				            		
				            	</span>
				            	<c:out value="${topo.summary}"></c:out>
				        	</p>
				        </div>
	</c:forEach>
</c:if>				


				        <small class="d-block text-right mt-3">
				          <a href="#">All updates</a>
				        </small>
				      </div>
				    </div>