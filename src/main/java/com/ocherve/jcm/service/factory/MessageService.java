package com.ocherve.jcm.service.factory;

import com.ocherve.jcm.service.Delivry;
import com.ocherve.jcm.service.Parameters;

/**
 * MessageService Interface
 * 
 * @author herve_dev
 *
 */
public interface MessageService extends Service {
	
	/**
	 * Execute Message service action when user requests to display message list 
	 * 
	 * @param parameters
	 * @return delivry which stores result of action in attributes used by jsp
	 */
	Delivry getList(Parameters parameters);
	
	/**
	 * Execute Message service action when user requests to display my message list 
	 * 
	 * @param parameters
	 * @return delivry which stores result of action in attributes used by jsp
	 */
	Delivry getMyDiscussions(Parameters parameters);

	/**
	 * Execute Message service action when user requests to display my message list 
	 * 
	 * @param parameters
	 * @return delivry which stores result of action in attributes used by jsp
	 */
	Delivry getFocusOnDiscussion(Parameters parameters);

	/**
	 * Execute Message service action when user requests to display a message 
	 * 
	 * @param parameters
	 * @return delivry which stores result of action in attributes used by jsp
	 */
	Delivry getMessage(Parameters parameters);
	
	/**
	 * Execute Message service action when user requests to display formular in order to create new message 
	 * 
	 * @param parameters
	 * @return delivry which stores result of action in attributes used by jsp
	 */
	Delivry getCreateForm(Parameters parameters);
		
	/**
	 * Execute Message service action when user requests to delete a message 
	 * 
	 * @param parameters
	 * @return delivry which stores result of action in attributes used by jsp
	 */
	Delivry delete(Parameters parameters);
	
	/**
	 * Execute Message service action when user requests to create a new message from data send in form 
	 * 
	 * @param parameters
	 * @return delivry which stores result of action in attributes used by jsp
	 */
	Delivry postCreateForm(Parameters parameters);
	
	/**
	 * Getter
	 * 
	 * @return final LIST_LIMIT
	 */
	Long getListLimit();
	
}
