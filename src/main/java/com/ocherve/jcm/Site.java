package com.ocherve.jcm;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;

import com.ocherve.jcm.service.Delivry;
import com.ocherve.jcm.service.Parameters;
import com.ocherve.jcm.service.ServiceProxy;
import com.ocherve.jcm.service.UrlException;
import com.ocherve.jcm.service.factory.SiteService;

/**
 * Servlet implementation class Site
 */
@WebServlet("/site/*")
@MultipartConfig(location="/home/1072/3/.dev/Donnees/Projets/JavaEE/Eclipse/workspace/AmisEscalade/tmp",
				fileSizeThreshold=1024*1024,
				maxFileSize=1024*1024*5,
				maxRequestSize=1024*1024*5*5)
public class Site extends JcmServlet {

	private static final long serialVersionUID = 1L;

	private static final String LAYOUT = "/WEB-INF/SiteLayout.jsp";
	private static final String PAGE_ERROR = "/WEB-INF/ErrorLayout.jsp";

	// session, service, parameters and delivry variable herited from abstract JcmServlet

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Site() {
        super();
        layout = LAYOUT;
        errorPage = PAGE_ERROR;
        service = (SiteService) ServiceProxy.getInstance().getSiteService();
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
					delivry = ((SiteService) service).getList(parameters);
					break;
				case "r" :
					delivry = ((SiteService) service).getSite(parameters);
					break;
				case "c" :
				case "u" :
				case "f" :
					delivry = ((SiteService) service).getSiteForm(parameters);
					break;
				case "upt" : 
				case "upf" :
					delivry = ((SiteService) service).putPublishedStatus(parameters);
					break;
				case "utt" : 
				case "utf" :
					delivry = ((SiteService) service).putFriendTag(parameters);
					break;
				case "d" :
					delivry = ((SiteService) service).delete(parameters);
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
				case "c" :
					delivry = ((SiteService) service).postCreateForm(parameters);
					break;
				case "u" :
					delivry = ((SiteService) service).postUpdateForm(parameters);
					break;
				case "f" :
					delivry = ((SiteService) service).postFindForm(parameters);
					break;
				case "utt" :
				case "utf" :
					delivry = ((SiteService) service).postAddCommentForm(parameters);
					break;
				default :
			}			
		} catch (UrlException e ) {
			delivry = service.abort(parameters);
			DLOG.log(Level.DEBUG, "DoPostAction failure : Site service aborted.");
		}
		return delivry;
	}


}
