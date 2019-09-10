<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
					<form id="connexionForm" class="text-center border border-light p-5" action="" method="POST">
					
					    <p class="h4 mb-4">Connexion</p>
					
					    <!-- Email -->
					    <input type="email" id="mailAddress" name="mailAddress" class="form-control mb-4" placeholder="Adresse mail">
					
					    <!-- Password -->
					    <input type="password" id="password" name="password" class="form-control mb-4" placeholder="Password">
					
					    <div class="d-flex justify-content-around">
					        <div>
					            <!-- Remember me -->
					            <div class="custom-control custom-checkbox">
					                <input type="checkbox" class="custom-control-input" id="rememberMe" name="rememberMe">
					                <label class="custom-control-label" for="rememberMe">Remember me</label>
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
