package com.ocherve.jcm.service.impl;

import com.ocherve.jcm.service.factory.SessionService;

/**
 * @author herve_dev
 *
 * Service call by Session servlet to set action Parameters and process action (or aborting)
 * 
 */
public class SessionServiceImpl extends ServiceImpl implements SessionService {
	
	private final static String SVC_DEFAULT_URL = "session/moncompte";
	private final static String[][] SVC_ACTIONS = {
			{"connexion","/session/connexion"},	
			{"deconnexion","/session/deconnexion"},	
			{"inscription","/session/inscription"},	
			{"moncompte","/session/pass"},	
			{"moncompte","/session/d/$id"}
	};

	/**
	 * Service instanciation 
	 * The "super()" instruction is called to herit of ServiceImpl instanciation. All other instruction will
	 * customize this constructor
	 */
	public SessionServiceImpl() {
		super(SVC_DEFAULT_URL, SVC_ACTIONS);
	}

}
