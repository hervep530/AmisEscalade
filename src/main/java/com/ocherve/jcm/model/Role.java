package com.ocherve.jcm.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the jcm_role database table.
 * 
 */
@Entity
@Table(name="jcm_role")
@NamedQuery(name="Role.findAll", query="SELECT r FROM Role r")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String name;

	//bi-directional many-to-one association to JcmAccessType
	@ManyToOne
	@JoinColumn(name="fk_site_access", insertable=false, updatable=false)
	private AccessType siteAccess;

	//bi-directional many-to-one association to JcmAccessType
	@ManyToOne
	@JoinColumn(name="fk_topo_access", insertable=false, updatable=false)
	private AccessType topoAccess;

	//bi-directional many-to-one association to JcmAccessType
	@ManyToOne
	@JoinColumn(name="fk_comment_access", insertable=false, updatable=false)
	private AccessType commentAccess;

	//bi-directional many-to-one association to JcmAccessType
	@ManyToOne
	@JoinColumn(name="fk_media_access", insertable=false, updatable=false)
	private AccessType mediaAccess;

	//bi-directional many-to-one association to JcmAccessType
	@ManyToOne
	@JoinColumn(name="fk_user_access", insertable=false, updatable=false)
	private AccessType userAccess;

	//bi-directional many-to-one association to JcmAccessType
	@ManyToOne
	@JoinColumn(name="fk_booking_access", insertable=false, updatable=false)
	private AccessType bookingAccess;

	//bi-directional many-to-one association to JcmAccessType
	@ManyToOne
	@JoinColumn(name="fk_message_access", insertable=false, updatable=false)
	private AccessType messageAccess;

	//bi-directional many-to-one association to JcmUser
	@OneToMany(mappedBy="role")
	private List<User> users;

	/**
	 * Basic Role Constructor
	 */
	public Role() {
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
	 * @return the siteAccess
	 */
	public AccessType getSiteAccess() {
		return siteAccess;
	}

	/**
	 * @param siteAccess the siteAccess to set
	 */
	public void setSiteAccess(AccessType siteAccess) {
		this.siteAccess = siteAccess;
	}

	/**
	 * @return the topoAccess
	 */
	public AccessType getTopoAccess() {
		return topoAccess;
	}

	/**
	 * @param topoAccess the topoAccess to set
	 */
	public void setTopoAccess(AccessType topoAccess) {
		this.topoAccess = topoAccess;
	}

	/**
	 * @return the commentAccess
	 */
	public AccessType getCommentAccess() {
		return commentAccess;
	}

	/**
	 * @param commentAccess the commentAccess to set
	 */
	public void setCommentAccess(AccessType commentAccess) {
		this.commentAccess = commentAccess;
	}

	/**
	 * @return the mediaAccess
	 */
	public AccessType getMediaAccess() {
		return mediaAccess;
	}

	/**
	 * @param mediaAccess the mediaAccess to set
	 */
	public void setMediaAccess(AccessType mediaAccess) {
		this.mediaAccess = mediaAccess;
	}

	/**
	 * @return the userAccess
	 */
	public AccessType getUserAccess() {
		return userAccess;
	}

	/**
	 * @param userAccess the userAccess to set
	 */
	public void setUserAccess(AccessType userAccess) {
		this.userAccess = userAccess;
	}

	/**
	 * @return the bookingAccess
	 */
	public AccessType getBookingAccess() {
		return bookingAccess;
	}

	/**
	 * @param bookingAccess the bookingAccess to set
	 */
	public void setBookingAccess(AccessType bookingAccess) {
		this.bookingAccess = bookingAccess;
	}

	/**
	 * @return the messageAccess
	 */
	public AccessType getMessageAccess() {
		return messageAccess;
	}

	/**
	 * @param messageAccess the messageAccess to set
	 */
	public void setMessageAccess(AccessType messageAccess) {
		this.messageAccess = messageAccess;
	}

	/**
	 * @return the users
	 */
	public List<User> getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	public User addUser(User user) {
		getUsers().add(user);
		user.setRole(this);

		return user;
	}

	public User removeUser(User user) {
		getUsers().remove(user);
		user.setRole(null);

		return user;
	}

}
