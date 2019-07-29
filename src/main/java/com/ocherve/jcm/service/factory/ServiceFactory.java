package com.ocherve.jcm.service.factory;

import com.ocherve.jcm.service.impl.DefaultServiceImpl;

public interface ServiceFactory {

	public static Service getService(Class<?> className) {
		
		Service service = null;
		switch ( className.getSimpleName() ) {
		case "DefaultService" :
			service = new DefaultServiceImpl();
			break;
		}
		return service;
	}

}
