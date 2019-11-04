<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<article id="acceuil">
	<div id="summary" class="mx-4">
		<h1 class="h2 text-center text-classic text-xxl py-2">
			<c:out value="ACCUEIL" />
		</h1>
		<p id="description">Les amis de l'escalade vous propose de
			découvrir des spots aussi nombreux que variés . Vous trouverez aussi
			bien des informations générales, que des données techniques ou
			pratiques.</p>
	</div>
	<div id="detail" class="container mx-4">
		<section id="teaser" class="row">
			<div id="medias" class="col-lg-8 mb-2">
				<img alt="Les amis de l'escalade" width="100%" height="100%"
					src="${pageContext.request.contextPath}/medias/welcome.jpg" />
			</div>
			<div id="technicalSheet" class="col-lg-4">
				<div class="monkey-box monkey-box-classic">
					<div class="monkey-hat monkey-hat-classic"></div>
					<p class="monkey-drawer">
						<strong>Vous trouverez :</strong>
					</p>
					<div class="monkey-separator"></div>
					<p class="monkey-drawer">Photos</p>
					<div class="monkey-separator"></div>
					<p class="monkey-drawer row">Données techniques</p>
					<div class="monkey-separator"></div>
					<p class="monkey-drawer">Descriptions</p>
					<div class="monkey-separator"></div>
					<p class="monkey-drawer">Conseils</p>
				</div>
			</div>
		</section>
		<section id="content">
			<p>Pour une découverte agréable et ludique de nouveaux sites
				d'escalade, nous vous proposons l'utilisation du menu ou encore la
				barre de recherche.</p>
			<p>Vous trouverez également de nombreux topos que nos membres
				vous proposent à l'échange. Pour cette raison, n'oubliez pas et
				n'hésitez pas à vous inscrire. Vous pourrez ainsi leur faire part de
				votre intérêt en message privé.</p>
			<p>Les rédacteurs se donnent de la peine pour vous présenter des
				articles de qualités. Il vous appartient de laisser des commentaires
				constructifs pour les encourager, et les aider à améliorer encore
				leur travail.</p>
		</section>
	</div>
</article>
