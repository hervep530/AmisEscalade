package com.ocherve.jcm.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the jcm_document database table.
 * 
 */
@Entity
@Table(name="jcm_document")
@NamedQuery(name="JcmDocument.findAll", query="SELECT j FROM JcmDocument j")
public class JcmDocument implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String name;

	private Boolean published;

	private String summary;

	@Column(name="ts_created")
	private Timestamp tsCreated;

	@Column(name="ts_modified")
	private Timestamp tsModified;

	//bi-directional many-to-one association to JcmComment
	@OneToMany(mappedBy="jcmDocument")
	private List<JcmComment> jcmComments;

	//bi-directional many-to-one association to JcmContent
	@OneToMany(mappedBy="jcmDocument")
	private List<JcmContent> jcmContents;

	//bi-directional many-to-one association to JcmUser
	@ManyToOne
	@JoinColumn(name="fk_user")
	private JcmUser jcmUser;

	//bi-directional many-to-many association to JcmMedia
	@ManyToMany(mappedBy="jcmDocuments")
	private List<JcmMedia> jcmMedias;

	//bi-directional one-to-one association to JcmSite
	@OneToOne(mappedBy="jcmDocument")
	private JcmSite jcmSite;

	//bi-directional one-to-one association to JcmTopo
	@OneToOne(mappedBy="jcmDocument")
	private JcmTopo jcmTopo;

	public JcmDocument() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getPublished() {
		return this.published;
	}

	public void setPublished(Boolean published) {
		this.published = published;
	}

	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Timestamp getTsCreated() {
		return this.tsCreated;
	}

	public void setTsCreated(Timestamp tsCreated) {
		this.tsCreated = tsCreated;
	}

	public Timestamp getTsModified() {
		return this.tsModified;
	}

	public void setTsModified(Timestamp tsModified) {
		this.tsModified = tsModified;
	}

	public List<JcmComment> getJcmComments() {
		return this.jcmComments;
	}

	public void setJcmComments(List<JcmComment> jcmComments) {
		this.jcmComments = jcmComments;
	}

	public JcmComment addJcmComment(JcmComment jcmComment) {
		getJcmComments().add(jcmComment);
		jcmComment.setJcmDocument(this);

		return jcmComment;
	}

	public JcmComment removeJcmComment(JcmComment jcmComment) {
		getJcmComments().remove(jcmComment);
		jcmComment.setJcmDocument(null);

		return jcmComment;
	}

	public List<JcmContent> getJcmContents() {
		return this.jcmContents;
	}

	public void setJcmContents(List<JcmContent> jcmContents) {
		this.jcmContents = jcmContents;
	}

	public JcmContent addJcmContent(JcmContent jcmContent) {
		getJcmContents().add(jcmContent);
		jcmContent.setJcmDocument(this);

		return jcmContent;
	}

	public JcmContent removeJcmContent(JcmContent jcmContent) {
		getJcmContents().remove(jcmContent);
		jcmContent.setJcmDocument(null);

		return jcmContent;
	}

	public JcmUser getJcmUser() {
		return this.jcmUser;
	}

	public void setJcmUser(JcmUser jcmUser) {
		this.jcmUser = jcmUser;
	}

	public List<JcmMedia> getJcmMedias() {
		return this.jcmMedias;
	}

	public void setJcmMedias(List<JcmMedia> jcmMedias) {
		this.jcmMedias = jcmMedias;
	}

	public JcmSite getJcmSite() {
		return this.jcmSite;
	}

	public void setJcmSite(JcmSite jcmSite) {
		this.jcmSite = jcmSite;
	}

	public JcmTopo getJcmTopo() {
		return this.jcmTopo;
	}

	public void setJcmTopo(JcmTopo jcmTopo) {
		this.jcmTopo = jcmTopo;
	}

}