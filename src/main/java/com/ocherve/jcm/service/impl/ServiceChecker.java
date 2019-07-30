package com.ocherve.jcm.service.impl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import com.ocherve.jcm.service.ServiceException;

public class ServiceChecker {

    public static final Logger DLOG = LogManager.getLogger("development_file");
    public static final Level LOGLEVEL = Level.TRACE;

    public ServiceChecker() {
        Configurator.setLevel(DLOG.getName(), LOGLEVEL);
    }
    
    public static void hasEmptyServletPath(String[] paths) throws ServiceException {
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

    public static void validatePathFromServlet(String servicePattern, String servicePath, String[] servletPaths) 
            throws ServiceException {
        // Validate global service pattern
        String globalPattern = "/(\\w{3,})?(/$id)?($slug)?";
        if (!servicePattern.contentEquals("default")) {
            globalPattern = "/" + servicePattern + "/(\\w{3,})(/$id)?($slug)?";
        }
        String actionMessage = "Action path " + servicePath + " doesn't match with global service pattern.";
        if (!servicePath.matches(globalPattern)) {
            DLOG.log(Level.ERROR, actionMessage);
            throw new ServiceException("WARN:" + actionMessage);
        }
        // Validate servicePath matches with one of servletPaths
        boolean hasMatchingPath = false;
        int p = 0;
        String servletPattern = "";
        while (!hasMatchingPath && p < servletPaths.length) {
            servletPattern = servletPaths[p].replaceAll("/\\*$", "/") + ".*";
            DLOG.log(Level.INFO, "Compare : " + servicePath + " / " + servletPattern);
            if (servicePath.matches(servletPattern)) {
                hasMatchingPath = true;
            }
            p++;
        }
        actionMessage = "Action path " + servicePath + " hasn't any matching path in Servlet.";
        if (!hasMatchingPath) {
            DLOG.log(Level.ERROR, actionMessage);
            throw new ServiceException("WARN:" + actionMessage);
        }
    }
	
}
