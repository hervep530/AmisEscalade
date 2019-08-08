package com.ocherve.jcm.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the jcm_message database table.
 * 
 */
@Entity
@Table(name="jcm_message")
@NamedQuery(name="JcmMessage.findAll", query="SELECT j FROM JcmMessage j")
public class JcmMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_message")
	private Integer idMessage;

	@Column(name="dt_created")
	private Timestamp dtCreated;

	@Column(name="dt_read")
	private Timestamp dtRead;

	private String text;

	private String title;

	//bi-directional many-to-one association to JcmMessage
	@ManyToOne
	@JoinColumn(name="parent_id_message")
	private JcmMessage jcmMessage;

	//bi-directional many-to-one association to JcmMessage
	@OneToMany(mappedBy="jcmMessage")
	private List<JcmMessage> jcmMessages;

	//bi-directional many-to-one association to JcmUser
	@ManyToOne
	@JoinColumn(name="fk_sender")
	private JcmUser jcmUser1;

	//bi-directional many-to-one association to JcmUser
	@ManyToOne
	@JoinColumn(name="fk_receiver")
	private JcmUser jcmUser2;

	public JcmMessage() {
	}

	public Integer getIdMessage() {
		return this.idMessage;
	}

	public void setIdMessage(Integer idMessage) {
		this.idMessage = idMessage;
	}

	public Timestamp getDtCreated() {
		return this.dtCreated;
	}

	public void setDtCreated(Timestamp dtCreated) {
		this.dtCreated = dtCreated;
	}

	public Timestamp getDtRead() {
		return this.dtRead;
	}

	public void setDtRead(Timestamp dtRead) {
		this.dtRead = dtRead;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public JcmMessage getJcmMessage() {
		return this.jcmMessage;
	}

	public void setJcmMessage(JcmMessage jcmMessage) {
		this.jcmMessage = jcmMessage;
	}

	public List<JcmMessage> getJcmMessages() {
		return this.jcmMessages;
	}

	public void setJcmMessages(List<JcmMessage> jcmMessages) {
		this.jcmMessages = jcmMessages;
	}

	public JcmMessage addJcmMessage(JcmMessage jcmMessage) {
		getJcmMessages().add(jcmMessage);
		jcmMessage.setJcmMessage(this);

		return jcmMessage;
	}

	public JcmMessage removeJcmMessage(JcmMessage jcmMessage) {
		getJcmMessages().remove(jcmMessage);
		jcmMessage.setJcmMessage(null);

		return jcmMessage;
	}

	public JcmUser getJcmUser1() {
		return this.jcmUser1;
	}

	public void setJcmUser1(JcmUser jcmUser1) {
		this.jcmUser1 = jcmUser1;
	}

	public JcmUser getJcmUser2() {
		return this.jcmUser2;
	}

	public void setJcmUser2(JcmUser jcmUser2) {
		this.jcmUser2 = jcmUser2;
	}

}