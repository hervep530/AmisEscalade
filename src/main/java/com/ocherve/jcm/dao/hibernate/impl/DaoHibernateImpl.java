package com.ocherve.jcm.dao.hibernate.impl;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.ocherve.jcm.dao.contract.Dao;
import com.ocherve.jcm.utils.JcmException;

/**
 * Dao Implementation using Hibernate / HikariCP / postgresql driver
 * 
 * @author herve_dev
 *
 */
public class DaoHibernateImpl implements Dao {
	
	protected static final Logger DLOG = LogManager.getLogger("development_file");
	protected SessionFactory sessionFactory;
	protected Session session;
	protected Transaction transaction;
	protected Object object;
	protected List<?> objects;
	protected Query<?> query;

	/**
	 * Instanciating new DaoHibernateImpl - Constructor
	 */
	public DaoHibernateImpl() {
	}
	
	/**
	 * Instanciating new DaoHibernateImpl with sharing sessionFactory - Constructor
	 * 
	 * @param sessionFactory (shared session factory for all the application) 
	 */
	public DaoHibernateImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected boolean daoInit() {
		Configurator.setLevel(DLOG.getName(), Level.TRACE);
		this.session = null;
		this.transaction = null;
		this.object = null;
		this.objects = null;
		return true;
	}
	
	@Override
	public Object create(Class<?> entityClass, Object object) {
		if ( ! daoInit() ) return null ;
		try { 
			// Persisting object and commit
			this.session = this.sessionFactory.openSession(); 
			this.transaction = session.beginTransaction(); 
		    this.object = session.save(entityClass.cast(object));
		    //session.flush() ;
		    this.transaction.commit();
		} catch (Exception e) {
			// If error, logging and rollback
			DLOG.log(Level.ERROR, entityClass.getSimpleName() + " can not persist object.");
			DLOG.log(Level.DEBUG, String.format(e.getMessage() + JcmException.formatStackTrace(e)));
		    if (this.transaction != null) this.transaction.rollback();
		} finally { 
			// Closing session
			if ( this.session.isOpen() ) this.session.close(); 
		} 
		// Closing factory and return object
		this.sessionFactory.close(); 
		return this.object;
	}

	@Override
	public Object update(Class<?> entityClass, Integer id, Map<String, Object> fields) {
		// initializing Dao, variables and trying to update object
		if ( ! daoInit() ) return null ;
		if ( this.object != null ) {
			try {
				// Getting session, object to modify
				this.session = this.sessionFactory.openSession(); 
				this.object = session.get(entityClass, id);
				// Detaching object and modifying it
				session.evict(this.object);
				this.setUpdateAttributes(fields);
				// Opening transaction, updating object, and commiting
				this.transaction = session.beginTransaction(); 
				session.update(entityClass.cast(this.object));
			    this.transaction.commit();
			} catch (Exception e) {
				// Logging and rollback
				DLOG.log(Level.ERROR, entityClass.getSimpleName() + " can not update object with id " + id + ".");
				DLOG.log(Level.DEBUG, String.format(e.getMessage() + JcmException.formatStackTrace(e)));
				this.transaction.rollback();
			} finally { 
		    	// closing session
			    if ( this.session.isOpen() ) this.session.close(); 
			} 
			// Closing factory and return object
			this.sessionFactory.close();			
		}
		return object;
	}

	@Override
	public Object update(Class<?> entityClass, Integer id, Object newObject) {
		// initializing Dao, variables and trying to merge newObject
		if ( ! daoInit() ) return null ;
		Object mergedObject = null;
		if ( this.object != null ) {
			try {
				// Getting session, object to modify
				this.session = this.sessionFactory.openSession(); 
				this.object = this.session.get(entityClass, id);
				// Opening transaction, merging new object and commiting
				this.transaction = this.session.beginTransaction(); 
				mergedObject = this.session.merge(entityClass.cast(newObject));
			    this.transaction.commit();
			} catch (Exception e) {
				// Logging and rollback
				DLOG.log(Level.ERROR, entityClass.getSimpleName() + " can not update object with id " + id + ".");
				DLOG.log(Level.DEBUG, String.format(e.getMessage() + JcmException.formatStackTrace(e)));
				this.transaction.rollback();
		    } finally { 
		    	// closing session
		      if ( this.session.isOpen() ) this.session.close(); 
		    } 
		    // Closing factory and return object
		    this.sessionFactory.close();
		}
		return mergedObject;
	}

	@Override
	public Object get(Class<?> entityClass, Integer id) {
		// Opening session and trying to get object
		if ( ! daoInit() ) return null ;
	    this.session = this.sessionFactory.openSession(); 
	    try { 
	    	// Getting object
	    	this.object = (Object) this.session.get(entityClass, id);
	    } catch ( Exception e ) {
			DLOG.log(Level.ERROR, entityClass.getSimpleName() + " can not get object with id." + id);
			DLOG.log(Level.DEBUG, String.format(e.getMessage() + JcmException.formatStackTrace(e)));
	    } finally { 
	    	// closing session
	      if ( this.session.isOpen() ) this.session.close(); 
	    } 
	    // Closing factory and return object
	    this.sessionFactory.close();
	    return this.object;
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
	public List<?> getListFromNamedQuery(Class<?> entityClass, String namedQuery, Map<String, Object> parameters) {
		// Opening session and trying to get list
		if ( ! daoInit() ) return null ;
		this.session = this.sessionFactory.openSession();
		try {
			// Creating query
			this.query = this.session.createNamedQuery(namedQuery, entityClass);
			// Setting parameters (excepted  offset and limit)
			if ( parameters != null ) {
				for (String parameterName : parameters.keySet()) {
					if ( ! parameterName.matches("^(offset|limit)$") )
					this.query.setParameter(parameterName, parameters.get(parameterName));
				}				
			}
			// If necessary, setting parameters offset and limit
			if ( parameters != null ) {
				if ( parameters.containsKey("limit")) 
					this.query.setMaxResults(Integer.valueOf(parameters.get("limit").toString()));
				if ( parameters.containsKey("offset")) 
					this.query.setFirstResult(Integer.valueOf(parameters.get("offset").toString()));
			}
			// Getting result - List<?> - will be cast in specific Dao
			this.objects = this.query.getResultList();
		} catch (Exception e) {
			DLOG.log(Level.ERROR, entityClass.getSimpleName() + " can not get list of objects.");
			DLOG.log(Level.DEBUG, String.format(e.getMessage() + JcmException.formatStackTrace(e)));
		} finally {
			if ( this.session.isOpen() ) this.session.close();
		}
		// Close factory and return list
		this.sessionFactory.close();
		return this.objects;
	}

	@Override
	public Object getColumnsFromNamedQuery(Class<?> entityClass, String namedQuery, Map<String, Object> parameters) {
		if ( ! daoInit() ) return null ;
		Object columns = null;
		this.session = this.sessionFactory.openSession();
		try {
			// Creating query
			this.query = this.session.createNamedQuery(namedQuery, entityClass);
			// Setting parameters
			if ( parameters != null ) {
				for (String parameterName : parameters.keySet()) {
					query.setParameter(parameterName, parameters.get(parameterName));
				}				
			}
			// Getting result
			columns = (Object)query.getSingleResult();
		} catch (Exception e) {
			// Logging error
			DLOG.log(Level.ERROR, "Query " + namedQuery + " : can not get id.");
			DLOG.log(Level.DEBUG, String.format(e.getMessage() + JcmException.formatStackTrace(e)));
		} finally {
			// Closing session
			if ( this.session.isOpen() ) this.session.close();
		}
		// Closing factory and return columns
		this.sessionFactory.close();
		DLOG.log(Level.DEBUG, String.format("result of query " + namedQuery + " : " + columns));		
		return columns;
	}

	@Override
	public Long getCountFromNamedQuery(Class<?> entityClass, String namedQuery, Map<String, Object> parameters) {
		// Opening session and trying to get list
		if ( ! daoInit() ) return null ;
		long count = 0;
		this.session = this.sessionFactory.openSession();
		try {
			// Creating query
			this.query = this.session.createNamedQuery(namedQuery, Long.class);
			// Setting parameters (excepted  offset and limit)
			if ( parameters != null ) {
				for (String parameterName : parameters.keySet()) {
					this.query.setParameter(parameterName, parameters.get(parameterName));
				}				
			}
			// Getting result - long
			count = (long)this.query.getSingleResult();
		} catch (Exception e) {
			DLOG.log(Level.ERROR, entityClass.getSimpleName() + " can not get list of objects.");
			DLOG.log(Level.DEBUG, String.format(e.getMessage() + JcmException.formatStackTrace(e)));
		} finally {
			if ( this.session.isOpen() ) this.session.close();
		}
		// Close factory and return list
		this.sessionFactory.close();
		DLOG.log(Level.DEBUG, String.format("result of query " + namedQuery + " : " + count));		
		return count;
	}

	@Override
	public Long getCountFromFilteredQuery(Class<?> entityClass, String queryString, Map<String, Object> parameters) {
		// Opening session and trying to get list
		if ( ! daoInit() ) return null ;
		long count = 0;
		this.session = this.sessionFactory.openSession();
		try {
			// Creating query
			this.query = this.session.createQuery(queryString, Long.class);
			// Setting parameters
			if ( parameters != null ) {
				for (String parameterName : parameters.keySet()) {
					this.query.setParameter(parameterName, parameters.get(parameterName));
				}				
			}
			// Getting result - long
			count = (long)this.query.getSingleResult();
		} catch (Exception e) {
			DLOG.log(Level.ERROR, entityClass.getSimpleName() + " can not get count from query.");
			DLOG.log(Level.DEBUG, String.format(e.getMessage() + JcmException.formatStackTrace(e)));
		} finally {
			if ( this.session.isOpen() ) this.session.close();
		}
		// Close factory and return list
		this.sessionFactory.close();
		DLOG.log(Level.DEBUG, String.format("result of query " + queryString + " : " + count));		
		return count;
	}

	@Override
	public List<?> getListFromNamedQueryAndIdParameter(Class<?> entityClass, String namedQuery, Integer id) {
		// Opening session and trying to get list
		if ( ! daoInit() ) return null ;
		this.session = this.sessionFactory.openSession();
		try {
			// Creating query, setting id parameter, and getting result (will be cast in specific Dao)
			this.query = this.session.createNamedQuery(namedQuery, entityClass);
			if ( id != null ) query.setParameter("filteredId", id);
			this.objects = this.query.getResultList();
		} catch (Exception e) {
			DLOG.log(Level.ERROR, entityClass.getSimpleName() + " can not get list of objects.");
			DLOG.log(Level.DEBUG, String.format(e.getMessage() + JcmException.formatStackTrace(e)));
		} finally {
			if ( this.session.isOpen() ) this.session.close();
		}
		// Close factory and return list
		this.sessionFactory.close();
		return this.objects;
	}

	@Override
	public List<?> getListFromFilteredQuery(Class<?> entityClass, String queryString, Map<String, Object> parameters) {
		// Opening session and trying to get list
		if ( ! daoInit() ) return null ;
		this.session = this.sessionFactory.openSession();
		try {
			// Creating query
			this.query = this.session.createQuery(queryString, entityClass);
			// Setting parameters (excepted  offset and limit)
			if ( parameters != null ) {
				for (String parameterName : parameters.keySet()) {
					if ( ! parameterName.matches("^(offset|limit)$") )
					this.query.setParameter(parameterName, parameters.get(parameterName));
				}				
			}
			// If necessary, setting parameters offset and limit
			if ( parameters != null ) {
				if ( parameters.containsKey("limit")) 
					this.query.setMaxResults(Integer.valueOf(parameters.get("limit").toString()));
				if ( parameters.containsKey("offset")) 
					this.query.setFirstResult(Integer.valueOf(parameters.get("offset").toString()));
			}
			// Getting result - List<?> - will be cast in specific Dao
			this.objects = this.query.getResultList();
		} catch (Exception e) {
			DLOG.log(Level.ERROR, entityClass.getSimpleName() + " can not get list of objects.");
			DLOG.log(Level.DEBUG, String.format(e.getMessage() + JcmException.formatStackTrace(e)));
		} finally {
			if ( this.session.isOpen() ) this.session.close();
		}
		// Close factory and return list
		this.sessionFactory.close();
		return this.objects;
	}

	@Override
	public boolean delete(Class<?> entityClass, Integer id) {
		// Opening session and trying to get list
		if ( ! daoInit() ) return false ;
		boolean deleted = false;
		this.session = this.sessionFactory.openSession();
		try {
			// Getting object, opening transaction, deleting object and commiting
			this.object = this.session.get(entityClass, id);
			if ( this.object != null ) {
				this.transaction = this.session.beginTransaction(); 
				this.session.delete(entityClass.cast(this.object));
			    this.transaction.commit();
			    deleted = true;
			}
		} catch (Exception e) {
			// Logging
			DLOG.log(Level.ERROR, entityClass.getSimpleName() + " can not get list of objects.");
			DLOG.log(Level.DEBUG, String.format(e.getMessage() + JcmException.formatStackTrace(e)));
			if ( this.transaction.isActive() ) this.transaction.rollback();
		} finally {
			if ( this.session.isOpen() ) this.session.close();
		}
		// Close factory and return list
		this.sessionFactory.close();
		return deleted;
	}

	@Override
	public void refresh(Class<?> entityClass, Integer id) {
		// Initializing
		if ( ! daoInit() ) return ;
		this.session = this.sessionFactory.openSession();
		try {
			// Getting object and refreshing it
			this.object = this.session.get(entityClass, id);
			this.transaction = this.session.beginTransaction(); 
			this.session.refresh(entityClass.cast(this.object));
		    this.transaction.commit();
		} catch (Exception e) {
			// Logging error
			DLOG.log(Level.ERROR, "Dao refresh : can not refresh object with id " + id + ".");
			DLOG.log(Level.DEBUG, String.format(e.getMessage() + JcmException.formatStackTrace(e)));
			if ( this.transaction.isActive() ) this.transaction.rollback();
		} finally {
			// Closing session
			if ( this.session.isOpen() ) this.session.close();
		}
			
		// Closing factory and return columns
		this.sessionFactory.close();
	}

	@Override
	public void refresh(Class<?> entityClass, Object entity) {
		// Initializing
		if ( ! daoInit() ) return ;
		if ( entity == null ) return;
		this.session = this.sessionFactory.openSession();
		try {
			// Getting object and refreshing it
			this.transaction = this.session.beginTransaction(); 
			this.session.refresh(entityClass.cast(entity));
		    this.transaction.commit();
		} catch (Exception e) {
			// Logging error
			DLOG.log(Level.ERROR, "Dao refresh : can not refresh object.");
			DLOG.log(Level.DEBUG, String.format(e.getMessage() + JcmException.formatStackTrace(e)));
			if ( this.transaction.isActive() ) this.transaction.rollback();
		} finally {
			// Closing session
			if ( this.session.isOpen() ) this.session.close();
		}
		// Closing factory and return columns
		this.sessionFactory.close();
	}

	protected void setUpdateAttributes(Map<String,Object> fields) {
		// Add code with overriding in each EntityDaoImpl
	}

	protected void setQueryParameters(Map<String,Object> fields) {
		// Add code with overriding in each EntityDaoImpl
	}

}
