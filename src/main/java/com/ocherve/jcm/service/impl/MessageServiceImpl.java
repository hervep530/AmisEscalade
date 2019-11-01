package com.ocherve.jcm.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Level;

import com.ocherve.jcm.dao.DaoProxy;
import com.ocherve.jcm.dao.contract.TopoDao;
import com.ocherve.jcm.form.MessageForm;
import com.ocherve.jcm.model.Message;
import com.ocherve.jcm.model.MessageBox;
import com.ocherve.jcm.model.Topo;
import com.ocherve.jcm.service.Delivry;
import com.ocherve.jcm.service.Notification;
import com.ocherve.jcm.service.NotificationType;
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
	 * c : create
	 * ca : create with answering other message (parent)
	 * cft : create from topo (request = new discussion)
	 * d : delete
	 */
	protected final static String[][] SVC_ACTIONS = {
			{"lmd","/message/lmd/$id/$slug"},
			{"lfd","/message/lfd/$id/$slug"},
			{"c","/message/c/$id/$slug"},
			{"ca","/message/ca/$id/$slug"},
			{"cft","/message/cft/$id/$slug"},
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
		this.delivry.appendattribute("title", "Messagerie");
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
			this.delivry.appendattribute("title", "Messagerie - discussion " + 
					messageBox.getDiscussions().get(0).getTitle());
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
			this.delivry.appendattribute("title", "Message " + messageBox.getFocusedMessage());
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
		this.delivry = new Delivry();
		MessageBox messageBox = null;
		MessageForm form = null;
		Topo topo = null;
		try {
			if ( parameters.getParsedUrl().getAction().contentEquals("ca") ) {
				// Getting form to create answer, we first get MessageBox with focused message and build form from it
				int userId = Integer.valueOf(parameters.getSessionUser().getId());
				int messageId = Integer.valueOf(parameters.getParsedUrl().getId());
				messageBox = new MessageBox(userId, messageId, "MessageFocus");	
				DLOG.log(Level.DEBUG, "Focused message id : " + messageBox.getFocusedMessage().getId());
				form = new MessageForm(messageBox.getFocusedMessage());
				DLOG.log(Level.DEBUG, "Form message id : " + form.getMessage().getId());
				// Appending messageBox to delivry
				this.delivry.appendattribute("title", "Nouveau message");
				this.delivry.appendattribute("messageBox", messageBox);
			} else if ( parameters.getParsedUrl().getAction().contentEquals("cft") ) {
				// Getting form for topo request, we first get topo and build messageForm from it
				int topoId = Integer.valueOf(parameters.getParsedUrl().getId());
				topo = ((TopoDao) DaoProxy.getInstance().getTopoDao()).get(topoId);
				this.delivry.appendattribute("title", "Demande de réservation de topo");
				form = new MessageForm(topo);
			}
		} catch (Exception e ) {
			// Error on getting form from topo or messageBox suggests an internal error already logged - deferred notification
			DLOG.log(Level.ERROR, JcmException.formatStackTrace(e));
			String message = "Une erreur interne s'est produite, le formulaire n'a pas pu s'afficher";
			Notification notification = new Notification(NotificationType.ERROR, message);
			this.delivry.appendSessionNotification("Creation Message", notification);
			this.delivry.appendattribute("redirect", parameters.getContextPath() + "message/lmd/1/" + parameters.getToken());
			return this.delivry;		
		}
		// Appending data to delivry and return it
		this.delivry.appendattribute("messageForm", form);
		this.appendMandatoryAttributesToDelivry(parameters);
		
		return this.delivry;
	}

	@Override
	public Delivry delete(Parameters parameters) {
		this.delivry = new Delivry();
		MessageBox messageBox = null;
		Boolean deleted = false;
		// default failure notification
		Notification notification = new Notification(NotificationType.ERROR, 
				"Une erreur interne s'est produite. Le message n'a pas pu être supprimé.");

		try {
			// Instanciating message box
			int userId = Integer.valueOf(parameters.getSessionUser().getId());
			int messageId = Integer.valueOf(parameters.getParsedUrl().getId());
			messageBox = new MessageBox(userId, messageId, "DiscussionFocus");	
			DLOG.log(Level.DEBUG, "Focused message id : " + messageBox.getFocusedDiscussionId());
			// Delete or mask
			if ( messageBox.isFocusedDiscussionMasked() ) {
				DLOG.log(Level.DEBUG, "Deleting discussion...");
				messageBox.deleteFocusDiscussion();
			} else {
				DLOG.log(Level.DEBUG, "Masking discussion...");
				messageBox.maskFocusDiscussion();
			}
			// No error, setting deleted to true
			deleted = true;
		} catch (Exception ignore) { 
			DLOG.log(Level.DEBUG, JcmException.formatStackTrace(ignore));
			deleted = false; 
		}

		// If deleting successfull, notification is modified
		if ( deleted ) notification = new Notification(NotificationType.SUCCESS, "Le message " + "" + " est supprimé.");
		// Append deferred notification, redirection and mandatory attributes from parameters to delivry
		this.delivry.appendSessionNotification("Suppression d'un Message", notification);
		this.delivry.appendattribute("redirect", parameters.getContextPath() + "/message/lmd/1/" + parameters.getStaticToken());
		this.appendMandatoryAttributesToDelivry(parameters);
		return this.delivry;
	}

	@Override
	public Delivry postCreateForm(Parameters parameters) {
		this.delivry = new Delivry();
		// Defaut value
		String notificationLabel = "Envoi de message";
		String message = "Votre message est envoyé avec succès.";
		Notification notification = new Notification(NotificationType.SUCCESS, message);
		int discussionId = 0;
		String action = "lfd";

		//Getting form, creating message and getting discussionId if success
		MessageForm messageForm = (MessageForm) parameters.getForm();
		@SuppressWarnings("unused")
		Message createMessage = messageForm.create();
		if (createMessage != null) discussionId = createMessage.getDiscussionId();
		// If errors (not internal error)we set result values and return it
		if (  ! messageForm.getErrors().isEmpty() && ! messageForm.getErrors().containsKey("internal") ) {
			this.delivry.appendattribute("siteForm", messageForm);
			this.appendMandatoryAttributesToDelivry(parameters);
			DLOG.log(Level.TRACE, "CreateMessageFom - Errors when creating message");
			return this.delivry;
		} 

		if ( messageForm.getErrors().containsKey("internal") ) {
			// Of course, changing default value if internal error 
			message = "L'envoi du message a échoué à cause d'une erreur interne.";
			notification = new Notification(NotificationType.ERROR, message);
		}
		// Appending deferred notification
		this.delivry.appendSessionNotification(notificationLabel, notification);
		// Appending redirection and finalizing delivry
		
		if ( discussionId == 0 ) {
			discussionId = 1;
			action = "lmd";
		}
		String redirection = parameters.getContextPath() + "/message/"; 
		redirection += action + "/" + discussionId + "/" + parameters.getStaticToken();
		this.delivry.appendattribute("redirect", redirection);
		this.appendMandatoryAttributesToDelivry(parameters);
		
		return this.delivry;
	}

	public Long getListLimit() {
		return LIST_LIMIT;
	}

}
