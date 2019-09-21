package com.ocherve.jcm;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ocherve.jcm.service.ServiceException;
import com.ocherve.jcm.service.ServiceProxy;
import com.ocherve.jcm.service.factory.DefaultService;

/**
 * Servlet implementation class Default
 */
@WebServlet("/")
public class Default extends JcmServlet {
	
	private static final long serialVersionUID = 1L;
       
	private static final String VUE = "/WEB-INF/DefaultLayout.jsp";
	private static final String PAGE_ERROR = "/WEB-INF/ErrorLayout.jsp";
	
	// session, service, parameters and delivry variable herited from abstract JcmServlet

	/**
     * @see HttpServlet#HttpServlet()
     */
    public Default() {
        super();
        service = (DefaultService) ServiceProxy.getInstance().getDefaultService();
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

		// we set service parameters from request and execute "GET" action. Result is return with delivry.		
		if ( delivry.getErrors().isEmpty() )
			this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
		else
			this.getServletContext().getRequestDispatcher(PAGE_ERROR).forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
