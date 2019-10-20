package com.ocherve.jcm.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
import javax.persistence.Transient;


/**
 * The persistent class for the jcm_message database table.
 *
 */
@Entity
@Table(name = "jcm_message")
@NamedQueries({
	@NamedQuery(name="Message.findAll", query="SELECT m FROM Message m"),
	@NamedQuery(name="Message.countAllMyDiscussions", 
				query="SELECT COUNT(0) FROM Message m "
						+ "WHERE ( m.sender.id = :userId OR m.receiver.id = :userId ) "
							+ "AND ( m.id = m.discussionId OR discussionId = 0 )"
							+ "AND NOT m.discussionMasked = :userId " ),
	@NamedQuery(name="Message.findMyDiscussionsOrderByIdDESC", 
				query="SELECT m FROM Message m "
						+ "WHERE ( m.sender.id = :userId OR m.receiver.id = :userId ) "
							+ "AND ( m.id = m.discussionId OR m.discussionId = 0 ) "
							+ "AND NOT m.discussionMasked = :userId "
						+ "ORDER BY m.id DESC"),
	@NamedQuery(name="Message.countPreviousDiscussionsOrderByIdDESC", 
				query="SELECT COUNT(0) FROM Message m "
						+ "WHERE ( m.sender.id = :userId OR m.receiver.id = :userId ) "
							+ "AND ( m.id = m.discussionId OR m.discussionId = 0 ) "
							+ "AND m.id > :discussionId "
							+ "AND NOT m.discussionMasked = :userId "),
	@NamedQuery(name="Message.findMinesByDiscussionOrderByIdDESC", 
				query="SELECT m FROM Message m "
						+ "WHERE ( m.sender.id = :userId OR m.receiver.id = :userId ) "
							+ "AND m.discussionId = :discussionId "
						+ "ORDER BY m.id DESC"),
	@NamedQuery(name="Message.findDiscussionMessagesOrderByIdDESC", 
				query="SELECT m FROM Message m "
						+ "WHERE m.discussionId = :discussionId "
						+ "ORDER BY m.id DESC"),
	@NamedQuery(name="Message.findAllOrderByIdDesc", query="SELECT m FROM Message m ORDER BY m.id DESC")
})
public class Message implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "fk_sender_user", nullable = false)
	private User sender;
	
	@ManyToOne
	@JoinColumn(name = "fk_receiver_user", nullable = false)
	private User receiver;
	
	@Column(nullable = false)
	private String title = "";
	
	@Column(name = "content")
	private String content = "";
	
	@Column(name = "dt_created", columnDefinition = "TIMESTAMP DEFAULT NOW()")
	private Timestamp tsSent = Timestamp.from(Instant.now());
	
	@Column(name = "dt_read", nullable = true)
	private Timestamp tsRead;
	
	@ManyToOne()
	@JoinColumn(name = "parent_id", nullable = true)
	private Message parent;
	
	@Column(name = "discussion_id", columnDefinition = "BIGINT DEFAULT  0")
	private Integer discussionId = 0;
	
	@Column(name = "discussion_masked")
	private Integer discussionMasked = 0;
	
	@Column(name = "last_discussion_message")
	private boolean lastDiscussionMessage = false;

	@OneToMany(mappedBy = "parent")
	private List<Message> responses;
	

	/**
	 * 
	 */
	public Message() {
		super();
	}
	
	/**
	 * @param sender
	 * @param receiver
	 * @param title 
	 * @param content
	 * @param parent
	 */
	public Message(User sender, User receiver, String title, String content, Message parent) {
		super();
		this.sender = sender;
		this.receiver = receiver;
		this.title = title;
		this.content = content;
		this.parent = parent;
		this.discussionId = 0;
		this.discussionMasked = 0;
		synchronizeDiscussionIdFromParent(parent);
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
		synchronizeDiscussionIdFromParent(parent);
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

	/**
	 * @return the discussionId
	 */
	public Integer getDiscussionId() {
		return discussionId;
	}

	/**
	 * @param discussionId the discussionId to set
	 */
	public void setDiscussionId(Integer discussionId) {
		this.discussionId = discussionId;
	}

	private void synchronizeDiscussionIdFromParent(Message parent) {
		this.lastDiscussionMessage = true;
		if (parent != null) {
			this.parent.lastDiscussionMessage = false;
			if (parent.getDiscussionId() == 0) {
				this.discussionId = parent.id;
				this.parent.discussionId = parent.id;
			} else {
				this.discussionId = parent.discussionId;
			}
		}
	}

	/**
	 * Getting partial content (substring of content
	 * 
	 * @return reduced content
	 */
	public String getReducedContent() {
		int lastStringIndex = 69;
		String reducedContent = this.content;
		if (this.content.length() > 70) {
			reducedContent = this.content.substring(0, lastStringIndex);
		} 
		return reducedContent;
	}

	/**
	 * @return the lastDiscussionMessage
	 */
	public boolean isLastDiscussionMessage() {
		return lastDiscussionMessage;
	}

	/**
	 * @param lastDiscussionMessage the lastDiscussionMessage to set
	 */
	public void setLastDiscussionMessage(boolean lastDiscussionMessage) {
		this.lastDiscussionMessage = lastDiscussionMessage;
	}
	
	/**
	 * @return the discussionMasked
	 */
	public Integer getDiscussionMasked() {
		return discussionMasked;
	}
	

	/**
	 * @param discussionMasked the discussionMasked to set
	 */
	public void setDiscussionMasked(Integer discussionMasked) {
		this.discussionMasked = discussionMasked;
	}
	

	/**
	 * @return tsSent under DateTime format
	 */
	public String getDisplayDtSent() {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(this.tsSent);
	}
	
	/**
	 * @return tsRead under DateTime format
	 */
	public String getDisplayDtRead() {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(this.tsRead);
	}
	
}
