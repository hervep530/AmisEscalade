package com.ocherve.jcm.service.impl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import com.ocherve.jcm.service.ServiceException;
import com.ocherve.jcm.service.UrlException;

/**
 * Provides checking methods to Services Classes
 * @author herve_dev
 *
 */
public class ServiceChecker {

	private static final Logger DLOG = LogManager.getLogger("development_file");
	private static final Level LOGLEVEL = Level.TRACE;

    /**
     * Constructor
     */
    public ServiceChecker() {
        Configurator.setLevel(DLOG.getName(), LOGLEVEL);
    }
    
    /** 
     * Validate rules defined in services : action paths matches with paths declared in @WebServlet
     *    annotation from Servlet
     *    
     * @param servicePattern		String - alias for service as part of uri in pattern provided
     *   by actions attribute in ServiceImpl Classe
     * @param servicePath			String - request uri rule (requirements) define in service
     * @param servletPaths			String - paths define in Servlet from @WebServlet annotation
     * @throws ServiceException		Exception if checking fails
     */
    public static void validatePathFromServlet(String servicePattern, String servicePath, String[] servletPaths) 
            throws ServiceException {
        // Validate global service pattern       
        //String globalPattern = "/(\\w{3,})?(/\\$id|/\\$id/\\$slug)?";         // CORRECTION BUG 190902 // ALLOW action with 1 char 190904
        String globalPattern = "/(\\w{1,})?(/\\$id|/\\$id/\\$slug)?";         // CORRECTION BUG 190902
        if ( ! servicePattern.contentEquals("default") ) {
            // globalPattern = "/" + servicePattern + "/\\w{3,}(/\\$id|/\\$id/\\$slug)?"; // ALLOW action with 1 char 190904
            globalPattern = "/" + servicePattern + "/\\w{1,}(/\\$id|/\\$id/\\$slug)?";
        }
        String actionMessage = "Action path " + servicePath + " doesn't match with global service pattern \""
        		+ globalPattern + "\".";
        if ( ! servicePath.matches(globalPattern) ) {
            DLOG.log(Level.ERROR, actionMessage);
            throw new ServiceException("WARN:" + actionMessage);
        }
        // Validate servicePath matches with one of servletPaths
        boolean hasMatchingPath = false;
        int p = 0;
        String servletPattern = "";
        while (!hasMatchingPath && p < servletPaths.length) {
            servletPattern = servletPaths[p].replaceAll("/\\*$", "/") + ".*";
            DLOG.log(Level.DEBUG, "Compare : " + servicePath + " / " + servletPattern);
            if (servicePath.matches(servletPattern)) {
                hasMatchingPath = true;
            }
            p++;
        }
        actionMessage = "Action path " + servicePath + " hasn't any matching path in Servlet.";
        if (!hasMatchingPath) {
            DLOG.log(Level.ERROR, actionMessage);
            throw new ServiceException("WARN:" + actionMessage);
        }
    }

    /**
     * Validate uri requested by user from global rule defined here, in order to prepare
     *   parsing action
     *  
     * @param context			String - as found in servlet request
     * @param serviceName		String - identify a service (serviceName = servletName)
     * @param uri				String - as found in servlet request
     * 
     * @throws UrlException		Exception if checking fails
     */
    public static void validateGlobalPatternUrl(String context, String serviceName, String uri)
    	throws UrlException {
    	String message = "";
    	String url = uri.replaceAll("^[^/]{1,}:[^/]*/{1,}[^/]{1,}:?[^/]*/?", "");
    	String globalPattern = "^(/[0-9a-zA-Z-.]{1,})?/?(\\w{1,})?(/\\w{1,})?(/\\d{1,8}|/\\d{1,8}/\\w{1,})?(#\\w*)?$";
    	// Specific case of Default service
    	if ( serviceName.contentEquals("Default") )
    		globalPattern = "^(/[0-9a-zA-Z-.]{1,})?/?(/\\w{1,})?(/\\d{1,8}|/\\d{1,8}/\\w{1,})?(#\\w*)?$";
    		// globalPattern = "^(/[0-9a-zA-Z-.]{1,})?/?(/\\w{3,})?(/\\d{1,8}|/\\d{1,8}/\\w{1,})?(#\\w*)?$";     // ALLOW action with 1 char 190904
    	
    	// Check Action
    	if ( ! url.matches(globalPattern) ) {
    		message = "Url \"" + url + "\" doesn't match with global pattern.";
            DLOG.log(Level.ERROR, message);
            throw new UrlException(message);
    	}
    }
    
}
