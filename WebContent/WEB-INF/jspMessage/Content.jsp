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
						<div id="content-${messageCount}" class="camel-box masked-message">
							<div class="row">
								<!-- message information -->
								<div class="camel-first-hump camel-box-classic col-3">
								
								
									<!-- sent / received -->
									<h3 class="camel-first-title camel-first-title-classic row">
										<img width="24" height="24" class="col-3"
											src="${pageContext.request.contextPath}/images/mail-${iconType}.png" 
											alt="Message ${mailType}" title="Message ${mailType}">
										<span class="col-9 text-center text-normal text-white py-1">
											<em>Message ${mailType}</em>
										</span>
									</h3>
									
									
									<!-- Date -->
									<p class="camel-drawer text-center"><strong>${message.displayDtSent}</strong></p>
									<div class="camel-separator"></div>
									<!-- Sender / receiver -->
									<h6 class="camel-drawer text-center text-normal text-soft">${interlocutorType} :</h6>
									<p class="camel-drawer text-center text-l"><strong>${interlocutor}</strong></p>
									
									
								</div>
								<!-- message content -->
								<div class="camel-second-hump camel-box-classic col-9">
									<!-- title + btn answer -->
									<h2 class="camel-second-title camel-second-title-classic text-white py-2">${ response ? 'Re: ' : '' }${message.title}</h2>
									<!-- content -->
									<p class="camel-drawer">${message.content}</p>
								</div>
							</div>
						</div>
	<c:set var="messageCount" scope="page" value="${ messageCount + 1 }"></c:set>
</c:forEach>
