package com.ocherve.jcm.service.impl;

import javax.servlet.http.HttpServletRequest;

import com.ocherve.jcm.service.Delivry;
import com.ocherve.jcm.service.Parameters;
import com.ocherve.jcm.service.UrlException;
import com.ocherve.jcm.service.factory.Service;

public abstract class ServiceImpl implements Service {

	@Override
	public Parameters setParameters(HttpServletRequest request) throws UrlException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Delivry doGetAction(Parameters parameters) throws UrlException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Delivry doPostAction(Parameters parameters) throws UrlException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Delivry abort(Parameters parameters) {
		// TODO Auto-generated method stub
		return null;
	}

}
