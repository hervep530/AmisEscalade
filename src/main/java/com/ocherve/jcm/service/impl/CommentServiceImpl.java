package com.ocherve.jcm.service.impl;

import com.ocherve.jcm.service.factory.CommentService;

/**
 * CommentService Implementation
 * 
 * @author herve_dev
 *
 */
public class CommentServiceImpl extends ServiceImpl implements CommentService {

	protected final static String SVC_DEFAULT_URL = "";
	protected final static String[][] SVC_ACTIONS = {
			{"l","/comment/l/$id"},
			{"u","/comment/u/$id"},
			{"upt","/comment/upt/$id"},
			{"upf","/comment/upf/$id"},
			{"d","/comment/d"}
	};

	/**
	 * Constructor 
	 */
	public CommentServiceImpl() {
		super(SVC_DEFAULT_URL);
	}


}
