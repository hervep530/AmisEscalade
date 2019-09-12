package com.ocherve.jcm.dao.impl;

/**
 * @author herve_dev
 *
 */
public enum AddingMode {
	
	AND(" AND "),
	AND_NOT(" AND NOT "),
	OR(" OR "),
	OR_NOT(" OR NOT ");
	
	private String sql;

	private AddingMode(String sql) {
		this.sql = sql;
	}

	/**
	 * @return the sql
	 */
	public String getSql() {
		return sql;
	}
	
	
}
