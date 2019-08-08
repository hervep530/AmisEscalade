package com.ocherve.jcm.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the jcm_content database table.
 * 
 */
@Embeddable
public class JcmContentPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="pfk_document", insertable=false, updatable=false)
	private Integer pfkDocument;

	@Column(name="pfk_type", insertable=false, updatable=false)
	private Integer pfkType;

	public JcmContentPK() {
	}
	public Integer getPfkDocument() {
		return this.pfkDocument;
	}
	public void setPfkDocument(Integer pfkDocument) {
		this.pfkDocument = pfkDocument;
	}
	public Integer getPfkType() {
		return this.pfkType;
	}
	public void setPfkType(Integer pfkType) {
		this.pfkType = pfkType;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof JcmContentPK)) {
			return false;
		}
		JcmContentPK castOther = (JcmContentPK)other;
		return 
			this.pfkDocument.equals(castOther.pfkDocument)
			&& this.pfkType.equals(castOther.pfkType);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.pfkDocument.hashCode();
		hash = hash * prime + this.pfkType.hashCode();
		
		return hash;
	}
}