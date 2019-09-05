package com.ocherve.jcm.service;

/**
 * Access level used to identified filter to apply
 * 
 * @author herve_dev
 *
 */
public enum AccessLevel {
	
	/**
	 * What anybody can do as anonymous
	 */
	DEFAULT,
	/**
	 * What all connected user can do
	 */
	USER,
	/**
	 * Reserved for user member of community, allow advanced action
	 */
	MEMBER,
	/**
	 * Reserved to administrator of the application
	 */
	ADMIN

}
