package com.ocherve.jcm;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.ocherve.jcm.service.Delivry;
import com.ocherve.jcm.service.Parameters;
import com.ocherve.jcm.service.ServiceProxy;
import com.ocherve.jcm.service.UrlException;
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
        layout = LAYOUT;
        errorPage = PAGE_ERROR;
        service = (CommentService) ServiceProxy.getInstance().getCommentService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doPost(request, response);
	}

	@Override
	protected Delivry doGetAction(Parameters parameters) {
		Delivry delivry = null;
		try {
			switch (parameters.getParsedUrl().getAction()) {
				case "u" :
					delivry = ((CommentService) service).getCommentForm(parameters);
					break;
				case "upt" :
				case "upf" :
					break;
				case "d" :
					delivry = ((CommentService) service).deleteComment(parameters);
					break;
				default :
			}			
		} catch (UrlException e ) {
			delivry = service.abort(parameters);
		}
		return delivry;
	}

	@Override
	protected Delivry doPostAction(Parameters parameters) {
		Delivry delivry = null;
		try {
			switch (parameters.getParsedUrl().getAction()) {
				case "u" :
					delivry = ((CommentService) service).postCommentForm(parameters);
					break;
				default :
			}			
		} catch (UrlException e ) {
			delivry = service.abort(parameters);
		}
		return delivry;
	}

}