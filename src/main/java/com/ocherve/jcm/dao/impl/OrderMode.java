/**
 * 
 */
package com.ocherve.jcm.dao.impl;

/**
 * @author herve_dev
 *
 */
public enum OrderMode {
	
	/**
	 * Ascendant mode for query order
	 */
	ASC(" ASC"),
	/**
	 * Descedant mode for query order
	 */
	DESC(" DESC");
	
	private String sql;
	
	private OrderMode(String sql) {
		this.sql = sql;
	}
	
	/**
	 * @return sql string
	 */
	public String getSql() {
		return sql;
	}

}
