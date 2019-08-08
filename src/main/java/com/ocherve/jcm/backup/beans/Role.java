package com.ocherve.jcm.backup.beans;

/**
 * @author herve_dev
 *
 */
public enum Role {
	/**
	 * Minimal access
	 */
	ANONYMOUS,
	/**
	 * Basic access
	 */
	MEMBER,
	/**
	 * Privileged access
	 */
	MASTER,
	/**
	 * Admin access
	 */
	ADMIN;
}
