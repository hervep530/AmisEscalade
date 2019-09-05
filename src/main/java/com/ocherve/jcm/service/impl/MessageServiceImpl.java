package com.ocherve.jcm.service.impl;

import com.ocherve.jcm.service.factory.MessageService;

/**
 * MessageService Implementation
 * 
 * @author herve_dev
 *
 */
public class MessageServiceImpl extends ServiceImpl implements MessageService {

	private final static String SVC_DEFAULT_URL = "";
	private final static String[][] SVC_ACTIONS = {
			{"l","/message/l/$id"},
			{"ls","/message/ls/$id"},
			{"lr","/message/lr/$id"},
			{"r","/message/r/$id"},
			{"c","/message/c"},
			{"d","/message/d/$id"}
	};

	/**
	 * Constructor 
	 */
	public MessageServiceImpl() {
		super(SVC_DEFAULT_URL, SVC_ACTIONS);
	}


}
