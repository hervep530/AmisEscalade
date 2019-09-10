package com.ocherve.jcm;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ocherve.jcm.service.Delivry;
import com.ocherve.jcm.service.Parameters;
import com.ocherve.jcm.service.ServiceException;
import com.ocherve.jcm.service.ServiceProxy;

/**
 * Servlet implementation class Session
 */
@WebServlet("/session/*")
public class Session extends HttpServlet {

	private static final long serialVersionUID = 1L;
       
	private static final String VUE = "/WEB-INF/SessionLayout.jsp";
	private static final String PAGE_ERROR = "/WEB-INF/ErrorLayout.jsp";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Session() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("uri", request.getRequestURI());
		// HttpSession session = request.getSession();
		Parameters parameters = null;
		Delivry delivry = null;
		
		try {
			parameters = ServiceProxy.getInstance().getSessionService().setParameters(request);
			delivry = ServiceProxy.getInstance().getSessionService().doGetAction(parameters);
		} catch (ServiceException e ) {
			System.out.println("Erreur Servlet Session");
			delivry = ServiceProxy.getInstance().getSessionService().abort(parameters);
		}

		request.setAttribute("delivry", delivry);
		// session.setAttribute("notification", delivry.getSession().get("notification"));

		if ( delivry.getErrors().isEmpty() )
			this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
		else
			this.getServletContext().getRequestDispatcher(PAGE_ERROR).forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setAttribute("uri", request.getRequestURI());
		HttpSession session = request.getSession();
		Parameters parameters = null;
		Delivry delivry = null;
		
		try {
			parameters = ServiceProxy.getInstance().getSessionService().setParameters(request);
			delivry = ServiceProxy.getInstance().getSessionService().doPostAction(parameters);
		} catch (ServiceException e ) {
			System.out.println("Erreur Servlet Session");
			delivry = ServiceProxy.getInstance().getSessionService().abort(parameters);
		}

		request.setAttribute("delivry", delivry);
		// session.setAttribute("notification", delivry.getSession().get("notification"));
		for ( String attribute : delivry.getSession().keySet()) {
			if ( ! attribute.contentEquals("notification") ) 
				session.setAttribute(attribute, delivry.getSession().get(attribute));
		}

		if ( delivry.getErrors().isEmpty() )
			response.sendRedirect(this.getServletContext().getContextPath());
		else
			this.getServletContext().getRequestDispatcher(VUE).forward(request, response);

	}

}
