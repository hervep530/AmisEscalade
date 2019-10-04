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
 * Servlet Filter implementation class AuthorFilter
 */
@WebFilter(filterName = "AuthorFilter", 
			urlPatterns = {
				"/site/u/*", 
				"/site/upt/*",
				"/site/upf/*",
				"/site/umc/*",
				"/comment/u/*"
			})
public class AuthorFilter extends JcmFilter {


    /**
     * Default constructor. 
     */
    public AuthorFilter() {
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
		
		DLOG.log(Level.INFO , "Filter MemberFilter is active for url " + uri + " with method " + method);
		
		try {
			if ( ! validateUrl() ) return;
		} catch (FilterException e) {
			setRequestError("UrlError", e.getMessage());
			request.getRequestDispatcher(PAGE_ERROR).forward(request, response);
		}

		setFilterVariables();

		try {
			if ( ! validateToken() ) return;
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

		try {
			if ( ! validateAuthor() ) return;
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
			String postActions = "(comment/u";
			postActions += "|site/u|site/uac|site/umc";
			postActions += "|topo/u|topo/uac|topo/umc)";
			return uri.matches("^/" + postActions + "/[0-9]{1,}/\\w{1,32}$");
		} else {
			String getActions = "(comment/u|comment/upt|comment/upf";
			getActions += "|site/u|site/upt|site/upf";
			getActions += "|topo/u|topo/upt|topo/upf)";
			return uri.matches("^/" + getActions + "/[0-9]{1,}/\\w{1,32}$");
		}	
	}
	
	@Override
	protected boolean skipTokenChecking() {
		// Defines request type / url allowed without checking token
		String getActions = "(comment/u|site/u|topo/u)";
		if ( method.contentEquals("GET") && uri.matches("^/" + getActions + "/.*$") ) return true;
		return false;
	}

}
