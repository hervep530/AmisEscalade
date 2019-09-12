<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
					<form id="searchForm" class="text-center border border-light p-3" action="" method="POST">
					    <p class="h4 mb-4">Filtres</p>
						<div class="row mb-3">
							<div class="input-group col-xl-6">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<input type="radio"  id="reference_site" name="reference_type" value="site"
											aria-label="Radio button for following text input" checked>
									</div>
								</div>
								<input type="text" value="Sites" class="form-control" 
									aria-label="Text input with radio button"/>
							</div>
							<div class="input-group col-xl-6">
								<div class="input-group-prepend">
									<div class="input-group-text">
										<input type="radio"  id="reference_topo" name="reference_type" value="topo"
											aria-label="Radio button for following text input">
									</div>
								</div>
								<input type="text" value="Topos" class="form-control" 
									aria-label="Text input with radio button"/>
							</div>							
						</div>
						<div class="input-group mb-3">
							<div class="input-group-prepend search-prepend-label">
								<label class="input-group-text search-label" for="site_type">Par type</label>
							</div>
							<select class="custom-select" id="site_type" name="site_type">
								<option value="0" selected>Choisir un type d'escalade...</option>
								<option value="1">Bloc</option>
								<option value="2">Falaise</option>
								<option value="3">Mur</option>
							</select>
						</div>						
						<div class="input-group mb-3">
							<div class="input-group-prepend search-prepend-label">
								<label class="input-group-text search-label" for="height">Par hauteur</label>
							</div>
							<input type="number" id="height" name="height" min="0" max="2000" class="form-control" 
								aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default"
								placeholder="Hauteur de voie (mètres)"/>
						</div>						
						<div class="input-group mb-3">
							<div class="input-group-prepend search-prepend-label">
								<label class="input-group-text search-label" for="cotation">Par cotation</label>
							</div>
							<select class="custom-select" id="cotation" name="cotation">
								<option value="0" selected>Choisir une cotation...</option>
<c:if test="${ fn:length(delivry.attributes.cotations) gt 0 }">
	<c:forEach items="${ delivry.attributes.cotations }" var="cotation">
								<option value="${ cotation.id }">${ cotation.label }</option>
	</c:forEach>
</c:if>								
							</select>
						</div>						
					    <p class="h4 mb-4">Expression à rechercher</p>
						<div class="input-group mb-3">
							<div class="input-group-prepend">
								<span class="input-group-text" id="inputGroup-sizing-default">Saisir</span>
							</div>
							<input type="text" class="form-control" id="searchText" name="searchText" 
								aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default">
						</div>

					    <!-- Search button -->
					    <button class="btn btn-info btn-block my-4" type="submit">Rechercher</button>
					</form>
