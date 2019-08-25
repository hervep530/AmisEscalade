package com.ocherve.jcm.dao.contract;

import java.util.List;
import java.util.Map;

import com.ocherve.jcm.model.Cotation;
import com.ocherve.jcm.model.Site;

/**
 * @author herve_dev
 *
 * DAO specific Interface for Site Module
 */
public interface SiteDao extends Dao {


	/**
	 * @param site
	 * @return site created
	 */
	Site create( Site site );

	/**
	 * @param id
	 * @param fields
	 * @return site
	 */
	Site update(Integer id, Map<String, Object> fields);

	/**
	 * @param site
	 * @return site updated
	 */
	Site update(Site site);

	/**
	 * @param id
	 * @return site requested
	 */
	Site get(Integer id );
		
	/**
	 * @return site list
	 */
	List<Site> getList();
	
	/**
	 * @param cotationMax
	 * @return sites list
	 */
	List<Site> getSitesWhereCotationMaxGreaterThan(Cotation cotationMax);

	/**
	 * @param cotationMin
	 * @return sites list
	 */
	List<Site> getSitesWhereCotationMinLessThan(Cotation cotationMin);

	/**
	 * @param id
	 * @return true if delete , false if not
	 */
	boolean delete (Integer id);
	
	/**
	 * @param cotationName
	 * @return cotation
	 */
	Cotation getCotationByLabel(String cotationName);

	
	
}
