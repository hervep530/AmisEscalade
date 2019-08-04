package com.ocherve.jcm.service;

/**
 * @author herve_dev
 * 
 * Exception for services Errors
 */
public class ServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor overloaded
	 * 
	 * @param message		String : error message 
	 */
	public ServiceException(String message) {
		super(message);
	}
}
