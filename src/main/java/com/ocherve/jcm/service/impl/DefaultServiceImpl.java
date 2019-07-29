package com.ocherve.jcm.service.impl;

import com.ocherve.jcm.service.factory.DefaultService;

public class DefaultServiceImpl extends ServiceImpl implements DefaultService {


	private final static String SVC_DEFAULT_URL = "";
	private final static String[][] SVC_ACTIONS = {
			{"empty","/"}	
	};

	public DefaultServiceImpl() {
		super(SVC_DEFAULT_URL, SVC_ACTIONS);
	}

}
