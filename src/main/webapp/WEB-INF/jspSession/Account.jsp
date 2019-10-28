<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
					<div id="accountProperties" class="text-center border border-light p-4">
					    <p class="h4 mb-4">Mon compte</p>
					    <div class="row mb-3">
							<div class="input-group">
								<div class="input-group mb-3">
									<div class="input-group-prepend">
										<span class="input-group-text" id="inputGroup-sizing-default">Nom d'utilisateur</span>
									</div>
									<input type="text" class="form-control" aria-label="Sizing example input"
										aria-describedby="inputGroup-sizing-default"
										value="<c:out value="${sessionScope.sessionUser.username}"></c:out>" readonly/>
								</div>
							</div>
						</div>
					    <div class="row mb-3">
							<div class="input-group">
								<div class="input-group mb-3">
									<div class="input-group-prepend">
										<span class="input-group-text" id="inputGroup-sizing-default">Adresse email</span>
									</div>
									<input type="text" class="form-control" aria-label="Sizing example input"
										aria-describedby="inputGroup-sizing-default"
										value="<c:out value="${sessionScope.sessionUser.mailAddress}"></c:out>" readonly/>
								</div>
							</div>
						</div>
					    <div class="row mb-3">
							<div class="input-group">
								<div class="input-group mb-3">
									<div class="input-group-prepend">
										<span class="input-group-text" id="inputGroup-sizing-default">Nombre de sites créés</span>
									</div>
									<input type="text" class="form-control" aria-label="Sizing example input"
										aria-describedby="inputGroup-sizing-default"
										value="<c:out value="A implementer"></c:out>" readonly/>
								</div>
							</div>
						</div>
					    <div class="row mb-3">
							<div class="input-group">
								<div class="input-group mb-3">
									<div class="input-group-prepend">
										<span class="input-group-text" id="inputGroup-sizing-default">Nombres de topos créés</span>
									</div>
									<input type="text" class="form-control" aria-label="Sizing example input"
										aria-describedby="inputGroup-sizing-default"
										value="<c:out value="A implementer"></c:out>" readonly/>
								</div>
							</div>
						</div>
					    <div class="row mb-3">
							<div class="input-group">
								<div class="input-group mb-3">
									<div class="input-group-prepend">
										<span class="input-group-text" id="inputGroup-sizing-default">Précedente connexion</span>
									</div>
									<input type="text" class="form-control" aria-label="Sizing example input"
										aria-describedby="inputGroup-sizing-default"
										value="<c:out value="A implementer"></c:out>" readonly/>
								</div>
							</div>
						</div>
					    <div class="row mb-3">
							<div class="input-group">
								<div class="input-group mb-3">
									<div class="input-group-prepend">
										<span class="input-group-text" id="inputGroup-sizing-default">Inscrit depuis</span>
									</div>
									<input type="text" class="form-control" aria-label="Sizing example input"
										aria-describedby="inputGroup-sizing-default"
										value="<c:out value="A implementer"></c:out>" readonly/>
								</div>
							</div>
						</div>
					</div>
