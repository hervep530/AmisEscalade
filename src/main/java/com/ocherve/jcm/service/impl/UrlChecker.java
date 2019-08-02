package com.ocherve.jcm.service.impl;

import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import com.ocherve.jcm.service.UrlException;

/**
 * @author herve_dev
 *
 * Used From Services to prevent wrong url
 */
public class UrlChecker {

	private static final Logger DLOG = LogManager.getLogger("development_file");
	private static final Level LOGLEVEL = Level.TRACE;
	
    /**
     * Constructor
     */
    public UrlChecker() {
        Configurator.setLevel(DLOG.getName(), LOGLEVEL);
	}
    
    /**
     * ParseUrl split uri string to build object. Never fails... In the worst case,
     *   it return object with empty values...
     * 
     * @param serviceName	String : service name
     * @param uri			String : uri prepared to be splitted
     * @return				ParsedUrl : uri under object form
     */
    public static ParsedUrl parseUrl(String serviceName, String uri) {
    	String[] arrayUrl = uri.split("/");
    	String action = "";
    	int minus = 0;
    	ParsedUrl parsedUrl = new ParsedUrl();
    	// Set service and action values, regarding serviceName equals "Default" or not
    	if ( arrayUrl.length > 1 ) action = arrayUrl[1];
    	if ( ! serviceName.contentEquals("Default") ) {
    		parsedUrl.setServiceAlias(arrayUrl[0]);
    	} else {
    		// Default service path is empty so index in arrayUrl is "-1" comparing other services
    		minus = 1;
    		action = arrayUrl[0];
    	}
    	parsedUrl.setAction(action);
    	// Set id value
		if ( arrayUrl.length > 2 - minus )
    		parsedUrl.setId( arrayUrl[2 - minus]);
		// Set slug value
		if ( arrayUrl.length > 3 - minus )
    		parsedUrl.setSlug( arrayUrl[3 - minus]);

		return parsedUrl;	
    }
	
	/**
     * Validate uri requested by user from service rules defined by actions attributes(Map)
     * 
     * @param serviceName		String name of service (=servlet)
     * @param actions			Map<String, String> giving regex pattern (rule) for each action
	 * @param parsedUrl			ParsedUrl  object with attributes service / action / id / slug given by uri
     * @throws UrlException 
     */
    public static void validateAction(String serviceName, Map<String,String> actions, ParsedUrl parsedUrl)
    		throws UrlException {

    	String action = parsedUrl.getAction();
    	if ( action.isEmpty() ) action = "empty";
    	    	
    	// Validate action
    	if ( ! actions.containsKey(action) ) {
    		DLOG.log(Level.DEBUG, "Invalid url - action " + action + " can not be found in actions array.");
    		throw new UrlException("Invalid Url.");
    	}
    	
    	String urlPattern = actions.get(action); 

    	// Validate id
		if ( urlPattern.contains("$id") && parsedUrl.getId().isEmpty() ) {
    		DLOG.log(Level.DEBUG, "Invalid url - id is empty and doesn't match with action");
			throw new UrlException("Invalid Url");
		}
    	
		if ( ! urlPattern.contains("$id") && ! parsedUrl.getId().isEmpty() ) {
    		DLOG.log(Level.DEBUG, "Invalid url - id is not empty and doesn't match with action");
			throw new UrlException("Invalid Url");
		}

		// Set and validate slug
		if ( urlPattern.contains("$slug") && parsedUrl.getSlug().isEmpty() ) {
    		DLOG.log(Level.DEBUG, "Invalid url - slug is empty and doesn't match with action");
			throw new UrlException("Invalid Url");
		}
    	
		if ( ! urlPattern.contains("$slug") && ! parsedUrl.getSlug().isEmpty() ) {
    		DLOG.log(Level.DEBUG, "Invalid url - slug is not empty and doesn't match with action");
			throw new UrlException("Invalid Url");
		}
    	
		// if ( ! DaoProxy.getInstance().getMachinDao().getById(id, Map<> clausesWhere) ) errors.put("","");

    }
		    	
}
