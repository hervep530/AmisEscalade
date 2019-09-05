package com.ocherve.jcm.service.impl;

import com.ocherve.jcm.service.factory.UserService;

/**
 * UserService Implementation
 * 
 * @author herve_dev
 *
 */
public class UserServiceImpl extends ServiceImpl implements UserService{

	private final static String SVC_DEFAULT_URL = "";
	private final static String[][] SVC_ACTIONS = {
			{"l","/user/l/$id"},
			{"f","/user/f/$id"},
			{"c","/user/c"},
			{"r","/user/r/$id/$slug"},
			{"u","/user/u/$id"},
			{"d","/user/d"},
	};

	/**
	 * Constructor 
	 */
	public UserServiceImpl() {
		super(SVC_DEFAULT_URL, SVC_ACTIONS);
	}


}
