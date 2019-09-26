package com.ocherve.jcm;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ocherve.jcm.service.ServiceException;
import com.ocherve.jcm.service.ServiceProxy;
import com.ocherve.jcm.service.factory.SiteService;
import com.ocherve.jcm.service.impl.SiteServiceImpl;

/**
 * Servlet implementation class Site
 */
@WebServlet("/site/*")
@MultipartConfig(location="/tmp", fileSizeThreshold=1024*1024, 
maxFileSize=1024*1024*5, maxRequestSize=1024*1024*5*5)
public class Site extends JcmServlet {

	private static final long serialVersionUID = 1L;

	private static final String VUE = "/WEB-INF/SiteLayout.jsp";
	private static final String PAGE_ERROR = "/WEB-INF/ErrorLayout.jsp";

	// session, service, parameters and delivry variable herited from abstract JcmServlet

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Site() {
        super();
        service = (SiteService) ServiceProxy.getInstance().getSiteService();
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

		// Forwarding to Session jsp or error
		if ( ! delivry.getErrors().isEmpty() ) {
			this.getServletContext().getRequestDispatcher(PAGE_ERROR).forward(request, response);
			return;
		}

		this.setSessionNotification();
		if ( ! delivry.getAttributes().containsKey("redirect") )
			this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
		else
			response.sendRedirect((String) delivry.getAttributes().get("redirect"));

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
				this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
			}
		}
		
	}


}
