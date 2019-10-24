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
import com.ocherve.jcm.service.factory.SessionService;


/**
 * Servlet implementation class Session
 */
@WebServlet("/session/*")
public class Session extends JcmServlet {

	private static final long serialVersionUID = 1L;
       
	private static final String LAYOUT = "/WEB-INF/SessionLayout.jsp";
	private static final String ERROR_PAGE = "/WEB-INF/ErrorLayout.jsp";
	
	// session, service, parameters and delivry variable herited from abstract JcmServlet

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Session() {
        super();
        layout = LAYOUT;
        errorPage = ERROR_PAGE;
        service = (SessionService) ServiceProxy.getInstance().getSessionService();
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
			case "deconnexion" :
				// Calling Message service method to delete connexion information in session
				delivry = ((SessionService) service).getDeconnexion(parameters);
				break;
			case "pass" :
				// Getting User information to provide changing password formular
				// Not asked by customer - NOT IMPLEMENTED
				break;
			case "d" :
				// Could be fun but not asked by customer so NOT IMPLEMENTED : nb of topo / site writed, last connexion ...
			case "connexion" :
				delivry = ((SessionService) service).getConnexionForm(parameters);
				break;
			case "inscription" :
				delivry = ((SessionService) service).getInscriptionForm(parameters);
				break;
			default :
				delivry = ((SessionService) service).doGetAction(parameters);
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
				case "connexion" :
					// Getting form data, trying to connect user, and return result in delivry
					delivry = ((SessionService) service).postConnexionForm(parameters);
					break;
				case "inscription" :
					// Getting form data, trying to register user, and return result in delivry
					delivry = ((SessionService) service).postInscriptionForm(parameters);
					break;
				case "pass" :
					// Not asked by customer - NOT IMPLEMENTED
					break;
				default :
			}
		} catch (UrlException e ) {
			delivry = service.abort(parameters);
		}
		return delivry;
	}
	
}
