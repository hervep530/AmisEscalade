package com.ocherve.jcm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

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

	/**
     * Load properties and set as system properties form default config file
	 * 
	 */
	public static void loadConfig() {
		loadDefaultConfig();
//		loadConfigFromFile("resources/config.properties");
	}
	
    /**
     * Given filename, Load properties and set as system properties
     * 
     * @param customFilename
     */
    public static void loadConfig(String customFilename){
		loadDefaultConfig();
		loadConfigFromFile(customFilename);
    }

    private static void loadDefaultConfig() {
		System.setProperty("jcm.log.support.level", "WARN");
		System.setProperty("jcm.debug", "false");
		System.setProperty("jcm.log.dev.level", "DEBUG");
    }
    
    private static void loadConfigFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
        	// reading file line by line
            String line;
            while ((line = br.readLine()) != null) {
            	// parsing line if it contains =
            	if ( line.contains("=") ) {
            		// getting potential key and value
            		String key = line.replaceAll("=.*","").trim();
            		String value = line.replaceAll("^.*=","").trim();
            		// Validating key
            		Property property = Property.valueOf(key.replaceAll(".","_").toUpperCase());
            		if ( value.matches(property.getRule()) ) {
            			// If key and value matches Property enum, setting property
            			System.setProperty(key, value);
            		}
            	}

            }
        } catch (FileNotFoundException e) {
        	logger.log(Level.ERROR,"Config file not found for jcm application.");
        } catch (Exception e1) {
            logger.log(Level.ERROR,"Error when loading config for jcm application : " + e1.getMessage());
        }

    }
    
    
}
