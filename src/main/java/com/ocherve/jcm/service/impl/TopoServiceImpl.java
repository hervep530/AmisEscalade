package com.ocherve.jcm.service.impl;

import com.ocherve.jcm.service.factory.TopoService;

/**
 * TopoService Implementation 
 * 
 * @author herve_dev
 *
 */
public class TopoServiceImpl extends ServiceImpl implements TopoService {

	private final static String SVC_DEFAULT_URL = "";
	private final static String[][] SVC_ACTIONS = {
			{"l","/topo/l/$id"},
			{"r","/topo/r/$id/$slug"},
			{"c","/topo/c"},
			{"u","/topo/u/$id"},
			{"uat","/topo/uat/$id"},
			{"uaf","/topo/uaf/$id"},
			{"upt","/topo/upt/$id"},
			{"upf","/topo/upf/$id"},
			{"d","/topo/d"}
	};

	/**
	 * Constructor 
	 */
	public TopoServiceImpl() {
		super(SVC_DEFAULT_URL, SVC_ACTIONS);
	}


}
