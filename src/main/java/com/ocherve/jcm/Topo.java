package com.ocherve.jcm;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ocherve.jcm.service.Delivry;
import com.ocherve.jcm.service.Parameters;
import com.ocherve.jcm.service.ServiceProxy;
import com.ocherve.jcm.service.UrlException;
import com.ocherve.jcm.service.factory.TopoService;

/**
 * Servlet implementation class Topo
 */
@WebServlet("/topo/*")
@MultipartConfig(location="/home/1072/3/.dev/Donnees/Projets/JavaEE/Eclipse/workspace/AmisEscalade/tmp",
	fileSizeThreshold=1024*1024,
	maxFileSize=1024*1024*2,
	maxRequestSize=1024*1024*2*2)
public class Topo extends JcmServlet {

	private static final long serialVersionUID = 1L;

	private static final String LAYOUT = "/WEB-INF/TopoLayout.jsp";
	private static final String ERROR_PAGE = "/WEB-INF/ErrorLayout.jsp";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Topo() {
        super();
        layout = LAYOUT;
        errorPage = ERROR_PAGE;
        service = (TopoService) ServiceProxy.getInstance().getTopoService();
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
					delivry = ((TopoService) service).getPublishedList(parameters);
					break;
				case "h" :
					delivry = ((TopoService) service).getMyList(parameters);
					break;
				case "r" :
					delivry = ((TopoService) service).getTopo(parameters);
					break;
				case "c" :
					delivry = ((TopoService) service).getCreateForm(parameters);
					break;
				case "u" :
					delivry = ((TopoService) service).getUpdateForm(parameters);
					break;
				case "upt" : 
				case "upf" :
					delivry = ((TopoService) service).putPublishStatus(parameters);
					break;
				case "uat" : 
				case "uaf" :
					delivry = ((TopoService) service).putAvailability(parameters);
					break;
				case "d" :
					delivry = ((TopoService) service).delete(parameters);
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
					delivry = ((TopoService) service).postCreateForm(parameters);
					break;
				case "u" :
					delivry = ((TopoService) service).postUpdateForm(parameters);
					break;
				default :
			}			
		} catch (UrlException e ) {
			delivry = service.abort(parameters);
		}
		return delivry;
	}

}
