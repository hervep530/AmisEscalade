<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" scope="page"></c:set>
<c:set var="method" value="${pageContext.request.method}" scope="page"></c:set>
<c:set var="service" value="${ delivry.parameters.parsedUrl.serviceAlias }" scope="page"></c:set>
<c:set var="action" value="${delivry.parameters.parsedUrl.action}" scope="page"></c:set>
<c:if test="${delivry.parameters.parsedUrl.id > 0}">
	<c:set var="id" value="${delivry.parameters.parsedUrl.id}"></c:set>
</c:if>
<c:set var="slug" value="${delivry.parameters.parsedUrl.slug}"></c:set>
