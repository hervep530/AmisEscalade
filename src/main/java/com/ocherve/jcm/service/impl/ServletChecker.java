package com.ocherve.jcm.service.impl;

import javax.servlet.annotation.WebServlet;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import com.ocherve.jcm.service.ServiceException;

/**
 * @author herve_dev
 * 
 * This class is used to make control on servlet when services are instanciated.
 */
public class ServletChecker {
	
    private static final Logger DLOG = LogManager.getLogger("development_file");
    private static final Level LOGLEVEL = Level.TRACE;
    private static boolean  isInitialized = false;

    /**
     * Replace constructor
     */
    private static void ServletCheckerInit() {
    	if ( ServletChecker.isInitialized ) return;
        Configurator.setLevel(DLOG.getName(), LOGLEVEL);
        ServletChecker.isInitialized = true;
    }

	/**
	 * Get @WebServlet annotation values
	 * 
	 * @param servletName			String : name of servlet
	 * @return						String[] : array of paths used for routing
	 * @throws ServiceException		ServiceException means that there's a technical error on service
	 */
	public static String[] getAnnotationPaths(String servletName) throws ServiceException {
		
		ServletCheckerInit();
		
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

	/**
	 * Check paths count in @WebServlet annotation (0 is wrong, more than 1 not necesserly useful)
	 * 
	 * @param servletName			String : Servlet name
	 * @param servletPaths			String[] : array of paths
	 * @throws ServiceException		
	 */
	public static void validatePathsCount(String servletName, String[] servletPaths) throws ServiceException {

		ServletCheckerInit();

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

		ServletCheckerInit();

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

	/**
	 * Given a servlet name and paths in annotation, check if Servlet is compliant with
	 *     usage of Services
	 *     
	 * @param servletName			String : Servlet name
	 * @param servletPath			String : path (value used for routing)
	 * @throws ServiceException
	 */
	public static void validatePath(String servletName, String servletPath) throws ServiceException {
		
		ServletCheckerInit();

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
