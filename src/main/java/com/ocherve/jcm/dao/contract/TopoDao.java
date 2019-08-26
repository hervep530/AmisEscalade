package com.ocherve.jcm.dao.contract;

import java.util.List;
import java.util.Map;

import com.ocherve.jcm.model.Topo;

/**
 * @author herve_dev
 *
 * DAO specific Interface for Topo Module
 */
public interface TopoDao extends Dao {


	/**
	 * @param topo
	 * @return topo created
	 */
	Topo create( Topo topo );

	/**
	 * @param id
	 * @param fields
	 * @return topo
	 */
	Topo update(Integer id, Map<String, Object> fields);

	/**
	 * @param topo
	 * @return topo updated
	 */
	Topo update(Topo topo);

	/**
	 * @param id
	 * @return topo requested
	 */
	Topo get(Integer id );
		
	/**
	 * @return topo list
	 */
	List<Topo> getList();
	
	/**
	 * @param available
	 * @return topos list
	 */
	List<Topo> getToposByAvailability(Boolean available);

	/**
	 * @param published
	 * @return topos list
	 */
	List<Topo> getToposByPublishingStatus(Boolean published);
	
	/**
	 * @param id
	 * @return Topo with author matching with parameter id
	 */
	List<Topo> getByAuthor(Integer id);
	
	/**
	 * @param id
	 * @return topos for site matching with id as parameter
	 */
	List<Topo> getBySite(Integer id);

	/**
	 * @param id
	 * @return true if delete , false if not
	 */
	boolean delete (Integer id);
	
}
