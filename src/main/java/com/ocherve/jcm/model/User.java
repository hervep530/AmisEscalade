package com.ocherve.jcm.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the jcm_user database table.
 * 
 */
@Entity
@Table(name="jcm_user")
@NamedQueries({
	@NamedQuery(name="User.findAll", query="SELECT u FROM User u"),
	@NamedQuery(name="User.findUserAtLeastGrantedTo", query="SELECT u FROM User u WHERE u.role.id > :roleIdMini")
})
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="mail_address", unique = true, nullable = false)
	private String mailAddress;

	@Column(unique = true, nullable = false)
	private String username;

	@Column(nullable = false)
	private String password;

	private String salt;

	private String token;

	@Column(name="ts_access", nullable = false)
	private Timestamp tsAccess;

/*	
	//bi-directional many-to-one association to JcmBooking
	@OneToMany(mappedBy="owner")
	private List<Booking> bookings;

	//bi-directional many-to-one association to JcmComment
	@OneToMany(mappedBy="author")
	private List<Comment> comments;

	//bi-directional many-to-one association to JcmMessage
	@OneToMany(mappedBy="sender")
	private List<Message> sentMessages;

	//bi-directional many-to-one association to JcmMessage
	@OneToMany(mappedBy="receiver")
	private List<Message> receivedMessages;
*/

	//bi-directional many-to-one association to JcmDocument
	@OneToMany(mappedBy="author")
	private List<Document> documents;
	
	//bi-directional many-to-one association to JcmRole
	@ManyToOne
	@JoinColumn(name="fk_user_role")
	private Role role;

	/**
	 * Basic constructeur
	 */
	public User() {
	}

	/**
	 * Constructor with parameters
	 * @param mailAddress
	 * @param password
	 * @param salt
	 * @param token
	 * @param username
	 */
	public User(String mailAddress, String username, String password, String salt, String token) {
		super();
		this.mailAddress = mailAddress;
		this.password = password;
		this.salt = salt;
		this.token = token;
		this.username = username;
		this.tsAccess = Timestamp.from(Instant.now());
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
	 * @return the mailAddress
	 */
	public String getMailAddress() {
		return mailAddress;
	}

	/**
	 * @param mailAddress the mailAddress to set
	 */
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
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
	 * @return the salt
	 */
	public String getSalt() {
		return salt;
	}

	/**
	 * @param salt the salt to set
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the tsAccess
	 */
	public Timestamp getTsAccess() {
		return tsAccess;
	}

	/**
	 * @param tsAccess the tsAccess to set
	 */
	public void setTsAccess(Timestamp tsAccess) {
		this.tsAccess = tsAccess;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
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
	 * @return list of documents where user is author
	 */
	public List<Document> getDocuments() {
		return this.documents;
	}

	/**
	 * @param documents
	 */
	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}

	/**
	 * @param document
	 * @return document added to list
	 */
	public Document addDocument(Document document) {
		getDocuments().add(document);
		document.setAuthor(this);

		return document;
	}

	/**
	 * @param document
	 * @return document removed from list
	 */
	public Document removeDocument(Document document) {
		getDocuments().remove(document);
		document.setAuthor(null);

		return document;
	}



}
