<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<c:set var="cancelActionAnswer" scope="page"
	value="${contextPath}/message/lmd/1/${staticToken}"></c:set>
<c:set var="cancelActionReserve" scope="page"
	value="${contextPath}/topo/l/1"></c:set>
<c:set var="cancelAction" scope="page"
	value="${action == 'ca' ? cancelActionAnswer : cancelActionReserve}"></c:set>

<c:set var="messageContentError"
	value="${delivry.attributes.siteForm.errors.content}" scope="page"></c:set>
<form id="MessageForm" class="text-center border border-light p-1 my-1"
	action="${contextPath}/message/c/0/${token}" method="POST">
	<input type="hidden" id="parentId" name="parentId"
		value="${delivry.attributes.messageForm.message.parent.id}"> <input
		type="hidden" id="receiverId" name="receiverId"
		value="${delivry.attributes.messageForm.message.receiver.id}">
	<input type="hidden" id="title" name="title"
		value="${delivry.attributes.messageForm.message.title}"> <input
		type="hidden" id="partMethod" name="partMethod" value="false">
	<div class="form-group">
		<div class="row">
			<div class="col-md-9 pt-2">
				<label class="basic-top-label" for="content">Ecrire un
					message à <strong>${delivry.attributes.messageForm.message.receiver.username}</strong></label>
			</div>
			<div class="col-md-3 mb-1">
				<button type="submit" class="btn btn-classic py-1 text-normal">Envoyer</button>
				<button type="button" class="btn btn-danger py-1 text-normal"
					onclick="location='${cancelAction}'">Annuler</button>
			</div>
		</div>
		<textarea id="content" name="content" rows="2"
			class="form-control${empty messageContentError ? '' : ' is-invalid'}"><c:out
				value='${delivry.attributes.messageForm.message.content}'></c:out></textarea>
		<div
			class="invalid-feedback${empty messageContentError ? ' invisible' : ''}"
			id="summaryError">${messageContentError}</div>
	</div>
</form>
