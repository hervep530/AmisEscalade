package com.ocherve.jcm.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.ocherve.jcm.utils.JcmDate;

/**
 * The persistent class for the jcm_comment database table.
 * 
 */
@Entity
@Table(name="jcm_comment")
@NamedQueries({
	@NamedQuery(name="Comment.findAll", query="SELECT c FROM Comment c"),
	@NamedQuery(name="Comment.findByAuthorId", query="SELECT c FROM Comment c"),
	@NamedQuery(name="Comment.findByAuthor", query="SELECT c FROM Comment c")
})
public class Comment implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "content")
	private String content = "";

	//bi-directional many-to-one association to Document
	@ManyToOne
	@JoinColumn(name="fk_comment_reference")
	private Reference reference;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="fk_comment_user")
	private User author;

	@Column(name="ts_created", columnDefinition = "TIMESTAMP DEFAULT NOW()", nullable = false)
	private Timestamp tsCreated = Timestamp.from(Instant.now());

	@Column(name="ts_modified", columnDefinition = "TIMESTAMP DEFAULT NOW()", nullable = false)
	private Timestamp tsModified  = Timestamp.from(Instant.now());


	/**
	 * Constructor
	 */
	public Comment() {
	}

	/**
	 * Constructor
	 * @param object 
	 * @param text 
	 * @param author 
	 */
	public Comment(Object object, String text, User author) {
		super();
		try {
			this.reference = Reference.class.cast(object);
		} catch (Exception e) {
			return;
		}
		this.content = text;
		this.author = author;
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
	 * @return content
	 */
	public String getContent() {
		return this.content;
	}

	/**
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return Timestamp created at
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
	 * @return Timestamp modified at
	 */
	public Timestamp getTsModified() {
		return this.tsModified;
	}
	
	/**
	 * @return elapsed time as string (usefull for jsp)
	 */
	public String getModifiedElapsedTime() {
		return JcmDate.getElapseTime(tsModified);
	}

	/**
	 * @param tsModified 
	 */
	public void setTsModified(Timestamp tsModified) {
		this.tsModified = tsModified;
	}


	/**
	 * @return reference target for this comment
	 */
	public Reference getReference() {
		return this.reference;
	}

	/**
	 * @param reference
	 */
	public void setReference(Reference reference) {
		this.reference = reference;
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