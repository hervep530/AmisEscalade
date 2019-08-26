package com.ocherve.jcm.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * @author herve_dev
 *
 */
@Entity(name = "Topo")
@Table(name="jcm_topo")
@PrimaryKeyJoinColumn(name = "pfk_topo_document")
@NamedQueries({
	@NamedQuery(name="Topo.findAll", query="SELECT t FROM Topo t"),
	@NamedQuery(name="Topo.findByAvailability", query="SELECT t FROM Topo t WHERE t.available = :available"),
	@NamedQuery(name="Topo.findByPublishingStatus", query="SELECT t FROM Topo t WHERE t.published = :published"),
	@NamedQuery(name="Topo.findByAuthor", 
		query="SELECT t FROM Topo t WHERE t.author.id = :authorId"),
	@NamedQuery(name="Topo.findBySite", 
		query="SELECT t FROM Topo t WHERE t.site.id = :siteId"),
	@NamedQuery(name="Topo.countAll", query="SELECT count(0) FROM Topo t")
})
public class Topo extends Document implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Boolean available;

	private String title;

	@Column(name="ts_writed")
	private Timestamp tsWrited;

	private String writer;

/*
	//bi-directional many-to-one association to JcmBooking
	@OneToMany(mappedBy="jcmTopo")
	private List<JcmBooking> jcmBookings;
*/

	//bi-directional many-to-one association to JcmSite
	@ManyToOne
	@JoinColumn(name="fk_topo_site")
	private Site site;

	/**
	 * Constructor
	 */
	public Topo() {
	}

	/**
	 * Constructor
	 * @param name 
	 * @param title 
	 * @param summary 
	 * @param writer 
	 * @param tsWrited 
	 * @param published 
	 * @param available 
	 * @param author 
	 * @param site 
	 */
	public Topo(String name, String title, String summary, String writer, Timestamp tsWrited,
			boolean published, boolean available, User author, Site site) {
		this.title = title;
		this.setName(name);
		this.setSummary(summary);
		this.writer = writer;
		this.tsWrited = tsWrited;
		this.setPublished(published);
		this.available = available;
		this.setAuthor(author);
		this.site = site;
		this.setTsCreated(Timestamp.from(Instant.now()));
		this.setTsModified(Timestamp.from(Instant.now()));
	}

	/**
	 * @return true if not reserved at this moment
	 */
	public Boolean isAvailable() {
		return this.available;
	}

	/**
	 * @param available
	 */
	public void setAvailable(Boolean available) {
		this.available = available;
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
	public Timestamp getTsWrited() {
		return this.tsWrited;
	}

	/**
	 * @param tsWrited
	 */
	public void setTsWrited(Timestamp tsWrited) {
		this.tsWrited = tsWrited;
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
	 * @return site linked to this topo
	 */
	public Site getSite() {
		return this.site;
	}

	/**
	 * @param site
	 */
	public void setSite(Site site) {
		this.site = site;
	}

}