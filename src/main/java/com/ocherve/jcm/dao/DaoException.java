package com.ocherve.jcm.dao;

/**
 * @author herve_dev
 *
 * Exception to manage DAO errors
 */
public class DaoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor
	 * 
	 * @param message		String : error message
	 */
	public DaoException(String message) {
		super(message);
	}

}
