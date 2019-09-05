package com.ocherve.jcm;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ocherve.jcm.service.AccessLevel;
import com.ocherve.jcm.service.Delivry;
import com.ocherve.jcm.service.Parameters;
import com.ocherve.jcm.service.ServiceException;
import com.ocherve.jcm.service.ServiceProxy;

/**
 * Servlet implementation class Comment
 */
@WebServlet("/comment/*")
public class Comment extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String LAYOUT = "/WEB-INF/CommentLayout.jsp";
	private static final String USER_LAYOUT = "/WEB-INF/user/CommentLayout.jsp";
	private static final String MEMBER_LAYOUT = "/WEB-INF/member/CommentLayout.jsp";
	private static final String ADMIN_LAYOUT = "/WEB-INF/admin/CommentLayout.jsp";
	private static final String PAGE_ERROR = "/WEB-INF/ErrorLayout.jsp";
       
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Comment() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setAttribute("uri", request.getRequestURI());
		Parameters parameters = null;
		AccessLevel accessLevel = AccessLevel.DEFAULT;
		Delivry delivry = null;
		
		try {		
			parameters = ServiceProxy.getInstance().getCommentService().setParameters(request);
			accessLevel = ServiceProxy.getInstance().getCommentService().checkSecurity(parameters);
			delivry = ServiceProxy.getInstance().getCommentService().doGetAction(parameters);
		} catch (ServiceException e) {
			delivry = ServiceProxy.getInstance().getCommentService().abort(parameters);
		}

		request.setAttribute("delivry", delivry);

		if ( delivry.getErrors().isEmpty() )
			switch (accessLevel) {
				case ADMIN :
					this.getServletContext().getRequestDispatcher(ADMIN_LAYOUT).forward(request, response);
					break;
				case MEMBER :
					this.getServletContext().getRequestDispatcher(MEMBER_LAYOUT).forward(request, response);
					break;
				case USER :
					this.getServletContext().getRequestDispatcher(USER_LAYOUT).forward(request, response);
					break;
				case DEFAULT :
				default:
					this.getServletContext().getRequestDispatcher(LAYOUT).forward(request, response);
			}
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
