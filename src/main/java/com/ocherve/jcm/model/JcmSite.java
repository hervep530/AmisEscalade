package com.ocherve.jcm.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the jcm_site database table.
 * 
 */
@Entity
@Table(name="jcm_site")
@NamedQuery(name="JcmSite.findAll", query="SELECT j FROM JcmSite j")
public class JcmSite implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="pfk_document")
	private Integer pfkDocument;

	private Boolean bloc;

	private Boolean cliff;

	private String country;

	private String departement;

	@Column(name="friend_tag")
	private Boolean friendTag;

	@Column(name="max_height")
	private Integer maxHeight;

	@Column(name="min_height")
	private Integer minHeight;

	private String orientation;

	@Column(name="paths_number")
	private Integer pathsNumber;

	private Boolean wall;

	//bi-directional many-to-one association to JcmCotation
	@ManyToOne
	@JoinColumn(name="min_cotation")
	private JcmCotation jcmCotation1;

	//bi-directional many-to-one association to JcmCotation
	@ManyToOne
	@JoinColumn(name="max_cotation")
	private JcmCotation jcmCotation2;

	//bi-directional one-to-one association to JcmDocument
	@OneToOne
	@JoinColumn(name="pfk_document")
	private JcmDocument jcmDocument;

	//bi-directional many-to-one association to JcmTopo
	@OneToMany(mappedBy="jcmSite")
	private List<JcmTopo> jcmTopos;

	public JcmSite() {
	}

	public Integer getPfkDocument() {
		return this.pfkDocument;
	}

	public void setPfkDocument(Integer pfkDocument) {
		this.pfkDocument = pfkDocument;
	}

	public Boolean getBloc() {
		return this.bloc;
	}

	public void setBloc(Boolean bloc) {
		this.bloc = bloc;
	}

	public Boolean getCliff() {
		return this.cliff;
	}

	public void setCliff(Boolean cliff) {
		this.cliff = cliff;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDepartement() {
		return this.departement;
	}

	public void setDepartement(String departement) {
		this.departement = departement;
	}

	public Boolean getFriendTag() {
		return this.friendTag;
	}

	public void setFriendTag(Boolean friendTag) {
		this.friendTag = friendTag;
	}

	public Integer getMaxHeight() {
		return this.maxHeight;
	}

	public void setMaxHeight(Integer maxHeight) {
		this.maxHeight = maxHeight;
	}

	public Integer getMinHeight() {
		return this.minHeight;
	}

	public void setMinHeight(Integer minHeight) {
		this.minHeight = minHeight;
	}

	public String getOrientation() {
		return this.orientation;
	}

	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

	public Integer getPathsNumber() {
		return this.pathsNumber;
	}

	public void setPathsNumber(Integer pathsNumber) {
		this.pathsNumber = pathsNumber;
	}

	public Boolean getWall() {
		return this.wall;
	}

	public void setWall(Boolean wall) {
		this.wall = wall;
	}

	public JcmCotation getJcmCotation1() {
		return this.jcmCotation1;
	}

	public void setJcmCotation1(JcmCotation jcmCotation1) {
		this.jcmCotation1 = jcmCotation1;
	}

	public JcmCotation getJcmCotation2() {
		return this.jcmCotation2;
	}

	public void setJcmCotation2(JcmCotation jcmCotation2) {
		this.jcmCotation2 = jcmCotation2;
	}

	public JcmDocument getJcmDocument() {
		return this.jcmDocument;
	}

	public void setJcmDocument(JcmDocument jcmDocument) {
		this.jcmDocument = jcmDocument;
	}

	public List<JcmTopo> getJcmTopos() {
		return this.jcmTopos;
	}

	public void setJcmTopos(List<JcmTopo> jcmTopos) {
		this.jcmTopos = jcmTopos;
	}

	public JcmTopo addJcmTopo(JcmTopo jcmTopo) {
		getJcmTopos().add(jcmTopo);
		jcmTopo.setJcmSite(this);

		return jcmTopo;
	}

	public JcmTopo removeJcmTopo(JcmTopo jcmTopo) {
		getJcmTopos().remove(jcmTopo);
		jcmTopo.setJcmSite(null);

		return jcmTopo;
	}

}