package com.ocherve.jcm;

import java.io.File;
import java.util.Map.Entry;

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
	
	private static final Logger DLOG = LogManager.getLogger("development_file");
	private static final Logger logger = LogManager.getLogger("root");
    private static final String UPLOAD_PATH = "/webapps/AmisEscalade/medias";

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		//Property helper just used for dao choice (only hibernate_em works, so using without file)
		PropertyHelper.loadConfig("config.properties");
		Configurator.setLevel(DLOG.getName(), Level.TRACE);
		for ( Entry<Object,Object> property : PropertyHelper.getProperties().entrySet() ) {
			if ( property.getKey().toString().contains("jcm.") )
				DLOG.log(Level.INFO, property.getKey().toString() + " => " + property.getValue() );
		}
		ServiceProxy.getInstance();
		DaoProxy.getInstance();
		validateMediaPath();
		DLOG.log(Level.DEBUG, "Jcm Web application is now started.");	
		Configurator.setLevel(logger.getName(), Level.INFO);
		logger.log(Level.INFO, getStartingMessage());		
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		DaoProxy.closeInstance();
		DLOG.log(Level.DEBUG, "Jcm Web application is now stopped.");
	}
	
	private void validateMediaPath() {
		File tmp = new File(System.getProperty("user.home") + "webapps/tmp");
		File topoImageDir = new File(System.getProperty("catalina.base") + UPLOAD_PATH + "/topo");
		File siteImageDir = new File(System.getProperty("catalina.base") + UPLOAD_PATH + "/site");
		try {
			if ( ! topoImageDir.exists() ) 
				topoImageDir.mkdirs();
			else if ( ! topoImageDir.isDirectory() ) {
				topoImageDir.delete();
				topoImageDir.mkdirs();				
			}
			if ( ! siteImageDir.exists() )
				siteImageDir.mkdirs();			
			else if ( ! topoImageDir.isDirectory() ) {
				siteImageDir.delete();
				siteImageDir.mkdirs();				
			}
			if ( ! tmp.exists() )
				tmp.mkdirs();			
			else if ( ! tmp.isDirectory() ) {
				tmp.delete();
				tmp.mkdirs();				
			}
		} catch (Exception e) {
			DLOG.log(Level.WARN, "Cannot create medias directory.");			
		}
	}

	private static String getStartingMessage() {
		String message = "%n%n    #  #### #   #   #### #####   #   ####  ##### ##### #### %n"
				+ "    # #     ## ##  #       #    ###  #   #   #   #     #   #%n" 
				+ "    # #     # # #   ###    #    # #  ####    #   ####  #   #%n"
				+ "#   # #     #   #      #   #   ##### #  #    #   #     #   #%n"
				+ " ###   #### #   #  ####    #   #   # #   #   #   ##### #### %n%n";
		return String.format(message);
	}
}
