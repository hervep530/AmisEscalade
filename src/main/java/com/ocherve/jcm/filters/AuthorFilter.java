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
				"/topo/u/*",
				"/topo/uaf/*",
				"/topo/uat/*",
				"/topo/d/*",
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
		
		DLOG.log(Level.INFO , "Filter AuthorFilter is active for url " + uri + " with method " + method);
		
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
			String postActions = "(site/u";
			postActions += "|topo/u)";
			return uri.matches("^/" + postActions + "/[0-9]{1,}/\\w{1,32}$");
		} else {
			String getActions = "(comment/upt|comment/upf";
			getActions += "|site/u|site/upt|site/upf";
			getActions += "|topo/d|topo/u|topo/uat|topo/uaf)";
			return uri.matches("^/" + getActions + "/[0-9]{1,}/\\w{1,32}$");
		}	
	}
	
	@Override
	protected boolean skipTokenChecking() {
		// Defines request type / url allowed without checking token
		/*
		String getActions = "(comment/u|site/u|topo/u)";
		if ( method.contentEquals("GET") && uri.matches("^/" + getActions + "/.*$") ) return true;
		*/
		return false;
	}

	@Override
	protected void setNotStaticToken() {
		this.isStaticToken = false;
		if ( method.contentEquals("GET") ) {
			String getActions = "(site/u|topo/u)";
			if ( uri.matches("^/" + getActions + "/[0-9]{1,16}/\\w{1,32}$") ) this.isStaticToken = true;
		} 
	}

}
