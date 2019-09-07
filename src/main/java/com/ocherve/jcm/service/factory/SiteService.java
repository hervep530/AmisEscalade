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
	List<Site> getList(Parameters parameters);
	
	/**
	 * @param parameters 
	 * @return Site List from request send to dao with form with post method
	 */
	List<Site> postFindForm(Parameters parameters);
	
	/**
	 * @param parameters 
	 * @return Site requested with Get method
	 */
	Delivry getSite(Parameters parameters);
	
	/**
	 *  requesting formular to create new Site
	 * @param parameters 
	 */
	void getCreateForm(Parameters parameters);
	
	/**
	 * @param parameters 
	 * @return site created form form sent with post method
	 */
	Site postCreateForm(Parameters parameters);
	
	/**
	 * requesting formular to update Site
	 * @param parameters 
	 */
	void getUpdateForm(Parameters parameters);
	
	
	/**
	 * @param parameters 
	 * @return Site updated from form send with post method
	 */
	Site postUpdateForm(Parameters parameters);
	
	/**
	 * @param parameters 
	 * @return Site updated after adding comment with post method
	 */
	Site postAddComment(Parameters parameters);
	
	/**
	 * @param parameters 
	 * @return Site without modification - only comment is modified with post method
	 */
	Site postUpdateComment(Parameters parameters);
	
	/**
	 * @param parameters 
	 * @return Site after update with get method
	 */
	Site getUpdatePublishedStatus(Parameters parameters);
	
	/**
	 * @param parameters 
	 * @return Site after update with get method
	 */
	Site getUpdateTag(Parameters parameters);
	
	/**
	 * @param parameters 
	 * @return Site after delete with get method
	 */
	Site getDelete(Parameters parameters);
	
}
