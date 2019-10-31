<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<c:set var="commentContentError"
	value="${delivry.attributes.commentForm.errors.content}" scope="page"></c:set>
<aside id="comment-group">
	<div class="card-header my-1 py-0">
		<em>Commentaires</em>
	</div>
	<c:if test="${ userId > 1 }">
		<form id="commentSiteForm"
			class="text-center border border-light p-1 my-1"
			action="${contextPath}/site/uac/0/${staticToken}" method="POST">
			<input type="hidden" id="partMethod" name="partMethod" value="false">
			<input type="hidden" id="commentReferenceId"
				name="commentReferenceId" value="${delivry.attributes.site.id}">
			<input type="hidden" id="commentReferenceType"
				name="commentReferenceType" value="${delivry.attributes.site.type}">
			<div class="form-group">
				<div class="row mb-1 align-base">
					<div class="col-md-9 align-bottom">
						<label class="basic-top-label" for="commentContent">Ecrire
							un commentaire</label>
					</div>
					<div class="col-md-3">
						<button type="submit" class="btn btn-classic py-1 text-normal">Envoyer</button>
					</div>
				</div>
				<textarea id="commentContent" name="commentContent" rows="2"
					class="form-control${empty commentContentError ? '' : ' is-invalid'}"><c:out
						value='${delivry.attributes.commentForm.content}'></c:out></textarea>
				<div
					class="invalid-feedback${empty commentContentError ? ' invisible' : ''}"
					id="summaryError">${commentContentError}</div>
			</div>
		</form>
	</c:if>
	<c:if test="${fn:length(delivry.attributes.site.comments) == 0}">
		<p class="text-center text-soft mt-5">
			<em>Aucun commentaire à afficher sur ce site.</em>
		</p>
	</c:if>
	<c:if test="${fn:length(delivry.attributes.site.comments) gt 0}">

		<c:forEach items="${delivry.attributes.site.comments}" var="comment">
			<div class="card mb-1">
				<div class="row no-gutters">
					<div class="col-md-1">
						<img
							src="${pageContext.request.contextPath}/images/comment-writer.png"
							class="card-img" alt="Comment writer">
					</div>
					<div class="col-md-9">
						<div class="card-body">
							<div class="row">
								<h6 class="card-title col-md-8">${comment.author.username}</h6>
								<p class="card-text col-md-4">
									<small class="text-muted"> il y a
										${comment.modifiedElapsedTime} </small>
								</p>
							</div>
							<p class="card-text">
								<c:out value="${comment.content}"></c:out>
							</p>
						</div>
					</div>
					<div class="col-md-2">
						<nav id="NavComment" class="navbar navbar-expand-lg">
							<ul class="navbar-nav">
								<%@ include file="../jspComment/NavComment.jsp"%>
							</ul>
						</nav>
					</div>
				</div>
			</div>
		</c:forEach>
	</c:if>
</aside>