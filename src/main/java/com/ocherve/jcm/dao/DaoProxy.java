package com.ocherve.jcm.dao;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import com.ocherve.jcm.dao.contract.Dao;
import com.ocherve.jcm.dao.contract.SessionDao;
import com.ocherve.jcm.dao.impl.DaoFactory;
import com.ocherve.jcm.utils.PropertiesHelper;

/**
 * @author herve_dev
 * 
 * Dao proxy which instanciate all dao and keep it as Class variable (as acache)
 */
public class DaoProxy {
	
    protected static final Logger DLOG = LogManager.getLogger("development_file");

    private static StorageType daoType;
	private Dao sessionDao;
	private static DaoProxy instance = new DaoProxy();
	
	private DaoProxy() {
		Configurator.setLevel(DLOG.getName(), Level.TRACE);
		DLOG.log(Level.DEBUG,"Instanciate Service Proxy");
		String daoConfig = PropertiesHelper.getConfigValue("daoType");
		if ( daoConfig == null ) throw new RuntimeException();
		daoType = StorageType.valueOf(daoConfig.toUpperCase());
		this.sessionDao = DaoFactory.getDao(daoType, SessionDao.class);
	}

	/**
	 * @return		ServiceProxy : the proxy itself
	 */
	public static DaoProxy getInstance() {
		if (instance == null ) {
			instance = new DaoProxy();
			System.out.println("Dao Proxy is null");
		}
		return instance;
	}

	/**
	 * Getter
	 * 
	 * @return		Dao : SessionDaoPgImpl under generic form
	 */
	public Dao getSessionDao() {
		return sessionDao;
	}



}
