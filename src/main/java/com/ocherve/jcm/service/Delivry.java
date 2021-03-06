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
	private Map<String,Object> session;
	private Map<String,Notification> notifications;
	private Parameters parameters;

	/**
	 * Constructor
	 */
	public Delivry() {
		errors = new HashMap<>();
		parameters = new Parameters();
		attributes = new HashMap<>();
		session = new HashMap<>();
		notifications = new HashMap<>();
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
	 * Appender for error - easier to use than setter
	 * 
	 * @param errorLabel		String : label to identified error
	 * @param ErrorDetail		String : text to describe error
	 */
	public void appendError(String errorLabel, String ErrorDetail) {
		this.errors.put(errorLabel, ErrorDetail);
	}

	/**
	 * Appender for errors - easier to use than setter
	 * 
	 * @param errors Map of errors
	 */
	public void appendErrors(Map<String,String> errors) {
		this.errors.putAll(errors);
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

	/**
	 * @return the session
	 */
	public Map<String, Object> getSession() {
		return session;
	}

	/**
	 * @param session the session to set
	 */
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	/**
	 * @param name
	 * @param value
	 */
	public void appendSession(String name, Object value) {
		this.session.put(name, value);
	}

	/**
	 * @return the notification
	 */
	public Map<String,Notification> getNotifications() {
		return notifications;
	}

	/**
	 * @param notifications the notification to set
	 */
	public void setNotifications(Map<String,Notification> notifications) {
		this.notifications = notifications;
	}
	
	/**
	 * @param actionLabel
	 * @param notification
	 */
	public void appendNotification(String actionLabel, Notification notification) {
		this.notifications.put(actionLabel, notification);
	}

	/**
	 * @param notifications
	 */
	public void appendNotifications(Map<String,Notification> notifications) {
		if ( notifications == null ) return;
		this.notifications.putAll(notifications);
	}

	/**
	 * @return the notification
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Notification> getSessionNotifications() {
		Map<String,Notification> sessionNotifications = new HashMap<>();
		try {
			sessionNotifications.putAll( (HashMap<String,Notification>) session.get("notifications") );
		} catch (Exception ignore) { }
		return sessionNotifications;
	}

	/**
	 * @param actionLabel
	 * @param notification
	 */
	@SuppressWarnings("unchecked")
	public void appendSessionNotification(String actionLabel, Notification notification) {
		Map<String,Notification> sessionNotifications = new HashMap<>();
		if ( session.containsKey("notifications") ) {
			try {
				sessionNotifications.putAll( (HashMap<String,Notification>) session.get("notifications") );
			} catch (Exception ignore) { }
		}
		sessionNotifications.put(actionLabel, notification);
		this.session.put("notifications", sessionNotifications);
	}


	

}
