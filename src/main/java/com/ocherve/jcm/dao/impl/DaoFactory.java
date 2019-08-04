package com.ocherve.jcm.dao.impl;

import com.ocherve.jcm.dao.StorageType;
import com.ocherve.jcm.dao.contract.Dao;
import com.ocherve.jcm.dao.impl.jpa.DaoJpaFactory;
import com.ocherve.jcm.dao.impl.postgresql.DaoPgFactory;

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
		Dao dao = null;
		switch (storageType) {
		case POSTGRESQL:
			DaoPgFactory.getDao(daoClass);
			break;
		case JPA:
			DaoJpaFactory.getDao(daoClass);
			break;
		}
		return dao;
	}

}
