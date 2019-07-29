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
import com.ocherve.jcm.service.UrlException;
import com.ocherve.jcm.service.factory.Service;

public abstract class ServiceImpl implements Service {

	// Logger (default log level can be adjust in each service impl
    public static final Logger DLOG = LogManager.getLogger("development_file");
    private static final Level LOGLEVEL = Level.TRACE;
    
	// Persistent variable because each service are initialized once and keep as cache in proxy
    private String serviceName;
	@SuppressWarnings("unused")
	private String servicePattern;
	@SuppressWarnings("unused")
	private String defaultUrl;
	private Map<String,String> actions;

	/**
	 * == Service Proxy usage ==
	 * Constructor : should be call for each service impl
	 * Mandatory for each service :
	 *  - Class name is $servletName + "ServiceImpl"
	 *  - paths in servlet must match with (@WebServlet declaration) must be "/" + $servleName.toLowerCase + "/.*"
	 *  - action paths need to match with one of @WebServlet Path
	 * Application need a Default servlet with a default path "/" or "/*" and matching service DefaultServiceImpl 
	 *     contains empty action path {"empty","/"}
	 * == End of usage instructions ==
	 * @param serviceName  : String (ClassName without "ServiceImpl" pattern : must be same as servlet name)
	 * @param servicePattern  : String used in paths as followed ("/" + servicePattern + "/.*")
	 * @param defaultUrl  : String to define fallback url for this service (unused)
	 * @param actions  : String[][] to define list of {actionName, actionUrlPattern}
	 */
	public ServiceImpl(String defaultUrl, String[][] actions) {
		// Set all Service Properties
		Configurator.setLevel(DLOG.getName(), LOGLEVEL);
		this.serviceName = this.getClass().getSimpleName().replaceAll("ServiceImpl", "");
		this.servicePattern = this.serviceName.toLowerCase();
		this.defaultUrl = defaultUrl;
		this.actions = new HashMap<>();
		for ( int a = 0; a < actions.length; a++ ) {
			this.actions.put(actions[a][0], actions[a][1]);
		}
		String info = "Service " + this.serviceName + " is now instanciated.";
		DLOG.log(Level.INFO , info);
	}

	@Override
	public Parameters setParameters(HttpServletRequest request) throws UrlException {
		String info = "Service " + this.serviceName + " set parameters";
		DLOG.log(Level.INFO , info);
		return null;
	}

	@Override
	public Delivry doGetAction(Parameters parameters) throws UrlException {
		String info = "Service " + this.serviceName + " do GetAction.";
		DLOG.log(Level.INFO , info);
		return null;
	}

	@Override
	public Delivry doPostAction(Parameters parameters) throws UrlException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Delivry abort(Parameters parameters) {
		// TODO Auto-generated method stub
		return null;
	}

}
