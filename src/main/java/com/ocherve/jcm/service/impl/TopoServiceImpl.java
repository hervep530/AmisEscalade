package com.ocherve.jcm.service.impl;

import com.ocherve.jcm.service.Delivry;
import com.ocherve.jcm.service.Parameters;
import com.ocherve.jcm.service.factory.TopoService;

/**
 * TopoService Implementation 
 * 
 * @author herve_dev
 *
 */
public class TopoServiceImpl extends ServiceImpl implements TopoService {

	protected final static String SVC_DEFAULT_URL = "";
	protected final static String[][] SVC_ACTIONS = {
			{"l","/topo/l/$id"},
			{"r","/topo/r/$id/$slug"},
			{"c","/topo/c/$id/$slug"},
			{"u","/topo/u/$id/$slug"},
			{"uat","/topo/uat/$id/$slug"},
			{"uaf","/topo/uaf/$id/$slug"},
			{"upt","/topo/upt/$id/$slug"},
			{"upf","/topo/upf/$id/$slug"},
			{"d","/topo/d/$id/$slug"}
	};

	/**
	 * Constructor 
	 */
	public TopoServiceImpl() {
		super(SVC_DEFAULT_URL);
	}

	@Override
	public Delivry getPublishedList(Parameters parameters) {
		// TODO Auto-generated method stub
		return this.getDefaultDelivry(parameters);
	}

	@Override
	public Delivry getTopo(Parameters parameters) {
		// TODO Auto-generated method stub
		return this.getDefaultDelivry(parameters);
	}

	@Override
	public Delivry getCreateForm(Parameters parameters) {
		// TODO Auto-generated method stub
		return this.getDefaultDelivry(parameters);
	}

	@Override
	public Delivry getUpdateForm(Parameters parameters) {
		// TODO Auto-generated method stub
		return this.getDefaultDelivry(parameters);
	}

	@Override
	public Delivry putAvailability(Parameters parameters) {
		// TODO Auto-generated method stub
		return this.getDefaultDelivry(parameters);
	}

	@Override
	public Delivry putPublishStatus(Parameters parameters) {
		// TODO Auto-generated method stub
		return this.getDefaultDelivry(parameters);
	}

	@Override
	public Delivry delete(Parameters parameters) {
		// TODO Auto-generated method stub
		return this.getDefaultDelivry(parameters);
	}

	@Override
	public Delivry postCreateForm(Parameters parameters) {
		// TODO Auto-generated method stub
		return this.getDefaultDelivry(parameters);
	}

	@Override
	public Delivry postUpdateForm(Parameters parameters) {
		// TODO Auto-generated method stub
		return this.getDefaultDelivry(parameters);
	}

}
