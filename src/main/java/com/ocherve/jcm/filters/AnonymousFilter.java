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
 * Servlet Filter implementation class AnonymousFilter
 */
@WebFilter(filterName = "AnonymousFilter", urlPatterns = {
	"/session/connexion",
	"/session/inscription"
})
public class AnonymousFilter implements Filter {

	private static final Logger DLOG = LogManager.getLogger("development_file");
    private static final Level DLOGLEVEL = Level.TRACE;

    private static final String URL_HOME = "/session/d";
    private static final String ATT_SESSION_USER = "sessionUser";

    /**
     * Default constructor. 
     */
    public AnonymousFilter() {
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

		DLOG.log(Level.INFO , "Filter ConnexionFilter active for " + request.getRequestURI());
		
        /* we don't filter public data */
        String chemin = request.getRequestURI().substring( request.getContextPath().length() );
        if ( chemin.matches("/(css|js|images|bootstrap|jquery|tinymce)/.*") ) {
            chain.doFilter( request, response );
            return;
        }

		HttpSession session = request.getSession();
		Integer idUser = 0;
		String username = "";
		try {
			if ( session.getAttribute(ATT_SESSION_USER) != null ) {
				idUser = ((User) session.getAttribute(ATT_SESSION_USER)).getId();
				username = ((User) session.getAttribute(ATT_SESSION_USER)).getUsername();
			}
		} catch (Exception e) {
			//log
		}

		DLOG.log(Level.INFO , "Filter ConnexionFilter - User Id : " + idUser);

		// pass the request along the filter chain
		if ( idUser > 1 ) {
			Notification notification = new Notification(NotificationType.ERROR, 
					"Vous êtes déjà connecté " + username + ".");
			Map<String,Notification> notifications = new HashMap<>();
			notifications.put("Action impossible", notification);
			session.setAttribute("notifications", notifications);
			
			response.sendRedirect( request.getContextPath() + URL_HOME );
		} else
			chain.doFilter(request, response);

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
