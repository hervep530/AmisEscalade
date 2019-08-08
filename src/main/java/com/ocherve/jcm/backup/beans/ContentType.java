package com.ocherve.jcm.backup.beans;

/**
 * @author herve_dev
 * 
 * Content Type : Document may have several contents particurly Site. So each kind of content
 * 	is identified by a ContentType which can be access from its id or its name in lower case
 */
public enum ContentType {

	/**
	 * Content is a single description (standard - most of the case)
	 */
	DESCRIPTION(1, "article"),
	/**
	 * Technical information for Climbing
	 */
	ESCALADE(2, "escalade"),
	/**
	 * Particular description about the way to go to the Site
	 */
	ACCES(3, "article");
	
	private int contentTypeId;
	private String contentTypeName;
	
	private ContentType(int contentTypeId, String contentTypeName) {
		this.contentTypeId = contentTypeId;
		this.contentTypeName = contentTypeName;
	}

	/**
	 * @return the contentTypeId
	 */
	public int getContentTypeId() {
		return contentTypeId;
	}

	/**
	 * @return the contentTypeName
	 */
	public String getContentTypeName() {
		return contentTypeName;
	}
	
	
}
