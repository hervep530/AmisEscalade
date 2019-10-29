package com.ocherve.jcm.dao.impl;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import com.ocherve.jcm.dao.DaoException;
import com.ocherve.jcm.dao.StorageType;
import com.ocherve.jcm.dao.contract.Dao;
import com.ocherve.jcm.dao.hibernate.impl.DaoHibernateFactory;
import com.ocherve.jcm.utils.JcmException;

/**
 * @author herve_dev
 *
 * Factory DAO
 */
public class DaoFactory {

	protected static final Logger DLOG = LogManager.getLogger("development_file");
	private static EntityManagerFactory emf = null;
		
	/**
	 * Getter 
	 * 
	 * @param storageType		StorageDao : Type of Dao Storage
	 * @param daoClass		Class<?> : Specific DAO Class
	 * @return				Dao : generic DAO
	 */
	public static Dao getDao(StorageType storageType, Class<?> daoClass) {
		switch (storageType) {
			case HIBERNATE:
				// Dao using Hibernate with session instruction and HikariCP connection pool
				DaoHibernateFactory.getDao(daoClass);
			case POSTGRESQL:
			case JPA:
			default:
				new DaoFactory();
				switch (daoClass.getSimpleName()) {
					case "SiteDao" :
						return new SiteDaoImpl();
					case "TopoDao" : 
						return new TopoDaoImpl();
					case "MessageDao" :
						return new MessageDaoImpl();
					case "UserDao" :
						return new UserDaoImpl();
					case "CommentDao" :
						return new CommentDaoImpl();
					default :
						throw new DaoException("this dao \"" + daoClass.getSimpleName() + "\" doesn't exist");
				}
		}
	}
	
	/**
	 * @return static EntityManagerFactory
	 */
	public static EntityManagerFactory getEntityManagerFactory() {
		Configurator.setLevel(DLOG.getName(), Level.TRACE);
		if ( emf == null ) {
			try { 
				emf = Persistence.createEntityManagerFactory("AmiescaPersistenceUnit"); 
			} catch (Exception e) {
				DLOG.log(Level.ERROR, "Can't get EntityManagerFactory");
				DLOG.log(Level.ERROR, JcmException.formatStackTrace(e));
			}
		}
		return emf;
	}
	
	/**
	 * Kind of destructor
	 */
	public static void close() {
		emf.close();
	}

}
