package com.ocherve.jcm.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import com.ocherve.jcm.dao.DaoProxy;
import com.ocherve.jcm.dao.contract.MessageDao;
import com.ocherve.jcm.service.ServiceProxy;
import com.ocherve.jcm.service.factory.MessageService;
import com.ocherve.jcm.utils.JcmException;

/**
 * Discussion means a message collection which have the same parent
 * Discussion lists is a collection of all discussions owned by a given user
 * 
 * @author herve_dev
 *
 */
public class MessageBox {

    protected static final Logger DLOG = LogManager.getLogger("development_file");
    private static final Level DLOGLEVEL = Level.TRACE;

	// Properties with accessors;
	private int limit;
	private int ownerId;
	private List<Message> discussions;
	private int discussionsCount;
	private List<Message> focusedDiscussion;
	private int focusedDiscussionId;
	private int focusedDiscussionCount;
	private boolean focusedDiscussionMasked;
	private int discussionOffset;
	private Message focusedMessage;
	private int messagesCount;
	private int offsetsCount;
	// Fonctionnal
	private MessageService messageService;
	private MessageDao messageDao;
	private Map<String,Object> fields;
	private String sort;
	
	/**
	 * Constructor
	 */
	public MessageBox() {
		this.reset();
	}
	
	/**
	 * reset : initializing variable in order to prevent NullPointer exceptions
	 */
	private void reset() {
		// initializing all properties...
		Configurator.setLevel(DLOG.getName(), DLOGLEVEL);
		this.messageService = (MessageService) ServiceProxy.getInstance().getMessageService();
		this.messageDao = (MessageDao) DaoProxy.getInstance().getMessageDao();
		this.limit = 0;
		try {
			this.limit = messageService.getListLimit().intValue();
		} catch (Exception e) {
			DLOG.log(Level.ERROR, "Invalid LIST_LIMIT in ServiceImpl");
		}
		this.ownerId = 0;
		this.discussions = new ArrayList<Message>();
		this.focusedDiscussion = new ArrayList<Message>();
		this.focusedDiscussionId = 0;
		this.focusedDiscussionCount = 0;
		this.focusedDiscussionMasked = false;
		this.discussionOffset = 0;
		this.focusedMessage = null;
		this.discussionsCount = 0;
		this.messagesCount = 0;
		this.offsetsCount = 0;
		this.fields = new HashMap<>(); 
		this.sort = "DESC";
		DLOG.log(Level.INFO, "MessageBox initialized with empty values.");
	}
	
	/**
	 * Constructor - DiscussionsList is built from userId without focused discussion nor selectMessage;
	 * 
	 * @param userId
	 * @param targetId 
	 * @param targetType 
	 */
	public MessageBox(Integer userId, Integer targetId, String targetType) {
		this.reset();
		this.ownerId = userId.intValue();
		DLOG.log(Level.INFO, "Instanciating MessageBox for user " + userId + " from " + targetType + "(" + targetId + ")...");
		// Setting discussions count owned by user (sender or receiver) with id userId
		String queryName = "Message.countAllMyDiscussions";
		DLOG.log(Level.INFO, "Getting discussions count...");
		try {
			this.fields.put("userId", userId);
			this.discussionsCount = messageDao.getCountFromNamedQuery(Message.class, queryName, fields).intValue();
		} catch (Exception e) {
			DLOG.log(Level.ERROR, "Cannot get discussionCount  - " + e.getMessage());
		}
		DLOG.log(Level.DEBUG, "Total messages count for user " + userId + " : " + this.discussionsCount);
		
		// Setting Offsets count
		this.setOffsetsCount(this.limit, this.discussionsCount);
		switch (targetType) {
			case "DiscussionsPage" :
				DLOG.log(Level.INFO, "Setting messageBox from user Id and discussionPage Id");
				this.setMessageBoxFromPageId(targetId);
				break;
			case "DiscussionFocus" :
				this.setMessageBoxFromDiscussionId(targetId);
				break;
			case "MessageFocus" :
				this.setMessageBoxFromMessageId(targetId);
				break;
		}
		DLOG.log(Level.INFO, "MessageBox is now instanciated.");
	}
	
	/**
	 * setMessageBoxFromPageId : Setting class variables from user id and page id
	 * 
	 * @param pageId
	 */
	public void setMessageBoxFromPageId(Integer pageId) {
		// Calculating an setting discussion offset from pageId 
		try {
			if ( pageId > 0 ) this.discussionOffset = (int)((pageId - 1) * this.limit); 
		} catch (Exception ignore) { }
		// Setting queryName, fields of where clause, and getting discussions Lists
		String queryName = "Message.findMyDiscussionsOrderById" + this.sort;
		this.fields.clear();
		this.fields.put("userId", this.ownerId);
		this.fields.put("limit", this.limit);
		this.fields.put("offset", this.discussionOffset);		
		this.discussions = messageDao.getListFromNamedQueryWithParameters(queryName, fields);
		
	}
	
	
	/**
	 * setMessageBoxFromDiscussionId : Setting messageBox values given a discussionId
	 * 
	 * @param discussionId
	 */
	public void setMessageBoxFromDiscussionId(Integer discussionId) {
		// Properties
		this.focusedDiscussionId = discussionId;
		// Getting count of previous discussion from a given discussionId
		String queryName = "Message.countPreviousDiscussionsOrderById" + this.sort;
		this.fields.put("userId", this.ownerId);
		this.fields.put("discussionId", discussionId);
		int previousDiscussionsCount = 0;
		try {
			previousDiscussionsCount = messageDao.getCountFromNamedQuery(Message.class, queryName, fields).intValue();
		} catch (Exception e) {
			DLOG.log(Level.ERROR, "Dao Long result of query " + queryName + " is out of Integer range.");
		}
		DLOG.log(Level.DEBUG, "Index of focusDiscussion in myDiscussions is set to " + previousDiscussionsCount);		
		// Calculating an setting discussion offset from pageId 
		this.discussionOffset = this.setCurrentOffset(this.limit, previousDiscussionsCount);
		DLOG.log(Level.DEBUG, "Offset for focusDiscussion is set to " + this.discussionOffset);		
		// Setting queryName, fields of where clause, and getting discussions List
		queryName = "Message.findMyDiscussionsOrderById" + this.sort;
		this.fields.clear();
		this.fields.put("userId", this.ownerId);
		this.fields.put("limit", this.limit);
		this.fields.put("offset", this.discussionOffset);		
		this.discussions = messageDao.getListFromNamedQueryWithParameters(queryName, fields);
		DLOG.log(Level.DEBUG, "Setting MessageBox : " + this.discussions.size() + " discussions in messageBox.");		
		// Setting queryName, fields of where clause, and getting discussion messages List
		queryName = "Message.findMinesByDiscussionOrderById" + this.sort;
		this.fields.clear();
		this.fields.put("userId", this.ownerId);
		this.fields.put("discussionId", discussionId);
		this.focusedDiscussion = messageDao.getListFromNamedQueryWithParameters(queryName, fields);
		// If Query return discussion without message, add discussion (message) itself to list of message
		if ( this.focusedDiscussion.size() == 0 ) {
			try { this.focusedDiscussion.add(messageDao.get(discussionId)); } catch (Exception ignore) {}
		}
		DLOG.log(Level.DEBUG, "Setting MessageBox : " + this.focusedDiscussion.size() + " messages in focusedDiscussion.");		
		// Check if focused discussion is masked by interlocutor
		try { this.checkFocusedDiscussionMaskedStatus(); } catch ( Exception e ) {
			DLOG.log(Level.ERROR, "Checking focusDiscussion masked status : " + e.getMessage());
		}
		// Set focused Discussion count
		this.focusedDiscussionCount = this.focusedDiscussion.size();
		if (this.focusedDiscussionCount > 0) {
			if ( this.sort.contentEquals("DESC") )
				this.focusedDiscussion.get(0).setLastDiscussionMessage(true);
			else 
				this.focusedDiscussion.get(this.focusedDiscussionCount -1).setLastDiscussionMessage(true);
		} else {
			DLOG.log(Level.ERROR, "Can't find any message in focusDiscussion");
		}
	}
	
	
	
	/**
	 * setMessageBoxFromMessageId : Setting messageBox values given a messageId
	 * 
	 * @param messageId
	 */
	public void setMessageBoxFromMessageId(Integer messageId) {
		// convert messageId to integer
		if ( this.ownerId < 1 ) return;
		if ( messageId < 1 ) return;
		Integer id = messageId; 
		this.focusedMessage = messageDao.get(id);
		DLOG.log(Level.DEBUG, "FocusedMessage set successfully. Will set focused discussion with id : " + this.focusedMessage.getDiscussionId());
		this.setMessageBoxFromDiscussionId(this.focusedMessage.getDiscussionId());
	}
	
	/**
	 * setOffsetsCount : Setting offset count from limit and total count of query results
	 * 
	 * @param limit
	 * @param totalCount
	 */
	protected void setOffsetsCount(Integer limit, Integer totalCount) {
		try {
			long count = Math.round(totalCount / limit) * limit;
			if ( totalCount / limit > 0 ) count ++;
			this.offsetsCount = (int) count;			
		} catch (Exception e ) {
			DLOG.log(Level.ERROR, "La limite de nombre entier est dépassée.");
		}
	}

	/**
	 * setCurrentOffset : Setting current offset given limit and position of previous element in query results
	 * 
	 * @param limit
	 * @param previousPosition
	 * @return
	 */
	protected int setCurrentOffset(int limit, int previousPosition) {
		if ( limit <= 0 ) return 0;
		if ( previousPosition < 0 ) return 0;
		try {
			return (int) Math.round(previousPosition / limit) * limit;			
		} catch (Exception e) {
			return 0;
		}
	}
	
	/**
	 * checkFocusedDiscussionMaskedStatus : setting discussion masked status if sender / receiver masked it
	 * 
	 * @throws ModelException :
	 * 			- if focusedDiscussion is null
	 * 			- if focusedDiscussion is empty
	 */
	public void checkFocusedDiscussionMaskedStatus() throws ModelException{
		// Test focusedDiscussion
		if ( this.focusedDiscussion == null ) throw new ModelException("FocusDiscussion is null");
		if ( this.focusedDiscussion.isEmpty() ) throw new ModelException("FocusDiscussion is empty");
		try {
			// Get first message of discussion (id = discussionId)
			int referenceIndex = 0;
			if ( this.sort.contentEquals("DESC") ) referenceIndex = this.focusedDiscussion.size() - 1;
			Message discussionReference = this.focusedDiscussion.get(referenceIndex);
			// Check if discussionMasked field contains id of interlocutor, and set properties if true (default is false)
			int idInterlocutor = discussionReference.getSender().getId();
			if ( idInterlocutor == this.ownerId ) idInterlocutor = discussionReference.getReceiver().getId();
			if ( discussionReference.getDiscussionMasked() == idInterlocutor ) this.focusedDiscussionMasked = true;
		} catch (Exception e) {
			// Log error and throw exception
			DLOG.log(Level.DEBUG, JcmException.formatStackTrace(e));
			throw new ModelException("Error on setting this MessageBox property"); 
		}
		DLOG.log(Level.DEBUG, "Discussion " + (this.focusedDiscussionMasked ? " will be deleted." : " will be masked."));
	}
	
	/**
	 * maskFocusDiscussion : Mask discussion instead of delete
	 */
	public void maskFocusDiscussion() {
		// Set property for Dao Update
		Map<String,Object> fields = new HashMap<>();
		fields.put("discussionMasked", this.ownerId);
		try {
			// Using MessageDao to update
			for ( Message message : this.focusedDiscussion ) {
				this.messageDao.update(message.getId(), fields);
			}
		} catch (Exception e) {
			DLOG.log(Level.ERROR, "Can not mask focusDiscussion - " + e.getMessage());
		}
		DLOG.log(Level.DEBUG, this.focusedDiscussionCount + " message(s) masked successfully.");
	}
	
	/**
	 * deleteFocusDiscussion : Delete discussion when other interlocutor masked it
	 */
	public void deleteFocusDiscussion() {
		try {
			// Using MessageDao to delete
			for ( Message message : this.focusedDiscussion ) {
				this.messageDao.delete(message.getId());
			}
		} catch (Exception e) {
			DLOG.log(Level.ERROR, "Can not delete focusDiscussion - " + e.getMessage());
		}
		DLOG.log(Level.DEBUG, this.focusedDiscussionCount + " message(s) deleted successfully.");
	}

	/**
	 * Getter for focusDiscussionMasked
	 * 
	 * @return true if masked for interlocutor... else false;
	 */
	public boolean isFocusedDiscussionMasked() {
		return this.focusedDiscussionMasked;
	}

	/**
	 * @return the limit
	 */
	public long getLimit() {
		return limit;
	}

	/**
	 * @return the discussions
	 */
	public List<Message> getDiscussions() {
		return discussions;
	}

	/**
	 * @return the discussionsCount
	 */
	public long getDiscussionsCount() {
		return discussionsCount;
	}

	/**
	 * @return the focusedDiscussion
	 */
	public List<Message> getFocusedDiscussion() {
		return focusedDiscussion;
	}

	/**
	 * @return the discussionOffset
	 */
	public long getDiscussionOffset() {
		return discussionOffset;
	}

	/**
	 * @return the messagesCount
	 */
	public long getMessagesCount() {
		return messagesCount;
	}

	/**
	 * @return the offsetsCount
	 */
	public long getOffsetsCount() {
		return offsetsCount;
	}

	/**
	 * @return the focusedMessage
	 */
	public Message getFocusedMessage() {
		return focusedMessage;
	}

	/**
	 * @return the focusedDiscussionId
	 */
	public long getFocusedDiscussionId() {
		return focusedDiscussionId;
	}

	/**
	 * @return the focusedDiscussionCount
	 */
	public long getFocusedDiscussionCount() {
		return focusedDiscussionCount;
	}
		
}
