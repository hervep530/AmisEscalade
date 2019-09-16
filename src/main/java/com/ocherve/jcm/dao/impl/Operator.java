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
	 * Insensitive Case String compare
	 */
	ILIKE(" ILIKE "),
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
	LESS(" < "),
	/**
	 * Numeric compare >
	 */
	GREATEREQ(" >= "),
	/**
	 * Numeric compare <
	 */
	LESSEQ(" <= ");
	
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
