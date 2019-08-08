package com.ocherve.jcm.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the jcm_media database table.
 * 
 */
@Entity
@Table(name="jcm_media")
@NamedQuery(name="JcmMedia.findAll", query="SELECT j FROM JcmMedia j")
public class JcmMedia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private Boolean published;

	private String source;

	private String summary;

	private String title;

	//bi-directional one-to-one association to JcmImage
	@OneToOne(mappedBy="jcmMedia")
	private JcmImage jcmImage;

	//bi-directional many-to-many association to JcmDocument
	@ManyToMany
	@JoinTable(
		name="jcm_document_medias"
		, joinColumns={
			@JoinColumn(name="pfk_media")
			}
		, inverseJoinColumns={
			@JoinColumn(name="pfk_document")
			}
		)
	private List<JcmDocument> jcmDocuments;

	//bi-directional one-to-one association to JcmVideo
	@OneToOne(mappedBy="jcmMedia")
	private JcmVideo jcmVideo;

	public JcmMedia() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getPublished() {
		return this.published;
	}

	public void setPublished(Boolean published) {
		this.published = published;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public JcmImage getJcmImage() {
		return this.jcmImage;
	}

	public void setJcmImage(JcmImage jcmImage) {
		this.jcmImage = jcmImage;
	}

	public List<JcmDocument> getJcmDocuments() {
		return this.jcmDocuments;
	}

	public void setJcmDocuments(List<JcmDocument> jcmDocuments) {
		this.jcmDocuments = jcmDocuments;
	}

	public JcmVideo getJcmVideo() {
		return this.jcmVideo;
	}

	public void setJcmVideo(JcmVideo jcmVideo) {
		this.jcmVideo = jcmVideo;
	}

}