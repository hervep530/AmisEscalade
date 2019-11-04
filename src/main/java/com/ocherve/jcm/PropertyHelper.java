package com.ocherve.jcm;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class to define application property by default, and if possible overload with config file
 * Default file is .../resources/config.properties
 * 
 * @author herve_dev
 */
public class PropertyHelper {

	private static final Logger logger = LogManager.getLogger("root");
	private static Properties properties;

	/**
     * Load properties and set as system properties form default config file
	 * 
	 */
	public static void loadConfig() {
		properties = new Properties();
		loadDefaultConfig();
	}
	
    /**
     * Given filename, Load properties and set as system properties
     * 
     * @param customFilename
     */
    public static void loadConfig(String customFilename){
		properties = new Properties();
		loadDefaultConfig();
		loadConfigFromFile(customFilename);
    }

    private static void loadDefaultConfig() {
		properties.setProperty("jcm.debug", "error");
		properties.setProperty("jcm.dao", "hibernate_em");
		properties.setProperty("jcm.list.limit", "10");
		
    }
    
    private static void loadConfigFromFile(String filename) {
		InputStream in = PropertyHelper.class.getResourceAsStream("/" + filename);
        // try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
        	// reading file line by line
            String line;
            while ((line = br.readLine()) != null) {
            	// parsing line if it contains =
            	if ( line.contains("=") ) {
            		// getting potential key and value
            		String key = line.replaceAll("=.*","").trim();
            		String value = line.replaceAll("^.*=","").trim();
            		// Validating key
            		if ( key.matches("^jcm.[a-zA-z0-9.]{1,32}$") ) {
	            		Property property = Property.valueOf(key.replaceAll("\\.","_").toUpperCase());
	            		if ( value.matches(property.getRule()) ) {
	            			// If key and value matches Property enum, setting property
	            			properties.setProperty(key, value);
	            		}
            		}
            	}
            }
        } catch (Exception e) {
            logger.log(Level.ERROR,"Error when loading config for jcm application : " + e.getMessage());
        }

    }
    
    /**
     * Getter
     * 
     * @return all properties with key and value as Object 
     */
    public static Properties getProperties() {
    	return properties;
    }
    
    /**
     * Getter for single key
     *  
     * @param key is name of property
     * @return value as String
     */
    public static String getProperty(String key) {
    	String value = "";
    	if ( properties.containsKey(key) ) value = properties.getProperty(key).toString();
    	return value;
    }
}
