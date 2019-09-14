<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    				<div  id="testSearchForm">
    					<div id="debugType" class="text-center border border-light p-3">
					    	<p class="h4 mb-4">Debug</p>
							<div class="input-group">
								<div class="input-group mb-3">
									<div class="input-group-prepend">
										<span class="input-group-text" id="inputGroup-sizing-default">Type</span>
									</div>
									<input type="text" class="form-control" value="${ delivry.parameters.form.typeField }"
										aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default" readonly>
								</div>
							</div>
							<div class="input-group">
								<div class="input-group mb-3">
									<div class="input-group-prepend">
										<span class="input-group-text" id="inputGroup-sizing-default">Type d'escalade</span>
									</div>
									<input type="text" class="form-control" value="${ delivry.parameters.form.siteTypeField }"
										aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default" readonly>
								</div>
							</div>
							<div class="input-group">
								<div class="input-group mb-3">
									<div class="input-group-prepend">
										<span class="input-group-text" id="inputGroup-sizing-default">Hauteur de voie</span>
									</div>
									<input type="text" class="form-control" value="${ delivry.parameters.form.heightField }"
										aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default" readonly>
								</div>
							</div>
							<div class="input-group">
								<div class="input-group mb-3">
									<div class="input-group-prepend">
										<span class="input-group-text" id="inputGroup-sizing-default">Cotation</span>
									</div>
									<input type="text" class="form-control" value="${ delivry.parameters.form.cotationField }"
										aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default" readonly>
								</div>
							</div>
							<div class="input-group">
								<div class="input-group mb-3">
									<div class="input-group-prepend">
										<span class="input-group-text" id="inputGroup-sizing-default">Texte de recherche</span>
									</div>
									<input type="text" class="form-control" value="${ delivry.parameters.form.searchField }"
										aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default" readonly>
								</div>
							</div>
							<div class="input-group">
								<div class="input-group mb-3">
									<div class="input-group-prepend">
										<span class="input-group-text" id="inputGroup-sizing-default">Query</span>
									</div>
									<input type="text" class="form-control" value="${ delivry.attributes.query }"
										aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default" readonly>
								</div>
							</div>
						</div>
    				</div>
