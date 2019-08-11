package com.ocherve.jcm.dao.impl;

import com.ocherve.jcm.dao.DaoException;
import com.ocherve.jcm.dao.StorageType;
import com.ocherve.jcm.dao.contract.Dao;

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
			case POSTGRESQL:
			case JPA:
			default:
				switch (daoClass.getSimpleName()) {
					case "SiteDao" :
						return new SiteDaoJpaImpl();
					case "TopoDao" : 
						return new TopoDaoJpaImpl();
					case "MessageDao" :
						return new MessageDaoJpaImpl();
					case "UserDao" :
						return new UserDaoJpaImpl();
					case "CommentDao" :
						return new CommentDaoJpaImpl();
					default :
						throw new DaoException("this dao \"" + daoClass.getSimpleName() + "\" doesn't exist");
				}
		}
	}

}
