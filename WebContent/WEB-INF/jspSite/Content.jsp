<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
		<article id="acceuil">
			<div id="summary" class="mx-4">
				<h1 class="h2 text-center">delivry.site.name</h1>
				<p id="description">delivry.site.summary</p>
			</div>
			<div id="detail" class="container mx-4">
				<section id="teaser" class="row">
					<div id="medias" class="col-xl-8">
						<img alt="" src="${pageContext.request.contextPath}/media/site/kerlouan.jpg"
							width="96%" height="96%" />
					</div>
					<div id="technicalSheet" class="col-xl-4">
						<p>$pays - $dpt</p>
						<ul>
							<li>Bloc / Falaise / Mur</li>
							<li>Hauteur : min / max</li>
							<li>cotations : min / max</li>
							<li>orientation : Nord / Nord-Est</li>
						</ul>
					</div>
				</section>
				<section id="content">
					<p>delivry.site.content</p>					
				</section>
			</div>
		</article>