package com.ocherve.jcm.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the jcm_video database table.
 * 
 */
@Entity
@Table(name="jcm_video")
@NamedQuery(name="JcmVideo.findAll", query="SELECT j FROM JcmVideo j")
public class JcmVideo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="pfk_media")
	private Integer pfkMedia;

	//bi-directional one-to-one association to JcmMedia
	@OneToOne
	@JoinColumn(name="pfk_media")
	private JcmMedia jcmMedia;

	public JcmVideo() {
	}

	public Integer getPfkMedia() {
		return this.pfkMedia;
	}

	public void setPfkMedia(Integer pfkMedia) {
		this.pfkMedia = pfkMedia;
	}

	public JcmMedia getJcmMedia() {
		return this.jcmMedia;
	}

	public void setJcmMedia(JcmMedia jcmMedia) {
		this.jcmMedia = jcmMedia;
	}

}