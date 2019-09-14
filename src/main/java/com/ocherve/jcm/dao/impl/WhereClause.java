package com.ocherve.jcm.dao.impl;

import java.util.Map;

/**
 * @author herve_dev
 *
 */
public class WhereClause {
	
	private String clause;
	
	/**
	 * Constructor
	 * @param target 
	 * @param operator 
	 */
	public WhereClause(String target, Operator operator) {
		clause = " WHERE " + target + operator.getSql() + ":" + getCamel(target);
	}

	/**
	 * @param andGroup
	 * @param group
	 */
	public WhereClause(Boolean andGroup, Map<String,Operator> group) {
		clause = "";
		if ( andGroup ) {
			addAndGroup(AddingMode.OR, group);
			clause = clause.replaceFirst("OR", "WHERE");
		} else {
			addOrGroup(AddingMode.AND, group);
			clause = clause.replaceFirst("AND", "WHERE");
		}
	}

	/**
	 * @param addingMode
	 * @param target
	 * @param operator
	 */
	public void add(AddingMode addingMode, String target, Operator operator) {
		clause += addingMode.getSql() + target + operator.getSql() + ":" + getCamel(target);
	}
	
	/**
	 * @param addingMode 
	 * @param group
	 */
	public void addAndGroup(AddingMode addingMode, Map<String,Operator> group) {
		String groupedClauses = "( ";
		for ( String target : group.keySet() ) {
			if ( ! groupedClauses.contentEquals("( ") ) groupedClauses += AddingMode.AND.getSql();
			groupedClauses += "LOWER(" + target.replaceAll("\\.[0-9]{1,}$", "") +")"
							+ group.get(target).getSql()
							+ "LOWER(:" + getCamel(target) + ")";
		}
		groupedClauses += " )";
		clause += addingMode.getSql() + groupedClauses;
	}

	/**
	 * @param addingMode 
	 * @param group
	 */
	public void addOrGroup(AddingMode addingMode, Map<String,Operator> group) {
		String groupedClauses = "( ";
		for ( String target : group.keySet() ) {
			if ( ! groupedClauses.contentEquals("( ") ) groupedClauses += AddingMode.OR.getSql();
			groupedClauses += "LOWER(" + target.replaceAll("\\.[0-9]{1,}$", "") +")"
							+ group.get(target).getSql() 
							+ "LOWER(:" + getCamel(target) +")";
		}
		groupedClauses += " )";
		clause += addingMode.getSql() + groupedClauses;
	}
	
	/**
	 * @param text
	 * @return camel String
	 */
	public static String getCamel(String text) {
		String[] splitText = text.replaceAll("[\\W]{1,}", "_").split("_");
		String camel = splitText[0];
		for (int i = 1 ; i < splitText.length; i++) {
			camel += splitText[i].substring(0, 1).toUpperCase() + splitText[i].substring(1);
		} 
		return camel;
	}
	
	/**
	 * @return string clause
	 */
	public String getSql() {
		return clause;
	}
	
}
