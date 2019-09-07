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
	private Map<String,Object> attributes;
	private Parameters parameters;

	/**
	 * Constructor
	 */
	public Delivry() {
		errors = new HashMap<>();
		parameters = new Parameters();
		attributes = new HashMap<>();
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

	/**
	 * @return the attributes
	 */
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes the attributes to set
	 */
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	/**
	 * Appender for errors - easier to use than setter
	 * @param name 
	 * @param value 
	 * 
	 */
	public void appendattribute(String name, Object value) {
		this.attributes.put(name, value);
	}
	
	/**
	 * @param name
	 * @return Object
	 */
	public Object getAttribute(String name) {
		return this.attributes.get(name);
	}



}
