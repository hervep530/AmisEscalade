package com.ocherve.jcm.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the jcm_content_type database table.
 * 
 */
@Entity
@Table(name="jcm_content_type")
@NamedQuery(name="JcmContentType.findAll", query="SELECT j FROM JcmContentType j")
public class JcmContentType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String type;

	//bi-directional many-to-one association to JcmContent
	@OneToMany(mappedBy="jcmContentType")
	private List<JcmContent> jcmContents;

	public JcmContentType() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<JcmContent> getJcmContents() {
		return this.jcmContents;
	}

	public void setJcmContents(List<JcmContent> jcmContents) {
		this.jcmContents = jcmContents;
	}

	public JcmContent addJcmContent(JcmContent jcmContent) {
		getJcmContents().add(jcmContent);
		jcmContent.setJcmContentType(this);

		return jcmContent;
	}

	public JcmContent removeJcmContent(JcmContent jcmContent) {
		getJcmContents().remove(jcmContent);
		jcmContent.setJcmContentType(null);

		return jcmContent;
	}

}