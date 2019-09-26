package com.ocherve.jcm.filters;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import com.ocherve.jcm.model.User;
import com.ocherve.jcm.service.Notification;
import com.ocherve.jcm.service.NotificationType;

/**
 * Servlet Filter implementation class ConnexionFilter
 */
@WebFilter(filterName = "UserFilter", urlPatterns = {
	"/site/c",
	"/site/u/*",
	"/site/uac",
	"/site/umc",
	"/site/upt/*",
	"/site/upf/*",
	"/session/deconnexion",
	"/session/d",
	"/session/pass"
})
public class UserFilter extends Object implements Filter {

	private static final Logger DLOG = LogManager.getLogger("development_file");
    private static final Level DLOGLEVEL = Level.TRACE;

    private static final String URL_CONNEXION = "/session/connexion";
    private static final String ATT_SESSION_USER = "sessionUser";

    /**
     * Default constructor. 
     */
    public UserFilter() {
		Configurator.setLevel(DLOG.getName(), DLOGLEVEL);
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest inRequest, ServletResponse inResponse, FilterChain chain) throws IOException, ServletException {


		HttpServletRequest request = (HttpServletRequest) inRequest;
		HttpServletResponse response = (HttpServletResponse) inResponse;

		DLOG.log(Level.INFO , "Filter UserFilter active for " + request.getRequestURI());
		
        /* we don't filter public data */
        String chemin = request.getRequestURI().substring( request.getContextPath().length() );
        if ( chemin.matches("/(css|js|images|bootstrap|jquery|tinymce)/.*") ) {
            chain.doFilter( request, response );
            return;
        }

		HttpSession session = request.getSession();
		Integer idUser = 0;
		try {
			if ( session.getAttribute(ATT_SESSION_USER) != null ) 
				idUser = ((User) session.getAttribute(ATT_SESSION_USER)).getId();
		} catch (Exception e) {
			//log
		}

		DLOG.log(Level.INFO , "Filter UserFilter - User Id : " + idUser);

		// pass the request along the filter chain
		if ( idUser > 1 )
			chain.doFilter(request, response);
		else {
			Notification notification = new Notification(NotificationType.ERROR, 
						"Vous devez vous connecter pour accéder à cette fonctionnalité");
			Map<String,Notification> notifications = new HashMap<>();
			notifications.put("Accès refusé", notification);
			session.setAttribute("notifications", notifications);
			
			response.sendRedirect( request.getContextPath() + URL_CONNEXION );
		}

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
