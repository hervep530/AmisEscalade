package com.ocherve.jcm.service.factory;

import javax.servlet.http.HttpServletRequest;

import com.ocherve.jcm.service.Delivry;
import com.ocherve.jcm.service.Parameters;
import com.ocherve.jcm.service.UrlException;

public interface Service {
	
	Parameters setParameters(HttpServletRequest request) throws UrlException;
	Delivry doGetAction(Parameters parameters) throws UrlException; 
	Delivry doPostAction(Parameters parameters) throws UrlException;
	Delivry abort(Parameters parameters);

}
