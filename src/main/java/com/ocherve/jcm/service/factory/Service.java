package com.ocherve.jcm.service.factory;

import javax.servlet.http.HttpServletRequest;

import com.ocherve.jcm.model.User;
import com.ocherve.jcm.service.AccessLevel;
import com.ocherve.jcm.service.Delivry;
import com.ocherve.jcm.service.Parameters;

/**
 * @author herve_dev
 * 
 * Generic Service Interface : provide generic way of working for each service
 *   call from ServiceProxy
 */
public interface Service {
	
	/**
	 * Get all parameters needed by services from a servlet request
	 * 
	 * @param request			HttpServletRequest : request matching with uri called by end user
	 * @return					Parameters : object containing all data needed by services to define action
	 * 											and process it
	 */
	Parameters setParameters(HttpServletRequest request);
	
	/**
	 * @param parameters
	 * @return accessLevel
	 */
	AccessLevel checkSecurity(Parameters parameters);
	
	/**
	 * Processing action matching with doGet call in HttpServlet
	 * 
	 * @param parameters		Parameters : get from HttpServletRequest and provides all information to process action
	 * @return					Delivry : result of action processing (contains object needed by jsp)
	 */
	Delivry doGetAction(Parameters parameters); 
	
	/**
	 * Processing action matching with doPost call in HttpServlet
	 * 
	 * @param parameters		Parameters : get from HttpServletRequest and provides all information to process action
	 * @return					Delivry : result of action processing (contains object needed by jsp)
	 */
	Delivry doPostAction(Parameters parameters);

	/**
	 * Processing recovery action in HttpServlet if doGetAction or doPostAction failed
	 * 
	 * @param parameters		Parameters : get from HttpServletRequest and provides all information to process action
	 * @return					Delivry : result of action processing (contains object needed by jsp)
	 */
	Delivry abort(Parameters parameters);
	
	/**
	 * Building a generic delivry object from parameters
	 * 
	 * @param parameters
	 */
	void appendMandatoryAttributesToDelivry(Parameters parameters);
	
	/**
	 * @return anonymous user if not connected
	 */
	User openAnonymousSession();

}
