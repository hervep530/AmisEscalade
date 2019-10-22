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
 * Servlet Filter implementation class JcmFilter
 */
abstract class JcmFilter implements Filter {

	protected static final Logger DLOG = LogManager.getLogger("development_file");
	protected static final Level DLOGLEVEL = Level.TRACE;
	
	protected static final String ATT_SESSION_USER = "sessionUser";
	protected static final String PAGE_ERROR = "/WEB-INF/ErrorLayout.jsp";

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected String method;
    protected HttpSession session;
    protected String sessionToken;
    protected String sessionStaticToken;
    protected String urlToken;
    protected boolean isStaticToken;
    protected String uri;
    protected String referer;
    protected Integer userId; 
    protected String username;
    protected Integer roleId;
    protected String component;
    protected Integer entityId;
    protected String message;
    protected String backUrl;
    protected String redirection;
	protected String url_connexion;

    /**
     * Default constructor. 
     */
    public JcmFilter() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest inRequest, ServletResponse inResponse, FilterChain chain) throws IOException, ServletException {
		// Set first stage variable
		this.request = (HttpServletRequest) inRequest;
		this.response = (HttpServletResponse) inResponse;		
		this.method = this.request.getMethod();
		this.session = this.request.getSession();
		this.uri = this.request.getRequestURI().substring( this.request.getContextPath().length() );
		this.referer = this.request.getHeader("referer");
		this.backUrl = this.referer;
		if (this.backUrl == null) this.backUrl = this.request.getContextPath();

        // Public data pass filter
        if ( uri.matches("/(css|js|images|bootstrap|jquery|tinymce)/.*") ) {
            chain.doFilter( this.request, this.response );
            return;
        }
        
        // Will be continue in each filter

	}

	/**
	 * Initialize value to prevent null pointer (don't use fconfig)
	 * 
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		Configurator.setLevel(DLOG.getName(), DLOGLEVEL);
		// Default miss access issue - user is not connected
		this.sessionToken = "";
		this.sessionStaticToken = "";
		this.isStaticToken = true;
		this.urlToken = "";
		this.message = "Vous devez vous connecter pour accéder à cette fonctionnalité.";
		this.redirection = "";
		this.backUrl = "";
		// Filter variables
		this.uri = "";
		this.component = "";
		this.method = "GET";
		this.username = "";
		this.userId = 0;
		this.roleId = 0;
		this.entityId = 0;
	}

	/**
	 * Set filter variables (2e stage variable)
	 * Variables can be added in specific Class with rewriting this method (super() + additionnal settings)
	 */
	protected void setFilterVariables() {
		// Getting user properties needed for security, from session
		try {
			if ( this.session.getAttribute(ATT_SESSION_USER) != null ) {
				this.userId = ((User) this.session.getAttribute(ATT_SESSION_USER)).getId();
				this.roleId = ((User) this.session.getAttribute(ATT_SESSION_USER)).getRole().getId();
				this.username = ((User) this.session.getAttribute(ATT_SESSION_USER)).getUsername();
			}
		} catch (Exception ignore) { /* first access - sessionUser not initialized */ }
		String[] splitUri = this.uri.split("/");
		// Splitting uri to get component, entityId and token
		try {
			this.component = splitUri[1];
			this.entityId = Integer.valueOf(splitUri[3]);
			this.urlToken = splitUri[4];
		} catch (Exception ignore) { /* uri was already checked - no need to log*/ }
		// Getting token in session if exists
		try {
			this.sessionToken = (String) this.session.getAttribute("token");	
			this.sessionStaticToken = (String) this.session.getAttribute("staticToken");	
		} catch (Exception ignore) {}
		// url_connexion only used on GET method, so there's no control on token and we don't need to generate it randomly
		this.url_connexion = this.request.getContextPath() + "/session/connexion/0/786775566A7674776D7541724E58766B";
		this.message = "Vous n'avez pas accès à cette fonctionnalité";
		this.redirection = this.backUrl;
	}

	/**
	 * Validate that token in url is the same as token in session
	 *  
	 * @return true if validated
	 * @throws FilterException
	 */
	protected boolean validateToken(boolean isStaticToken) throws FilterException {
		if ( skipTokenChecking() ) return true;
		try {
			if ( this.urlToken == null || this.sessionToken == null || this.sessionStaticToken == null ) {
				this.message = "Votre session est expirée.";
				// null token means expired session or CSRF attempt, so we redirect to previous page or welcome page
				this.setDeferredNotification();
				this.executeRedirection();
				DLOG.log(Level.DEBUG, "Token error : session = "+ this.sessionToken + " : url = " + this.urlToken);
				// return false to quit filter after redirection
				return false;
			}
			String effectiveToken = this.sessionStaticToken;
			if ( ! isStaticToken ) effectiveToken = this.sessionToken;
			if ( ! this.urlToken.contentEquals(effectiveToken) ) {
				// we redirect to previous page or welcome page
				if ( ! this.referer.isEmpty() && this.redirection.contentEquals(referer) ) 
					this.message = "Erreur de navigation. La page est maintenant rechargée, vous devriez ré-essayer.";
				this.setDeferredNotification();
				this.executeRedirection();
				DLOG.log(Level.DEBUG, "Token error : session = " + effectiveToken + " : url = " + this.urlToken);
				// return false to quit filter after redirection
				return false;
			}
		} catch (Exception e) {
			// throw exception in order to quit filter and forward to error.jsp
			throw new FilterException("Une erreur technique s'est produite.");
		}
		// token is validated and filter pass to the next step
		return true;
	}
	
	/**
	 * Validate that url pattern matches with rules defined in each filter
	 *  
	 * @return true if validated
	 * @throws FilterException
	 */
	protected boolean validateUrl() throws FilterException {
		try {
			if ( ! this.isValidUrl() ) {
				// Invalid url so we redirect to previous page or welcome page
				this.setDeferredNotification();
				this.executeRedirection();
				DLOG.log(Level.DEBUG, "url invalide : " + this.uri);
				// return false to quit filter after redirection
				return false;
			}
		} catch (Exception e) {
			DLOG.log(Level.ERROR, e.getMessage());
			// throw exception in order to quit filter and forward to error.jsp
			throw new FilterException("Une erreur technique s'est produite.");
		} 
		// Url is validate and filter pass to the next step
		return true;
	}

	/**
	 * Validate that user is not connected
	 *  
	 * @return true if anonymous and false is user is connected
	 * @throws FilterException
	 */
	public boolean validateAnonymous() throws FilterException {
		try {
			// If roleId < 2, user is not connected and anonymous
			if ( this.roleId > 1 ) {
				// modifying default redirection
				this.redirection = this.request.getContextPath() + "/session/d/0/" + sessionStaticToken;
				this.setDeferredNotification();
				this.executeRedirection();
				// return false to quit filter after redirection
				return false;
			}
		} catch (Exception e) {
			DLOG.log(Level.ERROR, e.getMessage());
			// throw exception in order to quit filter and forward to error.jsp
			throw new FilterException("Une erreur technique s'est produite.");
		} 
		return true;
	}

	/**
	 * Validate that user is connected
	 *  
	 * @return true if connected, false if anonymous
	 * @throws FilterException
	 */
	public boolean validateUser() throws FilterException {
		try {
			// If roleId < 2, user is not connected and anonymous
			if ( this.roleId < 2 ) {
				// Modifying default message and redirection
				this.message = "Vous devez vous connecter pour accéder à cet fonctionnalité.";
				this.redirection = this.url_connexion;
				// Invalid url so we redirect to previous page or welcome page
				this.setDeferredNotification();
				this.executeRedirection();
				// return false to quit filter after redirection
				return false;
			}
		} catch (Exception e) {
			DLOG.log(Level.ERROR, e.getMessage());
			// throw exception in order to quit filter and forward to error.jsp
			throw new FilterException("Une erreur technique s'est produite.");
		} 
		return true;
	}

	/**
	 * Validate that user is authorize to modify (author or granted to super access)
	 *  
	 * @return true if author
	 * @throws FilterException
	 */
	public boolean validateAuthor() throws FilterException {
		try {
			// if user is not author and if its roleId is less or equal than 2, he's not authorized
			if ( ! ( this.isAuthor() || this.roleId > 2 ) ) {
				// Invalid url so we redirect to previous page or welcome page
				this.setDeferredNotification();
				this.executeRedirection();
				// return false to quit filter after redirection
				return false;
			}
		} catch (Exception e) {
			DLOG.log(Level.ERROR, e.getMessage());
			// throw exception in order to quit filter and forward to error.jsp
			throw new FilterException("Une erreur technique s'est produite.");
		} 
		return true;
	}

	/**
	 * Validate that user is member (granted to super access)
	 *  
	 * @return true if member, false otherwise
	 * @throws FilterException
	 */
	public boolean validateMember() throws FilterException {
		try {
			// If roleId < 3, user is not member
			if ( this.roleId < 3 ) {
				// Invalid url so we redirect to previous page or welcome page
				this.setDeferredNotification();
				this.executeRedirection();
				// return false to quit filter after redirection
				return false;
			}
		} catch (Exception e) {
			DLOG.log(Level.ERROR, e.getMessage());
			// throw exception in order to quit filter and forward to error.jsp
			throw new FilterException("Une erreur technique s'est produite.");
		} 
		return true;
	}

	/**
	 * Control that url matches with pattern and return true or false
	 * 
	 * @return true if url is valid, else false
	 */
	protected Boolean isValidUrl() {
		// !! Must be Override !!
		return false;
	}

	/**
	 * Check if user is author and return true or false
	 * 
	 * @return true if is author, else false
	 */
	protected Boolean isAuthor() {
		Map<String,Object> parameters = new HashMap<>();
		parameters.put("id", this.entityId);
		Integer idAuthor = -1;
		try {
			if ( this.component.contentEquals("comment") ) {
				DLOG.log(Level.DEBUG, "component is comment");
				CommentDao commentDao = (CommentDao) DaoProxy.getInstance().getCommentDao();
				idAuthor = (Integer) commentDao.getIdFromNamedQuery("Comment.getAuthor", parameters);
				DLOG.log(Level.DEBUG, "component : " + this.component + " - authorId : " + idAuthor);
			} else {
				SiteDao siteDao = (SiteDao) DaoProxy.getInstance().getSiteDao();
				idAuthor = (Integer) siteDao.getIdFromNamedQuery("Reference.getAuthor", parameters);
				DLOG.log(Level.DEBUG, "component : " + this.component + " - authorId : " + idAuthor);
			}		
		} catch (Exception ignore) { /* however acces denied and if Dao error already traced */ }
				
		return this.userId.equals(idAuthor);
	}

	/**
	 * Store notification in session in order to display it in response of the next request
	 */
	protected void setDeferredNotification() {
		Notification notification = new Notification(NotificationType.ERROR, message);
		Map<String,Notification> notifications = new HashMap<>();
		notifications.put("Accès refusé", notification);
		this.session.setAttribute("notifications", notifications);
	}
	
	/**
	 * Method to exclude url from token checking
	 * Inactive as is... Must be overrided in specific Class to be used 
	 * 
	 * @return true if url is excluded from token checking
	 */
	protected boolean skipTokenChecking() {
		return false;
	}
		
	/**
	 * Append error to request
	 */
	protected void setRequestError(String error, String errorMessage) {
		this.request.setAttribute("error", error);
		this.request.setAttribute("errorMessage", errorMessage);
	}

	/**
	 * Redirects with preventing infinite loop - after twice we go to welcome page which is not filtered 
	 * 
	 * @throws FilterException
	 */
	protected void executeRedirection() throws FilterException {
		try {
			if ( exceedsRedirectionCount(2) ) this.redirection = this.request.getContextPath();
			this.response.sendRedirect( this.redirection );
			incrementRedirectionCount();			
		} catch (Exception ignore) {
			throw new FilterException("Une erreur technique est survenue.");
		}
	}
	
	/**
	 * Checking in session, if redirection count limit is exceeded
	 * 
	 * @param limit
	 * @return
	 */
	protected Boolean exceedsRedirectionCount(Integer limit) {
		Integer count = 0;
		try {
			if ( this.session.getAttribute("redirectionCount") != null )
				count = Integer.valueOf(String.valueOf(this.session.getAttribute("redirectionCount")));
		} catch (Exception ignore) {}
		if ( count > limit ) return true;
		return false;
	}
	
	/**
	 * Increment redirection count in session
	 */
	protected void incrementRedirectionCount() {
		Integer count = 0;
		try {
			if ( this.session.getAttribute("redirectionCount") != null )
				count = Integer.valueOf(String.valueOf(this.session.getAttribute("redirectionCount")));
				count ++;
		} catch (Exception ignore) {}
		this.session.setAttribute("redirectionCount", String.valueOf(count));
	}

	// Override it in specific filter to use it with conditionnal expression based on action, http mode,...
	protected void setNotStaticToken() {
		this.isStaticToken = false;
	}
	
}
