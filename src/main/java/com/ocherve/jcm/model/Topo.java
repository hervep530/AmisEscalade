package com.ocherve.jcm.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.Normalizer;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.ocherve.jcm.dao.DaoProxy;
import com.ocherve.jcm.dao.contract.SiteDao;

/**
 * @author herve_dev
 *
 */
@Entity()
@Table(name="jcm_topo")
@PrimaryKeyJoinColumn(name = "pfk_topo_reference")
@NamedQueries({
	@NamedQuery(name="Topo.findAll", query="SELECT t FROM Topo t"),
	@NamedQuery(name="Topo.findByPublishingStatusOrderByIdDesc", 
		query="SELECT t FROM Topo t WHERE t.published = :published ORDER BY t.id DESC"),
	@NamedQuery(name="Topo.findByPublishingStatusOrderByIdAsc", 
		query="SELECT t FROM Topo t WHERE t.published = :published ORDER BY t.id ASC"),
	@NamedQuery(name="Topo.findByAuthor", 
		query="SELECT t FROM Topo t WHERE t.author.id = :authorId"),
	@NamedQuery(name="Topo.findBySite", 
		query="SELECT t FROM Topo t JOIN t.sites s WHERE s.id = :siteId"),
	@NamedQuery(name="Topo.countAll", query="SELECT count(0) FROM Topo t"),
	@NamedQuery(name="Topo.countByPublishingStatus", 
		query="SELECT count(0) FROM Topo t WHERE t.published = :published")
})
public class Topo extends Reference implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String title;

	@Column(name="writed_at")
	private String writedAt;

	private String writer;

/*
	//bi-directional many-to-one association to JcmBooking
	@OneToMany(mappedBy="jcmTopo")
	private List<JcmBooking> jcmBookings;
*/

	//bi-directional many-to-one association to JcmSite
	@ManyToMany(cascade = {CascadeType.MERGE})
	@JoinTable(
			name = "jcm_topo_site",
			joinColumns = @JoinColumn(name="pfk_topo"),
			inverseJoinColumns = @JoinColumn(name="pfk_site")
	)
	private List<Site> sites = new ArrayList<>();

	/**
	 * Constructor
	 */
	public Topo() {
		super.setType(ReferenceType.TOPO.toString());
	}

	/**
	 * Constructor
	 * @param name 
	 * @param title 
	 * @param summary 
	 * @param writer 
	 * @param writedAt 
	 * @param published 
	 * @param author 
	 * @param siteIds 
	 */
	public Topo(String name, String title, String summary, String writer, String writedAt,
			boolean published, User author, Integer[] siteIds) {
		super.setType(ReferenceType.TOPO.toString());
		this.title = title;
		this.setName(name);
		String slug = Normalizer.normalize(name, Normalizer.Form.NFD).replaceAll("[\u0300-\u036F]", "");
		this.setSlug(slug.replaceAll("\\W", "_").replaceAll("_{1,}","_").toLowerCase());
		this.setSummary(summary);
		this.writer = writer;
		this.writedAt = writedAt;
		this.setPublished(published);
		this.setAuthor(author);
		this.addSites(siteIds);
		this.setTsCreated(Timestamp.from(Instant.now()));
		this.setTsModified(Timestamp.from(Instant.now()));
	}

	/**
	 * set type : transient in Reference, not get from database
	 */
	public void setType(String type) {
		super.setType(ReferenceType.TOPO.toString());
	}
	

	/**
	 * @return title
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return date when topo was written
	 */
	public String getWritedAt() {
		return this.writedAt;
	}

	/**
	 * @param writedAt
	 */
	public void setWritedAt(String writedAt) {
		this.writedAt = writedAt;
	}

	/**
	 * @return name of writer who write topo (the book)
	 */
	public String getWriter() {
		return this.writer;
	}

	/**
	 * @param writer
	 */
	public void setWriter(String writer) {
		this.writer = writer;
	}
	
/*
	public List<JcmBooking> getJcmBookings() {
		return this.jcmBookings;
	}

	public void setJcmBookings(List<JcmBooking> jcmBookings) {
		this.jcmBookings = jcmBookings;
	}

	public JcmBooking addJcmBooking(JcmBooking jcmBooking) {
		getJcmBookings().add(jcmBooking);
		jcmBooking.setJcmTopo(this);

		return jcmBooking;
	}

	public JcmBooking removeJcmBooking(JcmBooking jcmBooking) {
		getJcmBookings().remove(jcmBooking);
		jcmBooking.setJcmTopo(null);

		return jcmBooking;
	}
*/

	/**
	 * @return sites List linked to this topo
	 */
	public List<Site> getSites() {
		return this.sites;
	}

	/**
	 * @param sites list of sites related to this topo
	 */
	public void setSites(List<Site> sites) {
		this.sites = sites;
	}
	
	/**
	 * @param site
	 * @return site added to sites list
	 */
	public Site addSite(Site site) {
		if (this.sites == null) this.sites = new ArrayList<Site>();
		this.sites.add(site);
		List<Topo> siteTopos = site.getTopos();
		if ( siteTopos == null) siteTopos = new ArrayList<Topo>();
		siteTopos.add(this);
		site.setTopos(siteTopos);
		return site;
	}
	
	/**
	 * @param site
	 * @return site removed to sites list
	 */
	public Site removeSite(Site site) {
		if ( this.sites == null ) return null;
		if (this.sites.contains(site)) this.sites.remove(site);
		List<Topo> siteTopos = site.getTopos();
		if ( siteTopos == null) return site;
		if ( siteTopos.contains(this) ) {
			siteTopos.remove(this);
			site.setTopos(siteTopos);
		}
		return site;
	}
	
 	/**
 	 * Add sites from integer array of ids given as argument
	 * @param siteIds
	 */
	public void addSites(Integer[] siteIds) {
		if ( siteIds == null ) return;
		for ( int i = 0; i < siteIds.length; i++ ) {
			Site site = null;
			if ( siteIds[i] > 0 )
					site = ((SiteDao) DaoProxy.getInstance().getSiteDao()).get(Integer.valueOf(siteIds[i]));
			if ( site != null ) this.addSite(site);
		}
	}

 	/**
 	 * Add sites from string lis of ids with ":" separator, given as argument
	 * @param siteIds
	 */
	public void addSites(String siteIds) {
		// siteIds must be provided under form "number1[:number2][:number3][:...]"
		Integer[] ids = null;
		// Parsing string given as argument and build integer array
		if ( siteIds.matches("^[0-9]{1,9}(:[0-9]{1,9})*$") ) {
			String[] split = siteIds.split(":");
			ids = new Integer[split.length];
			for ( int s = 0; s < split.length; s++ ) {
				ids[s] = Integer.valueOf(split[s]); 
			}
		}
		// And now... Calling method with Integer array argument
		this.addSites(ids);
	}

	/**
	 * Another variant of add multiple sites under object form
	 * @param sites
	 */
	public void addSites(List<Site> sites) {
		for (Site site : sites) {
			this.addSite(site);
		}
	}
	
}