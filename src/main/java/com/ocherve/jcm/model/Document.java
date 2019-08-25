package com.ocherve.jcm.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the jcm_document database table.
 * 
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="jcm_document")
@NamedQuery(name="document.findAll", query="SELECT j FROM Document j")
public class Document implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Integer id;

	private String name;

	private Boolean published;

	private String summary;

	@Column(name="ts_created")
	private Timestamp tsCreated;

	@Column(name="ts_modified")
	private Timestamp tsModified;
	
	//bi-directional many-to-one association to JcmUser
	@ManyToOne
	@JoinColumn(name="fk_document_user")
	private User author;

	/**
	 * 
	 */
	public Document() {
	}

	/**
	 * @return id
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return document name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return published status
	 */
	public Boolean isPublished() {
		return this.published;
	}

	/**
	 * @param published
	 */
	public void setPublished(Boolean published) {
		this.published = published;
	}

	/**
	 * @return summary
	 */
	public String getSummary() {
		return this.summary;
	}

	/**
	 * @param summary
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * @return creation time
	 */
	public Timestamp getTsCreated() {
		return this.tsCreated;
	}

	/**
	 * @param tsCreated
	 */
	public void setTsCreated(Timestamp tsCreated) {
		this.tsCreated = tsCreated;
	}

	/**
	 * @return revision time
	 */
	public Timestamp getTsModified() {
		return this.tsModified;
	}

	/**
	 * @param tsModified
	 */
	public void setTsModified(Timestamp tsModified) {
		this.tsModified = tsModified;
	}

	/**
	 * @return author
	 */
	public User getAuthor() {
		return this.author;
	}

	/**
	 * @param author
	 */
	public void setAuthor(User author) {
		this.author = author;
	}

}
