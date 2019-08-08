package com.ocherve.jcm.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the jcm_topo database table.
 * 
 */
@Entity
@Table(name="jcm_topo")
@NamedQuery(name="JcmTopo.findAll", query="SELECT j FROM JcmTopo j")
public class JcmTopo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="pfk_document")
	private Integer pfkDocument;

	private Boolean available;

	private Boolean published;

	private String title;

	@Column(name="ts_writed")
	private Timestamp tsWrited;

	private String writer;

	//bi-directional many-to-one association to JcmBooking
	@OneToMany(mappedBy="jcmTopo")
	private List<JcmBooking> jcmBookings;

	//bi-directional one-to-one association to JcmDocument
	@OneToOne
	@JoinColumn(name="pfk_document")
	private JcmDocument jcmDocument;

	//bi-directional many-to-one association to JcmSite
	@ManyToOne
	@JoinColumn(name="pfk_card_reference")
	private JcmSite jcmSite;

	public JcmTopo() {
	}

	public Integer getPfkDocument() {
		return this.pfkDocument;
	}

	public void setPfkDocument(Integer pfkDocument) {
		this.pfkDocument = pfkDocument;
	}

	public Boolean getAvailable() {
		return this.available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public Boolean getPublished() {
		return this.published;
	}

	public void setPublished(Boolean published) {
		this.published = published;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Timestamp getTsWrited() {
		return this.tsWrited;
	}

	public void setTsWrited(Timestamp tsWrited) {
		this.tsWrited = tsWrited;
	}

	public String getWriter() {
		return this.writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

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

	public JcmDocument getJcmDocument() {
		return this.jcmDocument;
	}

	public void setJcmDocument(JcmDocument jcmDocument) {
		this.jcmDocument = jcmDocument;
	}

	public JcmSite getJcmSite() {
		return this.jcmSite;
	}

	public void setJcmSite(JcmSite jcmSite) {
		this.jcmSite = jcmSite;
	}

}