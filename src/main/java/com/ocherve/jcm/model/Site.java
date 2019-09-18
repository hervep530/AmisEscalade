package com.ocherve.jcm.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.Normalizer;
import java.time.Instant;
import java.util.ArrayList;
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
	@NamedQuery(name="Site.findAllOrderById", 
		query="SELECT s FROM Site s ORDER BY s.id"),
	@NamedQuery(name="Site.getSlug", 
		query="SELECT s.slug FROM Site s WHERE s.id = :id "),
	@NamedQuery(name="Site.getAuthor", 
		query="SELECT s.author.id FROM Site s WHERE s.id = :id "),
	@NamedQuery(name="Site.findCotationMaxGreaterThan", 
		query="SELECT s FROM Site s WHERE s.cotationMax.id > :cotationMax"),
	@NamedQuery(name="Site.findCotationMinLessThan",
		query="SELECT COUNT(s) as count FROM Site s WHERE s.cotationMin.id <= :cotationMin"),
	@NamedQuery(name="Site.countAll", query="SELECT COUNT(0) as count FROM Site")
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
	private String department = "";

	@Column(name="block")
	private boolean block = false;

	private boolean cliff = false;

	private boolean wall = false;

	@Column(name="max_height")
	private Integer maxHeight = 0;

	@Column(name="min_height")
	private Integer minHeight = 0;

	@Column(name="paths_number")
	private Integer pathsNumber = 0;

	private String orientation = "";

	@Column(name="friend_tag")
	private boolean friendTag = false;

	//bi-directional many-to-one association to JcmCotation
	@ManyToOne
	@JoinColumn(name="fk_min_site_cotation")
	private Cotation cotationMin = null;

	//bi-directional many-to-one association to JcmCotation
	@ManyToOne
	@JoinColumn(name="fk_max_site_cotation")
	private Cotation cotationMax = null;

	//bi-directional many-to-one association to JcmTopo
	@ManyToMany(mappedBy="sites", cascade = {CascadeType.MERGE })
	private List<Topo> topos = new ArrayList<>();
		
	/**
	 * Constructor without argument
	 */
	public Site() {
		setType(ReferenceType.SITE.toString());
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
		super.setType(ReferenceType.SITE.toString());
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
		super.setType(ReferenceType.SITE.toString());
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
	 * set type : transient in Reference, not get from database
	 */
	public void setType(String type) {
		super.setType(ReferenceType.SITE.toString());
	}
	
	/**
	 * @return true if is block
	 */
	public boolean isBlock() {
		return this.block;
	}

	/**
	 * @param block
	 */
	public void setBlock(boolean block) {
		this.block = block;
	}

	/**
	 * @return true if is cliff
	 */
	public boolean isCliff() {
		return this.cliff;
	}

	/**
	 * @param cliff
	 */
	public void setCliff(boolean cliff) {
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
	public boolean isFriendTag() {
		return this.friendTag;
	}

	/**
	 * @param friendTag
	 */
	public void setFriendTag(boolean friendTag) {
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
	public boolean isWall() {
		return this.wall;
	}

	/**
	 * @param wall
	 */
	public void setWall(boolean wall) {
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
		if (this.topos == null) this.topos = new ArrayList<Topo>();
		this.topos.add(topo);
		List<Site> topoSites = topo.getSites();
		if ( topoSites == null) topoSites = new ArrayList<Site>();
		topoSites.add(this);
		topo.setSites(topoSites);
		return topo;
	}

	/**
	 * @param topo
	 * @return topo removed from list
	 */
	public Topo removeTopo(Topo topo) {
		if (this.topos == null) return null;
		if (this.topos.contains(topo)) this.topos.remove(topo);
		List<Site> topoSites = topo.getSites();
		if ( topoSites == null) return topo;
		if ( topoSites.contains(this) ) {
			topoSites.remove(this);
			topo.setSites(topoSites);
		}
		return topo;
	}
	
}
