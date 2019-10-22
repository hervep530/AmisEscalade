<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
							  <button id="readMessage-${messageCount}" type="button" class="btn btn-default"
							  			onclick="javascript:readContent(${messageCount});">
									<img alt="Icone voir message" src="${contextPath}/images/message_read.png" 
										title="Visualiser le message" width="20" height="20">
							  </button>
<c:if test="${ message.lastDiscussionMessage && message.receiver.id == userId }">
							  <button type="button" class="btn btn-default"
							  			onclick="location='${contextPath}/message/ca/${message.id}/${staticToken}'">
									<img alt="Icone Répondre" src="${contextPath}/images/mail-write.png" 
										title="Répondre au message" width="20" height="20">
							  </button>
</c:if>
<c:if test="${ roleId > 10 }">    
							  <a class="active-link" href="#content${messageCount}" 
										onClick="javascript:readMessage(this.id);">
									<img alt="Icone voir message" src="${contextPath}/images/message_read.png" 
										title="Visualiser le message" width="20" height="20">
							  </a>
</c:if>
