package com.ocherve.jcm.service;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import com.ocherve.jcm.service.factory.CommentService;
import com.ocherve.jcm.service.factory.DefaultService;
import com.ocherve.jcm.service.factory.MessageService;
import com.ocherve.jcm.service.factory.Service;
import com.ocherve.jcm.service.factory.ServiceFactory;
import com.ocherve.jcm.service.factory.SessionService;
import com.ocherve.jcm.service.factory.SiteService;
import com.ocherve.jcm.service.factory.TopoService;
import com.ocherve.jcm.service.factory.UserService;

/**
 * @author herve_dev
 * 
 * Services proxy which instanciate all services and keep it as Class variable (cache)
 */
public class ServiceProxy {
	
    protected static final Logger dev = LogManager.getLogger("development_file");
	private Service defaultService;
	private Service sessionService;
	private Service siteService;
	private Service topoService;
	private Service commentService;
	private Service messageService;
	private Service userService;
	private static ServiceProxy instance;
	
	
	private ServiceProxy() {
		Configurator.setLevel(dev.getName(), Level.TRACE);
		dev.log(Level.DEBUG,"Instanciate Service Proxy");
		this.defaultService = ServiceFactory.getService(DefaultService.class);
		this.sessionService = ServiceFactory.getService(SessionService.class);
		this.siteService = ServiceFactory.getService(SiteService.class);
		this.topoService = ServiceFactory.getService(TopoService.class);
		this.commentService = ServiceFactory.getService(CommentService.class);
		this.messageService = ServiceFactory.getService(MessageService.class);
		this.userService = ServiceFactory.getService(UserService.class);
	}

	/**
	 * @return		ServiceProxy : the proxy itself
	 */
	public static ServiceProxy getInstance() {
		if ( instance == null ) instance = new ServiceProxy();
		return instance;
	}

	/**
	 * Getter
	 * 
	 * @return		Service : DefaultServiceImpl under generic form
	 */
	public Service getDefaultService() {
		return defaultService;
	}

	/**
	 * Getter
	 * 
	 * @return		Service : SessionServiceImpl under generic form
	 */
	public Service getSessionService() {
		return sessionService;
	}

	/**
	 * @return the siteService
	 */
	public Service getSiteService() {
		return siteService;
	}

	/**
	 * @return the topoService
	 */
	public Service getTopoService() {
		return topoService;
	}

	/**
	 * @return the commentService
	 */
	public Service getCommentService() {
		return commentService;
	}

	/**
	 * @return the messageService
	 */
	public Service getMessageService() {
		return messageService;
	}

	/**
	 * @return the userService
	 */
	public Service getUserService() {
		return userService;
	}
	
	

}
