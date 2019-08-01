package com.ocherve.jcm.service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author herve_dev
 *
 * Object return by Service when action is done or aborted - contains data for jsp
 */
public class Delivry {
	
	private Map<String,String> errors;
	private Parameters parameters;

	/**
	 * Constructor
	 */
	public Delivry() {
		errors = new HashMap<>();
		parameters = new Parameters();
	}

	/**
	 * Getter
	 * 
	 * @return		Map : errors
	 */
	public Map<String, String> getErrors() {
		return errors;
	}

	/**
	 * Setter - not really easy to use like this
	 * 
	 * @param errors		Map - all errors at once
	 */
	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}
	
	/**
	 * Appender for errors - easier to use than setter
	 * 
	 * @param errorLabel		String : label to identified error
	 * @param ErrorDetail		String : text to describe error
	 */
	public void appendError(String errorLabel, String ErrorDetail) {
		this.errors.put(errorLabel, ErrorDetail);
	}

	/**
	 * Getter
	 * 
	 * @return				Parameters : parameters that service defined from request
	 */
	public Parameters getParameters() {
		return parameters;
	}

	/**
	 * Setter
	 * 
	 * @param parameters	Parameters : parameters that service defined from request
	 */
	public void setParameters(Parameters parameters) {
		this.parameters = parameters;
	}
	

}
