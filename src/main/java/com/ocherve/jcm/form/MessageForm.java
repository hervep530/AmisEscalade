package com.ocherve.jcm.form;

import java.sql.Timestamp;
import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Level;

import com.ocherve.jcm.dao.DaoProxy;
import com.ocherve.jcm.dao.contract.MessageDao;
import com.ocherve.jcm.dao.contract.UserDao;
import com.ocherve.jcm.model.Message;
import com.ocherve.jcm.model.Topo;
import com.ocherve.jcm.model.User;
import com.ocherve.jcm.utils.JcmException;

/**
 * @author herve_dev
 *
 */
public class MessageForm extends Form {
	
	private Message message;
	private MessageDao messageDao;
	private UserDao userDao;

	private final static String USER_SESSION_ATT = "sessionUser";
	private final static String RECEIVED_ID_FIELD = "receiverId";
	private final static String TITLE_FIELD = "title";
	private final static String CONTENT_FIELD = "content";
	private final static String PARENT_ID_FIELD = "parentId";
	// Only for update, so not in used for the moment
	private final static String MESSAGE_ID_FIELD = "messageId";
	
	
	/**
	 * @param request
	 * @param updating
	 */
	@SuppressWarnings("unused")
	public MessageForm(HttpServletRequest request, boolean updating) {
		super();
		messageDao = (MessageDao) DaoProxy.getInstance().getMessageDao();
		userDao = (UserDao) DaoProxy.getInstance().getUserDao();
		this.request = request;
		
		try {
			// Hidden field createSiteControl tell us if it's possible to use getParameter with multipart or not
			if ( this.request.getParameter("partMethod") == null ) this.partMethod = true;
			// instanciating topo or getting it for update
			if ( ! updating ) {
				// Before all when creating topo instanciate new topo, set author from session, type and dates
				this.message = new Message();
				// Set sender from session
				this.message.setSender((User) request.getSession().getAttribute(USER_SESSION_ATT));
				// Set receiver from id in formular hidden field
				Integer receiverId = this.getInputIntegerValue(RECEIVED_ID_FIELD);
				DLOG.log(Level.DEBUG, "ReceiverId : " + receiverId);
				User receiver = null;
				if ( receiverId > 1 ) receiver = userDao.get(receiverId);
				if ( receiver != null ) this.message.setReceiver(receiver);
				// Set title from hidden field
				this.message.setTitle(this.getInputTextValue(TITLE_FIELD));
				// Set parent from id in formular hidden field
				Integer parentId = this.getInputIntegerValue(PARENT_ID_FIELD);
				Message parent = null;
				if ( parentId > 0 ) parent = messageDao.get(parentId);
				if ( parent != null ) this.message.setParent(parent);
				// Set sending timestamp
				this.message.setTsSent(Timestamp.from(Instant.now()));
			} else {	
				// Not used because not message update, but if needed, it's ready...
				Integer messageId = 0;
				if ( getInputTextValue(MESSAGE_ID_FIELD).matches("^[0-9]{1,}$") ) 
					messageId = Integer.valueOf(getInputTextValue(MESSAGE_ID_FIELD));
				// Before all when updating, we get message from id
				this.message = messageDao.get(messageId);
			}
			this.message.setContent(this.getInputTextValue(CONTENT_FIELD));
		} catch (Exception e ) {
			this.errors.put("Internal", "Cannot get formular content.");
			DLOG.log(Level.ERROR, JcmException.formatStackTrace(e));
		}
	}
	
	/**
	 * Given a topo, provide formular data where only content should be added
	 * 
	 * @param topo
	 */
	public MessageForm(Topo topo) {
		super();
		if (topo == null ) {
			DLOG.log(Level.ERROR, "Cannot not provide message formular - topo is null");
			return;
		}
		if (topo.getAuthor() == null || topo.getTitle().isEmpty() ) {
			DLOG.log(Level.ERROR, "Cannot not provide message formular - Title or Author are not defined");
			return;
		}
		this.message = new Message();
		this.message.setTitle("Demande de réservation du topo " + topo.getTitle());
		this.message.setReceiver(topo.getAuthor());
		this.message.setParent(null);
		this.message.setDiscussionId(0);
	}
	
	/**
	 * Given a message, provide formular data where only content should be added
	 * 
	 * @param parentMessage
	 */
	public MessageForm(Message parentMessage) {
		super();
		if ( parentMessage == null ) {
			DLOG.log(Level.ERROR, "Cannot not provide message formular - parentMessage is null");
			return;
		}
		if ( parentMessage.getTitle() ==  null || parentMessage.getSender() == null)  {
			DLOG.log(Level.ERROR, "Cannot not provide message formular - Title or Author are not defined");
			return;
		}
		this.message = new Message();
		String re = "";
		if (parentMessage.getDiscussionId() == 0 ) re = "Re : ";
		this.message.setTitle(re + parentMessage.getTitle());
		this.message.setReceiver(parentMessage.getSender());
		this.message.setParent(parentMessage);
	}
	
	/**
	 * Create message form formular
	 * 
	 * @return message created or null
	 */
	public Message create() {
		Message newMessage = null;
		try { this.validateSender(); } catch (FormException e ) { this.errors.put("internal", e.getMessage()); }
		try { this.validateReceiver(); } catch (FormException e ) { this.errors.put("internal", e.getMessage()); }
		try { this.validateTitle(); } catch (FormException e ) { this.errors.put("internal", e.getMessage()); }
		try { this.validateContent(); } catch (FormException e ) { this.errors.put("content", e.getMessage()); }
		if ( ! this.errors.isEmpty() ) {
			if (this.errors.containsKey("internal") ) DLOG.log(Level.ERROR, this.errors.get("internal"));
			return newMessage;
		}
		newMessage = new Message(this.message.getSender(),
					this.message.getReceiver(), 
					this.message.getTitle(), 
					this.message.getContent(), 
					this.message.getParent());
		newMessage = messageDao.create(newMessage);

		return newMessage;
	}
	
	private void validateSender() throws FormException {
		if ( this.message.getSender() == null ) throw new FormException("Sender is null.");
		int id = this.message.getSender().getId();
		if ( id < 2 ) throw new FormException("Sender has invalid id : " + id);
	}

	private void validateReceiver() throws FormException {
		if ( this.message.getReceiver() == null ) throw new FormException("Receiver is null.");
		int id = this.message.getReceiver().getId();
		if ( id < 2 ) throw new FormException("Receiver has invalid id : " + id);
	}

	private void validateTitle() throws FormException {
		if ( this.message.getTitle() == null ) throw new FormException("Title is null.");
		String title = this.message.getTitle();
		if ( title.length() < 3 ) throw new FormException("Title must contains at least 3 characters : " + title);
	}

	private void validateContent() throws FormException {
		if ( this.message.getContent() == null ) throw new FormException("Content is null.");
		String content = this.message.getContent();
		if ( content.length() < 10 ) throw new FormException("Le contenu doit avoir 10 caractère minimum : " + content);
	}

	/**
	 * @return the message
	 */
	public Message getMessage() {
		return message;
	}

	
}
