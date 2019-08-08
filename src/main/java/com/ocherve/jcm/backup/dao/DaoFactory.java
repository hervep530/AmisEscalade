package com.ocherve.jcm.backup.dao;

import com.ocherve.jcm.backup.dao.jpa.DaoJpaFactory;
import com.ocherve.jcm.backup.dao.postgresql.DaoPgFactory;
import com.ocherve.jcm.dao.StorageType;
import com.ocherve.jcm.dao.contract.Dao;

/**
 * @author herve_dev
 *
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
		Dao dao = null;
		switch (storageType) {
			case POSTGRESQL:
				DaoPgFactory.getDao(daoClass);
				break;
			case JPA:
				DaoJpaFactory.getDao(daoClass);
				break;
			default:
				break;
		}
		return dao;
	}

}
