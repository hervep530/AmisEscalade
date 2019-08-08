package com.ocherve.jcm.backup.beans;

import java.util.Date;

/**
 * @author herve_dev
 *
 */
public class User {
	
	private Integer id;
	private String email;
	private String firstName;
	private String surName;
	private String login;
	private String password;
	private Role role;
	private String tooken;
	private Date dateAccess;
	
	/**
	 * Constructor
	 */
	public User() {
		super();
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
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the surName
	 */
	public String getSurName() {
		return surName;
	}

	/**
	 * @param surName the surName to set
	 */
	public void setSurName(String surName) {
		this.surName = surName;
	}

	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * @return the tooken
	 */
	public String getTooken() {
		return tooken;
	}

	/**
	 * @param tooken the tooken to set
	 */
	public void setTooken(String tooken) {
		this.tooken = tooken;
	}

	/**
	 * @return the dateAccess
	 */
	public Date getDateAccess() {
		return dateAccess;
	}

	/**
	 * @param dateAccess the dateAccess to set
	 */
	public void setDateAccess(Date dateAccess) {
		this.dateAccess = dateAccess;
	}

}
