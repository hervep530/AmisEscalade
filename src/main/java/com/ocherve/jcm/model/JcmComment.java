package com.ocherve.jcm.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the jcm_comment database table.
 * 
 */
@Entity
@Table(name="jcm_comment")
@NamedQuery(name="JcmComment.findAll", query="SELECT j FROM JcmComment j")
public class JcmComment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String text;

	@Column(name="ts_created")
	private Timestamp tsCreated;

	//bi-directional many-to-one association to JcmDocument
	@ManyToOne
	@JoinColumn(name="fk_document")
	private JcmDocument jcmDocument;

	//bi-directional many-to-one association to JcmUser
	@ManyToOne
	@JoinColumn(name="fk_user")
	private JcmUser jcmUser;

	public JcmComment() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Timestamp getTsCreated() {
		return this.tsCreated;
	}

	public void setTsCreated(Timestamp tsCreated) {
		this.tsCreated = tsCreated;
	}

	public JcmDocument getJcmDocument() {
		return this.jcmDocument;
	}

	public void setJcmDocument(JcmDocument jcmDocument) {
		this.jcmDocument = jcmDocument;
	}

	public JcmUser getJcmUser() {
		return this.jcmUser;
	}

	public void setJcmUser(JcmUser jcmUser) {
		this.jcmUser = jcmUser;
	}

}