package com.ocherve.jcm.service.factory;

import java.util.List;

import com.ocherve.jcm.model.Site;
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
	Delivry postFindForm(Parameters parameters);
	
	/**
	 * @param parameters 
	 * @return Site requested with Get method
	 */
	Delivry getSite(Parameters parameters);
	
	/**
	 *  requesting formular to create new Site
	 * @param parameters 
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
	 */
	Delivry getUpdateForm(Parameters parameters);
	
	
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
