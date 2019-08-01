package com.ocherve.jcm.service.impl;

import java.util.HashMap;
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
     * Validate uri requested by user from service rules defined by actions attributes(Map)
     * 
     * @param serviceName		String name of service (=servlet)
     * @param arrayUrl 			Array of string giving servicePattern / action / id / slug
     * @param actions			Map<String, String> giving regex pattern (rule) for each action
     * @return 					ParsedUrl (service / action / id / slug given by uri) 
     * @throws UrlException 
     */
    public static ParsedUrl validateServicePatternUrl(String serviceName, String[] arrayUrl, Map<String,String> actions)
    		throws UrlException {

    	String action = "";
    	String urlPattern = "##";
    	int minus = 0;
    	ParsedUrl parsedUrl = new ParsedUrl();
    	Map<String,String> errors = new HashMap<>();
    	
    	if ( arrayUrl == null ) errors.put("nullArrayUrl", "Invalid Url.");
    	
    	// Set action
    	if ( arrayUrl.length > 1 ) action = arrayUrl[1];
    	if ( ! serviceName.contentEquals("Default") ) {
    		parsedUrl.setServiceAlias(arrayUrl[0]);
    	} else {
    		action = arrayUrl[0];
    		// Default service path is empty so index in arrayUrl is "-1" comparing other services
    		minus = 1;
    	}
    	parsedUrl.setAction(action);
    	if ( actions.containsKey(action) ) urlPattern = actions.get(action); 
    	// Validate action
    	if ( ! actions.containsKey("empty") && action.isEmpty() )
    		errors.put("emptyAction", "Invalid Url.");
    	if ( ! action.isEmpty() && ! actions.containsKey(action) )
    		errors.put("noMatchingAction", "Invalid Url.");
    	
    	// Set and validate id
		if ( urlPattern.contains("$id") ) {
			if ( arrayUrl.length > 2 - minus )
	    		parsedUrl.setId( arrayUrl[2 - minus]);
			else
	    		errors.put("emptyId", "Invalid Url.");				
		}
    	
    	// Set and validate slug
		if ( urlPattern.contains("$slug") ) {
			if ( arrayUrl.length > 3 - minus )
	    		parsedUrl.setId( arrayUrl[3 - minus]);
			else
	    		errors.put("emptySlug", "Invalid Url.");				
		}
    	
		parsedUrl.setErrors(errors);
		errors.forEach((k,v)->{
			DLOG.log(Level.ERROR, k + " - " + v);
		});
		// if ( ! DaoProxy.getInstance().getMachinDao().getById(id, Map<> clausesWhere) ) errors.put("","");
		return parsedUrl;
    }
		    	

	
}
