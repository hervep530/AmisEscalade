package com.ocherve.jcm.dao;

/**
 * @author herve_dev
 *
 */
public enum StorageType {
	
	/**
	 * Postgresql implementation without hibernate - removed
	 */
	POSTGRESQL,
	/**
	 * Hibernate implementation with using session
	 */
	HIBERNATE_SESSION,
	/**
	 * Hibernate implementation with using EntityManager
	 */
	HIBERNATE_EM;

}
