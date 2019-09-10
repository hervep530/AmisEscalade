package com.ocherve.jcm;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import com.ocherve.jcm.dao.DaoProxy;
import com.ocherve.jcm.service.ServiceProxy;

/**
 * @author herve_dev
 *
 */
@WebListener
public class JcmServletContextListener implements ServletContextListener{
	
	protected static final Logger DLOG = LogManager.getLogger("development_file");

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		DLOG.log(Level.DEBUG, "Jcm Web application is now stopped.");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		Configurator.setLevel(DLOG.getName(), Level.TRACE);
		ServiceProxy.getInstance();
		DaoProxy.getInstance();
		DLOG.log(Level.DEBUG, "Jcm Web application is now stopped.");		
	}

}
