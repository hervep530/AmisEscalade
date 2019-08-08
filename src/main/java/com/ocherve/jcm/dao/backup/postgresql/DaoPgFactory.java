package com.ocherve.jcm.dao.backup.postgresql;

import com.ocherve.jcm.dao.DaoException;
import com.ocherve.jcm.dao.contract.Dao;

/**
 * @author herve_dev
 *
 * Sub Factory for DaoFactory - specific to Jpa implementations
 */
public class DaoPgFactory {

	/**
	 * Getter 
	 * 
	 * @param daoClass		Class<?> : Specific DAO Class
	 * @return				Dao : generic DAO
	 */
	public static Dao getDao(Class<?> daoClass) {
		switch (daoClass.getSimpleName()) {
			case "SessionDao" :
				return new SessionDaoPgImpl();
			case "SiteDao" :
				return new SiteDaoPgImpl();
			case "TopoDao" : 
				return new TopoDaoPgImpl();
			case "MessageDao" :
				return new MessageDaoPgImpl();
			default :
				throw new DaoException("this dao \"" + daoClass.getSimpleName() + "\" doesn't exist");
		}

	}

}
