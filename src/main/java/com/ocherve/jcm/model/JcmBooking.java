package com.ocherve.jcm.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the jcm_booking database table.
 * 
 */
@Entity
@Table(name="jcm_booking")
@NamedQuery(name="JcmBooking.findAll", query="SELECT j FROM JcmBooking j")
public class JcmBooking implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private JcmBookingPK id;

	@Column(name="ts_begin")
	private Timestamp tsBegin;

	@Column(name="ts_end")
	private Timestamp tsEnd;

	//bi-directional many-to-one association to JcmBookingStatus
	@ManyToOne
	@JoinColumn(name="fk_status_enum")
	private JcmBookingStatus jcmBookingStatus;

	//bi-directional many-to-one association to JcmTopo
	@ManyToOne
	@JoinColumn(name="pfk_topo")
	private JcmTopo jcmTopo;

	//bi-directional many-to-one association to JcmUser
	@ManyToOne
	@JoinColumn(name="pfk_user")
	private JcmUser jcmUser;

	public JcmBooking() {
	}

	public JcmBookingPK getId() {
		return this.id;
	}

	public void setId(JcmBookingPK id) {
		this.id = id;
	}

	public Timestamp getTsBegin() {
		return this.tsBegin;
	}

	public void setTsBegin(Timestamp tsBegin) {
		this.tsBegin = tsBegin;
	}

	public Timestamp getTsEnd() {
		return this.tsEnd;
	}

	public void setTsEnd(Timestamp tsEnd) {
		this.tsEnd = tsEnd;
	}

	public JcmBookingStatus getJcmBookingStatus() {
		return this.jcmBookingStatus;
	}

	public void setJcmBookingStatus(JcmBookingStatus jcmBookingStatus) {
		this.jcmBookingStatus = jcmBookingStatus;
	}

	public JcmTopo getJcmTopo() {
		return this.jcmTopo;
	}

	public void setJcmTopo(JcmTopo jcmTopo) {
		this.jcmTopo = jcmTopo;
	}

	public JcmUser getJcmUser() {
		return this.jcmUser;
	}

	public void setJcmUser(JcmUser jcmUser) {
		this.jcmUser = jcmUser;
	}

}