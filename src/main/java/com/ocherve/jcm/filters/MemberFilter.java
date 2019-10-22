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
 * Servlet Filter implementation class MemberFilter
 */
@WebFilter(filterName = "MemberFilter", urlPatterns = {
	"/site/utt/*",
	"/site/utf/*",
	"/site/d/*",
	"/comment/u/*",
	"/comment/d/*"
})
public class MemberFilter extends JcmFilter {

	// Protected variables defined in Astract JcmFilter 
	
    /**
     * Default constructor. 
     */
    public MemberFilter() {
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
			if ( ! validateMember() ) return;
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
			return uri.matches("^/comment/u/[0-9]{1,16}/\\w{1,32}$");
		} else if ( method.contentEquals("GET") ) {
			String getActions = "(comment/d|comment/u";
			getActions += "|site/d|site/utt|site/utf)";
			return uri.matches("^/" + getActions + "/[0-9]{1,16}/\\w{1,32}$");
		}
		return false;
	}

	@Override
	protected void setNotStaticToken() {
		this.isStaticToken = false;
		if ( method.contentEquals("GET") ) {
			if ( uri.matches("^/comment/u/[0-9]{1,16}/\\w{1,32}$") ) this.isStaticToken = true;
		} 
	}
	
}
