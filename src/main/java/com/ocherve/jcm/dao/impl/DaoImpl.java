package com.ocherve.jcm.dao.impl;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;
import javax.persistence.TransactionRequiredException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import com.ocherve.jcm.dao.contract.Dao;

/**
 * @author herve_dev
 *
 */
public class DaoImpl implements Dao {

	protected static EntityManager em;
	private static final Logger DLOG = LogManager.getLogger("development_file");

	protected static void init() {
		if (em == null) {
			Configurator.setLevel(DLOG.getName(), Level.TRACE);
			em = Persistence.createEntityManagerFactory("JcmPersistentUnit").createEntityManager();
		}
	}

	/**
	 * Enregistrement d'un nouvel objet
	 * 
	 * @param entityClass
	 * @param object
	 * 
	 * @return if object is created or not
	 */
	@Override
	public Object create(Class<?> entityClass, Object object) {
		init();
		try {
			em.setFlushMode(FlushModeType.COMMIT);
			em.getTransaction().begin();
			em.persist(entityClass.cast(object));
			em.getTransaction().commit();
		} catch (Exception e) {
			String trace = "";
			for (int t = 0; t < e.getStackTrace().length; t++) {
				trace += "%n" + e.getStackTrace()[t].toString();
			}
			DLOG.log(Level.ERROR, entityClass.getSimpleName() + " can not be created in database.");
			DLOG.log(Level.DEBUG, String.format(e.getMessage() + trace));
			em.getTransaction().rollback();
		}
		return object;
	}

	@Override
	public Object get(Class<?> entityClass, Integer id) {
		init();
		Object obj = em.find(entityClass, id);
		return obj;
	}

	/**
	 * Recherche d'un utilisateur à partir de son id
	 * 
	 * @param entityClass
	 * 
	 * @param id
	 * @return a user
	 */
	protected Object getFromClause(Class<?> entityClass, String[] attributes , Map<String,String> clauses) {
		init();
		Long id = null;
		Object obj = em.find(entityClass, id);
		return obj;
	}

	@Override
	public boolean delete(Class<?> entityClass, Integer id) {
		init();
		boolean deleted = false;
		Object obj = em.find(entityClass, id);
		if ( obj != null ) {
			em.getTransaction().begin();
			try {
				em.remove(entityClass.cast(obj));
				em.getTransaction().commit();
			} catch (IllegalArgumentException | TransactionRequiredException e) {
				String trace = "";
				for (int t = 0; t < e.getStackTrace().length; t++) {
					trace += "%n" + e.getStackTrace()[t].toString();
				}
				DLOG.log(Level.ERROR, 
						entityClass.getSimpleName() + " with id " + id + " can not be removed from database.");
				DLOG.log(Level.DEBUG, String.format(e.getMessage() + trace));
				em.getTransaction().rollback();
			}
			deleted = true;
		}
		return deleted;
	}



}
