package com.ocherve.jcm.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.Normalizer;
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
@PrimaryKeyJoinColumn(name = "pfk_topo_reference")
@NamedQueries({
	@NamedQuery(name="Topo.findAll", query="SELECT t FROM Topo t"),
	@NamedQuery(name="Topo.findByPublishingStatus", query="SELECT t FROM Topo t WHERE t.published = :published"),
	@NamedQuery(name="Topo.findByAuthor", 
		query="SELECT t FROM Topo t WHERE t.author.id = :authorId"),
	@NamedQuery(name="Topo.findBySite", 
		query="SELECT t FROM Topo t WHERE t.site.id = :siteId"),
	@NamedQuery(name="Topo.countAll", query="SELECT count(0) FROM Topo t")
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
	 * @param writedAt 
	 * @param published 
	 * @param author 
	 * @param site 
	 */
	public Topo(String name, String title, String summary, String writer, String writedAt,
			boolean published, User author, Site site) {
		this.title = title;
		this.setName(name);
		String slug = Normalizer.normalize(name, Normalizer.Form.NFD).replaceAll("[\u0300-\u036F]", "");
		this.setSlug(slug.replaceAll("\\W", "_").replaceAll("_{1,}","_").toLowerCase());
		this.setSummary(summary);
		this.writer = writer;
		this.writedAt = writedAt;
		this.setPublished(published);
		this.setAuthor(author);
		this.site = site;
		this.setTsCreated(Timestamp.from(Instant.now()));
		this.setTsModified(Timestamp.from(Instant.now()));
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