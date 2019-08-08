package com.ocherve.jcm.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the jcm_content database table.
 * 
 */
@Entity
@Table(name="jcm_content")
@NamedQuery(name="JcmContent.findAll", query="SELECT j FROM JcmContent j")
public class JcmContent implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private JcmContentPK id;

	private String data;

	//bi-directional many-to-one association to JcmContentType
	@ManyToOne
	@JoinColumn(name="pfk_type")
	private JcmContentType jcmContentType;

	//bi-directional many-to-one association to JcmDocument
	@ManyToOne
	@JoinColumn(name="pfk_document")
	private JcmDocument jcmDocument;

	public JcmContent() {
	}

	public JcmContentPK getId() {
		return this.id;
	}

	public void setId(JcmContentPK id) {
		this.id = id;
	}

	public String getData() {
		return this.data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public JcmContentType getJcmContentType() {
		return this.jcmContentType;
	}

	public void setJcmContentType(JcmContentType jcmContentType) {
		this.jcmContentType = jcmContentType;
	}

	public JcmDocument getJcmDocument() {
		return this.jcmDocument;
	}

	public void setJcmDocument(JcmDocument jcmDocument) {
		this.jcmDocument = jcmDocument;
	}

}