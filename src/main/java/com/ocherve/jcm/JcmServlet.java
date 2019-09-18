package com.ocherve.jcm;

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
    }
    
    protected Notification getSessionNotification() {
    	if ( session == null ) return null;
    	Notification notification = null;
    	try {
        	notification = (Notification) session.getAttribute("notification");
    	} catch (Exception e ) {
    		DLOG.log(Level.ERROR, e.getMessage());
    	}
    	if ( notification != null ) session.setAttribute("notification", null);
    	return notification;
    }
    
    protected void setSessionNotification() {
    	if ( delivry.getSession().containsKey("notification") )
    		session.setAttribute("notification", delivry.getSession().get("notification"));
    }

}
