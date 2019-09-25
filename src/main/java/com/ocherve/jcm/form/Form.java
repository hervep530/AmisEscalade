package com.ocherve.jcm.form;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

/**
 * Form provide generic attributes and methods 
 * @author herve_dev
 *
 */
public abstract class Form {
	
    protected static final Logger DLOG = LogManager.getLogger("development_file");
    protected static final Level DLOGLEVEL = Level.TRACE;
	
	protected Map<String,String> errors;
	protected HttpServletRequest request;
	protected Boolean partMethod;
	
	Form(){
		Configurator.setLevel(DLOG.getName(), DLOGLEVEL);
		errors = new HashMap<>();
		partMethod = false;
	}
	
	/*
	 * Specific form must have a constructor with request as argument and set request attribute from it
	 * In order to use partMethod do not forget :
	 * - to add enctype to "multipart-form/data" in jsp form
	 * - to configure multipart for the target servlet (annotation or web.xml)
	 * - to add a hidden field in order to test if partMethod is need or not, for no-file field (with this test field,
	 * 		if getParameter(...) return null just set partMethod to true in constructor)
	 */
	
	
	/**
	 * Generic method to get string field value using getParameter or getParts
	 * @param fieldName
	 * @return
	 */
	protected String getInputTextValue(String fieldName ) {
		String value = "";
		String fieldValue = "";
		try {
			if ( ! this.partMethod )
				fieldValue = (String) this.request.getParameter( fieldName );
			else
				fieldValue = getStringValue(this.request.getPart(fieldName));			
		} catch (Exception e) {
			DLOG.log(Level.DEBUG, e.getMessage());							
		}
	    if ( fieldValue != null ) value = fieldValue ;
	    return value.trim();
	}

	/**
	 * Generic method to get integer field value using getParameter or getParts
	 * @param fieldName
	 * @return
	 */
	protected Integer getInputIntegerValue(String fieldName) {
		Integer value = 0;
		try {
			if ( ! this.partMethod ) {
			    value = Integer.valueOf(this.request.getParameter( fieldName ));
			} else {
				value = Integer.valueOf(getStringValue(this.request.getPart(fieldName)));
			}
		} catch (Exception e) {
			DLOG.log(Level.DEBUG, e.getMessage());							
		}
	    return value;
	}
		

	/**
	 * Generic method to get checkbox (boolean) value using getParameter or getParts
	 * @param fieldName
	 * @return
	 */
	protected Boolean getCheckBoxValue(String fieldName ) {
		Boolean value = false;
		try {
			if ( ! this.partMethod ) {
			    value = Boolean.valueOf(this.request.getParameter( fieldName ));
			} else {
				value = Boolean.valueOf(getStringValue(this.request.getPart(fieldName)));
			}
		} catch (Exception e) { 
			DLOG.log(Level.DEBUG, e.getMessage());				
		}
	    return value;
	}
		
	/**
	 * Utility added in order to read InputStream contained in part and convert it in string.
	 */
	protected String getStringValue( Part part ) throws IOException {
	    BufferedReader reader = new BufferedReader( new InputStreamReader( part.getInputStream(), "UTF-8" ) );
	    StringBuilder stringValue = new StringBuilder();
	    char[] buffer = new char[1024];
	    int longueur = 0;
	    while ( ( longueur = reader.read( buffer ) ) > 0 ) {
	    	stringValue.append( buffer, 0, longueur );
	    }
	    return stringValue.toString();
	}

}