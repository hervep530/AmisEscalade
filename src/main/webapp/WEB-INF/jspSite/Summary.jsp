<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
				        <div class="media text-muted pt-3">
				        	<img src="${pageContext.request.contextPath}/medias/site/${site.slug}.jpg"
								 alt="Kerlouan thumb" width="32" height="32" class="mr-2 rounded"/>
				        	<p class="media-body pb-3 mb-0 small lh-125 border-bottom border-gray">
				            	<span class="d-block text-gray-dark">
				            		<a href="${pageContext.request.contextPath}/site/r/${site.id}/${site.slug}">
				            			<strong><c:out value="${site.name}"></c:out></strong>
				            		</a>
				            		<c:out value="${site.country} - ${site.department}"></c:out>
				            		
				            	</span>
				            	<c:out value="${site.summary}"></c:out>
				        	</p>
				        </div>
    