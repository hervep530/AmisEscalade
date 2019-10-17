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
import com.ocherve.jcm.service.factory.MessageService;

/**
 * Servlet implementation class Message
 */
@WebServlet("/message/*")
public class Message extends JcmServlet {

	private static final long serialVersionUID = 1L;

	private static final String LAYOUT = "/WEB-INF/MessageLayout.jsp";
	private static final String ERROR_PAGE = "/WEB-INF/ErrorLayout.jsp";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Message() {
        super();
        layout = LAYOUT;
        errorPage = ERROR_PAGE;
        service = (MessageService) ServiceProxy.getInstance().getMessageService();
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
				case "l" :
					// Get list of all messages
					delivry = ((MessageService) service).getList(parameters);
					break;
				case "lmd" :
					// Get list of messages owned by user from session
					delivry = ((MessageService) service).getMyDiscussions(parameters);
					break;
				case "lfd" :
					// Get list of messages owned by user from session
					delivry = ((MessageService) service).getFocusOnDiscussion(parameters);
					break;
				case "r" :
					delivry = ((MessageService) service).getMessage(parameters);
					break;
				case "c" :
					delivry = ((MessageService) service).getCreateForm(parameters);
					break;
				case "d" :
					delivry = ((MessageService) service).delete(parameters);
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
			if (parameters.getParsedUrl().getAction().contentEquals("c")) {
					delivry = ((MessageService) service).postCreateForm(parameters);
			}			
		} catch (UrlException e ) {
			delivry = service.abort(parameters);
		}
		return delivry;
	}
	

}
