/**
 * 
 */
package com.ocherve.jcm.beans;

/**
 * @author herve_dev
 *
 */
public enum Access {

	/**
	 * Can't see object
	 */
	NONE,
	/**
	 * Can see and read object but not modify
	 */
	READER,
	/**
	 * Can read and modify content, but not managing it.
	 */
	EDITOR,
	/**
	 * Full access on object
	 */
	MANAGER;
	
}
