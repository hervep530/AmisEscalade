package com.ocherve.jcm.service;

/**
 * @author herve_dev
 *
 */
public enum NotificationType {
	
	/**
	 * Error notification
	 */
	ERROR("danger"),
	/**
	 * Success notification
	 */
	SUCCESS("success"),
	/**
	 * Information notification
	 */
	INFORMATION("info"),
	/**
	 * Warning notification
	 */
	WARNING("warning");
	
	private String alias;
	
	private NotificationType(String alias) {
		this.alias = alias;
	}

	/**
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}
	
	
}
