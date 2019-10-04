<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<c:set var="mailError" value="${delivry.errors.mailAddress}" scope="page"></c:set>
<c:set var="passwdError" value="${delivry.errors.password}" scope="page"></c:set>
					<form id="connexionForm" class="text-center border border-light p-5" action="${token}" method="POST">
					    <p class="h4 mb-4">Connexion</p>
<!-- Email -->
				    	<div class="form-group mb-3">
				    		<label class="basic-top-label" for="mailAddress">
				    			<em>Adresse mail </em><span class="required">*</span>
				    		</label>
							<input type="email" id="mailAddress" name="mailAddress"
									class="form-control${empty mailError?'':' is-invalid'}" 
									aria-label="Search" aria-describedby="labelMailAddress"
									placeholder="Saisir votre adresse mail..."
									value="<c:out value='${delivry.attributes.connexionForm.mailAddress}'></c:out>">
							<div class="invalid-feedback${empty mailError?' invisible':''}"
								id="mailError">${mailError}</div>
				    	</div>
<!-- Password -->
				    	<div class="form-group mb-3">
				    		<label class="basic-top-label" for="password">
				    			<em>Mot de passe </em><span class="required">*</span>
				    		</label>
							<input type="password" id="password" name="password"
								class="form-control${empty passwdError?'':' is-invalid'}" 
								aria-label="password" aria-describedby="labelPassword"
								placeholder="Saisissez votre mot de passe...">
							<div class="invalid-feedback${empty passwdError?' invisible':''}"
								id="passwdError">${passwdError}</div>
						</div>					
					    <div class="d-flex justify-content-around">
					        <div>
					            <!-- Remember me -->
					            <div class="custom-control custom-checkbox">
					                <input type="checkbox" class="custom-control-input" id="rememberMe" name="rememberMe">
					                <label class="custom-control-label" for="rememberMe">Se souvenir de moi</label>
					            </div>
					        </div>
					        <div>
					            <!-- Forgot password -->
					            <a href="">Mot de passe oublié?</a>
					        </div>
					    </div>
					
					    <!-- Sign in button -->
					    <button class="btn btn-info btn-block my-4" type="submit">Connecter</button>
					
					    <!-- Register -->
					    <p>Pas encore enregistré?
					        <a href="${pageContext.request.contextPath}/session/inscription">Créer un compte</a>
					    </p>
					
					</form>
