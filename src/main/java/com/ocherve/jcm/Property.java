package com.ocherve.jcm;

public enum Property {

	/**
	 * Log level for support
	 */
	JCM_LOG_SUPPORT_LEVEL("jcm.log.support.level","^(fatal|error|warn|debug|info|trace)$"),
	/**
	 * Log level for development
	 */
	JCM_LOG_DEV_LEVEL("jcm.log.dev.level","^(fatal|error|warn|debug|info|trace)$"),
	/**
	 * Mode debug
	 */
	JCM_DEBUG("jcm.log.debug","^(true|yes|1|false|no|0)$"),
	/**
	 * Mode debug
	 */
	JCM_DAO("jcm.dao","^(hibernate_em|hibernate_session|postgresql)$");
	
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
