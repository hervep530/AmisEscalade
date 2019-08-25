package com.ocherve.jcm.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the jcm_access_type database table.
 * 
 */
@Entity
@Table(name="jcm_access_type")
@NamedQuery(name="AccessType.findAll", query="SELECT at FROM AccessType at")
public class AccessType implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String name;

	private String description;

	//bi-directional many-to-one association to JcmRole
	@OneToMany(mappedBy="siteAccess")
	private List<Role> siteAccessRoles;

	//bi-directional many-to-one association to JcmRole
	@OneToMany(mappedBy="topoAccess")
	private List<Role> topoAccessRoles;

	//bi-directional many-to-one association to JcmRole
	@OneToMany(mappedBy="commentAccess")
	private List<Role> commentAccessRoles;

	//bi-directional many-to-one association to JcmRole
	@OneToMany(mappedBy="mediaAccess")
	private List<Role> mediaAccessRoles;

	//bi-directional many-to-one association to JcmRole
	@OneToMany(mappedBy="userAccess")
	private List<Role> userAccessRoles;

	//bi-directional many-to-one association to JcmRole
	@OneToMany(mappedBy="bookingAccess")
	private List<Role> bookingAccessRoles;

	//bi-directional many-to-one association to JcmRole
	@OneToMany(mappedBy="messageAccess")
	private List<Role> messageAccessRoles;

	/**
	 * Basic Constructor
	 */
	public AccessType() {
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the siteAccessRoles
	 */
	public List<Role> getSiteAccessRoles() {
		return siteAccessRoles;
	}

	/**
	 * @param siteAccessRoles the siteAccessRoles to set
	 */
	public void setSiteAccessRoles(List<Role> siteAccessRoles) {
		this.siteAccessRoles = siteAccessRoles;
	}
	
	/**
	 * @return the topoAccessRoles
	 */
	public List<Role> getTopoAccessRoles() {
		return topoAccessRoles;
	}

	/**
	 * @param topoAccessRoles the topoAccessRoles to set
	 */
	public void setTopoAccessRoles(List<Role> topoAccessRoles) {
		this.topoAccessRoles = topoAccessRoles;
	}

	/**
	 * @return the commentAccessRoles
	 */
	public List<Role> getCommentAccessRoles() {
		return commentAccessRoles;
	}

	/**
	 * @param commentAccessRoles the commentAccessRoles to set
	 */
	public void setCommentAccessRoles(List<Role> commentAccessRoles) {
		this.commentAccessRoles = commentAccessRoles;
	}

	/**
	 * @return the mediaAccessRoles
	 */
	public List<Role> getMediaAccessRoles() {
		return mediaAccessRoles;
	}

	/**
	 * @param mediaAccessRoles the mediaAccessRoles to set
	 */
	public void setMediaAccessRoles(List<Role> mediaAccessRoles) {
		this.mediaAccessRoles = mediaAccessRoles;
	}

	/**
	 * @return the userAccessRoles
	 */
	public List<Role> getUserAccessRoles() {
		return userAccessRoles;
	}

	/**
	 * @param userAccessRoles the userAccessRoles to set
	 */
	public void setUserAccessRoles(List<Role> userAccessRoles) {
		this.userAccessRoles = userAccessRoles;
	}

	/**
	 * @return the bookingAccessRoles
	 */
	public List<Role> getBookingAccessRoles() {
		return bookingAccessRoles;
	}

	/**
	 * @param bookingAccessRoles the bookingAccessRoles to set
	 */
	public void setBookingAccessRoles(List<Role> bookingAccessRoles) {
		this.bookingAccessRoles = bookingAccessRoles;
	}

	/**
	 * @return the messageAccessRoles
	 */
	public List<Role> getMessageAccessRoles() {
		return messageAccessRoles;
	}

	/**
	 * @param messageAccessRoles the messageAccessRoles to set
	 */
	public void setMessageAccessRoles(List<Role> messageAccessRoles) {
		this.messageAccessRoles = messageAccessRoles;
	}

	
	
}
