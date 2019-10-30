<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="d-none d-lg-block col-lg-3">
	<c:if test="${userId > 1}">
		<ul class="navbar-nav nav-justified px-4">
			<li class="nav-item"><a
				href="${contextPath}/session/d/0/${staticToken}" class="p-2"> <img
					alt="Mon compte" src="${contextPath}/images/user.png"
					title="<c:out value="${sessionScope.sessionUser.username}"></c:out>"
					width="32" height="32">
			</a></li>
			<li class="nav-item"><a
				href="${contextPath}/topo/h/${userId}/${staticToken}" class="p-2">
					<img alt="Mon espace personnel"
					src="${contextPath}/images/home.png" width="32" height="32">
			</a></li>
			<li class="nav-item"><a
				href="${contextPath}/message/lmd/1/${staticToken}" class="p-2">
					<img alt="Messagerie" src="${contextPath}/images/mailbox.png"
					width="32" height="32">
			</a></li>
		</ul>
	</c:if>
</div>
