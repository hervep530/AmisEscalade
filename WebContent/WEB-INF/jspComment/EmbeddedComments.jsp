<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<c:set var="commentContentError" value="${delivry.attributes.commentForm.errors.content}" scope="page"></c:set>
    				<aside id="comment-group">
    					<div class="card-header my-1 py-0"><em>Commentaires</em></div>
						<form id="commentSiteForm" class="text-center border border-light p-1 my-1" 
								action="${contextPath}/site/uac/0/${token}" method="POST">
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
<c:forEach items="${delivry.attributes.site.comments}" var="comment">
						<div class="card mb-1">
							<div class="row no-gutters">
								<div class="col-md-1">
								<img src="${pageContext.request.contextPath}/images/comment-writer.png" class="card-img" alt="Comment writer">
								</div>
								<div class="col-md-11">
									<div class="card-body">
										<div class="row">
										<h6 class="card-title col-md-6">${comment.author.username}</h6>
										<p class="card-text col-md-3"><small class="text-muted">
											il y a ${comment.modifiedElapsedTime}
										</small></p>
										<div class="col-md-3">
											<nav id="NavComment" class="navbar navbar-expand-lg">
												<ul class="navbar-nav">
<%@ include file="../jspComment/AuthorNavComment.jsp" %>
<%@ include file="../jspComment/MemberNavComment.jsp" %>
												</ul>
											</nav>
										</div>
										</div>
										<p class="card-text"><c:out value="${comment.content}"></c:out></p>
									</div>
								</div>
							</div>
						</div>
</c:forEach>
					</aside>