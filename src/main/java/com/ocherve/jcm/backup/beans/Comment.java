package com.ocherve.jcm.backup.beans;

import java.util.Date;

import javax.persistence.*;

/**
 * @author herve_dev
 *
 * Comment - discussion about a document
 */
@Entity
@Table( name = "jcm_comment")
public class Comment {
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private Integer id;
	private String author;
	private Document document;
	private Date dateCreated;
	private String text;
	private Boolean ownerRequest;
	
	/**
	 * Constructor
	 */
	public Comment() {
		super();
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return the document
	 */
	public Document getDocument() {
		return document;
	}

	/**
	 * @param document the document to set
	 */
	public void setDocument(Document document) {
		this.document = document;
	}

	/**
	 * @return the dateCreated
	 */
	public Date getDateCreated() {
		return dateCreated;
	}

	/**
	 * @param dateCreated the dateCreated to set
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the ownerRequest
	 */
	public Boolean isOwnerRequest() {
		return ownerRequest;
	}

	/**
	 * @param ownerRequest the ownerRequest to set
	 */
	public void setOwnerRequest(Boolean ownerRequest) {
		this.ownerRequest = ownerRequest;
	}

	
}
