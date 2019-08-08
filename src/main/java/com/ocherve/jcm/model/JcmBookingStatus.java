package com.ocherve.jcm.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the jcm_booking_status database table.
 * 
 */
@Entity
@Table(name="jcm_booking_status")
@NamedQuery(name="JcmBookingStatus.findAll", query="SELECT j FROM JcmBookingStatus j")
public class JcmBookingStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String status;

	//bi-directional many-to-one association to JcmBooking
	@OneToMany(mappedBy="jcmBookingStatus")
	private List<JcmBooking> jcmBookings;

	public JcmBookingStatus() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<JcmBooking> getJcmBookings() {
		return this.jcmBookings;
	}

	public void setJcmBookings(List<JcmBooking> jcmBookings) {
		this.jcmBookings = jcmBookings;
	}

	public JcmBooking addJcmBooking(JcmBooking jcmBooking) {
		getJcmBookings().add(jcmBooking);
		jcmBooking.setJcmBookingStatus(this);

		return jcmBooking;
	}

	public JcmBooking removeJcmBooking(JcmBooking jcmBooking) {
		getJcmBookings().remove(jcmBooking);
		jcmBooking.setJcmBookingStatus(null);

		return jcmBooking;
	}

}