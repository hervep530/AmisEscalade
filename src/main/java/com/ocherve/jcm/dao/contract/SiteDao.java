package com.ocherve.jcm.dao.contract;

import java.util.List;

import com.ocherve.jcm.model.Site;

/**
 * @author herve_dev
 *
 * DAO specific Interface for Site Module
 */
public interface SiteDao extends Dao {

	Site create(Site site);
	
	Site get(Integer id);
	
	List<Site> getList();
	
	boolean delete(Integer id);
	
	
	
}
