package com.ocherve.jcm.service;

import com.ocherve.jcm.service.factory.DefaultService;
import com.ocherve.jcm.service.factory.Service;
import com.ocherve.jcm.service.factory.ServiceFactory;

public class ServiceProxy {
	
	private Service defaultService;
	private static ServiceProxy instance = new ServiceProxy();
	
	
	private ServiceProxy() {
		this.defaultService = ServiceFactory.getService(DefaultService.class);
	}

	public static ServiceProxy getInstance() {
		return instance;
	}

	public Service getDefaultService() {
		return defaultService;
	}


}
