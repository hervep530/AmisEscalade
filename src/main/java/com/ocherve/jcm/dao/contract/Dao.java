package com.ocherve.jcm.dao.contract;

import java.util.List;
import java.util.Map;

/**
 * @author herve_dev
 * 
 * Dao Generic Interface
 */
public interface Dao {

	/**
	 * @param entityClass
	 * @param object
	 * @return entity manager by dao
	 */
	Object create(Class<?> entityClass, Object object);
	
	/**
	 * @param entityClass
	 * @param id
	 * @return entity manager by dao
	 */
	Object get(Class<?> entityClass, Integer id);
	
	/**
	 * @param entityClass 
	 * @param attributes
	 * @param clauses
	 * @return List of object
	 */
	List<?> getEntityFromFilteredQuery(Class<?> entityClass, Map<String,String> clauses);
	
	/**
	 * @param entityClass
	 * @param id
	 * @return true if deleted, false if not deleted
	 */
	boolean delete(Class<?> entityClass, Integer id);
	
}
