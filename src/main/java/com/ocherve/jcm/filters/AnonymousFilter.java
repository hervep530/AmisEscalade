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
 * Servlet Filter implementation class AnonymousFilter
 */
@WebFilter(filterName = "AnonymousFilter", urlPatterns = {
	"/session/connexion/*",
	"/session/inscription/*"
})
public class AnonymousFilter extends JcmFilter {


    /**
     * Default constructor. 
     */
    public AnonymousFilter() {
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

		DLOG.log(Level.INFO , "Filter AnonymousFilter is active for url " + uri + " with method " + method);
		
		try {
			if ( ! validateUrl() ) return;
		} catch (FilterException e) {
			setRequestError("UrlError", e.getMessage());
			request.getRequestDispatcher(PAGE_ERROR).forward(request, response);
		}

		this.setFilterVariables();
		this.setNotStaticToken();

		// skip token checking if request isn't in POST method
		try {
			if ( ! validateToken(this.isStaticToken) ) return;
		} catch (FilterException e) {
			setRequestError("TokenError", e.getMessage());
			request.getRequestDispatcher(PAGE_ERROR).forward(request, response);
		}
		
		try {
			if ( ! validateAnonymous() ) return;
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
		// Defines allowed url for this filter
		String actions = "(session/connexion|session/inscription)";
		return uri.matches("^/" + actions + "/[0-9]{1,}/\\w{1,32}$");
	}
	
	@Override
	protected boolean skipTokenChecking() {
		// Defines request type / url allowed without checking token
		String getActions = "(session/connexion|session/inscription)";
		if ( method.contentEquals("GET") && uri.matches("^/" + getActions + "/[0-9]{1,}/\\w{1,32}$") ) return true;
		return false;
	}

}
