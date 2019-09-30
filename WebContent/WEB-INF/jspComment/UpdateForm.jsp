<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<c:set var="commentContentError" value="${delivry.attributes.commentForm.errors.content}" scope="page"></c:set>

						<form id="commentSiteForm" class="text-center border border-light p-1 my-1" 
								action="${pageContext.request.contextPath}/site/uac" method="POST">
							<input type="hidden" id="commentReferenceId" name="commentReferenceId" 
								value="${delivry.attributes.site.id}">
							<input type="hidden" id="commentReferenceType" name="commentReferenceType" 
								value="${delivry.attributes.site.type}">
							<div class="form-group">
								<div class="row">
									<div class="col-md-9" >
										<label class="basic-top-label" for="commentContent">Ecrire un commentaire</label>
									</div>
									<div class="col-md-3">
								  		<button type="submit" class="btn btn-primary">Envoyer</button>
									</div>
								</div>
								<textarea id="commentContent" name="commentContent" rows="2"
											 class="form-control${empty commentContentError ? '' : ' is-invalid'}"
											 ><c:out value='${delivry.attributes.commentForm.content}'></c:out></textarea>
								<div class="invalid-feedback${empty commentContentError ? ' invisible' : ''}"
											id="summaryError">${commentContentError}</div>
							</div>
						</form>
