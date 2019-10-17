<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
						<form id="MessageForm" class="text-center border border-light p-1 my-1" 
								action="${contextPath}/message/c/0/${token}" method="POST">
							<input type="hidden" id="referenceId" name="referenceId" 
								value="${delivry.attributes.messageForm.referenceId}">
							<input type="hidden" id="parentId" name="parentId" 
								value="${delivry.attributes.messageForm.parentId}">
							<div class="form-group">
								<div class="row">
									<div class="col-md-9" >
										<label class="basic-top-label" for="commentContent">Ecrire un message</label>
									</div>
									<div class="col-md-3">
								  		<button type="submit" class="btn btn-primary">Envoyer</button>
									</div>
								</div>
								<textarea id="commentContent" name="commentContent" rows="2"
											 class="form-control${empty messageContentError ? '' : ' is-invalid'}"
											 ><c:out value='${delivry.attributes.messageForm.content}'></c:out></textarea>
								<div class="invalid-feedback${empty messageContentError ? ' invisible' : ''}"
											id="summaryError">${messageContentError}</div>
							</div>
						</form>
