package com.ocherve.jcm.service.impl;

import com.ocherve.jcm.service.factory.SessionService;

public class SessionServiceImpl extends ServiceImpl implements SessionService {
	
	private final static String SVC_DEFAULT_URL = "session/moncompte";
	private final static String[][] SVC_ACTIONS = {
			{"connexion","/session/connexion"},	
			{"deconnexion","/session/deconnexion"},	
			{"inscription","/session/inscription"},	
			{"moncompte","/session/moncompte"},	
	};

	public SessionServiceImpl() {
		super(SVC_DEFAULT_URL, SVC_ACTIONS);
	}

}
