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

import com.ocherve.jcm.dao.DaoProxy;
import com.ocherve.jcm.dao.contract.UserDao;

/**
 * The persistent class for the jcm_user database table.
 * 
 */
@Entity
@Table(name="jcm_user")
@NamedQueries({
	@NamedQuery(name="User.findAll", query="SELECT u FROM User u"),
	@NamedQuery(name="User.findByMail", query="SELECT u FROM User u WHERE u.mailAddress like :mailAddress"),
	@NamedQuery(name="User.findUserIdByMail", query="SELECT u.id FROM User u WHERE u.mailAddress like :mailAddress"),
	@NamedQuery(name="User.findUserIdByUsername", query="SELECT u.id FROM User u WHERE u.username like :username"),
	@NamedQuery(name="User.FindUserIdGreaterThan", query="SELECT u FROM User u WHERE u.id >= :idMin"),
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

	//bi-directional many-to-one association to JcmMessage
	@OneToMany(mappedBy="sender")
	private List<Message> sentMessages;
*/
	
	//bi-directional many-to-one association to Comment
	@OneToMany(mappedBy="author")
	private List<Comment> comments;

	//bi-directional many-to-one association to Message
	@OneToMany(mappedBy="sender")
	private List<Message> sentMessages;

	//bi-directional many-to-one association to Message
	@OneToMany(mappedBy="receiver")
	private List<Message> receivedMessages;

	//bi-directional many-to-one association to Reference
	@OneToMany(mappedBy="author")
	private List<Reference> references;
	
	//bi-directional many-to-one association to Role
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
	 * @param roleId
	 * @param username
	 */
	public User(String mailAddress, String username, String password, int roleId) {
		super();
		this.mailAddress = mailAddress;
		this.password = password;
		this.username = username;
		this.salt = "enPetitGrain";
		this.token = "telestprisquicroyaitprendre";
		this.tsAccess = Timestamp.from(Instant.now());
		this.role = ((UserDao) DaoProxy.getInstance().getUserDao()).getRole(roleId);
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
	 * @return list of comments owned by user
	 */
	public List<Comment> getComments() {
		return this.comments;
	}

	/**
	 * @param comments
	 */
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	/**
	 * @param comment
	 * @return comment added to list
	 */
	public Comment addComment(Comment comment) {
		getComments().add(comment);
		comment.setAuthor(this);

		return comment;
	}

	/**
	 * @param comment
	 * @return comment removed from list
	 */
	public Comment removeComment(Comment comment) {
		getComments().remove(comment);
		comment.setAuthor(null);

		return comment;
	}

	/**
	 * @return list of references where user is author
	 */
	public List<Reference> getReferences() {
		return this.references;
	}

	/**
	 * @param references
	 */
	public void setReferences(List<Reference> references) {
		this.references = references;
	}

	/**
	 * @param reference
	 * @return reference added to list
	 */
	public Reference addReference(Reference reference) {
		getReferences().add(reference);
		reference.setAuthor(this);

		return reference;
	}

	/**
	 * @param reference
	 * @return reference removed from list
	 */
	public Reference removeReference(Reference reference) {
		getReferences().remove(reference);
		reference.setAuthor(null);

		return reference;
	}

	/**
	 * @return the sentMessages
	 */
	public List<Message> getSentMessages() {
		return sentMessages;
	}

	/**
	 * @param sentMessages the sentMessages to set
	 */
	public void setSentMessages(List<Message> sentMessages) {
		this.sentMessages = sentMessages;
	}

	/**
	 * @param message
	 * @return message added to list
	 */
	public Message addSentMessage(Message message) {
		getSentMessages().add(message);
		message.setSender(this);

		return message;
	}

	/**
	 * @param message
	 * @return message removed from list
	 */
	public Message removeSentMessage(Message message) {
		getSentMessages().remove(message);
		message.setSender(null);

		return message;
	}

	/**
	 * @return the receivedMessages
	 */
	public List<Message> getReceivedMessages() {
		return receivedMessages;
	}

	/**
	 * @param receivedMessages the receivedMessages to set
	 */
	public void setReceivedMessages(List<Message> receivedMessages) {
		this.receivedMessages = receivedMessages;
	}

	/**
	 * @param message
	 * @return message added to list
	 */
	public Message addReceivedMessage(Message message) {
		getReceivedMessages().add(message);
		message.setReceiver(this);

		return message;
	}

	/**
	 * @param message
	 * @return message removed from list
	 */
	public Message removeReceivedMessage(Message message) {
		getReceivedMessages().remove(message);
		message.setReceiver(null);

		return message;
	}



}
