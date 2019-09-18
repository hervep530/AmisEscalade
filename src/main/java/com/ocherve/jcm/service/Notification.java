package com.ocherve.jcm.service;

/**
 * @author herve_dev
 *
 */
public class Notification {
	
	private NotificationType type;
	private String message;
	
	/**
	 * @param type
	 * @param message
	 */
	public Notification(NotificationType type, String message) {
		super();
		this.type = type;
		this.message = message;
	}

	/**
	 * @return the type
	 */
	public NotificationType getType() {
		return type;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	
	
	
}
