<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    	<section id="notification" class="mx-4 mt-3">
<c:forEach var="notification" items="${delivry.notifications}">
			<p class="alert alert-${notification.value.type.alias}">
				<strong>${notification.key}</strong> : ${notification.value.message}
				<button type="button" class="close" data-dismiss="alert" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</p>
</c:forEach>
    	</section>

