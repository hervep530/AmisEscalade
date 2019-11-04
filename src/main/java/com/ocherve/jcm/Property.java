package com.ocherve.jcm;

/**
 * Filter for PropertyHelper input as ENUM(key,regexp_value_filter)
 * 
 * @author herve_dev
 *
 */
public enum Property {

	/**
	 * Mode debug
	 */
	JCM_DEBUG("jcm.log.debug","^(debug|info|trace)$"),
	/**
	 * Dao type
	 */
	JCM_DAO("jcm.dao","^(hibernate_em|hibernate_session|postgresql)$"),
	/**
	 * Maximum entries count displayed in views (lists return by dao query)
	 */
	JCM_LIST_LIMIT("jcm.list.limit","^[1-9][0-9]?$");
	
	private String key;
	private String rule;
	
	Property(String key, String rule) {
		this.key = key;
		this.rule = rule;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @return the rule
	 */
	public String getRule() {
		return rule;
	}

}
