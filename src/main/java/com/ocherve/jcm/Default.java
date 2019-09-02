package com.ocherve.jcm;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ocherve.jcm.service.Delivry;
import com.ocherve.jcm.service.Parameters;
import com.ocherve.jcm.service.ServiceException;
import com.ocherve.jcm.service.ServiceProxy;

/**
 * Servlet implementation class Default
 */
@WebServlet("/")
public class Default extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	private static final String VUE = "/WEB-INF/DefaultLayout.jsp";
	private static final String PAGE_ERROR = "/WEB-INF/ErrorLayout.jsp";

	/**
     * @see HttpServlet#HttpServlet()
     */
    public Default() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setAttribute("uri", request.getRequestURI());
		Parameters parameters = null;
		Delivry delivry = null;
		
		try {		
			parameters = ServiceProxy.getInstance().getDefaultService().setParameters(request);
			delivry = ServiceProxy.getInstance().getDefaultService().doGetAction(parameters);
		} catch (ServiceException e) {
			delivry = ServiceProxy.getInstance().getDefaultService().abort(parameters);
		}

		request.setAttribute("delivry", delivry);

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
