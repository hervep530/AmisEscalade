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

import com.ocherve.jcm.dao.DaoProxy;
import com.ocherve.jcm.dao.contract.CommentDao;
import com.ocherve.jcm.dao.contract.SiteDao;
import com.ocherve.jcm.model.User;
import com.ocherve.jcm.service.Notification;
import com.ocherve.jcm.service.NotificationType;

/**
 * Servlet Filter implementation class AuthorFilter
 */
@WebFilter(filterName = "AuthorFilter", 
			urlPatterns = {
				"/site/u/*", 
				"/site/upt/*",
				"/site/upf/*",
				"/site/umc/*"
			})
public class AuthorFilter implements Filter {

	private static final Logger DLOG = LogManager.getLogger("development_file");
    private static final Level DLOGLEVEL = Level.TRACE;

    private static final String URL_CONNEXION = "/session/connexion";
    private static final String ATT_SESSION_USER = "sessionUser";
    
    private HttpServletRequest request;
    private HttpSession session;
    private String uri;
    private Integer userId; 
    private Integer roleId;
    private String component;
    private Integer entityId;
    private String message;
    private String redirection;

    /**
     * Default constructor. 
     */
    public AuthorFilter() {
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
		session = request.getSession();
		uri = request.getRequestURI().substring( request.getContextPath().length() );
		String backUrl = request.getHeader("referer");
		if (backUrl == null) backUrl = request.getContextPath();

		DLOG.log(Level.INFO , "Filter AuthorFilter active for " + uri);
		
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
		if ( isAuthor() || roleId > 2) {
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
		// TODO Auto-generated method stub
		Configurator.setLevel(DLOG.getName(), DLOGLEVEL);
		DLOG.log(Level.INFO , "Filter AuthorFilter active");
		// Default miss access issue - user is not connected
		message = "Vous devez vous connecter pour accéder à cette fonctionnalité.";
		redirection = URL_CONNEXION;
		// Filter variables
		uri = "";
		component = "";
		userId = 0;
		roleId = 0;
		entityId = 0;
	}

	
	private void setFilterVariables() {
		try {
			if ( session.getAttribute(ATT_SESSION_USER) != null ) {
				userId = ((User) session.getAttribute(ATT_SESSION_USER)).getId();
				roleId = ((User) session.getAttribute(ATT_SESSION_USER)).getRole().getId();
			}
		} catch (Exception ignore) { /* first access - sessionUser not initialized */ }
		String[] splitUri = uri.split("/");
		try {
			component = splitUri[1];
			entityId = Integer.valueOf(splitUri[3]);
		} catch (Exception ignore) { /* uri was already checked - no need to log*/ }
	}
	
	private Boolean isAuthor() {
		Map<String,Object> parameters = new HashMap<>();
		parameters.put("id", entityId);
		Integer idAuthor = -1;
		try {
			if ( component.contentEquals("comment") ) {
				CommentDao commentDao = (CommentDao) DaoProxy.getInstance().getCommentDao();
				idAuthor = (Integer) commentDao.getIdFromNamedQuery("Comment.getAuthor", parameters);
			} else {
				SiteDao siteDao = (SiteDao) DaoProxy.getInstance().getSiteDao();
				idAuthor = (Integer) siteDao.getIdFromNamedQuery("Reference.getAuthor", parameters);
			}		
		} catch (Exception ignore) { /* however acces denied and if Dao error already traced */ }
				
		return userId.equals(idAuthor);
	}
	
	private Boolean isValidUrl() {
		return uri.matches("^/(comment|site|topo)/u[a-z]*/[0-9]{1,}$");
	}
	
	private void setDeferredNotification() {
		Notification notification = new Notification(NotificationType.ERROR, message);
		Map<String,Notification> notifications = new HashMap<>();
		notifications.put("Accès refusé", notification);
		session.setAttribute("notifications", notifications);
	}
}
