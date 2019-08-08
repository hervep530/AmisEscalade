package com.ocherve.jcm.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the jcm_access_type database table.
 * 
 */
@Entity
@Table(name="jcm_access_type")
@NamedQuery(name="JcmAccessType.findAll", query="SELECT j FROM JcmAccessType j")
public class JcmAccessType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String description;

	private String name;

	public JcmAccessType() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}