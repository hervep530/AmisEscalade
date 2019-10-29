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
	 * @param queryName
	 * @param parameters
	 * @return site id
	 */
	Integer getIdFromNamedQuery(String queryName, Map<String, Object> parameters);

	/**
	 * @param entityClass
	 * @param namedQuery
	 * @param parameters
	 * @return list of requested objects
	 */
	List<?> getListFromNamedQuery(Class<?> entityClass, String namedQuery, Map<String, Object> parameters);

	/**
	 * @param entityClass
	 * @param namedQuery
	 * @param parameters
	 * @return id
	 */
	Object getColumnsFromNamedQuery(Class<?> entityClass, String namedQuery, Map<String, Object> parameters);

	/**
	 * @param entityClass
	 * @param namedQuery
	 * @param parameters
	 * @return list of requested objects
	 */
	Long getCountFromNamedQuery(Class<?> entityClass, String namedQuery, Map<String, Object> parameters);
	
	/**
	 * @param entityClass
	 * @param queryString
	 * @param parameters
	 * @return entitiescount   
	 */
	Long getCountFromFilteredQuery(Class<?> entityClass, String queryString, Map<String, Object> parameters);
	
	/**
	 * @param entityClass
	 * @param namedQuery
	 * @param id
	 * @return List of Objects
	 */
	List<?> getListFromNamedQueryAndIdParameter(Class<?> entityClass, String namedQuery, Integer id);

	/**
	 * @param entityClass
	 * @param queryString
	 * @param parameters
	 * @return List of Objects
	 */
	List<?> getListFromFilteredQuery(Class<?> entityClass, String queryString, Map<String, Object> parameters);

	/**
	 * @param em 
	 * @param entityClass
	 * @param id
	 * @return true if deleted, false if not deleted
	 */
	boolean delete(Class<?> entityClass, Integer id);
	
	/**
	 * Refresh entity when a child in collection is updated
	 * 
	 * @param entityClass
	 * @param id
	 */
	void refresh(Class<?> entityClass, Integer id);

	/**
	 * Refresh entity when a child in collection is updated
	 * 
	 * @param entityClass
	 * @param entity
	 */
	void refresh(Class<?> entityClass, Object entity);
	
	/**
	 * Kind of destructor when stopping application
	 */
	void close();
	
}
