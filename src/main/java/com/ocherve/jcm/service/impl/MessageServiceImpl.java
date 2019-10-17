package com.ocherve.jcm.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Level;

import com.ocherve.jcm.dao.DaoProxy;
import com.ocherve.jcm.dao.contract.MessageDao;
import com.ocherve.jcm.form.MessageForm;
import com.ocherve.jcm.model.MessageBox;
import com.ocherve.jcm.service.Delivry;
import com.ocherve.jcm.service.Parameters;
import com.ocherve.jcm.service.factory.MessageService;
import com.ocherve.jcm.utils.JcmException;

/**
 * MessageService Implementation
 * 
 * @author herve_dev
 *
 */
public class MessageServiceImpl extends ServiceImpl implements MessageService {

	protected final static String SVC_DEFAULT_URL = "";
	/*
	 * la : listing all messages
	 * lmd : listing my discussions
	 * lfd : listing my discussions and focusing on discussion where id is in url
	 * r : display message and list discussion with focusing on discussion containing message
	 */
	protected final static String[][] SVC_ACTIONS = {
			{"la","/message/la/$id/$slug"},
			{"lmd","/message/lmd/$id/$slug"},
			{"lfd","/message/lmd/$id/$slug"},
			{"r","/message/r/$id/$slug"},
			{"c","/message/c/$id/$slug"},
			{"d","/message/d/$id/$slug"}
	};

	/**
	 * Constructor 
	 */
	public MessageServiceImpl() {
		super(SVC_DEFAULT_URL);
	}
	
	public Parameters setParameters(HttpServletRequest request) {
		Parameters parameters = super.setParameters(request);
		// Overloading set parameters with creating and adding new form to parameters
		if ( request.getMethod().contentEquals("POST") ) {
			if (parameters.getParsedUrl().getAction().contentEquals("c") ) {
					parameters.setForm(new MessageForm(request,false));
			}
		}
		return parameters;
	}
	

	@Override
	public Delivry getList(Parameters parameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Delivry getMyDiscussions(Parameters parameters) {
		this.delivry = new Delivry();
		MessageBox messageBox;
		// Getting new MessageBox (type DiscutionPage) from userId and pageId
		try {
			int userId = Integer.valueOf(parameters.getSessionUser().getId());
			int pageId = Integer.valueOf(parameters.getParsedUrl().getId());
		
			
			
			DLOG.log(Level.DEBUG, "Getting message box with userId " + userId + " and pageId " + pageId);
			
			messageBox = new MessageBox(userId, pageId, "DiscussionsPage");			
			
			DLOG.log(Level.DEBUG, "Message box contains " + messageBox.getDiscussionsCount() + " discussions");
		} catch (Exception e) {
			messageBox = new MessageBox();
			DLOG.log(Level.ERROR, JcmException.formatStackTrace(e));
			DLOG.log(Level.DEBUG, "Error when creating message box.");
		}

		
		
		
		// Appending data to result and return it
		this.delivry.appendattribute("messageBox", messageBox);
		this.appendMandatoryAttributesToDelivry(parameters);
		
		return this.delivry;
	}
	
	@Override
	public Delivry getFocusOnDiscussion(Parameters parameters) {
		this.delivry = new Delivry();
		MessageBox messageBox;
		// Getting new MessageBox (type DiscutionPage) from userId and pageId
		try {
			int userId = Integer.valueOf(parameters.getSessionUser().getId());
			int discussionId = Integer.valueOf(parameters.getParsedUrl().getId());
			messageBox = new MessageBox(userId, discussionId, "DiscussionFocus");			
		} catch (Exception e) {
			messageBox = new MessageBox();
		}
		// Appending data to result and return it
		this.delivry.appendattribute("messageBox", messageBox);
		this.appendMandatoryAttributesToDelivry(parameters);
		
		return this.delivry;
	}

	@Override
	public Delivry getMessage(Parameters parameters) {
		this.delivry = new Delivry();
		MessageBox messageBox;
		// Getting new MessageBox (type DiscutionPage) from userId and pageId
		try {
			int userId = Integer.valueOf(parameters.getSessionUser().getId());
			int messageId = Integer.valueOf(parameters.getParsedUrl().getId());
			messageBox = new MessageBox(userId, messageId, "MessageFocus");			
		} catch (Exception e) {
			messageBox = new MessageBox();
		}
		// Appending data to result and return it
		this.delivry.appendattribute("messageBox", messageBox);
		this.appendMandatoryAttributesToDelivry(parameters);
		
		return this.delivry;
	}

	@Override
	public Delivry getCreateForm(Parameters parameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Delivry delete(Parameters parameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Delivry postCreateForm(Parameters parameters) {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getListLimit() {
		return LIST_LIMIT;
	}

}
