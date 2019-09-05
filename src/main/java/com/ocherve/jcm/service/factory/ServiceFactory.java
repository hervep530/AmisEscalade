package com.ocherve.jcm.service.factory;

import com.ocherve.jcm.service.impl.CommentServiceImpl;
import com.ocherve.jcm.service.impl.DefaultServiceImpl;
import com.ocherve.jcm.service.impl.MessageServiceImpl;
import com.ocherve.jcm.service.impl.SessionServiceImpl;
import com.ocherve.jcm.service.impl.SiteServiceImpl;
import com.ocherve.jcm.service.impl.TopoServiceImpl;
import com.ocherve.jcm.service.impl.UserServiceImpl;

/**
 * @author herve_dev
 * 
 * Given a specific Service Interface return a specific service with generic type
 */
public interface ServiceFactory {

	/**
	 * @param className		Class : Interface matching with service requested
	 * @return				Service : a specific service (for example SessionService) with generic type Service
	 */
	public static Service getService(Class<?> className) {
		
		Service service = null;
		switch ( className.getSimpleName() ) {
		case "DefaultService" :
			service = new DefaultServiceImpl();
			break;
		case "SessionService" :
			service = new SessionServiceImpl();
			break;
		case "SiteService" :
			service = new SiteServiceImpl();
			break;
		case "TopoService" :
			service = new TopoServiceImpl();
			break;
		case "CommentService" :
			service = new CommentServiceImpl();
			break;
		case "MessageService" :
			service = new MessageServiceImpl();
			break;
		case "UserService" :
			service = new UserServiceImpl();
			break;
		}
		return service;
	}

}
