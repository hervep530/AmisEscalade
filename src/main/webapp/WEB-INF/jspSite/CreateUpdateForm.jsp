<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<c:set var="siteId" scope="page" value="${delivry.attributes.siteForm.site.id}"></c:set>
<c:set var="postAction" scope="page" value="${contextPath}/site/${action}/${siteId gt 0 ? siteId : (id ? id : 0)}/${token}"></c:set>
<c:set var="labelAction" scope="page" value="${action == 'u' ? 'Mettre à jour' : 'Créer'}"></c:set>
<c:set var="image" value="${delivry.attributes.siteForm.image}" scope="page"></c:set>
<c:set var="nameError" value="${delivry.attributes.siteForm.errors.name}" scope="page"></c:set>
<c:set var="countryError" value="${delivry.attributes.siteForm.errors.country}" scope="page"></c:set>
<c:set var="departmentError" value="${delivry.attributes.siteForm.errors.department}" scope="page"></c:set>
<c:set var="pathsNumberError" value="${delivry.attributes.siteForm.errors.pathsNumber}" scope="page"></c:set>
<c:set var="orientationError" value="${delivry.attributes.siteForm.errors.orientation}" scope="page"></c:set>
<c:set var="typeError" value="${delivry.attributes.siteForm.errors.type}" scope="page"></c:set>
<c:set var="minHeightError" value="${delivry.attributes.siteForm.errors.minHeight}" scope="page"></c:set>
<c:set var="maxHeightError" value="${delivry.attributes.siteForm.errors.maxHeight}" scope="page"></c:set>
<c:set var="cotationMinError" value="${delivry.attributes.siteForm.errors.cotationMin}" scope="page"></c:set>
<c:set var="cotationMaxError" value="${delivry.attributes.siteForm.errors.cotationMax}" scope="page"></c:set>
<c:set var="fileError" value="${delivry.attributes.siteForm.errors.file}" scope="page"></c:set>
<c:set var="summaryError" value="${delivry.attributes.siteForm.errors.summary}" scope="page"></c:set>
<c:set var="mediaError" value="${delivry.attributes.siteForm.errors.media}" scope="page"></c:set>
<c:set var="contentError" value="${delivry.attributes.siteForm.errors.content}" scope="page"></c:set>
<c:set var="publishedError" value="${delivry.attributes.siteForm.errors.published}" scope="page"></c:set>
<c:set var="siteInformationError" scope="page"
	value="${ empty nameError && empty countryError && empty departmentError && empty pathsNumberError &&
		empty typeError && empty minHeightError && empty maxHeightError && empty cotationMinError && 
		empty cotationMaxError ? '' : 'tab-error'}">
</c:set>
<c:set var="siteMediasError" scope="page" value="${ empty fileError ? '' : 'tab-error'}"></c:set>
<c:set var="siteContentError" scope="page" 
	value="${ empty contentError && empty summaryError ? '' : 'tab-error'}">
</c:set>
    
					<form id="siteForm" class="text-center border border-light p-3" 
							action="${postAction}" method="POST" enctype="multipart/form-data">
							<input type="hidden" name="siteId" value="${delivry.attributes.siteForm.site.id}"/>
							<div class="form-row mb-3">
								<div class="col-md-9"><p class="h4">Nouveau site d'escalade</p></div>
								<div class="col-md-3">
							  		<button type="submit" class="btn btn-primary">${labelAction}</button>
								</div>
								
							</div>
					    
<ul class="nav nav-tabs mb-3" id="siteTab" role="tablist">
  <li class="nav-item col-md-4">
    <a class="nav-link active col-md-12" id="siteInformation-tab" data-toggle="tab" href="#siteInformation" role="tab" aria-controls="siteInformation" aria-selected="true">
    	<span class="${siteInformationError}">Informations globales</span>
    </a>
  </li>
  <li class="nav-item col-md-4">
    <a class="nav-link col-md-12" id="siteMedia-tab" data-toggle="tab" href="#siteMedia" role="tab" aria-controls="siteMedia" aria-selected="false">
    	<span class="${siteMediasError}">Medias</span>
    </a>
  </li>
  <li class="nav-item col-md-4">
    <a class="nav-link col-md-12" id="siteContent-tab" data-toggle="tab" href="#siteContent" role="tab" aria-controls="siteContent" aria-selected="false">
    	<span class="${siteContentError}">Contenu</span>
    </a>
  </li>
</ul>
<div class="tab-content" id="myTabContent">
  <div class="tab-pane fade show active" id="siteInformation" role="tabpanel" aria-labelledby="siteInformation-tab">
						<div class="form-row">
	    				    <div class="form-group col-md-4">
					    		<label class="basic-top-label" for="name">
					    			<em>Nom du site </em><span class="required">*</span>
					    		</label>
								<input type="text" id="name" name="name"
										class="form-control${empty nameError?'':' is-invalid'}" 
										aria-label="Name" aria-describedby="labelName"
										placeholder="Saisir le nom du site..."
										value="<c:out value='${delivry.attributes.siteForm.site.name}'></c:out>">
								<div class="invalid-feedback${empty nameError?' invisible':''}"
									id="nameError">${nameError}</div>
					    	</div>
	    				    <div class="form-group col-md-4">
					    		<label class="basic-top-label" for="country">
					    			<em>Pays </em><span class="required">*</span>
					    		</label>
								<input type="text" id="country" name="country"
										class="form-control${empty countryError?'':' is-invalid'}" 
										aria-label="Country" aria-describedby="labelCountry"
										placeholder="Saisir le pays..."
										value="<c:out value='${delivry.attributes.siteForm.site.country}'></c:out>">
								<div class="invalid-feedback${empty countryError?' invisible':''}"
									id="countryError">${countryError}</div>
					    	</div>
	    				    <div class="form-group col-md-4">
					    		<label class="basic-top-label" for="department">
					    			<em>Departement / Region </em><span class="required">*</span>
					    		</label>
								<input type="text" id="department" name="department"
										class="form-control${empty departmentError?'':' is-invalid'}" 
										aria-label="Department" aria-describedby="labelDepartment"
										placeholder="Saisir le department..."
										value="<c:out value='${delivry.attributes.siteForm.site.department}'></c:out>">
								<div class="invalid-feedback${empty departmentError?' invisible':''}"
									id="departmentError">${departmentError}</div>
					    	</div>
					    </div>
						<div class="form-row">
	    				    <div class="form-group col-md-3">
					    		<label class="basic-top-label" for="pathsNumber">
					    			<em>Nombre de voies </em><span class="required">*</span>
					    		</label>
								<input type="number" id="pathsNumber" name="pathsNumber" min="0" max="2000"
										class="form-control${empty pathsNumberError?'':' is-invalid'}" 
										aria-label="pathsNumber" aria-describedby="labelpathsNumber"
										placeholder="Saisir le nombre"
										value="<c:out value='${delivry.attributes.siteForm.site.pathsNumber}'></c:out>">
								<div class="invalid-feedback${empty pathsNumberError?' invisible':''}"
									id="pathsNumberError">${pathsNumberError}</div>
					    	</div>
	    				    <div class="form-group col-md-3">
					    		<label class="basic-top-label" for="orientation">
					    			<em>Exposition </em><span class="required">*</span>
					    		</label>
								<input type="text" id="orientation" name="orientation"
										class="form-control${empty orientationError?'':' is-invalid'}" 
										aria-label="Orientation" aria-describedby="labelOrientation"
										placeholder="Indiquez l'orientation..."
										value="<c:out value='${delivry.attributes.siteForm.site.orientation}'></c:out>">
								<div class="invalid-feedback${empty orientationError?' invisible':''}"
									id="orientationError">${orientationError}</div>
					    	</div>
	    				    <div class="form-group form-check-group col-md-6">
					    		<div class="basic-top-label">
					    			<em>Type d'escalade </em><span class="required">*</span>
					    		</div>
					    		<div class="form-control${empty typeError?'':' is-invalid'}">
								<div class="form-check form-check-inline">
									<input type="checkbox" id="block" name="block"
										class="form-check-input" value="true"
										${delivry.attributes.siteForm.site.block ? 'checked' : ''}>
									<label class="form-check-label" for="block">Bloc</label>
								</div>
								<div class="form-check form-check-inline">
									<input type="checkbox" id="cliff" name="cliff"
										class="form-check-input" value="true"
										${delivry.attributes.siteForm.site.cliff ? 'checked' : ''}>
									<label class="form-check-label" for="cliff">Falaise</label>
								</div>
								<div class="form-check form-check-inline">
									<input type="checkbox" id="wall" name="wall"
										class="form-check-input" value="true"
										${delivry.attributes.siteForm.site.wall ? 'checked' : ''}>
									<label class="form-check-label" for="wall">Mur</label>
								</div>
					    		</div>
								<div class="invalid-feedback${empty typeError?' invisible':''}"
									id="typeError">${typeError}</div>
					    	</div>
						</div>
						<div class="form-row">
	    				    <div class="form-group col-md-3">
					    		<label class="basic-top-label" for="minHeight">
					    			<em>Hauteur de voie min </em><span class="required">*</span>
					    		</label>
								<input type="number" id="minHeight" name="minHeight" min="0" max="2000"
										class="form-control${empty minHeightError?'':' is-invalid'}" 
										aria-label="MinHeight" aria-describedby="labelMinHeight"
										placeholder="Indiquez le minimum..."
										value="<c:out value='${delivry.attributes.siteForm.site.minHeight}'></c:out>">
								<div class="invalid-feedback${empty minHeightError?' invisible':''}"
									id="minHeightError">${minHeightError}</div>
					    	</div>
	    				    <div class="form-group col-md-3">
					    		<label class="basic-top-label" for="maxHeight">
					    			<em>Hauteur de voie max </em><span class="required">*</span>
					    		</label>
								<input type="number" id="maxHeight" name="maxHeight" min="0" max="2000"
										class="form-control${empty maxHeightError?'':' is-invalid'}" 
										aria-label="MaxHeight" aria-describedby="labelMaxHeight"
										placeholder="Indiquez le maximum..."
										value="<c:out value='${delivry.attributes.siteForm.site.maxHeight}'></c:out>">
								<div class="invalid-feedback${empty maxHeightError?' invisible':''}"
									id="maxHeightError">${maxHeightError}</div>
					    	</div>
	    				    <div class="form-group col-md-3">
					    		<label class="basic-top-label" for="cotationMin">
					    			<em>Cotation min </em><span class="required">*</span>
					    		</label>
					    		<select id="cotationMin" name="cotationMin"
										class="custom-select${empty cotationMinError?'':' is-invalid'}" 
										aria-label="CotationMin" aria-describedby="labelCotationMin">
									<option value="0"${delivry.attributes.siteForm.site.cotationMin.id == 0 ? ' selected' : ''}>Choisir le minimum...</option>
<c:if test="${ fn:length(delivry.attributes.cotations) gt 0 }">
	<c:forEach items="${ delivry.attributes.cotations }" var="cotation">
									<option value="${ cotation.id }"${delivry.attributes.siteForm.site.cotationMin.id == cotation.id ? ' selected' : ''}>${ cotation.label }</option>
	</c:forEach>
</c:if>								
								</select>
								<div class="invalid-feedback${empty cotationMinError?' invisible':''}"
									id="cotationMinError">${cotationMinError}</div>
					    	</div>
	    				    <div class="form-group col-md-3">
					    		<label class="basic-top-label" for="cotationMax">
					    			<em>Cotation max </em><span class="required">*</span>
					    		</label>
					    		<select id="cotationMax" name="cotationMax"
										class="custom-select${empty cotationMaxError?'':' is-invalid'}" 
										aria-label="CotationMax" aria-describedby="labelCotationMax">
									<option value="0"${delivry.attributes.siteForm.site.cotationMax.id == 0 ? ' selected' : ''}>Choisir le maximum...</option>
<c:if test="${ fn:length(delivry.attributes.cotations) gt 0 }">
	<c:forEach items="${ delivry.attributes.cotations }" var="cotation">
									<option value="${ cotation.id }"${delivry.attributes.siteForm.site.cotationMax.id == cotation.id ? ' selected' : ''}>${ cotation.label }</option>
	</c:forEach>
</c:if>								
								</select>
								<div class="invalid-feedback${empty cotationMaxError?' invisible':''}"
									id="cotationMaxError">${cotationMaxError}</div>
					    	</div>
					    </div>
  </div>
  <div class="tab-pane fade" id="siteMedia" role="tabpanel" aria-labelledby="siteMedia-tab">
  
  			<div class="file-panel">
  				<input type="hidden" id="filename" name="filename" value=${ image }/>
<c:if test="${ ! empty image }">
				<div class="input-group">
					<div class="input-group mb-3">
						<div class="input-group-prepend">
							<span class="input-group-text" id="displayFileName">Nom du Fichier</span>
						</div>
						<input type="text" class="form-control" 
							value="${ image }"
							aria-label="displayFileName" aria-describedby="displayFileName" readonly>
					</div>
				</div>
</c:if>
				<div class="form-group">
					<label class="basic-top-label" for="uploadFile">Photo(s)</label>
					<input type="file" id="uploadFile" name="uploadFile"
					 class="form-control-file${empty fileError?'':' is-invalid'}">
					<div class="invalid-feedback${empty fileError?' invisible':''}" id="fileError">
						${fileError}
					</div>
				</div>
				<input type="hidden" id="partMethod" name="partMethod" value="false">
  			</div>
  </div>
  <div class="tab-pane fade" id="siteContent" role="tabpanel" aria-labelledby="siteContent-tab">
						<div class="form-group">
							<label class="basic-top-label" for="summary">Résumé</label>
							<textarea id="summary" name="summary" rows="1"
								 class="form-control${empty summaryError?'':' is-invalid'}"
								 ><c:out value='${delivry.attributes.siteForm.site.summary}'></c:out></textarea>
							<div class="invalid-feedback${empty summaryError?' invisible':''}"
								id="summaryError">${summaryError}</div>
						</div>
						<div class="form-group">
							<label class="basic-top-label" for="content">Contenu</label>
							<textarea id="content" name="content" rows="6"
								 class="form-control${empty contentError?'':' is-invalid'}"
								 ><c:out value='${delivry.attributes.siteForm.site.content}'></c:out></textarea>
							<div class="invalid-feedback${empty contentError?' invisible':''}"
								id="contentError">${contentError}</div>
						</div>

  </div>
</div>
</form>