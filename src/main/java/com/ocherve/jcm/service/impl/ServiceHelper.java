package com.ocherve.jcm.service.impl;

import com.ocherve.jcm.service.ServiceException;
import com.ocherve.jcm.service.impl.DefaultServiceImpl;

/**
 * ServiceHelper Class provides static properties and methods to :
 * 		- initialized ServiceProperties once for each ServiceImpl
 * 		- use ServiceProperties on demand
 * 
 * @author herve_dev
 *
 */
public class ServiceHelper {
	
	private static ServiceProperties defaultServiceProperties;
	private static ServiceProperties sessionServiceProperties;
	private static ServiceProperties siteServiceProperties;
	private static ServiceProperties topoServiceProperties;
	private static ServiceProperties commentServiceProperties;
	private static ServiceProperties messageServiceProperties;
	private static ServiceProperties userServiceProperties;
	private static boolean ready = false;

	/**
	 * 
	 */
	private ServiceHelper() {
		if ( ! ready ) {
			try {
				defaultServiceProperties = new ServiceProperties(DefaultServiceImpl.class, DefaultServiceImpl.SVC_ACTIONS);
				sessionServiceProperties = new ServiceProperties(SessionServiceImpl.class, SessionServiceImpl.SVC_ACTIONS);
				siteServiceProperties = new ServiceProperties(SiteServiceImpl.class, SiteServiceImpl.SVC_ACTIONS);
				topoServiceProperties = new ServiceProperties(TopoServiceImpl.class, TopoServiceImpl.SVC_ACTIONS);
				commentServiceProperties = new ServiceProperties(CommentServiceImpl.class, CommentServiceImpl.SVC_ACTIONS);
				messageServiceProperties = new ServiceProperties(MessageServiceImpl.class, MessageServiceImpl.SVC_ACTIONS);
				userServiceProperties = new ServiceProperties(UserServiceImpl.class, UserServiceImpl.SVC_ACTIONS);
				ready = true;
			} catch (ServiceException e ) {
				throw new ServiceException("ServiceHelper can not be initialized (" + e.getMessage() + ").");				
			}
		}
	}
	
	protected static ServiceProperties getInstance(Class<?> serviceClass) {
		new ServiceHelper();
		switch (serviceClass.getSimpleName()) {
			case "DefaultServiceImpl" :
				return defaultServiceProperties;
			case "SiteServiceImpl" :
				return siteServiceProperties;
			case "TopoServiceImpl" :
				return topoServiceProperties;
			case "SessionServiceImpl" :
				return sessionServiceProperties;
			case "CommentServiceImpl" :
				return commentServiceProperties;
			case "MessageServiceImpl" :
				return messageServiceProperties;
			case "UserServiceImpl" :
				return userServiceProperties;
		}
		return null;		
	}

	/**
	 * @return the ready
	 */
	protected static boolean isReady() {
		return ready;
	}
	
}
