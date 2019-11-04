package com.ocherve.jcm.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import com.ocherve.jcm.model.User;
import com.ocherve.jcm.PropertyHelper;
import com.ocherve.jcm.dao.DaoProxy;
import com.ocherve.jcm.dao.contract.UserDao;
import com.ocherve.jcm.service.AccessLevel;
import com.ocherve.jcm.service.Delivry;
import com.ocherve.jcm.service.Notification;
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
    
	protected final static long LIST_LIMIT = Integer.valueOf(PropertyHelper.getProperty("jcm.list.limit"));

    
	// Persistent variable because each service are initialized once and keep as cache in proxy
	protected Delivry delivry;
    protected String serviceName;
    protected String servicePattern;
	protected String defaultUrl;
	protected String backUrl;
	private Map<String,String> actions;
	// Not persistent : set to null for garbage collector after init
    protected Map<String, String> errors;
    
    
    /**
     * Constructor
     */
    public ServiceImpl() {
    	super();
    }

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
	 */
	public ServiceImpl(String defaultUrl) {
		Configurator.setLevel(SLOG.getName(), SLOGLEVEL);
		Configurator.setLevel(DLOG.getName(), DLOGLEVEL);
		ServiceProperties serviceAttributes = null;
		try {
			serviceAttributes = ServiceHelper.getInstance(this.getClass());
		} catch (ServiceException e) {
			throw new ServiceException("Service " + this.serviceName + " can not be instanciated.");
		}
		// Set all Service Properties with ServiceHelper
		this.serviceName = serviceAttributes.getServiceName();
		this.servicePattern = serviceAttributes.getServicePattern();
		this.defaultUrl = defaultUrl;
		this.actions = serviceAttributes.getActions();
        this.errors = serviceAttributes.getErrors();
        
        //throw new ServiceException("Service " + this.serviceName + " can not be instanciated.");

 		String info = "Service " + this.serviceName + " is now instanciated.";
		DLOG.log(Level.INFO , info);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Parameters setParameters(HttpServletRequest request) {
		Parameters parameters = new Parameters();
		// Storing contextPath  and referer in parameters 
		String context = request.getContextPath();
		parameters.setContextPath(context);
		String referer = "";
		if ( request.getParameter("backUrl") != null ) this.backUrl = request.getParameter("backUrl");
		if ( request.getHeader("referer") != null ) referer = request.getHeader("referer");
		parameters.setReferer(referer);
		// Storing sessionUser in parameters
		User sessionUser = null;
		try {
			sessionUser = (User) request.getSession().getAttribute("sessionUser");
			parameters.setSessionUser(sessionUser);
			parameters.setToken((String) request.getSession().getAttribute("token"));
			parameters.setStaticToken((String) request.getSession().getAttribute("staticToken"));
		} catch (Exception e) {
			DLOG.log(Level.FATAL, serviceName + " - user not found in session" + e.getMessage());			
			sessionUser = openAnonymousSession();		
		}
		// Storing notifications (from session) in parameters 
		Map<String,Notification> notifications = new HashMap<>();
		try {
			if ( request.getAttribute("notifications") != null )
				notifications = (Map<String,Notification>) request.getAttribute("notifications");			
		} catch ( Exception e ) {
			DLOG.log(Level.ERROR, serviceName + " - no notification found" + e.getMessage());			
		}
		parameters.setNotifications(notifications); 		
		// Global uri validation before using it with String tools 
		DLOG.log(Level.INFO,"uri : " + (String) request.getAttribute("uri"));
		String uri = (String) request.getAttribute("uri");
		try {
			ServiceChecker.validateGlobalPatternUrl(context, this.serviceName, uri);
		} catch (UrlException e) {
			DLOG.log(Level.ERROR, "validateGlobalPatternUrl - " + e.getMessage());
			parameters.appendError(serviceName + "ServiceParameters", e.getMessage());
		}

		// keeping only service / action / id / slug in uri
		uri = uri.replaceAll("^.*" + context , "").replaceAll("^/", "");
    	uri = uri.replaceAll(";(JSESSIONID|jsessionid)=\\w*", "").replaceAll("#\\w*", "");
    	// CORRECTION BUG 190902 //     ParsedUrl parsedUrl = UrlChecker.parseUrl(context, uri);
    	ParsedUrl parsedUrl = UrlChecker.parseUrl(serviceName, uri);
    	
    	try {
    		UrlChecker.validateAction(serviceName, actions, parsedUrl);
    	} catch (UrlException e) {
			DLOG.log(Level.ERROR, "validateAction - " + e.getMessage());
			parameters.appendError(serviceName + "ServiceParameters", e.getMessage());			    		
    	}

		parameters.setParsedUrl(parsedUrl);
		parameters.appendAllError(parsedUrl.getErrors());
		
		String info = "Service" + this.serviceName + ".setParameters is done";
		DLOG.log(Level.INFO , info);

		return parameters;
	}

	@Override
	public Delivry doGetAction(Parameters parameters) {
		this.delivry = new Delivry();
		//this.delivry.setParameters(parameters);
		this.appendMandatoryAttributesToDelivry(parameters);
		String info = "ServiceGeneric.doGetAction is done.";
		DLOG.log(Level.DEBUG , info);
		return delivry;
	}

	@Override
	public Delivry doPostAction(Parameters parameters) {
		delivry = new Delivry();
		//this.delivry.setParameters(parameters);
		this.appendMandatoryAttributesToDelivry(parameters);
		String info = "ServiceGeneric.doPostAction is done.";
		DLOG.log(Level.DEBUG , info);
		return delivry;
	}

	@Override
	public Delivry abort(Parameters parameters) {
		this.delivry = new Delivry();
		this.delivry.setParameters(parameters);
		if ( ! parameters.getNotifications().isEmpty() ) this.delivry.appendNotifications(parameters.getNotifications());
		if ( ! parameters.getErrors().isEmpty() ) this.delivry.setErrors(parameters.getErrors());
		this.delivry.appendError(serviceName + "_" + parameters.getParsedUrl().getAction(), "message de l'erreur transmise en parametre de service.abort");
		String info = "Service" + this.serviceName + ".abort is done.";
		DLOG.log(Level.DEBUG , info);
		return this.delivry;
	}
	
	@Override
	public void appendMandatoryAttributesToDelivry(Parameters parameters) {
		if ( delivry == null ) this.delivry = new Delivry();
		this.delivry.setParameters(parameters);
		if ( ! parameters.getNotifications().isEmpty() ) this.delivry.appendNotifications(parameters.getNotifications());
		if ( ! parameters.getErrors().isEmpty() ) this.delivry.setErrors(parameters.getErrors());
	}
	
	@Override
	public AccessLevel checkSecurity(Parameters parameters) {
		return AccessLevel.DEFAULT;
	}
	
	@Override
	public User openAnonymousSession() {
		return ((UserDao) DaoProxy.getInstance().getUserDao()).get(1);
	}

}
