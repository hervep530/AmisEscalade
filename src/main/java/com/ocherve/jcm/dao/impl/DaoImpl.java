package com.ocherve.jcm.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import com.ocherve.jcm.dao.DaoException;
import com.ocherve.jcm.dao.contract.Dao;

/**
 * @author herve_dev
 *
 */
public class DaoImpl implements Dao {

	protected static EntityManager em;
	private static final Logger DLOG = LogManager.getLogger("development_file");

	protected static void init() {
		Configurator.setLevel(DLOG.getName(), Level.TRACE);
		if (em == null) {
			em = Persistence.createEntityManagerFactory("JcmPersistenceUnit").createEntityManager();
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

	@Override
	public List<?> getEntityFromFilteredQuery(Class<?> entityClass, Map<String,String> clauses) {
		init();
		List<?> objects = null;
		Query query = null;
		String className = entityClass.getSimpleName().replaceAll(".*\\.", "");
		String alias = entityClass.getSimpleName().substring(0, 1).toLowerCase();
		// begin of building query String
		String queryString = "select " + alias + " from " + className + " " + alias;
		String clauseName = "";
		String clauseValue = "";
		// Inserting where clauses
		if ( ! clauses.isEmpty() ) {
			queryString += " where ";
			int count = 0;
			for ( String clause : clauses.keySet() ) {
				clauseName = clause.replaceAll("\\.", "_");
				if ( count > 0 ) queryString += " AND ";
				queryString += alias + "." + clause + " " + clauses.get(clause).replaceAll(":.*", ":" + clauseName);
				count ++;
			};
			DLOG.log(Level.DEBUG, "Clause where : " + queryString);
		}
		//queryString += ";";
		query = em.createQuery(queryString);
		if ( ! clauses.isEmpty() ) {
			for ( String clause : clauses.keySet() ) {
				clauseName = clause.replaceAll("\\.", "_");
				clauseValue = clauses.get(clause).replaceAll(".*:'?", "").replaceAll("'$", "");
				DLOG.log(Level.DEBUG, "Clause : \nkey is " + clause + "\nname is " + clauseName + "\n"
						+ "value is " + clauses.get(clause) + "\nClause value is " + clauseValue);
				if (clauses.get(clause).contains(":")) {
					if ( clauseValue.contains("(int)") ) {
						query.setParameter(clauseName, Integer.valueOf(clauseValue.replaceAll("(int)", "")));
					} else {
						query.setParameter(clauseName, clauseValue);
					}
					DLOG.log(Level.DEBUG, "Set parameter : " + clauseName + " -> " + clauseValue);
				}
			};
		}
		try {
			objects = query.getResultList();
		} catch (NoResultException e) {
			DLOG.log(Level.ERROR, "Aucun resultat pour la requête :\n" + queryString + "\n" + e.getMessage());
		} catch (Exception e1) {
			DLOG.log(Level.ERROR, "Erreur inattendue :\n" + queryString + "\n" + e1.getMessage());
			throw new DaoException(e1.getMessage());
		}
		return objects;
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
