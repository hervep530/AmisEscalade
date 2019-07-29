package com.ocherve.jcm.service.factory;

import com.ocherve.jcm.service.impl.DefaultServiceImpl;
import com.ocherve.jcm.service.impl.SessionServiceImpl;

public interface ServiceFactory {

	public static Service getService(Class<?> className) {
		
		Service service = null;
		switch ( className.getSimpleName() ) {
		case "DefaultService" :
			service = new DefaultServiceImpl();
			break;
		case "SessionService" :
			service = new SessionServiceImpl();
			break;
		}
		return service;
	}

}
