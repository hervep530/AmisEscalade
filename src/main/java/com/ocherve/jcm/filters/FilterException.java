package com.ocherve.jcm.filters;

/**
 * Exception throws by filter methods
 * 
 * @author herve_dev
 *
 */
public class FilterException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor with message
	 * 
	 * @param message
	 */
	public FilterException(String message) {
		super(message);
	}

}
