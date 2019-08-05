package com.ocherve.jcm.beans;

import java.util.Date;

/**
 * @author herve_dev
 *
 * Message object - use to have booking agreement
 */
public class Message {

	private Integer id;
	private User sender;
	private User receiver;
	private String subject;
	private String Content;
	private Date dateSent;
	private Date dateRead;
	private Message parent;
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
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return Content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		Content = content;
	}
	/**
	 * @return the dateSent
	 */
	public Date getDateSent() {
		return dateSent;
	}
	/**
	 * @param dateSent the dateSent to set
	 */
	public void setDateSent(Date dateSent) {
		this.dateSent = dateSent;
	}
	/**
	 * @return the dateRead
	 */
	public Date getDateRead() {
		return dateRead;
	}
	/**
	 * @param dateRead the dateRead to set
	 */
	public void setDateRead(Date dateRead) {
		this.dateRead = dateRead;
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
	
}
