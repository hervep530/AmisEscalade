package com.ocherve.jcm.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import com.ocherve.jcm.dao.contract.Dao;

/**
 * @author herve_dev
 *
 */
public abstract class DaoImpl implements Dao {

	protected static final Logger DLOG = LogManager.getLogger("development_file");
	protected EntityManager em = null;
	protected Object object;
	protected List<?> objects;
	
	protected DaoImpl() {
		daoInit();
	}
	
	protected void daoInit() {
		Configurator.setLevel(DLOG.getName(), Level.TRACE);
		object = null;
		objects = null;
		if (em == null) {
			//em = Persistence.createEntityManagerFactory("HibernateHikariPersistenceUnit").createEntityManager();
			em = Persistence.createEntityManagerFactory("JpaPersistenceUnit").createEntityManager();			
			em.setFlushMode(FlushModeType.COMMIT);
			DLOG.log(Level.DEBUG, String.format(this.getClass().getSimpleName() + "is now initialized"));
		}
	}

	/**
	 * Enregistrement d'un nouvel objet
	 * 
	 * @param entityClass
	 * @param newObject
	 * 
	 * @return if object is created or not
	 */
	@Override
	public Object create(Class<?> entityClass, Object newObject) {
		daoInit();
		try {
			em.getTransaction().begin();
			em.persist(entityClass.cast(newObject));
			em.getTransaction().commit();
		} catch (Exception e) {
			DLOG.log(Level.ERROR, entityClass.getSimpleName() + " can not create object.");
			DLOG.log(Level.DEBUG, String.format(e.getMessage() + formatException(e)));
			em.getTransaction().rollback();
		} 
		return newObject;
	}

	/**
	 * Mise a jour d'un objet
	 * 
	 * @param entityClass
	 * @param id
	 * 
	 * @return if object is created or not
	 */
	@Override
	public Object update(Class<?> entityClass, Integer id, Object newObject) {
		daoInit();
		try {
			object=em.find(entityClass, id);
			em.getTransaction().begin();
			object = newObject;
			em.getTransaction().commit();
		} catch (Exception e) {
			DLOG.log(Level.ERROR, entityClass.getSimpleName() + " can not update object.");
			DLOG.log(Level.DEBUG, String.format(e.getMessage() + formatException(e)));
			em.getTransaction().rollback();
		}
		return object;
	}
	
	/**
	 * Mise a jour d'un objet
	 * 
	 * @param entityClass
	 * @param id
	 * 
	 * @return if object is created or not
	 */
	@Override
	public Object update(Class<?> entityClass, Integer id, Map<String,Object> fields) {
		daoInit();
		try {
			object=em.find(entityClass, id);
			em.getTransaction().begin();
			setUpdateAttributes(fields);
			em.getTransaction().commit();
		} catch (Exception e) {
			DLOG.log(Level.ERROR, entityClass.getSimpleName() + " can not update object.");
			DLOG.log(Level.DEBUG, String.format(e.getMessage() + formatException(e)));
			em.getTransaction().rollback();
		}
		return object;
	}

	@Override
	public Object get(Class<?> entityClass, Integer id) {
		daoInit();
		object = em.find(entityClass, id);
		return object;
	}

	@Override
	public List<?> getListFromNamedQuery(Class<?> entityClass, String queryName, Map<String,Object> parameters) {
		daoInit();
		try {
			Query query = em.createNamedQuery(queryName, entityClass);
			if ( parameters != null ) {
				for (String parameterName : parameters.keySet()) {
					if ( ! parameterName.matches("^(offset|limit)$") )
					query.setParameter(parameterName, parameters.get(parameterName));
				}				
			}
			if ( parameters.containsKey("limit")) 
				query.setMaxResults(Integer.valueOf(parameters.get("limit").toString()));
			if ( parameters.containsKey("offset")) 
				query.setFirstResult(Integer.valueOf(parameters.get("offset").toString()));
			objects = query.getResultList();
		} catch (Exception e) {
			DLOG.log(Level.ERROR, entityClass.getSimpleName() + " can not get list of objects.");
			DLOG.log(Level.DEBUG, String.format(e.getMessage() + formatException(e)));
		} 
		return objects;
	}

	@Override
	public List<?> getListFromNamedQueryAndIdParameter(Class<?> entityClass, String queryName, Integer id) {
		daoInit();
		try {
			Query query = em.createNamedQuery(queryName, entityClass);
			if ( id != null ) query.setParameter("filteredId", id);
			objects = query.getResultList();
		} catch (Exception e) {
			DLOG.log(Level.ERROR, entityClass.getSimpleName() + " can not get list of objects.");
			DLOG.log(Level.DEBUG, String.format(e.getMessage() + formatException(e)));
		} 
		return objects;
	}

	@Override
	public boolean delete(Class<?> entityClass, Integer id) {
		daoInit();
		boolean deleted = false;
		object = em.find(entityClass, id);
		if ( object != null ) {
			em.getTransaction().begin();
			try {
				em.remove(entityClass.cast(object));
				em.getTransaction().commit();
				deleted = true;
			} catch (Exception e) {
				DLOG.log(Level.ERROR, 
						entityClass.getSimpleName() + " with id " + id + " can not delete object.");
				DLOG.log(Level.DEBUG, String.format(e.getMessage() + formatException(e)));
				em.getTransaction().rollback();
			}
		}
		return deleted;
	}

	@Override
	public Long getCountFromNamedQuery(Class<?> entityClass, String queryName, Map<String, Object> parameters) {
		daoInit();
		long count = 0;
		try {
			Query query = em.createNamedQuery(queryName, Long.class);
			if ( parameters != null ) {
				for (String parameterName : parameters.keySet()) {
					query.setParameter(parameterName, parameters.get(parameterName));
				}				
			}
			count = (long)query.getSingleResult();
		} catch (Exception e) {
			DLOG.log(Level.ERROR, entityClass.getSimpleName() + " can not get count.");
			DLOG.log(Level.DEBUG, String.format(e.getMessage() + formatException(e)));
		} 
		DLOG.log(Level.DEBUG, String.format("result of query " + queryName + " : " + count));		
		return count;
	}

	@Override
	public Object getColumnsFromNamedQuery(Class<?> entityClass, String queryName, Map<String, Object> parameters) {
		daoInit();
		Object columns = null;
		try {
			Query query = em.createNamedQuery(queryName, entityClass);
			if ( parameters != null ) {
				for (String parameterName : parameters.keySet()) {
					query.setParameter(parameterName, parameters.get(parameterName));
				}				
			}
			columns = (Object)query.getSingleResult();
		} catch (Exception e) {
			DLOG.log(Level.ERROR, "Query " + queryName + " : can not get id.");
			DLOG.log(Level.DEBUG, String.format(e.getMessage() + formatException(e)));
		} 
		DLOG.log(Level.DEBUG, String.format("result of query " + queryName + " : " + columns));		
		return columns;
	}
	
	protected void setUpdateAttributes(Map<String,Object> fields) {
		// Add code with overriding in each EntityDaoImpl
	}

	protected void setQueryParameters(Map<String,Object> fields) {
		// Add code with overriding in each EntityDaoImpl
	}
	
	protected String formatException(Exception e) {
		String trace = "";
		for (int t = 0; t < e.getStackTrace().length; t++) {
			trace += "%n" + e.getStackTrace()[t].toString();
		}
		return trace;
	}



}
