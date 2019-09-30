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
	 * @return Site List from request send to dao with form with post method
	 */
	Delivry getFindForm(Parameters parameters);

	/**
	 * @param parameters 
	 * @return Site List from request send to dao with form with post method
	 */
	Delivry postFindForm(Parameters parameters);
	
	/**
	 * @param parameters 
	 * @return Site requested with Get method
	 */
	Delivry getSite(Parameters parameters);
	
	/**
	 *  requesting formular to create new Site
	 * @param parameters 
	 * @return delivry
	 */
	Delivry getCreateForm(Parameters parameters);
	
	/**
	 * @param parameters 
	 * @return site created form form sent with post method
	 */
	Delivry postCreateForm(Parameters parameters);
	
	/**
	 * requesting formular to update Site
	 * @param parameters 
	 * @return delivry
	 */
	Delivry getUpdateForm(Parameters parameters);
	
	/**
	 * Method call when comment is posted from Site content (RequestMethod : 'POST' - url : '/site/uac/$id')
	 *  
	 * @param parameters
	 * @return delivry as result
	 */
	Delivry postAddCommentForm(Parameters parameters);
	
	/**
	 * @param parameters 
	 * @return Site updated from form send with post method
	 */
	Delivry postUpdateForm(Parameters parameters);
	
	/**
	 * @param parameters 
	 * @return Site updated after adding comment with post method
	 */
	Delivry postAddComment(Parameters parameters);
	
	/**
	 * @param parameters 
	 * @return Site without modification - only comment is modified with post method
	 */
	Delivry postUpdateComment(Parameters parameters);
	
	/**
	 * @param parameters 
	 * @return Site after update with get method
	 */
	Delivry getUpdatePublishedStatus(Parameters parameters);
	
	/**
	 * @param parameters 
	 * @return Site after update with get method
	 */
	Delivry getUpdateTag(Parameters parameters);
	
	/**
	 * @param parameters 
	 * @return Site after delete with get method
	 */
	Delivry getDelete(Parameters parameters);
	
}
