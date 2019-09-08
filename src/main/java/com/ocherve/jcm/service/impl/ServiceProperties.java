package com.ocherve.jcm.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import com.ocherve.jcm.service.ServiceException;

/**
 * @author herve_dev
 *
 */
public class ServiceProperties {

    private static final Logger DLOG = LogManager.getLogger("development_file");
    private static final Level DLOGLEVEL = Level.TRACE;

	private String serviceName;
	private String servicePattern;
    private String[] servletPaths;
	private Map<String,String> actions;
	private Map<String, String> errors;

	/**
	 * @param serviceClass
	 * @param serviceActions 
	 */
	public ServiceProperties(Class<?> serviceClass, String[][] serviceActions) {

		Configurator.setLevel(DLOG.getName(), DLOGLEVEL);
		serviceName = serviceClass.getSimpleName().replaceAll("ServiceImpl", "");
		DLOG.log(Level.DEBUG, "ServiceName : " + serviceName);
		servicePattern = serviceName.toLowerCase();
		actions = new HashMap<>();
		for ( int a = 0; a < serviceActions.length; a++ ) {
			actions.put(serviceActions[a][0], serviceActions[a][1]);
		}
	    // Analyse Service Class definition and log errors
        errors = new HashMap<>();
        setServletPaths();
        //validateServletPaths();
        if ( errors.isEmpty() ) validateServicePaths();
        if ( ! errors.isEmpty() ) 
        	throw new ServiceException("Service " + serviceName + " can not be instanciated.");

        // Unused variable set to null for garbage collection
        errors = null;
        servletPaths = null;
 		String info = serviceName + " properties are now initialized.";
		DLOG.log(Level.INFO , info);

	}

	/**
     * Call from constructor to set servletPath
     * uses ServletChecker which throws ServiceException
     */
	private void setServletPaths() {
    	try {
    		servletPaths = ServletChecker.getAnnotationPaths(serviceName);
    		ServletChecker.validatePathsCount(serviceName, servletPaths);
    	} catch (ServiceException e) {
    		errors.put(serviceName + "Servlet", e.getMessage());
    	}
    	if ( ! errors.isEmpty() ) return;
		for (int p=0; p < servletPaths.length; p++) {
			try {
	 			ServletChecker.validatePath(serviceName, servletPaths[p]);
			} catch (ServiceException e) {
				errors.put(serviceName + "ServletPath" + p, e.getMessage());
			}
		}
    	if ( ! errors.isEmpty() ) return;
		// Check if Default Service contains the empty action path
		if (serviceName.contentEquals("Default")) {
			try {
				ServletChecker.hasEmptyPath(servletPaths);
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
		// Method available only if servletPaths has at least one element
		if (servletPaths == null) {
			return;
		}
		// For each action path, Check if it matches with one servlet Path (Call ServiceChecker method)
		actions.forEach((actionName, actionPath) -> {
			String message = "";
			if ( ! serviceName.contentEquals("Default") && actionName.contentEquals("empty") ) {
				message = "Only Default can contain empty action.";
				errors.put(serviceName + "Service_" + actionName, message);
			}
			try {
				ServiceChecker.validatePathFromServlet(servicePattern, actionPath, servletPaths);
			} catch (ServiceException e) {
				errors.put(serviceName + "Service_" + actionName, e.getMessage());
			}
		});
	}


	/**
	 * @return the serviceName
	 */
	protected String getServiceName() {
		return serviceName;
	}

	/**
	 * @return the servicePattern
	 */
	protected String getServicePattern() {
		return servicePattern;
	}

	/**
	 * @return the servletPaths
	 */
	protected String[] getServletPaths() {
		return servletPaths;
	}

	/**
	 * @return the actions
	 */
	protected Map<String, String> getActions() {
		return actions;
	}

	/**
	 * @return the errors
	 */
	protected Map<String, String> getErrors() {
		return errors;
	}

}


