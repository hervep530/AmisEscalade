package com.ocherve.jcm.beans;

import java.util.Date;

/**
 * @author herve_dev
 *
 * Topo extends Document : specific Document to describe a Climbing Site book
 */
public class Topo {
	
	private String title;
	private String writer;
	private Date dateWriting;
	private Integer siteReference;
	private Boolean available;
	private Boolean published;
	
	/**
	 * Constructor
	 */
	public Topo() {
		super();
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
	 * @return the writer
	 */
	public String getWriter() {
		return writer;
	}

	/**
	 * @param writer the writer to set
	 */
	public void setWriter(String writer) {
		this.writer = writer;
	}

	/**
	 * @return the dateWriting
	 */
	public Date getDateWriting() {
		return dateWriting;
	}

	/**
	 * @param dateWriting the dateWriting to set
	 */
	public void setDateWriting(Date dateWriting) {
		this.dateWriting = dateWriting;
	}

	/**
	 * @return the siteReference
	 */
	public Integer getSiteReference() {
		return siteReference;
	}

	/**
	 * @param siteReference the siteReference to set
	 */
	public void setSiteReference(Integer siteReference) {
		this.siteReference = siteReference;
	}

	/**
	 * @return the available
	 */
	public Boolean getAvailable() {
		return available;
	}

	/**
	 * @param available the available to set
	 */
	public void setAvailable(Boolean available) {
		this.available = available;
	}

	/**
	 * @return the published
	 */
	public Boolean getPublished() {
		return published;
	}

	/**
	 * @param published the published to set
	 */
	public void setPublished(Boolean published) {
		this.published = published;
	}

	
}
