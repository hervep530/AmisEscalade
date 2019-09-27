<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
					<div id="debugGroup" class="text-center border border-light p-3">
					    <p class="h4 mb-4">Debug - ParseUrl</p>
						<div class="row mb-3">
							<div class="input-group col-sm-6 col-md-3">
								<div class="input-group mb-3">
									<div class="input-group-prepend">
										<span class="input-group-text" id="inputService">Service</span>
									</div>
									<input type="text" class="form-control" value="${ delivry.parameters.parsedUrl.serviceAlias }"
										aria-label="Service" aria-describedby="inputService" readonly>
								</div>
							</div>
							<div class="input-group col-sm-6 col-md-3">
								<div class="input-group mb-3">
									<div class="input-group-prepend">
										<span class="input-group-text" id="inputAction">Action</span>
									</div>
									<input type="text" class="form-control" value="${ delivry.parameters.parsedUrl.action }"
										aria-label="Action" aria-describedby="inputAction" readonly>
								</div>
							</div>
							<div class="input-group col-sm-6 col-md-3">
								<div class="input-group mb-3">
									<div class="input-group-prepend">
										<span class="input-group-text" id="inputId">Id</span>
									</div>
									<input type="text" class="form-control" value="${ delivry.parameters.parsedUrl.id }"
										aria-label="Id" aria-describedby="inputId" readonly>
								</div>
							</div>
							<div class="input-group col-sm-6 col-md-3">
								<div class="input-group mb-3">
									<div class="input-group-prepend">
										<span class="input-group-text" id="inputSlug">Slug</span>
									</div>
									<input type="text" class="form-control" value="${ delivry.parameters.parsedUrl.slug }"
										aria-label="Slug" aria-describedby="inputSlug" readonly>
								</div>
							</div>
							<div class="input-group col-sm-6 col-md-6">
								<div class="input-group mb-3">
									<div class="input-group-prepend">
										<span class="input-group-text" id="inputUri">Uri</span>
									</div>
									<input type="text" class="form-control" value="${ uri }"
										aria-label="Uri" aria-describedby="inputUri" readonly>
								</div>
							</div>
						</div>
					</div>
					<div id="debugGroup2" class="text-center border border-light p-3">
					    <p class="h4 mb-4">Debug - User</p>
						<div class="row mb-3">
							<div class="input-group col-sm-6 col-md-3">
								<div class="input-group mb-3">
									<div class="input-group-prepend">
										<span class="input-group-text" id="inputGroup-sizing-default">User Id</span>
									</div>
									<input type="text" class="form-control" value="${ userId }"
										aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default" readonly>
								</div>
							</div>
							<div class="input-group col-sm-6 col-md-3">
								<div class="input-group mb-3">
									<div class="input-group-prepend">
										<span class="input-group-text" id="inputGroup-sizing-default">Role Id</span>
									</div>
									<input type="text" class="form-control" value="${ roleId }"
										aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default" readonly>
								</div>
							</div>
							<div class="input-group col-sm-6 col-md-3">
								<div class="input-group mb-3">
									<div class="input-group-prepend">
										<span class="input-group-text" id="inputGroup-sizing-default">Author Id</span>
									</div>
									<input type="text" class="form-control" value="${ authorId }"
										aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default" readonly>
								</div>
							</div>
							<div class="input-group col-sm-6 col-md-3">
								<div class="input-group mb-3">
									<div class="input-group-prepend">
										<span class="input-group-text" id="inputGroup-sizing-default">Site Name</span>
									</div>
									<input type="text" class="form-control" value="${ delivry.attributes.site.name }"
										aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default" readonly>
								</div>
							</div>
						</div>
					</div>
					