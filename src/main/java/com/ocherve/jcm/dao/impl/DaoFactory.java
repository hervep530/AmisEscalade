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
			default:
				switch (daoClass.getSimpleName()) {
					case "SessionDao" :
						return new SessionDaoHibImpl();
					case "SiteDao" :
						return new SiteDaoHibImpl();
					case "TopoDao" : 
						return new TopoDaoHibImpl();
					case "MessageDao" :
						return new MessageDaoHibImpl();
					default :
						throw new DaoException("this dao \"" + daoClass.getSimpleName() + "\" doesn't exist");
				}
		}
	}

}
