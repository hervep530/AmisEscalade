<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
		<header class="jumbotron jumbotron-fluid py-2 mb-0">
			<div class="container">
				<div class="row">
					<div id="logo" class="col-xl-2">
						<img alt="Logo Amis escalade" src="${contextPath}/images/escalade.png"
								width="64" height="64">
					</div>
					<div id="title" class="col-xl-7">
						<p class="h1 text-center pt-2">Les Amis de l'escalade</p>
					</div>
<c:if test="${userId > 1}">
					<nav id="condensedNav" class="navbar navbar-expand-lg col-xl-3">
						<ul class="navbar-nav">
							<li class="nav-item">
								<a href="${contextPath}/session/d/0/${token}" class="p-2">
									<img alt="Mon compte" src="${contextPath}/images/user.png" 
										title="<c:out value="${sessionScope.sessionUser.username}"></c:out>"
										width="32" height="32">
								</a>
							</li>
							<li class="nav-item">
								<a href="#" class="p-2">
								<img alt="Mon espace personnel" src="${contextPath}/images/home.png"
									width="32" height="32">
								</a>
							</li>
							<li class="nav-item">
								<a href="${contextPath}/message/lmd/1/${token}" class="p-2">
								<img alt="Messagerie" src="${contextPath}/images/mailbox.png"
									width="32" height="32">
								</a>
							</li>
						</ul>
					</nav>
</c:if>
				</div>				
			</div>
		</header>
