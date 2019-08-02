package com.ocherve.jcm.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import com.ocherve.jcm.service.Delivry;
import com.ocherve.jcm.service.Parameters;
import com.ocherve.jcm.service.ServiceException;
import com.ocherve.jcm.service.UrlException;
import com.ocherve.jcm.service.factory.Service;

/**
 * @author herve_dev
 * 
 * Abstract Service Class : Contains generic declaration and methods
 * In particularly it provides the way to instanciate service with checking actions
 * 
 */
public abstract class ServiceImpl implements Service {

	// Logger (default log level can be adjust in each service impl
    protected static final Logger SLOG = LogManager.getLogger("support_file");
    protected static final Logger DLOG = LogManager.getLogger("development_file");
    private static final Level SLOGLEVEL = Level.ERROR;
    private static final Level DLOGLEVEL = Level.TRACE;
    
	// Persistent variable because each service are initialized once and keep as cache in proxy
    private String serviceName;
	private String servicePattern;
	@SuppressWarnings("unused")
	private String defaultUrl;
	private Map<String,String> actions;
	// Not persistent : set to null for garbage collector after init
    private Map<String, String> errors;
    private String[] servletPaths;

	/**
	 *  
	 * Constructor : check Service Proxy usage requirement.
	 * Should be call for each service impl - check error on servlet and service
	 * 
	 * Mandatory for each service :
	 *  - Class name is $servletName + "ServiceImpl"
	 *  - paths in servlet must match with (@WebServlet declaration) must be "/" + $servleName.toLowerCase + "/.*"
	 *  - action paths need to match with one of @WebServlet Path
	 * Application need a Default servlet with a default path "/" and matching service DefaultServiceImpl 
	 *     contains empty action path {"empty","/"}
	 * 
	 * @param defaultUrl  : String to define fallback url for this service (unused)
	 * @param actions  : String[][] to define list of {actionName, actionUrlPattern}
	 */
	public ServiceImpl(String defaultUrl, String[][] actions) {
		// Set all Service Properties
		Configurator.setLevel(SLOG.getName(), SLOGLEVEL);
		Configurator.setLevel(DLOG.getName(), DLOGLEVEL);
		this.serviceName = this.getClass().getSimpleName().replaceAll("ServiceImpl", "");
		this.servicePattern = this.serviceName.toLowerCase();
		this.defaultUrl = defaultUrl;
		this.actions = new HashMap<>();
		for ( int a = 0; a < actions.length; a++ ) {
			this.actions.put(actions[a][0], actions[a][1]);
		}
	    // Analyse Service Class definition and log errors
        this.errors = new HashMap<>();
        this.setServletPaths();
        //this.validateServletPaths();
        if ( this.errors.isEmpty() ) this.validateServicePaths();
        if ( ! this.errors.isEmpty() ) 
        	throw new ServiceException("Service " + this.serviceName + " can not be instanciated.");

        // Unused variable set to null for garbage collection
        this.errors = null;
        this.servletPaths = null;
 		String info = "Service " + this.serviceName + " is now instanciated.";
		DLOG.log(Level.INFO , info);
	}

    /**
     * Call from constructor to set servletPath
     * uses ServletChecker which throws ServiceException
     */
	private void setServletPaths() {
    	try {
    		this.servletPaths = ServletChecker.getAnnotationPaths(this.serviceName);
    		ServletChecker.validatePathsCount(this.serviceName, this.servletPaths);
    	} catch (ServiceException e) {
    		this.errors.put(this.serviceName + "Servlet", e.getMessage());
    	}
    	if ( ! this.errors.isEmpty() ) return;
		for (int p=0; p < this.servletPaths.length; p++) {
			try {
	 			ServletChecker.validatePath(this.serviceName, this.servletPaths[p]);
			} catch (ServiceException e) {
				this.errors.put(this.serviceName + "ServletPath" + p, e.getMessage());
			}
		}
    	if ( ! this.errors.isEmpty() ) return;
		// Check if Default Service contains the empty action path
		if (this.serviceName.contentEquals("Default")) {
			try {
				ServletChecker.hasEmptyPath(this.servletPaths);
			} catch (ServiceException e) {
				errors.put("DefaultService", e.getMessage());
			}
		}
    }

	/**
	 * Called from constructor to validate that Service paths matches with servlet paths
	 * uses ServiceChecker which throws ServiceException
	 */
	private void validateServicePaths() {
		// Method available only if this.servletPaths has at least one element
		if (this.servletPaths == null) {
			return;
		}
		// For each action path, Check if it matches with one servlet Path (Call ServiceChecker method)
		this.actions.forEach((actionName, actionPath) -> {
			String message = "";
			if ( ! this.serviceName.contentEquals("Default") && actionName.contentEquals("empty") ) {
				message = "Only Default can contain empty action.";
				errors.put(this.serviceName + "Service_" + actionName, message);
			}
			try {
				ServiceChecker.validatePathFromServlet(this.servicePattern, actionPath, this.servletPaths);
			} catch (ServiceException e) {
				errors.put(this.serviceName + "Service_" + actionName, e.getMessage());
			}
		});
	}
	
	@Override
	public Parameters setParameters(HttpServletRequest request) {
		Parameters parameters = new Parameters();
		String context = request.getContextPath();
		String uri = (String) request.getAttribute("uri");
		
		// Global uri validation before using it with String tools 
		try {
			ServiceChecker.validateGlobalPatternUrl(context, this.serviceName, uri);
		} catch (UrlException e) {
			DLOG.log(Level.ERROR, serviceName + "- " + e.getMessage());
			parameters.appendError(serviceName + "ServiceParameters", e.getMessage());
		}

		// keeping only service / action / id / slug in uri
		uri = uri.replaceAll("^.*" + context , "").replaceAll("^/", "");
    	uri = uri.replaceAll(";(JSESSIONID|jsessionid)=\\w*", "").replaceAll("#\\w*", "");
    	ParsedUrl parsedUrl = UrlChecker.parseUrl(context, uri);
    	
    	try {
    		UrlChecker.validateAction(serviceName, actions, parsedUrl);
    	} catch (UrlException e) {
			parameters.appendError(serviceName + "ServiceParameters", e.getMessage());			    		
    	}

		parameters.setParsedUrl(parsedUrl);
		parameters.appendAllError(parsedUrl.getErrors());
		
		String info = "Service " + this.serviceName + " set parameters";
		DLOG.log(Level.INFO , info);

		return parameters;
	}

	@Override
	public Delivry doGetAction(Parameters parameters) {
		Delivry delivry = new Delivry();
		delivry.setParameters(parameters);
		if ( ! parameters.getErrors().isEmpty() ) delivry.setErrors(parameters.getErrors());
		String info = "Service " + this.serviceName + " do GetAction.";
		DLOG.log(Level.DEBUG , info);
		return delivry;
	}

	@Override
	public Delivry doPostAction(Parameters parameters) {
		Delivry delivry = new Delivry();
		String info = "Service " + this.serviceName + " do PostAction.";
		DLOG.log(Level.DEBUG , info);
		return delivry;
	}

	@Override
	public Delivry abort(Parameters parameters) {
		Delivry delivry = new Delivry();
		String info = "Service " + this.serviceName + " aborted.";
		DLOG.log(Level.DEBUG , info);
		return delivry;
	}

}
