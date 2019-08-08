package com.ocherve.jcm.dao.backup.jpa;

import com.ocherve.jcm.dao.DaoException;
import com.ocherve.jcm.dao.contract.Dao;

/**
 * @author herve_dev
 *
 * Sub Factory for DaoFactory - specific to Jpa implementations
 */
public class DaoJpaFactory {
	
	/**
	 * Getter 
	 * 
	 * @param daoClass		Class<?> : Specific DAO Class
	 * @return				Dao : generic DAO
	 */
	public static Dao getDao(Class<?> daoClass) {
		switch (daoClass.getSimpleName()) {
			case "SessionDao" :
				return new SessionDaoJpaImpl();
			case "SiteDao" :
				return new SiteDaoJpaImpl();
			case "TopoDao" : 
				return new TopoDaoJpaImpl();
			case "MessageDao" :
				return new MessageDaoJpaImpl();
			default :
				throw new DaoException("this dao \"" + daoClass.getSimpleName() + "\" doesn't exist");
		}

	}

}
