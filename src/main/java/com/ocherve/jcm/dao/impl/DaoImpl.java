package com.ocherve.jcm.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.Query;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import com.ocherve.jcm.dao.DaoException;
import com.ocherve.jcm.dao.contract.Dao;
import com.ocherve.jcm.utils.JcmException;

/**
 * @author herve_dev
 *
 */
public abstract class DaoImpl implements Dao {

	protected static final Logger DLOG = LogManager.getLogger("development_file");
	protected EntityManager em = null;
	//protected Object object;
	//protected List<?> objects;
	
	protected DaoImpl() throws DaoException {
		Configurator.setLevel(DLOG.getName(), Level.TRACE);
		if ( this.em == null ) {
			try {
				this.em = DaoFactory.getEntityManagerFactory().createEntityManager();
				this.em.setFlushMode(FlushModeType.COMMIT);
			} catch (Exception e) {
				DLOG.log(Level.ERROR, "can not open entity manager");
				DLOG.log(Level.DEBUG, JcmException.formatStackTrace(e));
				throw new DaoException("Cannot initialize Dao");
			}
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
		try {
			this.em.getTransaction().begin();
			this.em.persist(entityClass.cast(newObject));
			this.em.getTransaction().commit();
		} catch (Exception e) {
			DLOG.log(Level.ERROR, entityClass.getSimpleName() + " can not create object.");
			DLOG.log(Level.DEBUG, JcmException.formatStackTrace(e));
			if ( this.em.getTransaction().isActive() ) this.em.getTransaction().rollback();
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
		Object object = null;
		try {
			object=this.em.find(entityClass, id);
			this.em.getTransaction().begin();
			object = newObject;
			this.em.getTransaction().commit();
		} catch (Exception e) {
			DLOG.log(Level.ERROR, entityClass.getSimpleName() + " can not update object.");
			DLOG.log(Level.DEBUG, JcmException.formatStackTrace(e));
			if ( this.em.getTransaction().isActive() ) this.em.getTransaction().rollback();
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
		Object object = null;
		try {
			object=this.em.find(entityClass, id);
			if ( object != null ) {
				this.em.getTransaction().begin();
				setUpdateAttributes(fields);
				this.em.getTransaction().commit();
			}
		} catch (Exception e) {
			DLOG.log(Level.ERROR, entityClass.getSimpleName() + " can not update object.");
			DLOG.log(Level.DEBUG, JcmException.formatStackTrace(e));
			if ( this.em.getTransaction().isActive() ) this.em.getTransaction().rollback();
		}
		return object;
	}

	@Override
	public Object get(Class<?> entityClass, Integer id) {
		Object object = null;
		try {
			object = em.find(entityClass, id);
		} catch (Exception e) {
			DLOG.log(Level.ERROR, entityClass.getSimpleName() + " can not update object.");
			DLOG.log(Level.DEBUG, JcmException.formatStackTrace(e));
		}
		return object;
	}

	@Override
	public List<?> getListFromNamedQuery(Class<?> entityClass, String queryName, Map<String,Object> parameters) {
		List<?> objects = null;
		try {
			Query query = this.em.createNamedQuery(queryName, entityClass);
			if ( parameters != null ) {
				for (String parameterName : parameters.keySet()) {
					if ( ! parameterName.matches("^(offset|limit)$") )
					query.setParameter(parameterName, parameters.get(parameterName));
				}				
			}
			if ( parameters != null ) {
				if ( parameters.containsKey("limit")) 
					query.setMaxResults(Integer.valueOf(parameters.get("limit").toString()));
				if ( parameters.containsKey("offset")) 
					query.setFirstResult(Integer.valueOf(parameters.get("offset").toString()));
			}
			objects = query.getResultList();
		} catch (Exception e) {
			DLOG.log(Level.ERROR, entityClass.getSimpleName() + " can not get list of objects.");
			DLOG.log(Level.DEBUG, JcmException.formatStackTrace(e));
		}
		return objects;
	}

	@Override
	public List<?> getListFromNamedQueryAndIdParameter(Class<?> entityClass, String queryName, Integer id) {
		List<?> objects = null;
		try {
			Query query = this.em.createNamedQuery(queryName, entityClass);
			if ( id != null ) query.setParameter("filteredId", id);
			objects = query.getResultList();
		} catch (Exception e) {
			DLOG.log(Level.ERROR, entityClass.getSimpleName() + " can not get list of objects.");
			DLOG.log(Level.DEBUG, JcmException.formatStackTrace(e));
		}
		return objects;
	}

	@Override
	public List<?> getListFromFilteredQuery(Class<?> entityClass, String queryString, Map<String,Object> parameters) {
		List<?> objects = null;
		try {
			Query query = this.em.createQuery(queryString, entityClass);
			if ( parameters != null ) {
				for (String parameterName : parameters.keySet()) {
					if ( ! parameterName.matches("^(offset|limit)$") )
					query.setParameter(parameterName, parameters.get(parameterName));
				}				
			}
			if ( parameters != null ) {
				if ( parameters.containsKey("limit")) 
					query.setMaxResults(Integer.valueOf(parameters.get("limit").toString()));
				if ( parameters.containsKey("offset")) 
					query.setFirstResult(Integer.valueOf(parameters.get("offset").toString()));
			}
			objects = query.getResultList();
		} catch (Exception e) {
			DLOG.log(Level.ERROR, entityClass.getSimpleName() + " can not get list of objects.");
			DLOG.log(Level.DEBUG, JcmException.formatStackTrace(e));
		}
		return objects;
	}

	@Override
	public Integer getIdFromNamedQuery(String queryName, Map<String, Object> parameters) {
		try {
			return ((Integer) getColumnsFromNamedQuery(Integer.class, queryName, parameters));
		} catch (Exception e) {
			return 0;
		}
	}
	
	@Override
	public boolean delete(Class<?> entityClass, Integer id) {
		Object object = null;
		boolean deleted = false;
		try {
			object = this.em.find(entityClass, id);
			if ( object != null ) {
				this.em.getTransaction().begin();
				this.em.remove(entityClass.cast(object));
				this.em.getTransaction().commit();
				deleted = true;
			}
		} catch (Exception e) {
			if ( this.em.getTransaction().isActive() ) this.em.getTransaction().rollback();
			DLOG.log(Level.ERROR, entityClass.getSimpleName() + " can not get list of objects.");
			DLOG.log(Level.DEBUG, JcmException.formatStackTrace(e));
		}
		return deleted;
	}

	@Override
	public Long getCountFromNamedQuery(Class<?> entityClass, String queryName, Map<String, Object> parameters) {
		long count = 0;
		try {
			Query query = this.em.createNamedQuery(queryName, Long.class);
			if ( parameters != null ) {
				for (String parameterName : parameters.keySet()) {
					query.setParameter(parameterName, parameters.get(parameterName));
				}				
			}
			count = (long)query.getSingleResult();
		} catch (Exception e) {
			DLOG.log(Level.ERROR, entityClass.getSimpleName() + " can not get count.");
			DLOG.log(Level.DEBUG, JcmException.formatStackTrace(e));
		}
		DLOG.log(Level.DEBUG, String.format("result of query " + queryName + " : " + count));		
		return count;
	}

	@Override
	public Long getCountFromFilteredQuery(Class<?> entityClass, String queryString, Map<String, Object> parameters) {
		long count = 0;
		try {
			Query query = this.em.createQuery(queryString, Long.class);
			if ( parameters != null ) {
				for (String parameterName : parameters.keySet()) {
					query.setParameter(parameterName, parameters.get(parameterName));
				}				
			}
			count = (long)query.getSingleResult();
		} catch (Exception e) {
			DLOG.log(Level.ERROR, entityClass.getSimpleName() + " can not get count.");
			DLOG.log(Level.DEBUG, JcmException.formatStackTrace(e));
		}
		DLOG.log(Level.DEBUG, String.format("result of query " + queryString + " : " + count));		
		return count;
	}

	@Override
	public Object getColumnsFromNamedQuery(Class<?> entityClass, String queryName, Map<String, Object> parameters) {
		Object columns = null;
		try {
			Query query = this.em.createNamedQuery(queryName, entityClass);
			if ( parameters != null ) {
				for (String parameterName : parameters.keySet()) {
					query.setParameter(parameterName, parameters.get(parameterName));
				}				
			}
			columns = (Object)query.getSingleResult();
		} catch (Exception e) {
			DLOG.log(Level.ERROR, "Query " + queryName + " : can not get id.");
			DLOG.log(Level.DEBUG, JcmException.formatStackTrace(e));
		}
		DLOG.log(Level.DEBUG, String.format("result of query " + queryName + " : " + columns));		
		return columns;
	}
	
	@Override
	public void refresh(Class<?> entityClass, Integer id) {
		Object object = null;
		try {
			object = this.em.find(entityClass, id);
			if ( object != null ) {
				this.em.getTransaction().begin();
				this.em.refresh(entityClass.cast(object));
				this.em.getTransaction().commit();
			}
		} catch (Exception e) {
			if ( this.em.getTransaction().isActive() ) this.em.getTransaction().rollback();
			DLOG.log(Level.ERROR, 
					entityClass.getSimpleName() + " can not refresh object" + " with id " + id +".");
			DLOG.log(Level.DEBUG, JcmException.formatStackTrace(e));
		}
	}

	@Override
	public void refresh(Class<?> entityClass, Object entity) {
		if ( entity != null ) {
			try {
				this.em.getTransaction().begin();
				this.em.refresh(entityClass.cast(entity));
				this.em.getTransaction().commit();
			} catch (Exception e) {
				if ( this.em.getTransaction().isActive() ) this.em.getTransaction().rollback();
				DLOG.log(Level.ERROR, 
						entityClass.getSimpleName() + "can not be refreshed.");
				DLOG.log(Level.DEBUG, JcmException.formatStackTrace(e));
			}
		}
	}
	
	protected void setUpdateAttributes(Map<String,Object> fields) {
		// Add code with overriding in each EntityDaoImpl
	}

	protected void setQueryParameters(Map<String,Object> fields) {
		// Add code with overriding in each EntityDaoImpl
	}
	
	@Override
	public void close() {
		try {
			this.em.close();
		} catch (Exception e ) {
			DLOG.log(Level.WARN, "Entity manager cannot be closed");
		}
	}
	
}
