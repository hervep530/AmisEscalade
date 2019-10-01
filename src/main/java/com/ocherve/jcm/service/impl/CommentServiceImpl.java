package com.ocherve.jcm.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Level;

import com.ocherve.jcm.dao.DaoProxy;
import com.ocherve.jcm.dao.contract.SiteDao;
import com.ocherve.jcm.form.CommentForm;
import com.ocherve.jcm.model.Comment;
import com.ocherve.jcm.model.Site;
import com.ocherve.jcm.service.Delivry;
import com.ocherve.jcm.service.Notification;
import com.ocherve.jcm.service.NotificationType;
import com.ocherve.jcm.service.Parameters;
import com.ocherve.jcm.service.UrlException;
import com.ocherve.jcm.service.factory.CommentService;

/**
 * CommentService Implementation
 * 
 * @author herve_dev
 *
 */
public class CommentServiceImpl extends ServiceImpl implements CommentService {

	protected final static String SVC_DEFAULT_URL = "";
	protected final static String[][] SVC_ACTIONS = {
			{"l","/comment/l/$id"},
			{"u","/comment/u/$id"},
			{"upt","/comment/upt/$id"},
			{"upf","/comment/upf/$id"},
			{"d","/comment/d/$id"}
	};

	/**
	 * Constructor 
	 */
	public CommentServiceImpl() {
		super(SVC_DEFAULT_URL);
	}

	public Parameters setParameters(HttpServletRequest request) {
		Parameters parameters = super.setParameters(request);
		// Stopping here if parameters has errors
		if ( ! parameters.getErrors().isEmpty() ) return parameters;
		// In case of update we add form to parameters (from request for POST, from comment with GET)
		if ( parameters.getParsedUrl().getAction().contentEquals("u") ) {
			if ( request.getMethod().contentEquals("POST") )
				parameters.setForm(new CommentForm(request));
			else
				parameters.setForm(new CommentForm(Integer.valueOf(parameters.getParsedUrl().getId())));
		}
		return parameters;
	}

	@Override
	public Delivry doGetAction(Parameters parameters) {
		Delivry delivry = null;
		try {
			switch (parameters.getParsedUrl().getAction()) {
				case "l" :
					//delivry = getList(parameters);
					break;
				case "u" :
					delivry = getCommentForm(parameters);
					break;
				case "upt" :
				case "upf" :
					break;
				case "d" :
					break;
				default :
			}			
		} catch (UrlException e ) {
			DLOG.log(Level.ERROR , e.getMessage());
			delivry = new Delivry();
			delivry.appendError(serviceName + "_" + parameters.getParsedUrl().getAction(), e.getMessage());
		}
		// We really want to prevent null delivry
		if ( delivry == null ) delivry = new Delivry();
		// Finalizing Delivry with including parameters, immediate notifications, and errors
		delivry.setParameters(parameters);
		if ( ! parameters.getNotifications().isEmpty() ) delivry.appendNotifications(parameters.getNotifications());
		if ( ! parameters.getErrors().isEmpty() ) delivry.setErrors(parameters.getErrors());
		DLOG.log(Level.DEBUG , "Service" + this.serviceName + ".doGetAction is done.");

		return delivry;
	}
	
	@Override
	public Delivry doPostAction(Parameters parameters) {
		Delivry delivry = null;
		try {
			switch (parameters.getParsedUrl().getAction()) {
				case "u" :
					delivry = postCommentForm(parameters);
					break;
				default :
			}			
		} catch (UrlException e ) {
			DLOG.log(Level.ERROR , e.getMessage());
			delivry = new Delivry();
			delivry.appendError(serviceName + "_" + parameters.getParsedUrl().getAction(), e.getMessage());
		}
		// We really want to prevent null delivry
		if ( delivry == null ) delivry = new Delivry();
		// Finalizing Delivry with including parameters, immediate notifications, and errors
		delivry.setParameters(parameters);
		if ( ! parameters.getNotifications().isEmpty() ) delivry.appendNotifications(parameters.getNotifications());
		if ( ! parameters.getErrors().isEmpty() ) delivry.appendErrors(parameters.getErrors());
		String info = "Service " + this.serviceName + " do PostAction.";
		DLOG.log(Level.DEBUG , info);
		return delivry;
	}

	private Delivry getCommentForm(Parameters parameters) {
		Delivry delivry = new Delivry();
		// Add site to Delivry Attributes to display summary in form
		try {
			Integer siteId = ((CommentForm) parameters.getForm()).getReference().getId();
			Site site = ((SiteDao) DaoProxy.getInstance().getSiteDao()).get(siteId);
			delivry.appendattribute("site", site);			
		} catch (Exception ignore) {/* already traced from Dao */}
		// Adding form to complete fields ( = importing comment in form)
		delivry.appendattribute("commentForm", (CommentForm) parameters.getForm());
		// return delivry
		return delivry;
	}
	
	private Delivry postCommentForm(Parameters parameters) {
		Delivry result = new Delivry();
		CommentForm commentForm = (CommentForm) parameters.getForm();
		Comment comment = commentForm.update();
		String redirection = parameters.getContextPath();
		// If errors we set result values and return it
		if ( ! commentForm.getErrors().isEmpty() ) {
			DLOG.log(Level.ERROR, commentForm.getErrors().keySet().toString());
			if ( commentForm.getErrors().containsKey("internal")) {
				// Internal error - we redirect with notification
				Notification notification = new Notification(NotificationType.ERROR, 
					"Une erreur interne s'est produite. Le commentaire n'a pas pu être mis à jour.");
				result.appendSessionNotification("Modification commentaire", notification);
				redirection += "/site/l/1";
				result.appendattribute("redirect", redirection);
			} else {
				// content error - we forward error toward formular inside site content
				Notification notification = new Notification(NotificationType.ERROR, 
						"Le commentaire n'a pas pu être mis à jour, car le contenu n'est pas valide.");
				result.appendNotification("Modification commentaire", notification);
				result.appendattribute("commentForm", commentForm);
				result.appendattribute("site", (Site) commentForm.getReference());
			}
			return result;				
		} 
		// Else we set redirection and notification(s) to display after redirection
		Notification notification = new Notification(NotificationType.SUCCESS, 
				"Votre commentaire est mis à jour.");
		result.appendSessionNotification("Modification commentaire", notification);
		redirection += "/site/r/" + comment.getReference().getId() + "/" + comment.getReference().getSlug();
		// We redirect except if errors only contains "content" key
		//if ( ! commentForm.getErrors().containsKey("content") || commentForm.getErrors().size() > 1 ) 
			result.appendattribute("redirect", redirection);
		DLOG.log(Level.DEBUG, result.getAttribute("redirect").toString());
		return result;
	}
	
	
}
