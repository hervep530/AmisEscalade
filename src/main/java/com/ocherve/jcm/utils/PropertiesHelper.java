package com.ocherve.jcm.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author herve_dev
 * 
 *	Utility to get a set of properties from a .properties file
 */
public class PropertiesHelper {

	private static Map<String,String> properties ;
	private static boolean initialized = initConfig();
	
	/**
	 * @return
	 * 
	 * In order to use it quicky, initialize with given expected value instead of parsing file
	 */
	public static boolean initConfig() {
		if ( initialized ) return true;
		properties = new HashMap<>();
		// storeless ou postgresql
		properties.put("daoType", "jpa");
		//properties.put("daoType", "hibernate");
		return true;
	}
	
	/**
	 * Setter with key as parameter
	 * 
	 * @param configKey		String property key
	 * @return 				String property value matching with the key
	 */
	public static String getConfigValue(String configKey) {
		if ( ! properties.containsKey(configKey) ) return null;
		return properties.get(configKey);
	}

}
