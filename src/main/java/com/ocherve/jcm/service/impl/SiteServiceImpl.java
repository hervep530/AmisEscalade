package com.ocherve.jcm.service.impl;

import com.ocherve.jcm.service.factory.SiteService;

/**
 * Implementing Service for Site component
 * 
 * @author herve_dev
 *
 */
public class SiteServiceImpl extends ServiceImpl implements SiteService {
	
	private final static String SVC_DEFAULT_URL = "";
	private final static String[][] SVC_ACTIONS = {
			{"l","/site/l/$id"},
			{"v","/site/v/$id/$slug"},
			{"c","/site/c"},
			{"m","/site/m/$id/$slug"},
	};

	/**
	 * Constructor 
	 */
	public SiteServiceImpl() {
		super(SVC_DEFAULT_URL, SVC_ACTIONS);
	}

}
