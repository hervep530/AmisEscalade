package com.ocherve.jcm.backup.beans;

import javax.persistence.*;

/**
 * @author herve_dev
 * 
 * Document Content - in opposition of metadata
 */
@Table( name = "jcm_content" )
public class Content {

	private Document document ;
	private ContentType contentType;
	private String data;
		
	/**
	 * Instanciate Content
	 */
	public Content() {
		super();
	}
	

	/**
	 * @param contentType
	 * @param data
	 */
	public Content(ContentType contentType, String data) {
		super();
		this.contentType = contentType;
		this.data = data;
	}


	/**
	 * @return the contentType
	 */
	public ContentType getContentType() {
		return contentType;
	}


	/**
	 * @param contentType the contentType to set
	 */
	public void setContentType(ContentType contentType) {
		this.contentType = contentType;
	}


	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}


	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}


	/**
	 * @return document
	 */
	public Document getDocument() {
		return document;
	}


	/**
	 * @param document
	 */
	public void setDocument(Document document) {
		this.document = document;
	}
	
}