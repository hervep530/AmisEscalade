/**
 * 
 */
package com.ocherve.jcm.form;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import com.ocherve.jcm.dao.DaoProxy;
import com.ocherve.jcm.dao.contract.SiteDao;
import com.ocherve.jcm.dao.impl.AddingMode;
import com.ocherve.jcm.dao.impl.Operator;
import com.ocherve.jcm.dao.impl.OrderClause;
import com.ocherve.jcm.dao.impl.WhereClause;
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
	public final static String TYPE_FIELD = "reference_type";
	
	/**
	 * Formular field name for block climb type (select or not)
	 */
	public final static String SITE_TYPE_FIELD = "site_type";
	
	/**
	 * Formular field name for block climb type (select or not)
	 */
	public final static String HEIGHT_FIELD = "height";

	/**
	 * Formular field name for block climb type (select or not)
	 */
	public final static String COTATION_FIELD = "cotation";

	/**
	 * Formular field name for block climb type (select or not)
	 */
	public final static String SEARCH_FIELD = "search";

	@SuppressWarnings("unused")
	private SiteDao siteDao;
	
	private String typeField = "";
	private Integer siteTypeField = 0;
	private Integer heightField = 0;
	private Integer cotationField = 0;
	private String searchField = "";
	
	private String basicQuery = "";
	private WhereClause whereClauses = null;
	private OrderClause orderClauses = null;
	private Map<String,Object> queryParameters;
	private String query = "";
	
	private Map<String,String> errors;
	private Map<String,String> help;

	/**
	 * Just create empty form in order to get help, for example...
	 */
	public SearchForm() {
		super();
	}

	/**
	 * @param request
	 */
	public SearchForm(HttpServletRequest request) {
		
		Configurator.setLevel(DLOG.getName(), DLOGLEVEL);
		siteDao = (SiteDao) DaoProxy.getInstance().getSiteDao();
		typeField = getFormFieldValue(request, TYPE_FIELD, false);
		siteTypeField = Integer.valueOf(getFormFieldValue(request, SITE_TYPE_FIELD, true));
		heightField = Integer.valueOf(getFormFieldValue(request, HEIGHT_FIELD, true));
		cotationField = Integer.valueOf(getFormFieldValue(request, COTATION_FIELD, true));
		searchField = getFormFieldValue(request, SEARCH_FIELD, false);
		queryParameters = new HashMap<>();
		this.errors = new HashMap<>();
	}
	
	/**
	 * 
	 */
	public void Search() {
		try {
			this.validateTypeField();
		} catch (FormException e ) {
			this.errors.put("SearchForm_Type", e.getMessage());
		}
		try {
			if ( typeField.toUpperCase().contentEquals("SITE") ) this.validateSiteTypeField();			
		} catch (FormException e) {
			this.errors.put("SearchForm_SiteType", e.getMessage());
		}
		try {
			if ( typeField.toUpperCase().contentEquals("SITE") ) this.validateHeightField();			
		} catch (FormException e) {
			this.errors.put("SearchForm_Height", e.getMessage());
		}
		try {
			if ( typeField.toUpperCase().contentEquals("SITE") ) this.validateCotationField();			
		} catch (FormException e) {
			this.errors.put("SearchForm_Cotation", e.getMessage());
		}
		try {
			if ( typeField.toUpperCase().contentEquals("SITE") ) this.validateSearchField();			
		} catch (FormException e) {
			this.errors.put("SearchForm_Text", e.getMessage());
		}
		query = basicQuery;
		if ( whereClauses != null )  query += whereClauses.getSql();
		if ( orderClauses != null )  query += orderClauses.getSql();
	}
	
	private void validateTypeField() throws FormException {
		basicQuery = "SELECT r FROM Site r";
		try {
			ReferenceType type = ReferenceType.valueOf(typeField.toUpperCase());
			if ( type == ReferenceType.TOPO ) basicQuery = "SELECT r FROM Topo r";
		} catch ( Exception e ) {
			DLOG.log(Level.ERROR , e.getMessage());
			DLOG.log(Level.ERROR , "Invalid ReferenceType : " + typeField);
			throw new FormException("Type de recherche invalide.");
		}
	}
	
	private void validateSiteTypeField() throws FormException {
		String[] arrayType = {"", "block", "cliff", "wall"}; 
		if ( siteTypeField < 0 || siteTypeField > 3 ) {
			DLOG.log(Level.ERROR , "Invalid SiteType : " + siteTypeField);
			throw new FormException("Type de site invalide.");
		}
		if ( siteTypeField ==  0) return;
		if ( whereClauses == null ) {
			whereClauses = new WhereClause("r." + arrayType[siteTypeField], Operator.EQUAL);
		} else {
			whereClauses.add(AddingMode.AND, "r." + arrayType[siteTypeField], Operator.EQUAL);
		}
		queryParameters.put(WhereClause.getCamel("r." + arrayType[siteTypeField]), true);
	}
	
	private void validateHeightField() throws FormException {
		if ( heightField < 0 || heightField > 2000 ) {
			DLOG.log(Level.ERROR , "Invalid height value : " + heightField);
			throw new FormException("La hauteur de voie doit être comprise entre 0 et 2000m.");
		}
		if ( heightField == 0 ) return;
		if ( whereClauses == null ) {
			whereClauses = new WhereClause("r.minHeight" , Operator.LESSEQ);
		} else {
			whereClauses.add(AddingMode.AND, "r.minHeight" , Operator.LESSEQ);
		}
		whereClauses.add(AddingMode.AND, "r.maxHeight" , Operator.GREATEREQ);		
		queryParameters.put("rMinHeight", heightField);
		queryParameters.put("rMaxHeight", heightField);
	}
	
	private void validateCotationField() throws FormException {
		if ( cotationField < 0 || cotationField > 30 ) {
			DLOG.log(Level.ERROR , "Invalid cotation id value : " + cotationField);
			throw new FormException("La cotation indiquée n'existe pas.");
		}
		if ( cotationField == 0 ) return;
		if ( whereClauses == null ) {
			whereClauses = new WhereClause("r.cotationMin.id" , Operator.LESSEQ);
		} else {
			whereClauses.add(AddingMode.AND, "r.cotationMin.id" , Operator.LESSEQ);
		}
		whereClauses.add(AddingMode.AND, "r.cotationMax.id" , Operator.GREATEREQ);		
		queryParameters.put("rCotationMinId", cotationField);
		queryParameters.put("rCotationMaxId", cotationField);
	}
	
	private void validateSearchField() throws FormException{
		if ( searchField.matches("") ) {
			DLOG.log(Level.ERROR , "Search text contains invalid characters ." +
					"Only words are accepted - have a look on [" + searchField + "]");
			throw new FormException("La recherche ne doit contenir que des mots sans tiret.");
		}
		if ( searchField.trim().isEmpty() ) return;
		String[] arraySearch = searchField.replaceAll("_", " ")
										  .replaceAll(" {1,}", " ")
										  .trim()
										  .split(" ");
		Map<String,Operator> group = new HashMap<>();
		for ( int i = 0; i < arraySearch.length; i++) {
			group.put("r.name." + i, Operator.LIKE);
			queryParameters.put(WhereClause.getCamel("r.name." + i), "%" + arraySearch[i] + "%");
			group.put("r.summary." + i, Operator.LIKE);
			queryParameters.put(WhereClause.getCamel("r.summary." + i), "%" + arraySearch[i] + "%");
			group.put("r.content." + i, Operator.LIKE);			
			queryParameters.put(WhereClause.getCamel("r.content." + i), "%" + arraySearch[i] + "%");
		}
		if ( whereClauses == null ) {
			whereClauses = new WhereClause(false , group);
		} else {
			whereClauses.addOrGroup(AddingMode.AND, group);
		}		
	}
	
	private static String getFormFieldValue( HttpServletRequest request, String nomChamp, Boolean numeric ) {
	    String value = (String) request.getParameter( nomChamp );
	    if ( value == null ) value = "";
	    if ( numeric && ! value.matches("[0-9]{1,10}") ) value = "0";
        return value.trim();
	}

	/**
	 * @return the typeField
	 */
	public String getTypeField() {
		return typeField;
	}

	/**
	 * @return the siteTypeField
	 */
	public Integer getSiteTypeField() {
		return siteTypeField;
	}

	/**
	 * @return the heightField
	 */
	public Integer getHeightField() {
		return heightField;
	}

	/**
	 * @return the cotationField
	 */
	public Integer getCotationField() {
		return cotationField;
	}

	/**
	 * @return the searchField
	 */
	public String getSearchField() {
		return searchField;
	}

	/**
	 * @return query
	 */
	public String getQuery() {
		return query;
	}

	/**
	 * @return the basicQuery
	 */
	public String getBasicQuery() {
		return basicQuery;
	}

	/**
	 * @return the whereClauses
	 */
	public WhereClause getWhereClauses() {
		return whereClauses;
	}

	/**
	 * @return the orderClauses
	 */
	public OrderClause getOrderClauses() {
		return orderClauses;
	}

	/**
	 * @return the queryParameters
	 */
	public Map<String, Object> getQueryParameters() {
		return queryParameters;
	}

	/**
	 * @return the errors
	 */
	public Map<String, String> getErrors() {
		return errors;
	}
	
	/**
	 * Method to get help content for formular bullets
	 * 
	 * @return help contents 
	 */
	public Map<String,String> getHelp() {
		if (this.help == null ) {
			this.help = new HashMap<>();
			this.help.put("type", "Selectionnez un des choix proposés.");
			this.help.put("height", "La recherche donnera pour résultat les sites pour lesquels cette hauteur est comprise entre le mininum et le maximum.");
			this.help.put("cotation", "La recherche donnera pour résultat les sites pour lesquels cette cotation est comprise entre le mininum et le maximum.");
			this.help.put("content", "Entrez un ou plusieurs mots, qui seront recherchés dans le titre, le résumé ou le contenu");
		}
		return this.help;
	}
	

}
