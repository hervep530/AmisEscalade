package com.ocherve.jcm;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;

import com.ocherve.jcm.service.ServiceException;
import com.ocherve.jcm.service.ServiceProxy;
import com.ocherve.jcm.service.factory.CommentService;

/**
 * Servlet implementation class Comment
 */
@WebServlet("/comment/*")
public class Comment extends JcmServlet {

	private static final long serialVersionUID = 1L;

	private static final String LAYOUT = "/WEB-INF/CommentLayout.jsp";
	private static final String PAGE_ERROR = "/WEB-INF/ErrorLayout.jsp";
       
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Comment() {
        super();
        service = (CommentService) ServiceProxy.getInstance().getCommentService();
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
		} catch (ServiceException e) {
			delivry = service.abort(parameters);
		}
		request.setAttribute("delivry", delivry);

		// Deferred notification (if exists) copied from delivry to session (heriting jcmServlet)
		this.setSessionNotification();


		// Forwarding to CommentLayout jsp or error
		if ( ! delivry.getErrors().isEmpty() ) {
			DLOG.log(Level.DEBUG, "Forward on errors : " + delivry.getErrors().keySet().toString());
			this.getServletContext().getRequestDispatcher(PAGE_ERROR).forward(request, response);
			return;
		}

		this.setSessionNotification();
		if ( ! delivry.getAttributes().containsKey("redirect") ) {
			DLOG.log(Level.DEBUG, "Forward to view : " + LAYOUT);
			this.getServletContext().getRequestDispatcher(LAYOUT).forward(request, response);
		} else {
			DLOG.log(Level.DEBUG, "Redirection : " + delivry.getAttributes().get("redirect"));
			DLOG.log(Level.DEBUG, "Redirection : " + delivry.getAttribute("redirect").toString());
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
		
		// we set service parameters from request and execute "POST" action. Result is return with delivry.
		try {		
			parameters = service.setParameters(request);
			delivry = service.doPostAction(parameters);
		} catch (ServiceException e) {
			delivry = service.abort(parameters);
		}

		request.setAttribute("delivry", delivry);

		// Forwarding to Site jsp, redirect,  or forwarding error
		if ( ! delivry.getErrors().isEmpty() ) {
			this.getServletContext().getRequestDispatcher(PAGE_ERROR).forward(request, response);
		} else {
			if (delivry.getAttributes().containsKey("redirect") ) {
				this.setSessionNotification();
				response.sendRedirect((String) delivry.getAttributes().get("redirect")); 
			} else {
				this.getServletContext().getRequestDispatcher(LAYOUT).forward(request, response);
			}
		}
	}

}
