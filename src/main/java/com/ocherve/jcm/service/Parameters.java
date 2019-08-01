package com.ocherve.jcm.service;

import java.util.HashMap;
import java.util.Map;

import com.ocherve.jcm.service.impl.ParsedUrl;

/**
 * @author herve_dev
 * 
 *	Parameters is an objet build from request to modelize / normalize all
 *	  data required by services to do Get or Post Actions
 */
public class Parameters {
	
	private Map<String,String> errors;
	private ParsedUrl parsedUrl;

	/**
	 * Constructor
	 */
	public Parameters() {
		errors = new HashMap<>();
	}
	
	/**
	 * Getter
	 *  
	 * @return		Map<String,String> containing errors collected by methods setParameters in services
	 */
	public Map<String, String> getErrors() {
		return errors;
	}
	
	/**
	 * Setter
	 * 
	 * @param errors Map<String,String> containing errors collected by methods setParameters in services
	 */
	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}
	
	/**
	 * Map Entry Appender - easier to use than Setter
	 * 
	 * @param errorLabel		String : name identifing error 
	 * @param errorDetail 		String : text describing error
	 * 
	 */
	public void appendError(String errorLabel, String errorDetail) {
		this.errors.put(errorLabel, errorDetail);
	}
	
	/**
	 * Map Appender - easier to use than Setter
	 * 
	 * @param errors 		Map<String,String> errors
	 */
	public void appendAllError(Map<String,String> errors) {
		this.errors.putAll(errors);
	}
	
	/**
	 * Getter
	 * 
	 * @return		ParsedUrl : object containing all part of url as used by services
	 */
	public ParsedUrl getParsedUrl() {
		return parsedUrl;
	}
	
	/**
	 * Setter
	 * 
	 * @param parsedUrl		ParsedUrl : object containing all part of url as used by services
	 */
	public void setParsedUrl(ParsedUrl parsedUrl) {
		this.parsedUrl = parsedUrl;
	}

}
