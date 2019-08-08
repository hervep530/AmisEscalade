package com.ocherve.jcm.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the jcm_cotation database table.
 * 
 */
@Entity
@Table(name="jcm_cotation")
@NamedQuery(name="JcmCotation.findAll", query="SELECT j FROM JcmCotation j")
public class JcmCotation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String label;

	//bi-directional many-to-one association to JcmSite
	@OneToMany(mappedBy="jcmCotation1")
	private List<JcmSite> jcmSites1;

	//bi-directional many-to-one association to JcmSite
	@OneToMany(mappedBy="jcmCotation2")
	private List<JcmSite> jcmSites2;

	public JcmCotation() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<JcmSite> getJcmSites1() {
		return this.jcmSites1;
	}

	public void setJcmSites1(List<JcmSite> jcmSites1) {
		this.jcmSites1 = jcmSites1;
	}

	public JcmSite addJcmSites1(JcmSite jcmSites1) {
		getJcmSites1().add(jcmSites1);
		jcmSites1.setJcmCotation1(this);

		return jcmSites1;
	}

	public JcmSite removeJcmSites1(JcmSite jcmSites1) {
		getJcmSites1().remove(jcmSites1);
		jcmSites1.setJcmCotation1(null);

		return jcmSites1;
	}

	public List<JcmSite> getJcmSites2() {
		return this.jcmSites2;
	}

	public void setJcmSites2(List<JcmSite> jcmSites2) {
		this.jcmSites2 = jcmSites2;
	}

	public JcmSite addJcmSites2(JcmSite jcmSites2) {
		getJcmSites2().add(jcmSites2);
		jcmSites2.setJcmCotation2(this);

		return jcmSites2;
	}

	public JcmSite removeJcmSites2(JcmSite jcmSites2) {
		getJcmSites2().remove(jcmSites2);
		jcmSites2.setJcmCotation2(null);

		return jcmSites2;
	}

}