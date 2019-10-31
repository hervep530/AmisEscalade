<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<c:set var="focusedDiscussionId"
	value="${delivry.attributes.messageBox.focusedDiscussionId}"
	scope="page"></c:set>
<c:set var="focusedDiscussion"
	value="${delivry.attributes.messageBox.focusedDiscussion}" scope="page"></c:set>
<c:if
	test="${fn:length(delivry.attributes.messageBox.discussions) == 0}">
	<p class="text-center text-soft mt-5">
		<em>Aucun message à afficher dans la vue.</em>
	</p>
</c:if>
<c:if
	test="${fn:length(delivry.attributes.messageBox.discussions) gt 0}">
	<c:forEach items="${delivry.attributes.messageBox.discussions}"
		var="discussion">
		<div class="card mb-1">
			<div class="row no-gutters">
				<div class="col-md-4 row">
					<div class="col-md-2 pt-1">
						<img
							src="${pageContext.request.contextPath}/images/comment-writer.png"
							class="card-img" alt="Comment writer">
					</div>
					<div class="col-md-2 pt-1">
						<img
							src="${pageContext.request.contextPath}/images/comment-writer.png"
							class="card-img" alt="Comment writer">
					</div>
					<c:set var="interlocutor" scope="page"
						value="${discussion.sender.id == userId ? discussion.receiver.username : discussion.sender.username}">
					</c:set>
					<p class="card-title col-md-8 pt-2">
						<em>${interlocutor}</em>
					</p>
				</div>
				<div class="col-md-8 row">
					<h6 class="card-text col-md-9 pt-2">
						<small class="text-muted">${discussion.title}</small>
					</h6>
					<div class="col-md-3">
						<nav id="NavDiscussion" class="">
							<%@ include file="../jspMessage/NavDiscussion.jsp"%>
						</nav>
					</div>
				</div>
			</div>
		</div>
		<c:if test="${discussion.id == focusedDiscussionId}">
			<c:set var="messageCount" scope="page" value="0"></c:set>
			<c:forEach items="${focusedDiscussion}" var="message">
				<div class="card ml-5 mb-1">
					<c:set var="iconType" scope="page"
						value="${message.sender.id == userId ? 'send' : 'receive'}"></c:set>
					<c:set var="mailType" scope="page"
						value="${message.sender.id == userId ? 'envoyé' : 'reçu'}"></c:set>
					<c:set var="interlocutor" scope="page"
						value="${message.sender.id == userId ? message.receiver.username : message.sender.username}">
					</c:set>
					<c:set var="displayAuthor" scope="page"
						value="${message.sender.id == userId ? 'moi' : message.sender.username }">
					</c:set>
					<div id="message-link-${messageCount}" class="row no-gutters pl-2">


						<div class="col-md-1 pt-2">
							<img
								src="${pageContext.request.contextPath}/images/mail-${iconType}.png"
								alt="Message ${mailType}" title="Message ${mailType}" width="24"
								height="24">
						</div>
						<!-- h6 class="card-title col-md-8 pt-2"><em>${interlocutor}</em></h6 -->
						<p class="card-text col-md-7 pt-2">
							<c:out value="${displayAuthor} : ${message.reducedContent}"></c:out>
						</p>
						<p class="card-text col-md-2 pt-2">
							<small class="text-muted">${message.displayDtSent}</small>
						</p>
						<div class="col-md-2">
							<nav id="NavMessage" class="nav-mailbox">
								<%@ include file="../jspMessage/NavMessage.jsp"%>
							</nav>
						</div>


					</div>
				</div>
				<c:set var="messageCount" scope="page" value="${ messageCount + 1 }"></c:set>
			</c:forEach>
		</c:if>
	</c:forEach>
</c:if>