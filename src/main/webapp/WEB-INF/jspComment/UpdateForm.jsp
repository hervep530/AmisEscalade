<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<c:set var="site" scope="page" value="${delivry.attributes.site}"></c:set>
<c:set var="commentId" scope="page" value="${action == 'u' ? id : ''}"></c:set>
<c:set var="postAction" scope="page"
	value="${contextPath}${action == 'u' ? '/comment/u/' : '/site/uac/'}${id}/${token}"></c:set>
<c:set var="identifyingComment"
	value="commentaire sur le site ${site.name}" scope="page"></c:set>
<c:set var="labelAction" scope="page"
	value="${action == 'u' ? identifyingComment : 'Ecrire un commentaire'}"></c:set>
<c:set var="commentContentError" scope="page"
	value="${delivry.attributes.commentForm.errors.content}"></c:set>
<div class="container">
	<form method="POST" id="commentSiteForm"
		class="text-center border border-light p-1 my-1"
		action="${postAction}">
		<input type="hidden" id="partMethod" name="partMethod" value="false">
		<input type="hidden" id="commentId" name="commentId"
			value="${commentId}"> <input type="hidden"
			id="commentReferenceId" name="commentReferenceId" value="${site.id}">
		<input type="hidden" id="commentReferenceType"
			name="commentReferenceType" value="${site.type}">
		<div class="form-group">
			<div class="row align-base mb-1">
				<div class="col-md-9">
					<label class="basic-top-label" for="commentContent"><small><em>${labelAction}</em></small></label>
				</div>
				<div class="col-md-3 ">
					<button type="submit" class="btn btn-classic py-0 text-s">Valider</button>
				</div>
			</div>
			<textarea id="commentContent" name="commentContent" rows="4"
				class="form-control${empty commentContentError ? '' : ' is-invalid'}"><c:out
					value='${delivry.attributes.commentForm.content}'></c:out></textarea>
			<div
				class="invalid-feedback${empty commentContentError ? ' invisible' : ''}"
				id="summaryError">${commentContentError}</div>
		</div>
	</form>
</div>
