/**
 * 
 */
package com.ocherve.jcm.dao.impl;

/**
 * @author herve_dev
 *
 */
public enum Operator {

	/**
	 * String compare
	 */
	LIKE(" LIKE "),
	/**
	 * Numeric compare =
	 */
	EQUAL(" = "),
	/**
	 * Numeric compare >
	 */
	GREATER(" > "),
	/**
	 * Numeric compare <
	 */
	LESS(" < ");
	
	private String sql;
	
	private Operator(String sql) {
		this.sql = sql;
	}

	/**
	 * @return the sql
	 */
	public String getSql() {
		return sql;
	}
	
	
}
