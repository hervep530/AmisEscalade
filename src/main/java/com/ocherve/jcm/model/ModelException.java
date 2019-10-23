package com.ocherve.jcm.model;

/**
 * Exception used inside model Classes
 * 
 * @author herve_dev
 *
 */
public class ModelException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * New ModelException without argument
	 */
	public ModelException() {
		super();
	}
	
	/**
	 * New ModelException with message as String argument
	 * 
	 * @param message String
	 */
	public ModelException(String message) {
		super(message);
	}

}
