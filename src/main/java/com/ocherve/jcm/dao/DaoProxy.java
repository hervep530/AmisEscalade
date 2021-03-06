package com.ocherve.jcm.dao;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import com.ocherve.jcm.dao.contract.*;
import com.ocherve.jcm.dao.impl.DaoFactory;

/**
 * @author herve_dev
 * 
 * Dao proxy which instanciate all dao and keep it as Class variable (as acache)
 */
public class DaoProxy {
	
    protected static final Logger DLOG = LogManager.getLogger("development_file");

    private static StorageType daoType;
	private Dao userDao;
	private Dao siteDao;
	private Dao topoDao;
	private Dao messageDao;
	private Dao commentDao;
	private static DaoProxy instance = new DaoProxy();
	
	private DaoProxy() {
		Configurator.setLevel(DLOG.getName(), Level.TRACE);
		DLOG.log(Level.DEBUG,"Instanciate Dao Proxy");
		// String daoConfig = PropertiesHelper.getConfigValue("daoType");
		String daoConfig = System.getProperty("jcm.dao");
		if ( daoConfig == null ) daoConfig = "hibernate_em";
		daoType = StorageType.valueOf(daoConfig.toUpperCase());
		this.userDao = DaoFactory.getDao(daoType, UserDao.class);
		this.siteDao = DaoFactory.getDao(daoType, SiteDao.class);
		this.topoDao = DaoFactory.getDao(daoType, TopoDao.class);
		this.messageDao = DaoFactory.getDao(daoType, MessageDao.class);
		this.commentDao = DaoFactory.getDao(daoType, CommentDao.class);
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
	 * Attempt to cclose all Dao connexions
	 */
	public static void closeInstance() {
		try {
			if (instance != null ) {
				DaoFactory.getDao(daoType, UserDao.class).close();
				DaoFactory.getDao(daoType, SiteDao.class).close();
				DaoFactory.getDao(daoType, TopoDao.class).close();
				DaoFactory.getDao(daoType, CommentDao.class).close();
				DaoFactory.getDao(daoType, MessageDao.class).close();
			}
			DaoFactory.close();
		} catch (Exception e) {
			DLOG.log(Level.DEBUG,"Errors on closing Dao connexions");
		}
	}
	
	/**
	 * Getter
	 * 
	 * @return the userDao
	 */
	public Dao getUserDao() {
		return userDao;
	}

	/**
	 * Getter
	 * 
	 * @return the siteDao
	 */
	public Dao getSiteDao() {
		return siteDao;
	}

	/**
	 * Getter
	 * 
	 * @return the topoDao
	 */
	public Dao getTopoDao() {
		return topoDao;
	}

	/**
	 * Getter
	 * 
	 * @return the messageDao
	 */
	public Dao getMessageDao() {
		return messageDao;
	}

	/**
	 * Getter
	 * 
	 * @return the commentDao
	 */
	public Dao getCommentDao() {
		return commentDao;
	}
	
	
}
