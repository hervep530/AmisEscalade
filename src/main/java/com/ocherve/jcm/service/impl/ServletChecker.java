package com.ocherve.jcm.service.impl;

import javax.servlet.annotation.WebServlet;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import com.ocherve.jcm.service.ServiceException;

public class ServletChecker {
	
    public static final Logger DLOG = LogManager.getLogger("development_file");
    public static final Level LOGLEVEL = Level.TRACE;

    public ServletChecker() {
    	super();
        Configurator.setLevel(DLOG.getName(), LOGLEVEL);
    }

	public static String[] getAnnotationPaths(String servletName) throws ServiceException {
		
        String message = "";
        String[] servletPaths = null;
        try {
            Class<?> servletClass = Class.forName("com.ocherve.jcm." + servletName);
            servletPaths = servletClass.getAnnotation(WebServlet.class).value();
        } catch (ClassNotFoundException e) {
            message = "Service Name \"" + servletName + "\" doesn't match any Servlet Name";
			DLOG.log(Level.ERROR, message);	
            throw new ServiceException(message);
        } catch (NullPointerException e1) {
        	message = "Servlet \"" + servletName + "\" hasn't any valid path in @WebServlet annotation.";
			DLOG.log(Level.ERROR, message);	
            throw new ServiceException(message);
        }
        return servletPaths;
	}

	public static void validatePathsCount(String servletName, String[] servletPaths) throws ServiceException {
		String message;
		if ( servletPaths == null ) {
        	message = "Servlet \"" + servletName + "\" hasn't any valid path in @WebServlet annotation.";
			DLOG.log(Level.ERROR, "ServletPaths : " + message);	
            throw new ServiceException(message);
		}
        if (servletPaths.length < 1) {
            message = "@WebServlet annotation in " + servletName + " servlet does not contains any path.";
			DLOG.log(Level.ERROR, "ServletPaths : " + message);	
            throw new ServiceException(message);
        }
        if (servletPaths.length > 1) {
            message = "Default servlet @WebServlet has multiple paths."
                    + " It's not wrong, but be sure that it's really necessary"
                    + "(better to use actions efficently in service...).";
			DLOG.log(Level.WARN, "ServletPaths : " + message);	
        }

	}
	
    /**
     * Validate rule defined in servlet : Default Servlet provices path matching with path "/"
     * 
     * @param paths					String[] - array of path from @WebServlet annotation in Servlet
     * @throws ServiceException		Exception if checking fails
     */
    public static void hasEmptyPath(String[] paths) throws ServiceException {
        boolean hasEmptyPath = false;
        String message;
        int p = 0;
        while (!hasEmptyPath && p < paths.length) {
            if (paths[p].matches("^/$")) {
                hasEmptyPath = true;
            }
            p++;
        }
        if (!hasEmptyPath) {
        	message = "DefaultService : Default servlet @WebServlet hasn't empty path";
            DLOG.log(Level.ERROR, message);
            throw new ServiceException("ERROR:" + message);
        }
    }

	public static void validatePath(String servletName, String servletPath) throws ServiceException {
		// Pattern are not the same for Default Servlet and others
		String pathPattern = "/(\\w{3,})?" ;
		String servletAlias = servletName.toLowerCase();
		if ( ! servletName.contentEquals("Default") ) pathPattern = "/" + servletAlias + "/(\\w{3,}|\\*)/?";
		
		String message = servletName + " servlet paths must match with pattern \"" + pathPattern + "\"";
		
		if ( ! servletPath.matches(pathPattern) ) {
			DLOG.log(Level.ERROR, "ServletPath (" + servletPath + ") : " + message);	
			throw new ServiceException(message);
		}

	}
	
	
}
