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
	private List<Message> discussions;
	private int discussionsCount;
	private List<Message> focusedDiscussion;
	private int focusedDiscussionId;
	private int focusedDiscussionCount;
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
	
	private void reset() {
		Configurator.setLevel(DLOG.getName(), DLOGLEVEL);
		this.messageService = (MessageService) ServiceProxy.getInstance().getMessageService();
		this.messageDao = (MessageDao) DaoProxy.getInstance().getMessageDao();
		this.limit = 0;
		try {
			this.limit = messageService.getListLimit().intValue();
		} catch (Exception e) {
			DLOG.log(Level.ERROR, "Invalid LIST_LIMIT in ServiceImpl");
		}
		this.discussions = new ArrayList<Message>();
		this.focusedDiscussion = new ArrayList<Message>();
		this.focusedDiscussionId = 0;
		this.focusedDiscussionCount = 0;
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
		DLOG.log(Level.INFO, "Instanciating MessageBox for user " + userId + " from " + targetType + "...");
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
				this.setMessageBoxFromPageId(userId, targetId);
				break;
			case "DiscussionFocus" :
				this.setMessageBoxFromDiscussionId(userId, targetId);
				break;
			case "MessageFocus" :
				this.setMessageBoxFromMessageId(userId, targetId);
				break;
		}
		DLOG.log(Level.INFO, "MessageBox is now instanciated.");
	}
	
	/**
	 * Set class variables from user id and page id
	 * 
	 * @param userId
	 * @param pageId
	 */
	public void setMessageBoxFromPageId(Integer userId, Integer pageId) {
		// Calculating an setting discussion offset from pageId 
		try {
			if ( pageId > 0 ) this.discussionOffset = (int)((pageId - 1) * this.limit); 
		} catch (Exception ignore) { }
		// Setting queryName, fields of where clause, and getting discussions Lists
		String queryName = "Message.findMyDiscussionsOrderById" + this.sort;
		this.fields.clear();
		this.fields.put("userId", userId);
		this.fields.put("limit", this.limit);
		this.fields.put("offset", this.discussionOffset);		
		this.discussions = messageDao.getListFromNamedQueryWithParameters(queryName, fields);
		
	}
	
	
	/**
	 * Setting messageBox values given a userId and a discussionId
	 * 
	 * @param userId
	 * @param discussionId
	 */
	public void setMessageBoxFromDiscussionId(Integer userId, Integer discussionId) {
		// Properties
		this.focusedDiscussionId = discussionId;
		// Getting count of previous discussion from a given discussionId
		String queryName = "Message.countPreviousDiscussionsOrderById" + this.sort;
		this.fields.put("userId", userId.intValue());
		this.fields.put("discussionId", discussionId.intValue());
		int previousDiscussionsCount = 0;
		try {
			previousDiscussionsCount = messageDao.getCountFromNamedQuery(Message.class, queryName, fields).intValue();
		} catch (Exception e) {
			DLOG.log(Level.ERROR, "Dao Long result of query " + queryName + " is out of Integer range.");
		}
		// Calculating an setting discussion offset from pageId 
		this.discussionOffset = this.setCurrentOffset(this.limit, previousDiscussionsCount);
		// Setting queryName, fields of where clause, and getting discussions List
		queryName = "Message.findMyDiscussionsOrderById" + this.sort;
		this.fields.clear();
		this.fields.put("userId", userId.intValue());
		this.fields.put("limit", this.limit);
		this.fields.put("offset", this.discussionOffset);		
		this.discussions = messageDao.getListFromNamedQueryWithParameters(queryName, fields);
		// Setting queryName, fields of where clause, and getting discussion messages List
		queryName = "Message.findMinesByDiscussionOrderById" + this.sort;
		this.fields.clear();
		this.fields.put("userId", userId.intValue());
		this.fields.put("discussionId", discussionId.intValue());
		this.focusedDiscussion = messageDao.getListFromNamedQueryWithParameters(queryName, fields);
		this.focusedDiscussionCount = this.focusedDiscussion.size();
		if (this.focusedDiscussionCount > 0)
			this.focusedDiscussion.get(0).setLastDiscussionMessage(true);
	}
	
	/**
	 * @param userId
	 * @param messageId
	 */
	public void setMessageBoxFromMessageId(Integer userId, Integer messageId) {
		// convert messageId to integer
		if ( userId < 1 ) return;
		if ( messageId < 1 ) return;
		Integer id = messageId; 
		this.focusedMessage = messageDao.get(id);
		this.setMessageBoxFromDiscussionId(userId, this.focusedMessage.getDiscussionId());
	}
	
	protected void setOffsetsCount(Integer limit, Integer totalCount) {
		try {
			long count = Math.round(totalCount / limit) * limit;
			if ( totalCount / limit > 0 ) count ++;
			this.offsetsCount = (int) count;			
		} catch (Exception e ) {
			DLOG.log(Level.ERROR, "La limite de nombre entier est dépassée.");
		}
	}

	
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
