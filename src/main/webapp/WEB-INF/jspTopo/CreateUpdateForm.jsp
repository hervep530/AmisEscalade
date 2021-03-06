<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<c:set var="form" scope="page" value="${delivry.attributes.topoForm}"></c:set>
<c:set var="postAction" scope="page"
	value="${contextPath}/topo/${action}/${ action == 'u' ? id : '0'}/${token}">
</c:set>
<c:set var="cancelActionCreate" scope="page"
	value="${contextPath}/topo/l/1"></c:set>
<c:set var="cancelActionUpdate" scope="page"
	value="${contextPath}/topo/r/${id}/${delivry.attributes.topoForm.topo.slug}"></c:set>
<c:set var="cancelAction" scope="page"
	value="${action == 'c' ? cancelActionCreate : cancelActionUpdate}"></c:set>
<c:set var="formTitle" scope="page"
	value="${ action == 'c' ? 'Nouveau Topo' : 'Modifier le Topo'}"></c:set>
<c:set var="buttonLabel" scope="page"
	value="${ action == 'c' ? 'Créer' : 'Mettre à jour'}"></c:set>
<c:set var="fieldAccess" scope="page"
	value="${ action == 'u' ? ' readonly' : ''}"></c:set>


<c:set var="nameError" value="${form.errors.name}" scope="page"></c:set>
<c:set var="titleError" value="${form.errors.title}" scope="page"></c:set>
<c:set var="writerError" value="${form.errors.writer}" scope="page"></c:set>
<c:set var="writedAtError" value="${form.errors.writedAt}" scope="page"></c:set>
<c:set var="sitesError" value="${form.errors.sites}" scope="page"></c:set>
<c:set var="fileError" value="${form.errors.file}" scope="page"></c:set>
<c:set var="mediaError" value="${form.errors.media}" scope="page"></c:set>
<c:set var="summaryError" value="${form.errors.summary}" scope="page"></c:set>
<c:set var="contentError" value="${form.errors.content}" scope="page"></c:set>
<c:set var="publishedError" value="${form.errors.published}"
	scope="page"></c:set>
<c:set var="topoInformationError" scope="page"
	value="${ empty nameError && empty titleError && empty writerError && empty writedAtError && empty sitesError ? '' : 'tab-error'}">
</c:set>
<c:set var="topoMediasError" scope="page"
	value="${ empty fileError ? '' : 'tab-error'}"></c:set>
<c:set var="topoContentError" scope="page"
	value="${ empty contentError && empty summaryError ? '' : 'tab-error'}">
</c:set>

<form id="createTopoForm" name="createTopoForm"
	class="text-center border border-light p-3" action="${postAction}"
	method="POST" enctype="multipart/form-data">
	<!-- Formular header with title and submitt button -->
	<input type="hidden" name="topoId"
		value="${delivry.attributes.topoForm.topo.id}" />
	<div class="form-row mb-3">
		<h1 class="h2 text-center text-classic text-xxl py-2 col-lg-9">
			<c:out value="${ delivry.attributes.title }" />
		</h1>
		<div class="col-md-3">
			<button type="submit" class="btn btn-classic">${buttonLabel}</button>
			<button type="button" class="btn btn-danger"
				onclick="location='${cancelAction}'">Annuler</button>
		</div>
	</div>
	<!-- Tab headers -->
	<ul class="nav nav-tabs mb-3" id="topoTab" role="tablist">
		<li class="nav-item col-md-4"><a
			class="nav-link link-help active col-md-12" id="topoInformation-tab"
			data-toggle="tab" href="#topoInformation" role="tab"
			aria-controls="topoInformation" aria-selected="true"> <span
				class="${topoInformationError}">Informations globales</span>
		</a></li>
		<li class="nav-item col-md-4"><a
			class="nav-link link-help col-md-12" id="topoMedia-tab"
			data-toggle="tab" href="#topoMedia" role="tab"
			aria-controls="topoMedia" aria-selected="false"> <span
				class="${topoMediasError}">Medias</span>
		</a></li>
		<li class="nav-item col-md-4"><a
			class="nav-link link-help col-md-12" id="topoContent-tab"
			data-toggle="tab" href="#topoContent" role="tab"
			aria-controls="topoContent" aria-selected="false"> <span
				class="${topoContentError}">Contenu</span>
		</a></li>
	</ul>
	<!-- Tabs content -->
	<div class="tab-content" id="myTabContent">
		<!-- First panel -->
		<div class="tab-pane fade show active" id="topoInformation"
			role="tabpanel" aria-labelledby="topoInformation-tab">
			<!-- First row with Topo Title -->
			<div class="form-row">
				<div class="form-group col-md-12">
					<label class="basic-top-label" for="title"> <em>Titre
							du topo </em><span class="required">*</span><span class="ml-2"><img
							alt="Icone aide" src="${helpIcon}" width="20" height="20"
							title="${form.help.title}" /></span>
					</label> <input type="text" id="title" name="title"
						class="form-control${empty titleError?'':' is-invalid'}"
						aria-label="Title" aria-describedby="labelTitle"
						placeholder="Saisir le titre du topo"
						value="<c:out value='${delivry.attributes.topoForm.topo.title}'></c:out>">
					<div class="invalid-feedback${empty titleError?' invisible':''}"
						id="titleError">${titleError}</div>
				</div>
			</div>
			<!-- Second row with Writer name and writing date -->
			<div class="form-row">
				<div class="form-group col-md-9">
					<label class="basic-top-label" for="writer"> <em>Nom
							de l'auteur </em><span class="required">*</span><span class="ml-2"><img
							alt="Icone aide" src="${helpIcon}" width="20" height="20"
							title="${form.help.writer}" /></span>
					</label> <input type="text" id="writer" name="writer"
						class="form-control${empty writerError?'':' is-invalid'}"
						aria-label="Writer" aria-describedby="labelWriter"
						placeholder="Saisir le nom de l'écrivain..."
						value="<c:out value='${delivry.attributes.topoForm.topo.writer}'></c:out>">
					<div class="invalid-feedback${empty writerError?' invisible':''}"
						id="writerError">${writerError}</div>
				</div>
				<div class="form-group col-md-3">
					<label class="basic-top-label" for="writedAt"> <em>Publié
							le </em><span class="required">*</span><span class="ml-2"><img
							alt="Icone aide" src="${helpIcon}" width="20" height="20"
							title="${form.help.writedAt}" /></span>
					</label> <input type="text" id="writedAt" name="writedAt"
						class="form-control${empty writedAtError?'':' is-invalid'}"
						aria-label="WritedAt" aria-describedby="labelWritedAt"
						placeholder="jj/mm/aaaa"
						value="<c:out value='${delivry.attributes.topoForm.topo.writedAt}'></c:out>">
					<div class="invalid-feedback${empty writedAtError?' invisible':''}"
						id="writedAtError">${writedAtError}</div>
				</div>
			</div>
			<!-- third row with multiselect box to choose attached sites -->
			<div class="form-row">
				<div class="form-group col-md-2"></div>
				<div class="form-group col-md-8">
					<label class="basic-top-label" for="sites"> <em>Selectionner
							le(s) site(s) à rattacher à ce topo </em><span class="required">*</span><span
						class="ml-2"><img alt="Icone aide" src="${helpIcon}"
							width="20" height="20" title="${form.help.sites}" /></span>
					</label> <select multiple id="sites" name="sites"
						class="form-control${empty sitesError?'':' is-invalid'}"
						aria-label="Sites" aria-describedby="labelSites">
						<c:if test="${ fn:length(delivry.attributes.sites) gt 0 }">
							<c:set var="selectedIds"
								value="${delivry.attributes.topoForm.selectedIds}" scope="page"></c:set>
							<c:forEach items="${ delivry.attributes.sites }" var="site">
								<c:set var="idAsString">${site.id}</c:set>
								<option value="${ site.id }" ${selectedIds[idAsString]}>
									${ site.name}</option>
							</c:forEach>
						</c:if>
					</select>
					<div class="invalid-feedback${empty sitesError?' invisible':''}"
						id="sitesError">${sitesError}</div>
				</div>
				<div class="form-group col-md-2"></div>
			</div>
		</div>
		<div class="tab-pane fade" id="topoMedia" role="tabpanel"
			aria-labelledby="topoMedia-tab">
			<div class="file-panel pt-5">
				<input type="hidden" id="filename" name="filename" value=${ form.image } />
				<c:if test="${ ! empty form.image }">
					<div class="input-group">
						<div class="input-group mb-3">
							<div class="input-group-prepend">
								<span class="input-group-text" id="displayFileName">Nom
									du Fichier</span>
							</div>
							<input type="text" class="form-control" value="${ form.image }"
								aria-label="displayFileName" aria-describedby="displayFileName"
								readonly>
						</div>
					</div>
				</c:if>
				<div class="form-group">
					<label class="basic-top-label" for="uploadFile">Photo(s)<span class="ml-2"><img
							alt="Icone aide" src="${helpIcon}" width="20" height="20"
							title="${form.help.image}" /></span></label> <input type="file" id="uploadFile"
						name="uploadFile"
						class="form-control-file${empty fileError?'':' is-invalid'}">
					<div class="invalid-feedback${empty fileError?' invisible':''}"
						id="fileError">${fileError}</div>
				</div>
				<input type="hidden" id="partMethod" name="partMethod" value="false">
			</div>
		</div>
		<div class="tab-pane fade" id="topoContent" role="tabpanel"
			aria-labelledby="topoContent-tab">
			<div class="form-group">
				<label class="basic-top-label" for="summary">Résumé<span class="ml-2"><img
							alt="Icone aide" src="${helpIcon}" width="20" height="20"
							title="${form.help.summary}" /></span></label>
				<textarea id="summary" name="summary" rows="1"
					class="form-control${empty summaryError?'':' is-invalid'}"><c:out
						value='${delivry.attributes.topoForm.topo.summary}'></c:out></textarea>
				<div class="invalid-feedback${empty summaryError?' invisible':''}"
					id="summaryError">${summaryError}</div>
			</div>
			<div class="form-group">
				<label class="basic-top-label" for="content">Contenu<span class="ml-2"><img
							alt="Icone aide" src="${helpIcon}" width="20" height="20"
							title="${form.help.content}" /></span></label>
				<textarea id="content" name="content" rows="6"
					class="form-control${empty contentError?'':' is-invalid'}"><c:out
						value='${delivry.attributes.topoForm.topo.content}'></c:out></textarea>
				<div class="invalid-feedback${empty contentError?' invisible':''}"
					id="contentError">${contentError}</div>
			</div>

		</div>
	</div>
</form>