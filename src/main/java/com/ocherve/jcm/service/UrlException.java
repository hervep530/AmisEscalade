package com.ocherve.jcm.service;

/**
 * @author herve_dev
 *
 * Exception to manage Url Error
 */
public class UrlException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor overloaded
	 * 
	 * @param message		String : error message
	 */
	public UrlException(String message){
		super(message);
	}

}
