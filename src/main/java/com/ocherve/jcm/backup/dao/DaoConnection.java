package com.ocherve.jcm.backup.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

/**
 * @author herve_dev
 *
 */
public class DaoConnection {

    private static final Logger DLOG = LogManager.getLogger("development_file");
	private static boolean loaded = loadDriver();
	private static String url = "jdbc:postgresql://localhost:10432/spotit?characterEncoding=utf8";
	private static String user = "spotit";
	private static String password = "spotit";
	
	private DaoConnection() {
		Configurator.setLevel(DLOG.getName(), Level.TRACE);
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * @return boolean meaning that driver is loaded or not
	 */
	public static boolean loadDriver() {
		// if not loaded, load driver with private instanciation
		if ( ! loaded ) new DaoConnection();
		return true;
	}
	
	/**
	 * Create new instance of database connexion using jdbc driver
	 * 
	 * @param autoCommit
	 * @return an instance of connection
	 */
	public static Connection getInstance(boolean autoCommit) {
		// Load driver and declare new connection
		loadDriver();
		Connection connection = null;
		// use driver to establish connection, set autocommit mode and return connection
		try {
			connection = DriverManager.getConnection(url, user, password);
			connection.setAutoCommit(autoCommit);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection ;
	}

	/**
	 * Close and destroy database connection
	 * 
	 * @param connection
	 */
	public static void close(Connection connection) {
		if ( connection != null )
	        try {
	            // if connection is not null, close connection
        	    if ( ! connection.isClosed()) DLOG.log(Level.INFO,"Connection ouverte");
	        	connection.close();
        	    if ( connection.isClosed()) DLOG.log(Level.INFO,"Connection closed");
	        } catch ( SQLException ignore ) {
	        	DLOG.log(Level.INFO,"La connexion n'a pas pu être close");
	        }
	}

}
