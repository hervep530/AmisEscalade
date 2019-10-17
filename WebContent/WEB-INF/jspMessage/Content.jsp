<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<c:set var="messageCount" scope="page" value="0"></c:set>
<c:set var="focusedDiscussion" value="${delivry.attributes.messageBox.focusedDiscussion}" scope="page"></c:set>
<c:forEach items="${focusedDiscussion}" var="message">
	<c:set var="iconType" scope="page" value="${message.sender.id == userId ? 'send' : 'receive'}"></c:set>
	<c:set var="mailType" scope="page" value="${message.sender.id == userId ? 'envoyé' : 'reçu'}"></c:set>
	<c:set var="interlocutorType" scope="page" value="${message.sender.id == userId ? 'Destinataire' : 'Expéditeur'}"></c:set>
	<c:set var="interlocutor" scope="page" 
	value="${message.sender.id == userId ? message.receiver.username : message.sender.username}">
	</c:set>
	<c:set var="response" scope="page" value="${message.id == message.discussionId ? false : true}"></c:set>
						<div id="content-${messageCount}" class="card p-2 mb-1 masked-message">
							<div class="row">
								<!-- message information -->
								<div class="col-3">
									<!-- sent / received -->
									<div class="row">
										<div class="col-3">
											<img src="${pageContext.request.contextPath}/images/mail-${iconType}.png" 
												alt="Message ${mailType}" title="Message ${mailType}" width="24" height="24">
										</div>
										<div class="col-9">
											<p class="text-center"><em>Message ${mailType}</em></p>
										</div>
									</div>
									<!-- Date -->
									<p class="text-center"><strong>${message.displayDtSent}</strong></p>
									<!-- Sender / receiver -->
									<p>${interlocutorType} :</p>
									<p class="text-center"><strong>${interlocutor}</strong></p>
								</div>
								<!-- message content -->
								<div class="col-9">
									<!-- title + btn answer -->
									<h2>${ response ? 'Re: ' : '' }${message.title}</h2>
									<!-- content -->
									<p>${message.content}</p>
								</div>
							</div>
						</div>
	<c:set var="messageCount" scope="page" value="${ messageCount + 1 }"></c:set>
</c:forEach>
