package com.ocherve.jcm.beans;

import java.util.Date;
import java.util.List;



/**
 * @author herve_dev
 * 
 * Object Class Document - will be herited by Site and Topo
 */
public class Document {
	
	private Integer id;
	private String title;
	private Date creationDate;
	private Date modificationDate;
	private String author;
	private String summary;
	private String description;
	private Boolean published;
	private List<Content> contents;
	private List<Comment> comments;
	private Boolean ownerRequest;
	
	/**
	 * Constructor
	 */
	public Document() {
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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return the modificationDate
	 */
	public Date getModificationDate() {
		return modificationDate;
	}

	/**
	 * @param modificationDate the modificationDate to set
	 */
	public void setModificationDate(Date modificationDate) {
		this.modificationDate = modificationDate;
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
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * @param summary the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the published
	 */
	public boolean isPublished() {
		return published;
	}

	/**
	 * @param published the published to set
	 */
	public void setPublished(boolean published) {
		this.published = published;
	}

	/**
	 * @return the contents
	 */
	public List<Content> getContents() {
		return contents;
	}

	/**
	 * @param contents the contents to set
	 */
	public void setContents(List<Content> contents) {
		this.contents = contents;
	}

	/**
	 * @return the comments
	 */
	public List<Comment> getComments() {
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments(List<Comment> comments) {
		this.comments = comments;
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
