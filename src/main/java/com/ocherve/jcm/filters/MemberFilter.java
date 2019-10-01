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
 * Servlet Filter implementation class MemberFilter
 */
@WebFilter(filterName = "MemberFilter", urlPatterns = {
	"/site/utt/*",
	"/site/utf/*",
	"/site/d/*",
	"/comment/d/*"
})
public class MemberFilter extends Object implements Filter {

	private static final Logger DLOG = LogManager.getLogger("development_file");
    private static final Level DLOGLEVEL = Level.TRACE;

    private static final String URL_CONNEXION = "/session/connexion";
    private static final String ATT_SESSION_USER = "sessionUser";
    
    private HttpServletRequest request;
    private String method;
    private HttpSession session;
    private String uri;
    private Integer userId; 
    private Integer roleId;
    private String message;
    private String redirection;
    
    /**
     * Default constructor. 
     */
    public MemberFilter() {
        // TODO Auto-generated constructor stub
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
		// scope Class - not static
		request = (HttpServletRequest) inRequest;
		HttpServletResponse response = (HttpServletResponse) inResponse;		
		method = request.getMethod();
		session = request.getSession();
		uri = request.getRequestURI().substring( request.getContextPath().length() );
		String backUrl = request.getHeader("referer");
		if (backUrl == null) backUrl = request.getContextPath();

		DLOG.log(Level.INFO , "Filter MemberFilter active for " + uri);
		
        // Public data pass filter
        if ( uri.matches("/(css|js|images|bootstrap|jquery|tinymce)/.*") ) {
            chain.doFilter( request, response );
            return;
        }

		// Filter invalid URL
		if ( ! isValidUrl() ) {
			DLOG.log(Level.DEBUG, "AuthorFilter - url invalide : " + uri);
			message = "Vous n'avez pas accès à cet fonctionnalité.";
			redirection = backUrl;
			// Set notification, and redirect
			setDeferredNotification();
			response.sendRedirect( redirection );
			return;
		}
		
		// Continue with valid Url
		setFilterVariables();
		
		//DLOG.log(Level.INFO , "Filter AuthorFilter - User Id : " + userId);
		if ( roleId > 2) {
			// pass the request along the filter chain
			chain.doFilter(request, response);
		} else {
			if ( userId > 1 ) {
				DLOG.log(Level.DEBUG, "AuthorFilter - ni author, ni member : " + userId + "/" + roleId);
				// User is connected  but access is not suffisant - modify default message and redirection
				message = "Vous n'avez pas accès à cet fonctionnalité.";
				redirection = backUrl;
			}
			// Set notification and redirect
			setDeferredNotification();
			response.sendRedirect( redirection );
		}

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		Configurator.setLevel(DLOG.getName(), DLOGLEVEL);
		DLOG.log(Level.INFO , "Filter AuthorFilter active");
		// Default miss access issue - user is not connected
		message = "Vous devez vous connecter pour accéder à cette fonctionnalité.";
		redirection = "";
		// Filter variables
		uri = "";
		method = "GET";
		userId = 0;
		roleId = 0;
	}

	private void setFilterVariables() {
		redirection += request.getContextPath() + URL_CONNEXION;
		try {
			if ( session.getAttribute(ATT_SESSION_USER) != null ) {
				userId = ((User) session.getAttribute(ATT_SESSION_USER)).getId();
				roleId = ((User) session.getAttribute(ATT_SESSION_USER)).getRole().getId();
			}
		} catch (Exception ignore) { /* first access - sessionUser not initialized */ }
	}

	private Boolean isValidUrl() {
		if ( method == "POST" ) {
			return false;
		} else {
			String getActions = "(comment/d";
			getActions += "|site/d|site/utt|site/utf";
			getActions += "|topo/d)";
			return uri.matches("^/" + getActions + "/[0-9]{1,}$");
		}	
	}
	
	private void setDeferredNotification() {
		Notification notification = new Notification(NotificationType.ERROR, message);
		Map<String,Notification> notifications = new HashMap<>();
		notifications.put("Accès refusé", notification);
		session.setAttribute("notifications", notifications);
	}

}
