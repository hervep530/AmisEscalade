<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

		<!-- Importing tinymce library if we need to load formular -->
		<c:if test="${pageContext.request.method!='GET' && delivry.parameters.parsedUrl.action=='nouveau'}">
		<script type="application/javascript" src="${pageContext.request.contextPath}/tinymce/tinymce.min.js"> </script>
		</c:if>
		<!-- Importing jquery library -->
		<script type="application/javascript" src="${pageContext.request.contextPath}/jquery/jquery-3.4.1.min.js"> </script>
		<!-- Importing bootstrap library -->
		<script type="application/javascript" src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"> </script>
		<!-- Application owned javascript -->
		<script type="application/javascript" src="${pageContext.request.contextPath}/js/jcm.js"> </script>
		<!-- Activating tinymce if we need to load formular -->
		<c:if test="${pageContext.request.method!='POST' && action=='nouveau'}">
		<script>tinymce.init({selector:'textarea'});</script>
		</c:if>
			