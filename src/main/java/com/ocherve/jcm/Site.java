package com.ocherve.jcm;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ocherve.jcm.service.ServiceException;
import com.ocherve.jcm.service.ServiceProxy;
import com.ocherve.jcm.service.factory.SiteService;

/**
 * Servlet implementation class Site
 */
@WebServlet("/site/*")
public class Site extends JcmServlet {

	private static final long serialVersionUID = 1L;

	private static final String VUE = "/WEB-INF/SiteLayout.jsp";
	private static final String PAGE_ERROR = "/WEB-INF/ErrorLayout.jsp";

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
		
		this.startSession(request);
		request.setAttribute("notifications", this.getSessionNotifications());
		request.setAttribute("uri", request.getRequestURI());
		
		try {		
			parameters = service.setParameters(request);
			delivry = service.doGetAction(parameters);
		} catch (ServiceException e) {
			delivry = service.abort(parameters);
		}

		request.setAttribute("delivry", delivry);
		this.setSessionNotification();

		if ( delivry.getErrors().isEmpty() )
			this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
		else
			this.getServletContext().getRequestDispatcher(PAGE_ERROR).forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.startSession(request);

		request.setAttribute("notifications", this.getSessionNotifications());
		request.setAttribute("uri", request.getRequestURI());
		
		try {		
			parameters = service.setParameters(request);
			delivry = service.doPostAction(parameters);
		} catch (ServiceException e) {
			delivry = service.abort(parameters);
		}

		request.setAttribute("delivry", delivry);

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
