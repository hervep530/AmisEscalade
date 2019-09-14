<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<c:set var="method" value="${pageContext.request.method}" scope="page"></c:set>
<c:set var="service" value="${ delivry.parameters.parsedUrl.serviceAlias }" scope="page"/>
<c:set var="action" value="${delivry.parameters.parsedUrl.action}" scope="page"/>
<c:set var="action" value="${delivry.parameters.parsedUrl.action}" scope="page"/>
<c:set var="footerAuthor" value="${(service == 'topo' || service == 'site') && action == 'r' ? true : false}" scope="page"/>	
<c:set var="footerPagination" value="${(service == 'topo' || service == 'site') && action == 'l' ? true : false}" scope="page"/>	
<c:if test="${delivry.parameters.parsedUrl.id > 0}">
	<c:set var="pageId" value="${delivry.parameters.parsedUrl.id}"></c:set>
</c:if>
<c:if test="${delivry.attributes.listsCount > 0}">
	<c:set var="pageCount" value="${delivry.attributes.listsCount}"></c:set>
</c:if>
