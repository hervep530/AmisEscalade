package com.ocherve.jcm.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.Normalizer;
import java.time.Instant;
import java.util.List;

import javax.persistence.*;

/**
 * The persistent class for the jcm_site database table.
 * 
 */
@Entity(name = "Site")
@Table(name = "jcm_site")
@PrimaryKeyJoinColumn(name = "pfk_site_reference")
@NamedQueries({
	@NamedQuery(name="Site.findAll", query="SELECT s FROM Site s"),
	@NamedQuery(name="Site.findCotationMaxGreaterThan", 
		query="SELECT s FROM Site s WHERE s.cotationMax.id > :cotationMax"),
	@NamedQuery(name="Site.findCotationMinLessThan",
		query="SELECT s FROM Site s WHERE s.cotationMin.id <= :cotationMin"),
	@NamedQuery(name="Site.countAll", query="SELECT count(0) FROM Site s")
})
public class Site extends Reference implements Serializable {

	private static final long serialVersionUID = 1L;

/*	
	//bi-directional one-to-one association to JcmDocument   
	//@OneToOne(mappedBy = "site", cascade = CascadeType.ALL, orphanRemoval = true )
	@OneToOne
	@JoinColumn(name="id")
	private Document document;
*/
	
	private String country;

	@Column(name="department")
	private String department;

	@Column(name="block")
	private Boolean block;

	private Boolean cliff;

	private Boolean wall;

	@Column(name="max_height")
	private Integer maxHeight;

	@Column(name="min_height")
	private Integer minHeight;

	@Column(name="paths_number")
	private Integer pathsNumber;

	private String orientation;

	@Column(name="friend_tag")
	private Boolean friendTag;

	//bi-directional many-to-one association to JcmCotation
	@ManyToOne
	@JoinColumn(name="fk_min_site_cotation")
	private Cotation cotationMin;

	//bi-directional many-to-one association to JcmCotation
	@ManyToOne
	@JoinColumn(name="fk_max_site_cotation")
	private Cotation cotationMax;

	//bi-directional many-to-one association to JcmTopo
	@OneToMany(mappedBy="site")
	private List<Topo> topos;
		
	/**
	 * Constructor without argument
	 */
	public Site() {
	}

	/**
	 * @param name
	 * @param country 
	 * @param department 
	 * @param summary 
	 * @param published 
	 * @param block 
	 * @param cliff 
	 * @param wall 
	 * @param minHeight 
	 * @param maxHeight 
	 * @param orientation 
	 * @param pathsNumber 
	 * @param friendTag 
	 */
	public Site(String name, String country, String department, String summary, boolean published,
			boolean block, boolean cliff, boolean wall, int minHeight, int maxHeight,
			String orientation, int pathsNumber,boolean friendTag) {
		this.setName(name);
		String slug = Normalizer.normalize(name, Normalizer.Form.NFD).replaceAll("[\u0300-\u036F]", "");
		this.setSlug(slug.replaceAll("\\W", "_").replaceAll("_{1,}","_").toLowerCase());
		this.setSummary(summary);
		this.setPublished(published);
		this.setTsCreated(Timestamp.from(Instant.now()));
		this.setTsModified(Timestamp.from(Instant.now()));
		this.country = country;
		this.department = department;
		this.block = block;
		this.cliff = cliff;
		this.wall = wall;
		this.minHeight = minHeight;
		this.maxHeight = maxHeight;
		this.orientation = orientation;
		this.pathsNumber = pathsNumber;
		this.friendTag = friendTag;
	}
	
	/**
	 * @param name
	 * @param country 
	 * @param department 
	 * @param summary 
	 * @param published 
	 * @param block 
	 * @param cliff 
	 * @param wall 
	 * @param minHeight 
	 * @param maxHeight 
	 * @param orientation 
	 * @param pathsNumber 
	 * @param cotationMin 
	 * @param cotationMax 
	 * @param author 
	 * @param friendTag 
	 */
	public Site(String name, String country, String department, String summary, boolean published,
			boolean block, boolean cliff, boolean wall, int minHeight, int maxHeight,
			String orientation, int pathsNumber, Cotation cotationMin, Cotation cotationMax, User author,
			boolean friendTag) {

		this.setName(name);
		String slug = Normalizer.normalize(name, Normalizer.Form.NFD).replaceAll("[\u0300-\u036F]", "");
		this.setSlug(slug.replaceAll("\\W", "_").replaceAll("_{1,}","_").toLowerCase());
		this.country = country;
		this.department = department;
		this.setSummary(summary);
		this.setAuthor(author);
		this.setPublished(published);
		this.setTsCreated(Timestamp.from(Instant.now()));
		this.setTsModified(Timestamp.from(Instant.now()));
		this.block = block;
		this.cliff = cliff;
		this.wall = wall;
		this.minHeight = minHeight;
		this.maxHeight = maxHeight;
		this.orientation = orientation;
		this.pathsNumber = pathsNumber;
		this.cotationMin = cotationMin;
		this.cotationMax = cotationMax;
		this.friendTag = friendTag;
	}
	
	/**
	 * @return pfkDocument
	public Integer getPfkDocument() {
		return this.pfkDocument;
	}
	 */

	/**
	 * @param pfkDocument
	public void setId(Integer pfkDocument) {
		this.pfkDocument = pfkDocument;
	}
	 */

	/**
	 * @return true if is block
	 */
	public Boolean isBlock() {
		return this.block;
	}

	/**
	 * @param block
	 */
	public void setBlock(Boolean block) {
		this.block = block;
	}

	/**
	 * @return true if is cliff
	 */
	public Boolean isCliff() {
		return this.cliff;
	}

	/**
	 * @param cliff
	 */
	public void setCliff(Boolean cliff) {
		this.cliff = cliff;
	}

	/**
	 * @return country
	 */
	public String getCountry() {
		return this.country;
	}

	/**
	 * @param country
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return department
	 */
	public String getDepartment() {
		return this.department;
	}

	/**
	 * @param department
	 */
	public void setDepartment(String department) {
		this.department = department;
	}

	/**
	 * @return true if is tagged as friend site
	 */
	public Boolean isFriendTag() {
		return this.friendTag;
	}

	/**
	 * @param friendTag
	 */
	public void setFriendTag(Boolean friendTag) {
		this.friendTag = friendTag;
	}

	/**
	 * @return max height in meters
	 */
	public Integer getMaxHeight() {
		return this.maxHeight;
	}

	/**
	 * @param maxHeight
	 */
	public void setMaxHeight(Integer maxHeight) {
		this.maxHeight = maxHeight;
	}

	/**
	 * @return min height in meters
	 */
	public Integer getMinHeight() {
		return this.minHeight;
	}

	/**
	 * @param minHeight
	 */
	public void setMinHeight(Integer minHeight) {
		this.minHeight = minHeight;
	}

	/**
	 * @return orientation
	 */
	public String getOrientation() {
		return this.orientation;
	}

	/**
	 * @param orientation
	 */
	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

	/**
	 * @return number of paths
	 */
	public Integer getPathsNumber() {
		return this.pathsNumber;
	}

	/**
	 * @param pathsNumber
	 */
	public void setPathsNumber(Integer pathsNumber) {
		this.pathsNumber = pathsNumber;
	}

	/**
	 * @return true if is wall
	 */
	public Boolean isWall() {
		return this.wall;
	}

	/**
	 * @param wall
	 */
	public void setWall(Boolean wall) {
		this.wall = wall;
	}

	/**
	 * @return cotation min
	 */
	public Cotation getCotationMin() {
		return this.cotationMin;
	}

	/**
	 * @param cotationMin
	 */
	public void setCotationMin(Cotation cotationMin) {
		this.cotationMin = cotationMin;
	}

	/**
	 * @return cotation max
	 */
	public Cotation getCotationMax() {
		return this.cotationMax;
	}

	/**
	 * @param cotationMax
	 */
	public void setCotationMax(Cotation cotationMax) {
		this.cotationMax = cotationMax;
	}

	/**
	 * @return topo list
	 */
	public List<Topo> getTopos() {
		return this.topos;
	}

	/**
	 * @param topos
	 */
	public void setTopos(List<Topo> topos) {
		this.topos = topos;
	}

	/**
	 * @param topo
	 * @return topo added to topos list associated to this site
	 */
	public Topo addTopo(Topo topo) {
		getTopos().add(topo);
		topo.setSite(this);

		return topo;
	}

	/**
	 * @param topo
	 * @return topo removed from list
	 */
	public Topo removeTopo(Topo topo) {
		getTopos().remove(topo);
		topo.setSite(null);

		return topo;
	}
	
}
