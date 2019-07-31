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

	public String getServiceAlias() {
		return serviceAlias;
	}

	public void setServiceAlias(String serviceAlias) {
		this.serviceAlias = serviceAlias;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}
	

}
