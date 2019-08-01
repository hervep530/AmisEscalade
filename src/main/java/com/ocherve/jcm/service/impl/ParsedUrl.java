package com.ocherve.jcm.service.impl;

/**
 * This objet stores parts of url analysed and used by services
 * @author herve_dev
 *
 */
public class ParsedUrl {
	
	private String serviceAlias;
	private String action;
	private String id = "";
	private String slug = "";
	
	/**
	 * Constructor without parameters
	 */
	public ParsedUrl() {
		this.serviceAlias = "";
		this.action = "";
	}
	
	/**
	 * Constructor with main attributes as arguments for fast usage
	 * @param serviceAlias
	 * @param action
	 */
	public ParsedUrl(String serviceAlias, String action) {
		this.serviceAlias = serviceAlias;
		this.action = action;
	}

	/**
	 * Getter
	 * 
	 * @return		String serviceAlias (service name as second part of url - the first is context)
	 */
	public String getServiceAlias() {
		return serviceAlias;
	}

	/**
	 * Setter
	 * @param serviceAlias		String containg serviceAlias (service name as second part of url - the first is context)
	 */
	public void setServiceAlias(String serviceAlias) {
		this.serviceAlias = serviceAlias;
	}

	/**
	 * Getter
	 * 
	 * @return		String serviceAlias (service name as third part of url)
	 */
	public String getAction() {
		return action;
	}

	/**
	 * Setter
	 * @param action		String containg action (service name as third part of url)
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * Getter
	 * 
	 * @return		String id (service name as fourth part of url)
	 */
	public String getId() {
		return id;
	}

	/**
	 * Setter
	 * @param id		String containg id (service name as fourth part of url)
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Getter
	 * 
	 * @return		String slug (fifth part of url)
	 */
	public String getSlug() {
		return slug;
	}

	/**
	 * Setter
	 * @param slug		String containg slug (service name as fifth part of url)
	 */
	public void setSlug(String slug) {
		this.slug = slug;
	}
	

}
