<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="accountProperties" class="text-center border border-light p-4">
	<h1 class="h2 text-center text-classic text-xxl py-2">
		<c:out value="Mon compte" />
	</h1>
	<div class="row mb-3">
		<div class="input-group">
			<div class="input-group mb-3">
				<div class="input-group-prepend">
					<span class="input-group-text" id="inputGroup-sizing-default">Nom
						d'utilisateur</span>
				</div>
				<input type="text" class="form-control"
					aria-label="Sizing example input"
					aria-describedby="inputGroup-sizing-default"
					value="<c:out value="${sessionScope.sessionUser.username}"></c:out>"
					readonly />
			</div>
		</div>
	</div>
	<div class="row mb-3">
		<div class="input-group">
			<div class="input-group mb-3">
				<div class="input-group-prepend">
					<span class="input-group-text" id="inputGroup-sizing-default">Adresse
						email</span>
				</div>
				<input type="text" class="form-control"
					aria-label="Sizing example input"
					aria-describedby="inputGroup-sizing-default"
					value="<c:out value="${sessionScope.sessionUser.mailAddress}"></c:out>"
					readonly />
			</div>
		</div>
	</div>
	<div class="row mb-3">
		<div class="input-group">
			<div class="input-group mb-3">
				<div class="input-group-prepend">
					<span class="input-group-text" id="inputGroup-sizing-default">Nombre
						de sites créés</span>
				</div>
				<input type="text" class="form-control"
					aria-label="Sizing example input"
					aria-describedby="inputGroup-sizing-default"
					value="<c:out value="Pas demandé - axe d'amélioration possible"></c:out>" readonly />
			</div>
		</div>
	</div>
	<div class="row mb-3">
		<div class="input-group">
			<div class="input-group mb-3">
				<div class="input-group-prepend">
					<span class="input-group-text" id="inputGroup-sizing-default">Nombres
						de topos créés</span>
				</div>
				<input type="text" class="form-control"
					aria-label="Sizing example input"
					aria-describedby="inputGroup-sizing-default"
					value="<c:out value="Pas demandé - axe d'amélioration possible"></c:out>" readonly />
			</div>
		</div>
	</div>
	<div class="row mb-3">
		<div class="input-group">
			<div class="input-group mb-3">
				<div class="input-group-prepend">
					<span class="input-group-text" id="inputGroup-sizing-default">Précedente
						connexion</span>
				</div>
				<input type="text" class="form-control"
					aria-label="Sizing example input"
					aria-describedby="inputGroup-sizing-default"
					value="<c:out value="Pas demandé - axe d'amélioration possible"></c:out>" readonly />
			</div>
		</div>
	</div>
	<div class="row mb-3">
		<div class="input-group">
			<div class="input-group mb-3">
				<div class="input-group-prepend">
					<span class="input-group-text" id="inputGroup-sizing-default">Inscrit
						depuis</span>
				</div>
				<input type="text" class="form-control"
					aria-label="Sizing example input"
					aria-describedby="inputGroup-sizing-default"
					value="<c:out value="Pas demandé - axe d'amélioration possible"></c:out>" readonly />
			</div>
		</div>
	</div>
</div>
