package com.ocherve.jcm;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import com.ocherve.jcm.service.Delivry;
import com.ocherve.jcm.service.Notification;
import com.ocherve.jcm.service.Parameters;
import com.ocherve.jcm.service.factory.Service;

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

	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    protected JcmServlet() {
        super();
		Configurator.setLevel(DLOG.getName(), DLOGLEVEL);
    }

    protected void startSession(HttpServletRequest request) {
		session = request.getSession();
		if ( session.getAttribute("sessionUser") == null ) 
			session.setAttribute("sessionUser", service.openAnonymousSession());
		if ( session.getAttribute("notifications") == null ) {
			Map<String,Notification> notifications = new HashMap<>();
			session.setAttribute("notifications", notifications);
		}
    }
    
    @SuppressWarnings("unchecked")
	protected Map<String,Notification> getSessionNotifications() {
    	// Probably never null because we excecute startSession before...
    	if ( session == null ) return new HashMap<String,Notification>();
    	if ( session.getAttribute("notifications") == null ) return new HashMap<String,Notification>();
    	// Get notifications ... or not... if error
    	Map<String,Notification> notifications;
    	try {
        	notifications = (Map<String,Notification>) session.getAttribute("notifications");
    	} catch (Exception e ) {
    		notifications = new HashMap<String,Notification>();
    		DLOG.log(Level.ERROR, e.getMessage());
    	}
    	// Reset notifications session attribute
    	session.setAttribute("notifications", new HashMap<String,Notification>());
    	// ... and return notifications
    	return notifications;
    }
    
    @SuppressWarnings("unchecked")
	protected void setSessionNotification() {
    	// Do nothing if delivry doesn't content notifications
    	if ( ! delivry.getSession().containsKey("notifications") ) return;
    	// Get delivry notifications or set empty if errors
    	Map<String,Notification> notifications = new HashMap<>();
    	try {
    		notifications = (Map<String,Notification>) delivry.getSession().get("notifications");
    	} catch ( Exception e ) {
    		notifications = new HashMap<String,Notification>();
    		DLOG.log(Level.ERROR, e.getMessage());    		
    	}
    	// Set session attribute
   		if ( ! notifications.isEmpty() ) session.setAttribute("notifications", notifications);
    }

}
