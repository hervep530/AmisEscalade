package com.ocherve.jcm.service.impl;

import com.ocherve.jcm.service.factory.DefaultService;

/**
 * @author herve_dev
 * 
 * Service call by Default servlet to set action Parameters and process action (or aborting)
 * 
 */
public class DefaultServiceImpl extends ServiceImpl implements DefaultService {


	protected final static String SVC_DEFAULT_URL = "";
	protected final static String[][] SVC_ACTIONS = {
			{"empty","/"},
			{"accueil","/accueil"},
			{"welcome","/welcome"},
			{"about","/about"},
			{"help","/help/$id/$slug"}
	};

	/**
	 * Service instanciation 
	 * The "super()" instruction is called to herit of ServiceImpl instanciation. All other instruction will
	 * customize this constructor
	 */
	public DefaultServiceImpl() {
		super(SVC_DEFAULT_URL);
	}

}
