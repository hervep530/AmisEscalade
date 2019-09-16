<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<c:set var="mailError" value="${delivry.errors.mailAddress}" scope="page"></c:set>
<c:set var="nameError" value="${delivry.errors.username}" scope="page"></c:set>
<c:set var="passwdError" value="${delivry.errors.password}" scope="page"></c:set>
					<form id="InscriptionForm" class="text-center border border-light p-3" 
							action="inscription" method="POST">
					    <p class="h4 mb-4">Inscription</p>
<!-- Email -->
				    	<div class="form-group mb-3">
				    		<label class="basic-top-label" for="mailAddress">
				    			<em>Adresse mail </em><span class="required">*</span>
				    		</label>
							<input type="text" id="mailAddress" name="mailAddress"
									class="form-control${empty mailError?'':' is-invalid'}" 
									aria-label="Search" aria-describedby="labelMailAddress"
									placeholder="Saisir une adresse mail valide..."
									value="<c:out value='${delivry.attributes.inscriptionForm.mailAddress}'></c:out>">
							<div class="invalid-feedback${empty mailError?' invisible':''}"
								id="mailError">${mailError}</div>
				    	</div>
<!-- Username -->
				    	<div class="form-group mb-3">
				    		<label class="basic-top-label" for="username">
				    			<em>Nom d'utilisateur </em><span class="required">*</span>
				    		</label>
							<input type="text" id="username" name="username"
								class="form-control${empty nameError?'':' is-invalid'}" 
								aria-label="username" aria-describedby="labelUsername"
								placeholder="Choisissez un nom d'utilisateur..."
								value="<c:out value='${delivry.attributes.inscriptionForm.username}'></c:out>">
							<div class="invalid-feedback${empty nameError?' invisible':''}"
								id="usernameError">${nameError}</div>
						</div>
<!-- Password -->
				    	<div class="form-group mb-3">
				    		<label class="basic-top-label" for="password">
				    			<em>Mot de passe </em><span class="required">*</span>
				    		</label>
							<input type="text" id="password" name="password"
								class="form-control${empty passwdError?'':' is-invalid'}" 
								aria-label="password" aria-describedby="labelPassword"
								placeholder="Saisissez un mot de passe...">
							<div class="invalid-feedback${empty passwdError?' invisible':''}"
								id="passwdError">${passwdError}</div>
						</div>
<!-- Confirm Password -->
				    	<div class="form-group mb-3">
				    		<label class="basic-top-label" for="confirmPassword">
				    			<em>Confirmation </em><span class="required">*</span>
				    		</label>
							<input type="text" id="confirmPassword" name="confirmPassword"
								class="form-control${empty passwdError?'':' is-invalid'}" 
								aria-label="confirmPassword" aria-describedby="labelConfirmPassword"
								placeholder="Saisir à nouveau votre mot de passe...">
					    </div>
<!-- Sign up button -->
					    <button class="btn btn-info btn-block my-4" type="submit">Creer mon compte</button>
					</form>
