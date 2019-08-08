package com.ocherve.jcm.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the jcm_booking database table.
 * 
 */
@Embeddable
public class JcmBookingPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="pfk_topo", insertable=false, updatable=false)
	private Integer pfkTopo;

	@Column(name="pfk_user", insertable=false, updatable=false)
	private Integer pfkUser;

	public JcmBookingPK() {
	}
	public Integer getPfkTopo() {
		return this.pfkTopo;
	}
	public void setPfkTopo(Integer pfkTopo) {
		this.pfkTopo = pfkTopo;
	}
	public Integer getPfkUser() {
		return this.pfkUser;
	}
	public void setPfkUser(Integer pfkUser) {
		this.pfkUser = pfkUser;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof JcmBookingPK)) {
			return false;
		}
		JcmBookingPK castOther = (JcmBookingPK)other;
		return 
			this.pfkTopo.equals(castOther.pfkTopo)
			&& this.pfkUser.equals(castOther.pfkUser);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.pfkTopo.hashCode();
		hash = hash * prime + this.pfkUser.hashCode();
		
		return hash;
	}
}