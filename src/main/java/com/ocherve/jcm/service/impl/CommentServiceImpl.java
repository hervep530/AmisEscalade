package com.ocherve.jcm.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Level;

import com.ocherve.jcm.dao.DaoProxy;
import com.ocherve.jcm.dao.contract.CommentDao;
import com.ocherve.jcm.dao.contract.SiteDao;
import com.ocherve.jcm.form.CommentForm;
import com.ocherve.jcm.model.Comment;
import com.ocherve.jcm.model.Site;
import com.ocherve.jcm.model.Reference;
import com.ocherve.jcm.service.Delivry;
import com.ocherve.jcm.service.Notification;
import com.ocherve.jcm.service.NotificationType;
import com.ocherve.jcm.service.Parameters;
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
			{"u","/comment/u/$id/$slug"},
			{"upt","/comment/upt/$id/$slug"},
			{"upf","/comment/upf/$id/$slug"},
			{"d","/comment/d/$id/$slug"}
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

	

	public Delivry getCommentForm(Parameters parameters) {
		this.delivry = new Delivry();
		// Adding site to Delivry Attributes to display summary in form
		try {
			Integer siteId = ((CommentForm) parameters.getForm()).getReference().getId();
			Site site = ((SiteDao) DaoProxy.getInstance().getSiteDao()).get(siteId);
			this.delivry.appendattribute("site", site);			
		} catch (Exception ignore) {/* already traced from Dao */}
		// Adding form to complete fields ( = importing comment in form) and appending common data to delivry
		this.delivry.appendattribute("commentForm", (CommentForm) parameters.getForm());
		this.appendMandatoryAttributesToDelivry(parameters);

		// return delivry
		return this.delivry;
	}
	
	public Delivry postCommentForm(Parameters parameters) {
		this.delivry = new Delivry();
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
				this.delivry.appendSessionNotification("Modification commentaire", notification);
				redirection += "/site/l/1";
				this.delivry.appendattribute("redirect", redirection);
			} else {
				// content error - we forward error toward formular inside site content
				Notification notification = new Notification(NotificationType.ERROR, 
						"Le commentaire n'a pas pu être mis à jour, car le contenu n'est pas valide.");
				this.delivry.appendNotification("Modification commentaire", notification);
				this.delivry.appendattribute("commentForm", commentForm);
				this.delivry.appendattribute("site", (Site) commentForm.getReference());
			}
			// appending common data to delivry and return it
			this.appendMandatoryAttributesToDelivry(parameters);
			return this.delivry;				
		} 
		// Else we set redirection and notification(s) to display after redirection
		Notification notification = new Notification(NotificationType.SUCCESS, 
				"Votre commentaire est mis à jour.");
		this.delivry.appendSessionNotification("Modification commentaire", notification);
		redirection += "/site/r/" + comment.getReference().getId() + "/" + comment.getReference().getSlug();
		// We redirect except if errors only contains "content" key
		//if ( ! commentForm.getErrors().containsKey("content") || commentForm.getErrors().size() > 1 ) 
		this.delivry.appendattribute("redirect", redirection);
		this.appendMandatoryAttributesToDelivry(parameters);
		DLOG.log(Level.DEBUG, this.delivry.getAttribute("redirect").toString());
		return this.delivry;
	}
	
	public Delivry deleteComment(Parameters parameters) {
		this.delivry = new Delivry();
		Boolean deleted = false;
		String redirection = parameters.getReferer();
		// Default redirection and notification for delete success
		if ( redirection.isEmpty() ) redirection = parameters.getContextPath() + "/site/l/1";
		Notification notification = new Notification(NotificationType.ERROR, 
				"Une erreur interne s'est produite. Le commentaire n'a pas pu être supprimé.");
		// Trying to delete
		try {
			// Set dao's and init variable
			SiteDao siteDao = (SiteDao) DaoProxy.getInstance().getSiteDao();
			CommentDao commentDao = (CommentDao) DaoProxy.getInstance().getCommentDao();
			Integer commentId = 0;
			Integer siteId = 0;
			// Get comment and site id
			commentId = Integer.valueOf(parameters.getParsedUrl().getId());
			Comment comment = commentDao.get(commentId);
			Reference reference = comment.getReference();
			siteId = reference.getId();
			// delete comment and refresh lazy parent (site)... more than lazy...
			deleted = ((CommentDao) DaoProxy.getInstance().getCommentDao()).delete(commentId);
			reference.removeComment(comment);
			siteDao.refresh(Site.class, siteId);
		} catch (Exception ignore) {/* Already traced in Dao */}
		// If it fails, notification is modified
		if ( deleted ) notification = new Notification(NotificationType.SUCCESS, "Le commentaire est supprimé.");
		// Append notification and redirection to delivry
		this.delivry.appendSessionNotification("Modification commentaire", notification);
		this.delivry.appendattribute("redirect", redirection);
		this.appendMandatoryAttributesToDelivry(parameters);
		
		return delivry;
	}

	
}
