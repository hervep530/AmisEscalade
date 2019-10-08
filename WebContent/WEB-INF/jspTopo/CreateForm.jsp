<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<c:set var="postAction" scope="page" value="${contextPath}/topo/c/0/${token}"></c:set>
<c:set var="nameError" value="${delivry.attributes.createTopoForm.errors.name}" scope="page"></c:set>
<c:set var="titleError" value="${delivry.attributes.createTopoForm.errors.title}" scope="page"></c:set>
<c:set var="writerError" value="${delivry.attributes.createTopoForm.errors.writer}" scope="page"></c:set>
<c:set var="writedAtError" value="${delivry.attributes.createTopoForm.errors.writedAt}" scope="page"></c:set>
<c:set var="sitesError" value="${delivry.attributes.createTopoForm.errors.sites}" scope="page"></c:set>
<c:set var="fileError" value="${delivry.attributes.createTopoForm.errors.file}" scope="page"></c:set>
<c:set var="mediaError" value="${delivry.attributes.createTopoForm.errors.media}" scope="page"></c:set>
<c:set var="summaryError" value="${delivry.attributes.createTopoForm.errors.summary}" scope="page"></c:set>
<c:set var="contentError" value="${delivry.attributes.createTopoForm.errors.content}" scope="page"></c:set>
<c:set var="publishedError" value="${delivry.attributes.createTopoForm.errors.published}" scope="page"></c:set>
<c:set var="topoInformationError" scope="page"
	value="${ empty nameError && empty titleError && empty writerError && empty writedAtError && empty sitesError ? '' : 'tab-error'}">
</c:set>
<c:set var="topoMediasError" scope="page" value="${ empty fileError ? '' : 'tab-error'}"></c:set>
<c:set var="topoContentError" scope="page" 
	value="${ empty contentError && empty summaryError ? '' : 'tab-error'}">
</c:set>
    
					<form id="createTopoForm" name="createTopoForm" class="text-center border border-light p-3" 
							action="${postAction}" method="POST" enctype="multipart/form-data">
							<div class="form-row mb-3">
								<div class="col-md-9"><p class="h4">Nouveau Topo</p></div>
								<div class="col-md-3">
							  		<button type="submit" class="btn btn-primary">Créer</button>
								</div>
								
							</div>
					    
<ul class="nav nav-tabs mb-3" id="topoTab" role="tablist">
  <li class="nav-item col-md-4">
    <a class="nav-link active col-md-12" id="topoInformation-tab" data-toggle="tab" href="#topoInformation" role="tab" aria-controls="topoInformation" aria-selected="true">
    	<span class="${topoInformationError}">Informations globales</span>
    </a>
  </li>
  <li class="nav-item col-md-4">
    <a class="nav-link col-md-12" id="topoMedia-tab" data-toggle="tab" href="#topoMedia" role="tab" aria-controls="topoMedia" aria-selected="false">
    	<span class="${topoMediasError}">Medias</span>
    </a>
  </li>
  <li class="nav-item col-md-4">
    <a class="nav-link col-md-12" id="topoContent-tab" data-toggle="tab" href="#topoContent" role="tab" aria-controls="topoContent" aria-selected="false">
    	<span class="${topoContentError}">Contenu</span>
    </a>
  </li>
</ul>
<div class="tab-content" id="myTabContent">
  <div class="tab-pane fade show active" id="topoInformation" role="tabpanel" aria-labelledby="topoInformation-tab">
						<div class="form-row">
	    				    <div class="form-group col-md-12">
					    		<label class="basic-top-label" for="title">
					    			<em>Titre du topo </em><span class="required">*</span>
					    		</label>
								<input type="text" id="title" name="title"
										class="form-control${empty titleError?'':' is-invalid'}" 
										aria-label="Title" aria-describedby="labelTitle"
										placeholder="Saisir le titre du topo"
										value="<c:out value='${delivry.attributes.createTopoForm.topo.title}'></c:out>">
								<div class="invalid-feedback${empty titleError?' invisible':''}"
									id="titleError">${titleError}</div>
					    	</div>
					    </div>
						<div class="form-row">
	    				    <div class="form-group col-md-9">
					    		<label class="basic-top-label" for="writer">
					    			<em>Nom de l'auteur </em><span class="required">*</span>
					    		</label>
								<input type="text" id="writer" name="writer"
										class="form-control${empty writerError?'':' is-invalid'}" 
										aria-label="Writer" aria-describedby="labelWriter"
										placeholder="Saisir le nom de l'écrivain..."
										value="<c:out value='${delivry.attributes.createTopoForm.topo.writer}'></c:out>">
								<div class="invalid-feedback${empty writerError?' invisible':''}"
									id="writerError">${writerError}</div>
					    	</div>
	    				    <div class="form-group col-md-3">
					    		<label class="basic-top-label" for="writedAt">
					    			<em>Publié le </em><span class="required">*</span>
					    		</label>
								<input type="text" id="writedAt" name="writedAt"
										class="form-control${empty writedAtError?'':' is-invalid'}" 
										aria-label="WritedAt" aria-describedby="labelWritedAt"
										placeholder="jj/mm/aaaa"
										value="<c:out value='${delivry.attributes.createTopoForm.topo.writedAt}'></c:out>">
								<div class="invalid-feedback${empty writedAtError?' invisible':''}"
									id="writedAtError">${writedAtError}</div>
					    	</div>
						</div>
						<div class="form-row">
					    	</div>
	    				    <div class="form-group col-md-2">
					    	</div>
	    				    <div class="form-group col-md-8">
					    		<label class="basic-top-label" for="sites">
					    			<em>Sites concernés par le topo </em><span class="required">*</span>
					    		</label>
								<select multiple id="sites" name="sites" 
										class="form-control${empty sitesError?'':' is-invalid'}" 
										aria-label="Sites" aria-describedby="labelSites">
									<option value="0">Selectionner le(s) site(s) à rattacher à ce topo</option>
<c:if test="${ fn:length(delivry.attributes.sites) gt 0 }">
	<c:forEach items="${ delivry.attributes.sites }" var="site">
									<option value="${ site.id }">${ site.name }</option>
	</c:forEach>
</c:if>								
								</select>
								<div class="invalid-feedback${empty sitesError?' invisible':''}"
									id="sitesError">${sitesError}</div>
					    	</div>
	    				    <div class="form-group col-md-2">
					    	</div>
						</div>
  </div>
  <div class="tab-pane fade" id="topoMedia" role="tabpanel" aria-labelledby="topoMedia-tab">
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
  <div class="tab-pane fade" id="topoContent" role="tabpanel" aria-labelledby="topoContent-tab">
						<div class="form-group">
							<label class="basic-top-label" for="summary">Résumé</label>
							<textarea id="summary" name="summary" rows="1"
								 class="form-control${empty summaryError?'':' is-invalid'}"
								 ><c:out value='${delivry.attributes.createTopoForm.topo.summary}'></c:out></textarea>
							<div class="invalid-feedback${empty summaryError?' invisible':''}"
								id="summaryError">${summaryError}</div>
						</div>
						<div class="form-group">
							<label class="basic-top-label" for="content">Contenu</label>
							<textarea id="content" name="content" rows="6"
								 class="form-control${empty contentError?'':' is-invalid'}"
								 ><c:out value='${delivry.attributes.createTopoForm.topo.content}'></c:out></textarea>
							<div class="invalid-feedback${empty contentError?' invisible':''}"
								id="contentError">${contentError}</div>
						</div>

  </div>
</div>
</form>