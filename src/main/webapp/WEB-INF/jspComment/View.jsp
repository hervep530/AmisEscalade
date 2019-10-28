<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
