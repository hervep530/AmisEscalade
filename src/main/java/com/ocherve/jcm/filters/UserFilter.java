package com.ocherve.jcm.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.apache.logging.log4j.Level;

/**
 * Servlet Filter implementation class ConnexionFilter
 */
@WebFilter(filterName = "UserFilter", urlPatterns = {
	"/site/c/*",
	"/site/uac/*",
	"/topo/l/*",
	"/topo/h/*",
	"/topo/r/*",
	"/topo/c/*",
	"/message/lmd/*",
	"/message/lfd/*",
	"/message/r/*",
	"/message/c/*",
	"/message/ca/*",
	"/message/cft/*",
	"/session/deconnexion/*",
	"/session/d/*",
	"/session/pass/*"
})
public class UserFilter extends JcmFilter {

    /**
     * Default constructor. 
     */
    public UserFilter() {
    	
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
		/*
		 * super initializes Filter variables, and let public requests (js,css,images...) pass filter 
		 */
		super.doFilter(inRequest, inResponse, chain);
	
		DLOG.log(Level.INFO , "Filter UserFilter is active for url " + uri + " with method " + method);
		
		try {
			if ( ! validateUrl() ) return;
		} catch (FilterException e) {
			setRequestError("UrlError", e.getMessage());
			request.getRequestDispatcher(PAGE_ERROR).forward(request, response);
		}

		this.setFilterVariables();
		this.setNotStaticToken();

		try {
			if ( ! validateToken(this.isStaticToken) ) return;
		} catch (FilterException e) {
			setRequestError("TokenError", e.getMessage());
			request.getRequestDispatcher(PAGE_ERROR).forward(request, response);
		}
		
		try {
			if ( ! validateUser() ) return;
		} catch (FilterException e) {
			setRequestError("UserError", e.getMessage());
			request.getRequestDispatcher(PAGE_ERROR).forward(request, response);
		}
		
		chain.doFilter(request, response);		
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		super.init(fConfig);
	}

	@Override
	protected Boolean isValidUrl() {
		if ( method.contentEquals("POST") ) {
			String postActions = "(session/pass|site/c|site/uac|topo/c|message/c)";
			return uri.matches("^/" + postActions + "/[0-9]{1,16}/\\w{1,32}$");
		} else {
			String getActions = "(session/deconnexion|session/pass|session/d|"
								+ "site/c|"
								+ "topo/r|topo/c|topo/h|"
								+ "message/lmd|message/lfd|message/r|message/ca|message/cft)";
			String topoListAction = "^/topo/l/[0-9]{1,16}$";
			return uri.matches("^/" + getActions + "/[0-9]{1,16}/\\w{1,32}$") || uri.matches(topoListAction);
		}	
	}
	
	@Override
	protected boolean skipTokenChecking() {
		// Defines request type / url allowed without checking token
			String getActions = "^(/topo/l/[0-9]{1,16}|/topo/r/[0-9]{1,16}/\\w{1,32})$";
		if ( method.contentEquals("GET") && uri.matches(getActions) ) return true;
		return false;
	}

	@Override
	protected void setNotStaticToken() {
		this.isStaticToken = false;
		if ( method.contentEquals("GET") ) {
			String getActions = "(session/deconnexion|session/pass|session/d|"
								+ "site/c|"
								+ "topo/c|topo/h|"
								+ "message/lmd|message/lfd|message/r|message/ca|message/cft)";
			if ( uri.matches("^/" + getActions + "/[0-9]{1,16}/\\w{1,32}$") ) this.isStaticToken = true;
		} else if ( method.contentEquals("POST") ) {
			String postActions = "(site/uac)";
			if ( uri.matches("^/" + postActions + "/[0-9]{1,16}/\\w{1,32}$") ) this.isStaticToken = true;
		}	
	}


}
