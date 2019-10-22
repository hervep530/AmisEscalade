package com.ocherve.jcm;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import com.ocherve.jcm.service.Delivry;
import com.ocherve.jcm.service.Notification;
import com.ocherve.jcm.service.Parameters;
import com.ocherve.jcm.service.ServiceException;
import com.ocherve.jcm.service.factory.Service;
import com.ocherve.jcm.utils.JcmException;

import net.bytebuddy.utility.RandomString;

import com.ocherve.jcm.dao.DaoProxy;
import com.ocherve.jcm.dao.contract.UserDao;
import com.ocherve.jcm.model.User;

/**
 * Servlet implementation class JcmServlet
 */
abstract class JcmServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected static final Logger DLOG = LogManager.getLogger("development_file");
    private static final Level DLOGLEVEL = Level.TRACE;
	
	protected HttpSession session;
	protected Service service;
	protected Parameters parameters;
	protected Delivry delivry;
	protected String layout;
	protected String errorPage;

	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    protected JcmServlet() {
        super();
		Configurator.setLevel(DLOG.getName(), DLOGLEVEL);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// initializing session (heriting jcmServlet)
		this.startSession(request);
		// Getting deferred notification from the next http request (was stored in session)
		request.setAttribute("notifications", this.getSessionNotifications());
		request.setAttribute("uri", request.getRequestURI());
		String backUrl = request.getHeader("referer");
		if (backUrl == null) backUrl = request.getContextPath();
		request.setAttribute("backUrl", backUrl);

		
		// we set service parameters from request and execute "GET" action. Result is return with delivry.
		try {		
			this.parameters = this.service.setParameters(request);
			this.delivry = this.doGetAction(parameters);
		} catch (ServiceException e) {
			DLOG.log(Level.ERROR, "Aborting service - " + e.getMessage());
			this.delivry = this.service.abort(parameters);
		}
		request.setAttribute("delivry", this.delivry);

		// Deferred notification (if exists) copied from delivry to session (heriting jcmServlet)
		this.setSessionNotification();

		// Forwarding to Session jsp or error
		if ( ! this.delivry.getErrors().isEmpty() ) {
			DLOG.log(Level.DEBUG, "Forward on errors : " + this.delivry.getErrors().keySet().toString());
			this.getServletContext().getRequestDispatcher(this.errorPage).forward(request, response);
			return;
		}

		this.setSessionNotification();
		if ( ! this.delivry.getAttributes().containsKey("redirect") ) {
			DLOG.log(Level.DEBUG, "Forward to view : " + this.layout);
			this.getServletContext().getRequestDispatcher(this.layout).forward(request, response);
		} else {
			DLOG.log(Level.DEBUG, "Redirection : " + this.delivry.getAttributes().get("redirect"));
			DLOG.log(Level.DEBUG, "Redirection : " + this.delivry.getAttribute("redirect").toString());
			response.sendRedirect((String) delivry.getAttributes().get("redirect"));
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// initializing session (heriting jcmServlet)
		this.startSession(request);

		// Getting deferred notification from the next http request (was stored in session)
		request.setAttribute("notifications", this.getSessionNotifications());
		request.setAttribute("uri", request.getRequestURI());
		String backUrl = request.getHeader("referer");
		if (backUrl == null) backUrl = request.getContextPath();
		request.setAttribute("backUrl", backUrl);
		
		// we set service parameters from request and execute "POST" action. Result is return with delivry.
		try {		
			this.parameters = this.service.setParameters(request);
			this.delivry = this.doPostAction(this.parameters);
		} catch (ServiceException e) {
			DLOG.log(Level.ERROR, JcmException.formatStackTrace(e));
			this.delivry = this.service.abort(this.parameters);
		}

		request.setAttribute("delivry", this.delivry);

		// Forwarding to Site jsp, redirect,  or forwarding error
		if ( ! this.delivry.getErrors().isEmpty() ) {
			this.getServletContext().getRequestDispatcher(this.errorPage).forward(request, response);
			DLOG.log(Level.DEBUG, "Forward to view : " + this.errorPage);
		} else {
			if (this.delivry.getAttributes().containsKey("redirect") ) {
				this.setSessionNotification();
				response.sendRedirect((String) this.delivry.getAttributes().get("redirect")); 
				DLOG.log(Level.DEBUG, "Redirection : " + this.delivry.getAttributes().get("redirect"));
			} else {
				DLOG.log(Level.DEBUG, "Forward to view : " + this.layout);
				this.getServletContext().getRequestDispatcher(this.layout).forward(request, response);
			}
		}	
	}
	
	/**
	 * Method to update session at each request, after filter, before all other actions
	 * 
	 * @param request
	 */
	protected void startSession(HttpServletRequest request) {
		this.session = request.getSession();
		if ( this.session.getAttribute("sessionUser") == null ) 
			this.session.setAttribute("sessionUser", this.service.openAnonymousSession());
		if ( this.session.getAttribute("notifications") == null ) {
			Map<String,Notification> notifications = new HashMap<>();
			this.session.setAttribute("notifications", notifications);
		}
		// Security from filter is passed, so for each request, we generate a new token
		try {
			this.session.setAttribute("token", DatatypeConverter.printHexBinary(RandomString.make(16).getBytes()));
			if ( session.getAttribute("staticToken") == null) {
				this.session.setAttribute("staticToken", DatatypeConverter.printHexBinary(RandomString.make(16).getBytes()));
			}
		} catch (Exception ignore) {}
		this.session.setAttribute("redirectionCount", 0);

    }
 
	/**
	 * Reset session when user requests deconnection
	 */
    protected void resetSession() {
    	this.session.setAttribute("sessionUser", getAnonymous());
    	this.session.removeAttribute("notifications");
    }

    /**
     * Call Service action
     * 
     * @return delivry which is result of actions from service
     */
    protected Delivry doGetAction(Parameters parameters) {
    	return this.service.doGetAction(parameters);
    }

    /**
     * Call Service action
     * Should be override in specific servlet to implement a "switch (parameters.getParsedUrl().getAction()) ..."
     * 
     * @return delivry which is result of actions from service
     */
    protected Delivry doPostAction(Parameters parameters) {
    	return this.service.doPostAction(parameters);
    }
    
    /**
     * Get deferred notification saved in session at the last request
     * 
     * @return notification Map
     */
    @SuppressWarnings("unchecked")
	protected Map<String,Notification> getSessionNotifications() {
    	// Probably never null because we excecute startSession before...
    	if ( this.session == null ) return new HashMap<String,Notification>();
    	if ( this.session.getAttribute("notifications") == null ) return new HashMap<String,Notification>();
    	// Get notifications ... or not... if error
    	Map<String,Notification> notifications;
    	try {
        	notifications = (Map<String,Notification>) this.session.getAttribute("notifications");
    	} catch (Exception e ) {
    		notifications = new HashMap<String,Notification>();
    		DLOG.log(Level.ERROR, e.getMessage());
    	}
    	// Reset notifications session attribute
    	this.session.setAttribute("notifications", new HashMap<String,Notification>());
    	// ... and return notifications
    	return notifications;
    }
    
    /**
     * Set deferred notification in session from notificationSession return in delivry 
     */
    @SuppressWarnings("unchecked")
	protected void setSessionNotification() {
    	// Do nothing if delivry doesn't content notifications
    	if ( ! this.delivry.getSession().containsKey("notifications") ) return;
    	// Get delivry notifications or set empty if errors
    	Map<String,Notification> notifications = new HashMap<>();
    	try {
    		notifications = (Map<String,Notification>) this.delivry.getSession().get("notifications");
    	} catch ( Exception e ) {
    		notifications = new HashMap<String,Notification>();
    		DLOG.log(Level.ERROR, e.getMessage());    		
    	}
    	// Set session attribute
   		if ( ! notifications.isEmpty() ) this.session.setAttribute("notifications", notifications);
    }

    /**
     * Get user anonymous
     * 
     * @return User anonymous (id = 1 , roleId = 1)
     */
    protected User getAnonymous(){
    	return ((UserDao) DaoProxy.getInstance().getUserDao()).get(1);
    }
}
