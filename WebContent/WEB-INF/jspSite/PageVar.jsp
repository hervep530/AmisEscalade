<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<c:set var="service" value="${ delivry.parameters.parsedUrl.serviceAlias }" scope="page"/>
<c:set var="action" value="${delivry.parameters.parsedUrl.action}" scope="page"/>
<c:set var="action" value="${delivry.parameters.parsedUrl.action}" scope="page"/>
<c:set var="footerAuthor" value="${(service == 'topo' || service == 'site') && action == 'r' ? true : false}" scope="page"/>	
<c:set var="footerPreviousNext" value="" scope="page"/>
