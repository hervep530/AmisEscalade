<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
					<div id="debugGroup" class="text-center border border-light p-3">
					    <p class="h4 mb-4">Debug</p>
						<div class="row mb-3">
							<div class="input-group col-sm-6 col-md-3">
								<div class="input-group mb-3">
									<div class="input-group-prepend">
										<span class="input-group-text" id="inputGroup-sizing-default">Service</span>
									</div>
									<input type="text" class="form-control" value="${ delivry.parameters.parsedUrl.serviceAlias }"
										aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default" readonly>
								</div>
							</div>
							<div class="input-group col-sm-6 col-md-3">
								<div class="input-group mb-3">
									<div class="input-group-prepend">
										<span class="input-group-text" id="inputGroup-sizing-default">Action</span>
									</div>
									<input type="text" class="form-control" value="${ delivry.parameters.parsedUrl.action }"
										aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default" readonly>
								</div>
							</div>
							<div class="input-group col-sm-6 col-md-3">
								<div class="input-group mb-3">
									<div class="input-group-prepend">
										<span class="input-group-text" id="inputGroup-sizing-default">Id</span>
									</div>
									<input type="text" class="form-control" value="${ delivry.parameters.parsedUrl.id }"
										aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default" readonly>
								</div>
							</div>
							<div class="input-group col-sm-6 col-md-3">
								<div class="input-group mb-3">
									<div class="input-group-prepend">
										<span class="input-group-text" id="inputGroup-sizing-default">Slug</span>
									</div>
									<input type="text" class="form-control" value="${ delivry.parameters.parsedUrl.slug }"
										aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default" readonly>
								</div>
							</div>
						</div>
					</div>