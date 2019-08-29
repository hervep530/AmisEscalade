package com.ocherve.jcm.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the jcm_message database table.
 *
 */
@Entity
@Table(name = "jcm_message")
public class Message implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "fk_sender_user")
	private User sender;
	
	@ManyToOne
	@JoinColumn(name = "fk_receiver_user")
	private User receiver;
	
	private String title;
	
	@Column(name = "text")
	private String content;
	
	@Column(name = "dt_created")
	private Timestamp tsSent;
	
	@Column(name = "dt_read")
	private Timestamp tsRead;
	
	@ManyToOne()
	@JoinColumn(name = "id", insertable = false,  updatable = false)
	private Message parent;
	
	@OneToMany(mappedBy = "parent")
	private List<Message> responses;

	/**
	 * 
	 */
	public Message() {
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
	 * @return the sender
	 */
	public User getSender() {
		return sender;
	}

	/**
	 * @param sender the sender to set
	 */
	public void setSender(User sender) {
		this.sender = sender;
	}

	/**
	 * @return the receiver
	 */
	public User getReceiver() {
		return receiver;
	}

	/**
	 * @param receiver the receiver to set
	 */
	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the tsSent
	 */
	public Timestamp getTsSent() {
		return tsSent;
	}

	/**
	 * @param tsSent the tsSent to set
	 */
	public void setTsSent(Timestamp tsSent) {
		this.tsSent = tsSent;
	}

	/**
	 * @return the tsRead
	 */
	public Timestamp getTsRead() {
		return tsRead;
	}

	/**
	 * @param tsRead the tsRead to set
	 */
	public void setTsRead(Timestamp tsRead) {
		this.tsRead = tsRead;
	}

	/**
	 * @return the parent
	 */
	public Message getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(Message parent) {
		this.parent = parent;
	}

	/**
	 * @return the responses
	 */
	public List<Message> getResponses() {
		return responses;
	}

	/**
	 * @param responses the responses to set
	 */
	public void setResponses(List<Message> responses) {
		this.responses = responses;
	}
	
	/**
	 * @param response
	 * @return response added to responses list associated to this as parent
	 */
	public Message addResponse(Message response) {
		getResponses().add(response);
		response.setParent(this);

		return response;
	}

	/**
	 * @param response
	 * @return response removed from list
	 */
	public Message removeResponse(Message response) {
		getResponses().remove(response);
		response.setParent(null);

		return response;
	}


	
	

}
