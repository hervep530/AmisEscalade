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
	 * @param em 
	 * @param entityClass
	 * @param object
	 * @return entity manager by dao
	 */
	Object create(Class<?> entityClass, Object object);

	/**
	 * @param em 
	 * @param entityClass
	 * @param id
	 * @param fields 
	 * @return entity manager by dao
	 */
	Object update(Class<?> entityClass, Integer id, Map<String,Object> fields);

	/**
	 * @param em 
	 * @param entityClass
	 * @param id
	 * @param newObject
	 * 
	 * @return object updated
	 */
	Object update(Class<?> entityClass, Integer id, Object newObject);
	
	/**
	 * @param em 
	 * @param entityClass
	 * @param id
	 * @return entity manager by dao
	 */
	Object get(Class<?> entityClass, Integer id);
	
	/**
	 * @param entityClass
	 * @param namedQuery
	 * @param Parameters
	 * @return list of requested objects
	 */
	List<?> getListFromNamedQuery(Class<?> entityClass, String namedQuery, Map<String, Object> Parameters);

	/**
	 * @param entityClass
	 * @param namedQuery
	 * @param Parameters
	 * @return id
	 */
	Object getColumnsFromNamedQuery(Class<?> entityClass, String namedQuery, Map<String, Object> Parameters);

	/**
	 * @param entityClass
	 * @param namedQuery
	 * @param Parameters
	 * @return list of requested objects
	 */
	Long getCountFromNamedQuery(Class<?> entityClass, String namedQuery, Map<String, Object> Parameters);
	
	/**
	 * @param entityClass
	 * @param namedQuery
	 * @param id
	 * @return List of Objects
	 */
	List<?> getListFromNamedQueryAndIdParameter(Class<?> entityClass, String namedQuery, Integer id);
	
	/**
	 * @param em 
	 * @param entityClass
	 * @param id
	 * @return true if deleted, false if not deleted
	 */
	boolean delete(Class<?> entityClass, Integer id);

	
}
