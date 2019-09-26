package com.ocherve.jcm;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ocherve.jcm.service.ServiceException;
import com.ocherve.jcm.service.ServiceProxy;
import com.ocherve.jcm.service.factory.SessionService;

import com.ocherve.jcm.model.User;

/**
 * Servlet implementation class Session
 */
@WebServlet("/session/*")
public class Session extends JcmServlet {

	private static final long serialVersionUID = 1L;
       
	private static final String VUE = "/WEB-INF/SessionLayout.jsp";
	private static final String PAGE_ERROR = "/WEB-INF/ErrorLayout.jsp";
	
	// session, service, parameters and delivry variable herited from abstract JcmServlet

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Session() {
        super();
        service = (SessionService) ServiceProxy.getInstance().getSessionService();
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
		
		// we set service parameters from request and execute "GET" action. Result is return with delivry.
		try {
			parameters = service.setParameters(request);
			delivry = service.doGetAction(parameters);
		} catch (ServiceException e ) {
			System.out.println("Erreur Servlet Session");
			delivry = service.abort(parameters);
		}
		request.setAttribute("delivry", delivry);
		
		// when user is disconnecting delivry session map contains anonymous as sessionUser 
		if ( delivry.getSession().containsKey("resetSession") ) resetSession();

		// Deferred notification (if exists) copied from delivry to session (heriting jcmServlet)
		this.setSessionNotification();
		
		// Forwarding to Session jsp or error
		if ( ! delivry.getErrors().isEmpty() ) {
			this.getServletContext().getRequestDispatcher(PAGE_ERROR).forward(request, response);
			return;
		}


		if ( delivry.getAttributes().containsKey("redirect") ) {
			this.setSessionNotification();
			response.sendRedirect((String) delivry.getAttributes().get("redirect")); 
		} else {
			this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
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
		
		// we set service parameters from request and execute "POST" action. Result is return with delivry.
		try {
			parameters = service.setParameters(request);
			delivry = service.doPostAction(parameters);
		} catch (ServiceException e ) {
			delivry = service.abort(parameters);
		}
		
		// forwarding delivry to jsp inside request, and if delivry contains sessionUser, we update session
		request.setAttribute("delivry", delivry);
		
		if ( delivry.getSession().containsKey("sessionUser") ) {
			session.setAttribute("sessionUser", (User) delivry.getSession().get("sessionUser") );
		}

		// Forwarding to Site jsp, redirect,  or forwarding error
		if ( delivry.getAttributes().containsKey("redirect") ) {
			this.setSessionNotification();
			response.sendRedirect((String) delivry.getAttributes().get("redirect")); 
		} else {
			// when posting formular... it means that we can display VUE even if delivry contents errors
			this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
		}

	}


	
}
