package com.ocherve.jcm.dao.impl;

import com.ocherve.jcm.dao.DaoException;
import com.ocherve.jcm.dao.StorageType;
import com.ocherve.jcm.dao.contract.Dao;
import com.ocherve.jcm.dao.hibernate.impl.DaoHibernateFactory;

/**
 * @author herve_dev
 *
 * Factory DAO
 */
public class DaoFactory {

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

}
