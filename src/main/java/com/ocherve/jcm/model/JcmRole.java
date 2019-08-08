package com.ocherve.jcm.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the jcm_role database table.
 * 
 */
@Entity
@Table(name="jcm_role")
@NamedQuery(name="JcmRole.findAll", query="SELECT j FROM JcmRole j")
public class JcmRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="booking_access")
	private Integer bookingAccess;

	@Column(name="comment_access")
	private Integer commentAccess;

	@Column(name="media_access")
	private Integer mediaAccess;

	@Column(name="message_access")
	private Integer messageAccess;

	private String name;

	@Column(name="site_access")
	private Integer siteAccess;

	@Column(name="topo_access")
	private Integer topoAccess;

	@Column(name="user_access")
	private String userAccess;

	//bi-directional many-to-many association to JcmUser
	@ManyToMany(mappedBy="jcmRoles")
	private List<JcmUser> jcmUsers;

	public JcmRole() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBookingAccess() {
		return this.bookingAccess;
	}

	public void setBookingAccess(Integer bookingAccess) {
		this.bookingAccess = bookingAccess;
	}

	public Integer getCommentAccess() {
		return this.commentAccess;
	}

	public void setCommentAccess(Integer commentAccess) {
		this.commentAccess = commentAccess;
	}

	public Integer getMediaAccess() {
		return this.mediaAccess;
	}

	public void setMediaAccess(Integer mediaAccess) {
		this.mediaAccess = mediaAccess;
	}

	public Integer getMessageAccess() {
		return this.messageAccess;
	}

	public void setMessageAccess(Integer messageAccess) {
		this.messageAccess = messageAccess;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSiteAccess() {
		return this.siteAccess;
	}

	public void setSiteAccess(Integer siteAccess) {
		this.siteAccess = siteAccess;
	}

	public Integer getTopoAccess() {
		return this.topoAccess;
	}

	public void setTopoAccess(Integer topoAccess) {
		this.topoAccess = topoAccess;
	}

	public String getUserAccess() {
		return this.userAccess;
	}

	public void setUserAccess(String userAccess) {
		this.userAccess = userAccess;
	}

	public List<JcmUser> getJcmUsers() {
		return this.jcmUsers;
	}

	public void setJcmUsers(List<JcmUser> jcmUsers) {
		this.jcmUsers = jcmUsers;
	}

}