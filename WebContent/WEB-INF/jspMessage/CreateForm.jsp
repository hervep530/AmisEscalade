<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<c:set var="messageContentError" value="${delivry.attributes.siteForm.errors.content}" scope="page"></c:set>
						<form id="MessageForm" class="text-center border border-light p-1 my-1" 
								action="${contextPath}/message/c/0/${token}" method="POST">
							<input type="hidden" id="parentId" name="parentId" 
								value="${delivry.attributes.messageForm.message.parent.id}">
							<input type="hidden" id="receiverId" name="receiverId" 
								value="${delivry.attributes.messageForm.message.receiver.id}">
							<input type="hidden" id="title" name="title" 
								value="${delivry.attributes.messageForm.message.title}">
							<input type="hidden" id="partMethod" name="partMethod" value="false">
							<div class="form-group">
								<div class="row">
									<div class="col-md-9" >
										<label class="basic-top-label" for="content">Ecrire un message</label>
									</div>
									<div class="col-md-3">
								  		<button type="submit" class="btn btn-primary">Envoyer</button>
									</div>
								</div>
								<textarea id="content" name="content" rows="2"
											 class="form-control${empty messageContentError ? '' : ' is-invalid'}"
											 ><c:out value='${delivry.attributes.messageForm.message.content}'></c:out></textarea>
								<div class="invalid-feedback${empty messageContentError ? ' invisible' : ''}"
											id="summaryError">${messageContentError}</div>
							</div>
						</form>
