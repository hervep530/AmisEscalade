/**
 * 
 */
package com.ocherve.jcm.dao.impl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;


/**
 * @author herve_dev
 *
 */
public class OrderClause {

	private static final Logger DLOG = LogManager.getLogger("development_file");
	private static final Level DLOGLEVEL = Level.TRACE;

	private String clause;
	
	/**
	 * Constructor
	 * @param target 
	 * @param order 
	 */
	public OrderClause(String target, OrderMode order) {
		Configurator.setLevel(DLOG.getName(), DLOGLEVEL);
		clause = " ORDER BY " + target + order.getSql();
	}
	
	/**
	 * @param target
	 * @param order 
	 */
	public void add(String target, OrderMode order) {
		clause += ", " + target + order.getSql();
	}
		
	/**
	 * @return string clause
	 */
	public String getSql() {
		return clause;
	}

}
