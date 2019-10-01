package com.ocherve.jcm.service;

import java.util.HashMap;
import java.util.Map;

import com.ocherve.jcm.model.User;
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
	private Object form;
	private String contextPath;
	private String referer;
	private User sessionUser;
	private Map<String,Notification> notifications;

	/**
	 * Constructor
	 */
	public Parameters() {
		errors = new HashMap<>();
		notifications = new HashMap<>();
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

	/**
	 * @return the form
	 */
	public Object getForm() {
		return form;
	}

	/**
	 * @param form the form to set
	 */
	public void setForm(Object form) {
		this.form = form;
	}

	/**
	 * @return the contextPath
	 */
	public String getContextPath() {
		return contextPath;
	}

	/**
	 * @param contextPath the contextPath to set
	 */
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
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
	 * Map Entry Appender - easier to use than Setter
	 * 
	 * @param notificationLabel		String : name identifing error 
	 * @param notification 		Notification
	 * 
	 */
	public void appendNotification(String notificationLabel, Notification notification) {
		this.notifications.put(notificationLabel, notification);
	}
	
	/**
	 * Map Appender - easier to use than Setter
	 * 
	 * @param notifications 		Map<String,Notification> errors
	 */
	public void appendNotifications(Map<String,Notification> notifications) {
		this.notifications.putAll(notifications);
	}

	/**
	 * @return the sessionUser
	 */
	public User getSessionUser() {
		return sessionUser;
	}

	/**
	 * @param sessionUser the sessionUser to set
	 */
	public void setSessionUser(User sessionUser) {
		this.sessionUser = sessionUser;
	}

	/**
	 * @return the referer
	 */
	public String getReferer() {
		return referer;
	}

	/**
	 * @param referer the referer to set
	 */
	public void setReferer(String referer) {
		this.referer = referer;
	}
	
	
	
}
