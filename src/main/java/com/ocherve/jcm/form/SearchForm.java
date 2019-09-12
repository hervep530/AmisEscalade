/**
 * 
 */
package com.ocherve.jcm.form;

import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ocherve.jcm.dao.contract.UserDao;
import com.ocherve.jcm.model.Cotation;
import com.ocherve.jcm.model.ReferenceType;

/**
 * @author herve_dev
 *
 */
public class SearchForm {
	
    private static final Logger DLOG = LogManager.getLogger("development_file");
    private static final Level DLOGLEVEL = Level.TRACE;

	/**
	 * Formular field name for the type (site or topo)
	 */
	public final static String TYPE_FIELD = "referenceType";
	
	/**
	 * Formular field name for block climb type (select or not)
	 */
	public final static String BLOCK_FIELD = "block";
	
	/**
	 * Formular field name for cliff climb type (select or not)
	 */
	public final static String CLIFF_FIELD = "cliff";

	/**
	 * Formular field name for wall climb type (select or not)
	 */
	public final static String WALL_FIELD = "wall";

	/**
	 * Formular field name for block climb type (select or not)
	 */
	public final static String HEIGHT_FIELD = "heightMin";

	/**
	 * Formular field name for block climb type (select or not)
	 */
	public final static String COTATION_FIELD = "cotationMin";

	/**
	 * Formular field name for block climb type (select or not)
	 */
	public final static String PATTERN_FIELD = "searchText";

	private UserDao userDao;
	
	
	private ReferenceType referenceType = ReferenceType.SITE;
	private boolean block = true;
	private boolean cliff = true;
	private boolean wall = true;
	private Integer height = 0;
	private Cotation cotation = null;
	private String result = "";
	private int userId = 0;
	private Map<String,String> errors;

	
	
	
	
	
	
	
	
	
	
	
	
	
}
