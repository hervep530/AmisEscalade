package com.ocherve.jcm.service.factory;

import com.ocherve.jcm.service.Delivry;
import com.ocherve.jcm.service.Parameters;

/**
 * TopoService Interface
 * 
 * @author herve_dev
 *
 */
public interface TopoService extends Service {

	/**
	 * Execute Topo service action when user requests to display topo list 
	 * 
	 * @param parameters
	 * @return delivry which stores result of action in attributes used by jsp
	 */
	Delivry getPublishedList(Parameters parameters);

	/**
	 * Execute Topo service action when user requests to display a topo 
	 * 
	 * @param parameters
	 * @return delivry which stores result of action in attributes used by jsp
	 */
	Delivry getTopo(Parameters parameters);
	
	/**
	 * Execute Topo service action when user requests to display formular in order to create new topo 
	 * 
	 * @param parameters
	 * @return delivry which stores result of action in attributes used by jsp
	 */
	Delivry getCreateForm(Parameters parameters);
	
	/**
	 * Execute Topo service action when user requests to display formular in order to update existent topo 
	 * 
	 * @param parameters
	 * @return delivry which stores result of action in attributes used by jsp
	 */
	Delivry getUpdateForm(Parameters parameters);
	
	/**
	 * Execute Topo service action when user requests to modify a topo publish status 
	 * 
	 * @param parameters
	 * @return delivry which stores result of action in attributes used by jsp
	 */
	Delivry putPublishStatus(Parameters parameters);

	/**
	 * Execute Topo service action when user requests to modify a topo availability status 
	 * 
	 * @param parameters
	 * @return delivry which stores result of action in attributes used by jsp
	 */
	Delivry putAvailability(Parameters parameters);
	
	/**
	 * Execute Topo service action when user requests to delete a topo 
	 * 
	 * @param parameters
	 * @return delivry which stores result of action in attributes used by jsp
	 */
	Delivry delete(Parameters parameters);
	
	/**
	 * Execute Topo service action when user requests to create a new topo from data send in form 
	 * 
	 * @param parameters
	 * @return delivry which stores result of action in attributes used by jsp
	 */
	Delivry postCreateForm(Parameters parameters);
	
	/**
	 * Execute Topo service action when user requests to update a topo from data send in form  
	 * 
	 * @param parameters
	 * @return delivry which stores result of action in attributes used by jsp
	 */
	Delivry postUpdateForm(Parameters parameters);

}
