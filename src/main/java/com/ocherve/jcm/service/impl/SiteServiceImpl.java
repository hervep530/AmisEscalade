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
			{"f","/site/f"},
			{"r","/site/r/$id/$slug"},
			{"c","/site/c"},
			{"u","/site/u/$id"},
			{"uac","/site/uac"},
			{"umc","/site/umc"},
			{"ut","/site/ut"},
			{"utt","/site/utt/$id"},
			{"utf","/site/utf/$id"},
			{"upt","/site/upt/$id"},
			{"upf","/site/upf/$id"},
			{"d","/site/d"}
	};

	/**
	 * Constructor 
	 */
	public SiteServiceImpl() {
		super(SVC_DEFAULT_URL, SVC_ACTIONS);
	}

}
