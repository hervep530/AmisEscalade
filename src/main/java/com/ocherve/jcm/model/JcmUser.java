package com.ocherve.jcm.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the jcm_user database table.
 * 
 */
@Entity
@Table(name="jcm_user")
@NamedQuery(name="JcmUser.findAll", query="SELECT j FROM JcmUser j")
public class JcmUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="mail_address")
	private String mailAddress;

	private String password;

	private String salt;

	private String token;

	@Column(name="ts_access")
	private String tsAccess;

	private String username;

	//bi-directional many-to-one association to JcmBooking
	@OneToMany(mappedBy="jcmUser")
	private List<JcmBooking> jcmBookings;

	//bi-directional many-to-one association to JcmComment
	@OneToMany(mappedBy="jcmUser")
	private List<JcmComment> jcmComments;

	//bi-directional many-to-one association to JcmDocument
	@OneToMany(mappedBy="jcmUser")
	private List<JcmDocument> jcmDocuments;

	//bi-directional many-to-one association to JcmMessage
	@OneToMany(mappedBy="jcmUser1")
	private List<JcmMessage> jcmMessages1;

	//bi-directional many-to-one association to JcmMessage
	@OneToMany(mappedBy="jcmUser2")
	private List<JcmMessage> jcmMessages2;

	//bi-directional many-to-many association to JcmRole
	@ManyToMany
	@JoinTable(
		name="jcm_user_roles"
		, joinColumns={
			@JoinColumn(name="pfk_user")
			}
		, inverseJoinColumns={
			@JoinColumn(name="pfk_role")
			}
		)
	private List<JcmRole> jcmRoles;

	public JcmUser() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMailAddress() {
		return this.mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return this.salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTsAccess() {
		return this.tsAccess;
	}

	public void setTsAccess(String tsAccess) {
		this.tsAccess = tsAccess;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<JcmBooking> getJcmBookings() {
		return this.jcmBookings;
	}

	public void setJcmBookings(List<JcmBooking> jcmBookings) {
		this.jcmBookings = jcmBookings;
	}

	public JcmBooking addJcmBooking(JcmBooking jcmBooking) {
		getJcmBookings().add(jcmBooking);
		jcmBooking.setJcmUser(this);

		return jcmBooking;
	}

	public JcmBooking removeJcmBooking(JcmBooking jcmBooking) {
		getJcmBookings().remove(jcmBooking);
		jcmBooking.setJcmUser(null);

		return jcmBooking;
	}

	public List<JcmComment> getJcmComments() {
		return this.jcmComments;
	}

	public void setJcmComments(List<JcmComment> jcmComments) {
		this.jcmComments = jcmComments;
	}

	public JcmComment addJcmComment(JcmComment jcmComment) {
		getJcmComments().add(jcmComment);
		jcmComment.setJcmUser(this);

		return jcmComment;
	}

	public JcmComment removeJcmComment(JcmComment jcmComment) {
		getJcmComments().remove(jcmComment);
		jcmComment.setJcmUser(null);

		return jcmComment;
	}

	public List<JcmDocument> getJcmDocuments() {
		return this.jcmDocuments;
	}

	public void setJcmDocuments(List<JcmDocument> jcmDocuments) {
		this.jcmDocuments = jcmDocuments;
	}

	public JcmDocument addJcmDocument(JcmDocument jcmDocument) {
		getJcmDocuments().add(jcmDocument);
		jcmDocument.setJcmUser(this);

		return jcmDocument;
	}

	public JcmDocument removeJcmDocument(JcmDocument jcmDocument) {
		getJcmDocuments().remove(jcmDocument);
		jcmDocument.setJcmUser(null);

		return jcmDocument;
	}

	public List<JcmMessage> getJcmMessages1() {
		return this.jcmMessages1;
	}

	public void setJcmMessages1(List<JcmMessage> jcmMessages1) {
		this.jcmMessages1 = jcmMessages1;
	}

	public JcmMessage addJcmMessages1(JcmMessage jcmMessages1) {
		getJcmMessages1().add(jcmMessages1);
		jcmMessages1.setJcmUser1(this);

		return jcmMessages1;
	}

	public JcmMessage removeJcmMessages1(JcmMessage jcmMessages1) {
		getJcmMessages1().remove(jcmMessages1);
		jcmMessages1.setJcmUser1(null);

		return jcmMessages1;
	}

	public List<JcmMessage> getJcmMessages2() {
		return this.jcmMessages2;
	}

	public void setJcmMessages2(List<JcmMessage> jcmMessages2) {
		this.jcmMessages2 = jcmMessages2;
	}

	public JcmMessage addJcmMessages2(JcmMessage jcmMessages2) {
		getJcmMessages2().add(jcmMessages2);
		jcmMessages2.setJcmUser2(this);

		return jcmMessages2;
	}

	public JcmMessage removeJcmMessages2(JcmMessage jcmMessages2) {
		getJcmMessages2().remove(jcmMessages2);
		jcmMessages2.setJcmUser2(null);

		return jcmMessages2;
	}

	public List<JcmRole> getJcmRoles() {
		return this.jcmRoles;
	}

	public void setJcmRoles(List<JcmRole> jcmRoles) {
		this.jcmRoles = jcmRoles;
	}

}