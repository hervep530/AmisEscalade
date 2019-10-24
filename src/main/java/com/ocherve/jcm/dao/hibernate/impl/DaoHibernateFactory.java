/**
 * 
 */
package com.ocherve.jcm.dao.hibernate.impl;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.ocherve.jcm.dao.DaoException;
import com.ocherve.jcm.dao.contract.Dao;

/**
 * @author herve_dev
 *
 */
public class DaoHibernateFactory {
	
	protected static final Logger DLOG = LogManager.getLogger("development_file");
	private static SessionFactory sessionFactory;
	
	/**
	 * Setting a unique session factory for application because using connection pool
	 * 
	 * @return true if sessionFactory is built
	 */
	private static boolean setSessionFactory() {
		if ( sessionFactory == null ) {
			try {
				sessionFactory = new Configuration().configure("hibernate-hikari.cfg.xml").buildSessionFactory();
			} catch (Exception e) {
				DLOG.log(Level.FATAL, "Hibernate configuration not found.");
				return false;
			}			
		}
		return true;
	}
	
	/**
	 * Getting dao impl from interface Class
	 * 
	 * @param daoClass
	 * @return dao requested
	 */
	public static Dao getDao(Class<?> daoClass) {
		
		if ( ! setSessionFactory() ) throw new DaoException("Dao can't build session Factory. Please check hibernate config.");
		
		switch (daoClass.getSimpleName()) {
			case "SiteDao" :
				return new SiteDaoHibernateImpl(sessionFactory);
			case "TopoDao" : 
				return new TopoDaoHibernateImpl(sessionFactory);
			case "MessageDao" :
				return new MessageDaoHibernateImpl(sessionFactory);
			case "UserDao" :
				return new UserDaoHibernateImpl(sessionFactory);
			case "CommentDao" :
				return new CommentDaoHibernateImpl(sessionFactory);
			default :
				throw new DaoException("this dao \"" + daoClass.getSimpleName() + "\" doesn't exist");
		}
		
	}

}
