package com.ocherve.jcm.service.factory;

import com.ocherve.jcm.service.Delivry;
import com.ocherve.jcm.service.Parameters;

/**
 * Interface for SiteService
 * 
 * @author herve_dev
 *
 */
public interface SiteService extends Service {
	
	/**
	 * @param parameters 
	 * @return Site List from dao
	 */
	Delivry getList(Parameters parameters);
	
	/**
	 * @param parameters 
	 * @return Site requested with Get method
	 */
	Delivry getSite(Parameters parameters);
	
	/**
	 * Getting data for formular to create update or search Site
	 * 
	 * @param parameters 
	 * @return delivry
	 */
	Delivry getSiteForm(Parameters parameters);
		
	

	/**
	 * @param parameters 
	 * @return Site after update with get method
	 */
	Delivry putPublishedStatus(Parameters parameters);
	
	/**
	 * @param parameters 
	 * @return Site after update with get method
	 */
	Delivry putFriendTag(Parameters parameters);
	
	
	
	/**
	 * @param parameters 
	 * @return Site after delete with get method
	 */
	Delivry delete(Parameters parameters);

	
	
	/**
	 * @param parameters 
	 * @return site created form form sent with post method
	 */
	Delivry postCreateForm(Parameters parameters);
	
	/**
	 * @param parameters 
	 * @return Site updated from form send with post method
	 */
	Delivry postUpdateForm(Parameters parameters);

	/**
	 * @param parameters 
	 * @return Site List from request send to dao with form with post method
	 */
	Delivry postFindForm(Parameters parameters);
	
	/**
	 * Method call when comment is posted from Site content (RequestMethod : 'POST' - url : '/site/uac/$id')
	 *  
	 * @param parameters
	 * @return delivry as result
	 */
	Delivry postAddCommentForm(Parameters parameters);
		
}
