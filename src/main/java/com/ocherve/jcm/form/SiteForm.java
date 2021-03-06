package com.ocherve.jcm.form;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.Normalizer;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.logging.log4j.Level;

import com.ocherve.jcm.model.User;
import com.ocherve.jcm.dao.DaoProxy;
import com.ocherve.jcm.dao.contract.SiteDao;
import com.ocherve.jcm.model.Cotation;
import com.ocherve.jcm.model.Site;

/**
 * @author herve_dev
 *
 */
public class SiteForm extends Form {
	
	private SiteDao siteDao;
	private Site site;
	private String slug;
	private Part uploadFile;
	private Map<String,String> help;

	
	/**
	 * Constructor without arguments
	 */
	public SiteForm() {
		super();
		this.filepath = System.getProperty("catalina.base") + UPLOAD_PATH + "/site" ;
		DLOG.log(Level.TRACE, "filepath : " + this.filepath);
	}
	
	/**
	 * Constructor using request to instanciate class
	 * 
	 * @param request
	 * @param updating 
	 */
	public SiteForm(HttpServletRequest request, boolean updating) {
		super();
		this.filepath = System.getProperty("catalina.base") + UPLOAD_PATH + "/site" ;
		DLOG.log(Level.TRACE, "filepath : " + this.filepath);
		this.request = request;
		this.slug = "";
		this.siteDao = (SiteDao) DaoProxy.getInstance().getSiteDao();
		// creating a null cotation in order to return it to jsp when formular is wrong
		Cotation nullCotation = new Cotation();
		nullCotation.setId(0);
		nullCotation.setLabel("null");
		
		
		try {
			// Hidden field createSiteControl tell us if it's possible to use getParameter with multipart or not
			if ( this.request.getParameter("partMethod") == null ) partMethod = true;
			if ( ! updating) {
				this.site = new Site();
				// Setting author from session
				this.site.setAuthor((User) request.getSession().getAttribute("sessionUser"));
				this.site.setPublished(true);
				this.site.setFriendTag(false);
				this.site.setType("SITE");
				this.site.setTsCreated(Timestamp.from(Instant.now()));
			} else {
				Integer siteId = 0;
				if ( getInputTextValue("siteId").matches("^[0-9]{1,}$") ) 
					siteId = Integer.valueOf(getInputTextValue("siteId"));
				// Before all when updating, we get site from id, set this.slug from topo, and trace name updating
				this.site = siteDao.get(siteId);
				this.slug = this.site.getSlug();
				this.image = this.slug + ".jpg";
				if ( ! getInputTextValue("name").contentEquals(this.site.getName()) ) 
					this.updatingName = true;
			}
			// Getting field necessary to build site object
			this.site.setName(getInputTextValue("name"));
			this.site.setCountry(getInputTextValue("country"));
			this.site.setDepartment(getInputTextValue("department"));
			this.site.setPathsNumber(getInputIntegerValue("pathsNumber"));
			this.site.setOrientation(getInputTextValue("orientation"));
			this.site.setBlock(getCheckBoxValue("block"));
			this.site.setCliff(getCheckBoxValue("cliff"));
			this.site.setWall(getCheckBoxValue("wall"));
			this.site.setMinHeight(getInputIntegerValue("minHeight"));
			this.site.setMaxHeight(getInputIntegerValue("maxHeight"));
			// cotationMin : set to nullCotation by default & if field not empty get cotation with siteDao
			this.site.setCotationMin(nullCotation);
			Integer cotationMin = getInputIntegerValue("cotationMin");
			if ( cotationMin > 0 ) this.site.setCotationMin(siteDao.getCotation(cotationMin));
			// cotationMax : set to nullCotation by default & if field not empty get cotation with siteDao
			this.site.setCotationMax(nullCotation);
			Integer cotationMax = getInputIntegerValue("cotationMax");
			if ( cotationMax > 0 ) this.site.setCotationMax(siteDao.getCotation(cotationMax));
			// Upload file
			@SuppressWarnings("unused")
			String fileDescription = getInputTextValue("description");  // not use - just to keep it in mind
			this.uploadFile = getRawPart("uploadFile");
			// summary and content
			this.site.setSummary(getInputTextValue("summary"));
			this.site.setContent(getInputTextValue("content"));
			// Giving default value for other site attributes
			this.site.setTsModified(Timestamp.from(Instant.now()));
		} catch (Exception e) {
			DLOG.log(Level.ERROR, "Site can not be instanciated from Formular");
			DLOG.log(Level.ERROR, e.getMessage());
		}
		this.request = null;
	}

	/**
	 * Constructor using site id to instanciate class
	 * 
	 * @param siteId
	 */
	public SiteForm(Integer siteId) {
		super();
		this.filepath = System.getProperty("catalina.base") + UPLOAD_PATH + "/site" ;
		DLOG.log(Level.TRACE, "filepath : " + this.filepath);
		if (siteId == null) {
			DLOG.log(Level.ERROR, "Topo form can not be instanciated - siteId is null.");
			return;
		}
		this.siteDao = (SiteDao) DaoProxy.getInstance().getSiteDao();
		// Getting site from id, then setting site form
		try {
			this.site = siteDao.get(siteId);
			this.slug = site.getSlug();
			this.image = site.getSlug() + ".jpg";
		} catch (Exception e) {
			DLOG.log(Level.ERROR, "Site form can not be instanciated - siteId : " + siteId);
			DLOG.log(Level.ERROR, e.getMessage());
		}
	}
	
	/**
	 * @return site instanciate from formular
	 */
	public Site createSite() {
		try { validateName(); } catch (FormException e ) { this.errors.put("name",e.getMessage()); }
		DLOG.log(Level.DEBUG, "Author : " + this.site.getAuthor().getUsername());
		this.filename = this.slug + ".jpg";
		this.thumbFilename = this.slug + "-thumb.jpg";
		this.tmpFilename = this.slug + "-tmp.jpg";
		// validate country
		try { validateCountry() ; } catch (FormException e) { this.errors.put("country", e.getMessage()); }
		try { validateDepartment() ; } catch (FormException e) { this.errors.put("department", e.getMessage()); }
		try { validatePathsNumber() ; } catch (FormException e) { this.errors.put("pathsNumber", e.getMessage()); }
		try { validateOrientation() ; } catch (FormException e) { this.errors.put("orientation", e.getMessage()); }
		try { validateType() ; } catch (FormException e) { this.errors.put("type", e.getMessage()); }
		try { validateMinHeight() ; } catch (FormException e) { this.errors.put("minHeight", e.getMessage()); }
		try { validateMaxHeight() ; } catch (FormException e) { this.errors.put("maxHeight", e.getMessage()); }
		try { validateCotationMin() ; } catch (FormException e) { this.errors.put("cotationMin", e.getMessage()); }
		try { validateCotationMax() ; } catch (FormException e) { this.errors.put("cotationMax", e.getMessage()); }
		try { validateFileWhenCreatingSite() ; } catch (FormException e) { this.errors.put("file", e.getMessage()); }
		try { validateSummary() ; } catch (FormException e) { this.errors.put("summary", e.getMessage()); }
		try { validateContent() ; } catch (FormException e) { this.errors.put("content", e.getMessage()); }
		if ( ! this.errors.isEmpty() ) {
			// On Validation Errors we stop and remove tmp file. Form with errors will be forward to formular...
			try { this.removeFile(this.tmpFilename, true); } catch (FormException e) { errors.put("file", e.getMessage()); }
			return this.site;
		}
		// If formular is validated, we call dao to create sit
		Integer siteId = 0;
		try { 
			siteId = this.siteDao.create(this.site).getId(); 
			this.siteDao.refresh(Site.class, siteId);
		} catch (Exception e) {
			// On dao error, we delete file, write log add error to formular and forward it at the end of servlet
			DLOG.log(Level.ERROR, "SiteDao : creating site failed");
			this.errors.put("creationSite","La création du site a échoué.");
			try { this.removeFile(this.tmpFilename, true); } catch (FormException e1) { errors.put("file", e1.getMessage()); }
			return site;
		}
		// If no error, we definitly publish image : tmpFilename -> filename
		try { this.publishFile(this.tmpFilename); } catch (FormException ignore) { }
		return site;
	}

	/**
	 * @return site instanciate from formular
	 */
	public Site updateSite() {
		try { 
			if ( this.updatingName ) validateName(); 
		} catch (FormException e ) { this.errors.put("name",e.getMessage()); }
		this.filename = this.slug + ".jpg";
		this.thumbFilename = this.slug + "-thumb.jpg";
		this.tmpFilename = this.slug + "-tmp.jpg";
		try { validateCountry() ; } catch (FormException e) { this.errors.put("country", e.getMessage()); }
		try { validateDepartment() ; } catch (FormException e) { this.errors.put("department", e.getMessage()); }
		try { validatePathsNumber() ; } catch (FormException e) { this.errors.put("pathsNumber", e.getMessage()); }
		try { validateOrientation() ; } catch (FormException e) { this.errors.put("orientation", e.getMessage()); }
		try { validateType() ; } catch (FormException e) { this.errors.put("type", e.getMessage()); }
		try { validateMinHeight() ; } catch (FormException e) { this.errors.put("minHeight", e.getMessage()); }
		try { validateMaxHeight() ; } catch (FormException e) { this.errors.put("maxHeight", e.getMessage()); }
		try { validateCotationMin() ; } catch (FormException e) { this.errors.put("cotationMin", e.getMessage()); }
		try { validateCotationMax() ; } catch (FormException e) { this.errors.put("cotationMax", e.getMessage()); }
		try { validateFileWhenUpdatingSite() ; } catch (FormException e) { this.errors.put("file", e.getMessage()); }
		try { validateSummary() ; } catch (FormException e) { this.errors.put("summary", e.getMessage()); }
		try { validateContent() ; } catch (FormException e) { this.errors.put("content", e.getMessage()); }
		if ( ! this.errors.isEmpty() ) {
			try { this.removeFile(this.tmpFilename, true); } catch (FormException e) { errors.put("file", e.getMessage()); }
			return this.site;
		}
		try { 
			this.siteDao.update(this.site); 
			// this.siteDao.refresh(Site.class, siteId);
		} catch (Exception e) {
			DLOG.log(Level.ERROR, "SiteDao : creating site failed");
			this.errors.put("creationSite","La création du site a échoué.");
			try { this.removeFile(this.tmpFilename, true); } catch (FormException e1) { errors.put("file", e1.getMessage()); }
			return site;
		}
		if ( this.updatingFile || this.updatingName ) this.updateImage(); 		
		return site;
	}
	
	private void validateName() throws FormException {
		// Test not null
		if ( this.site.getName() == null ) throw new FormException("Ce nom de site n'est pas valide.");
		// Test contains more than 4 chars (word, " " or - are accepted
		if ( ! this.site.getName().matches("\\w[- \\w]{2,}\\w") )
			throw new FormException("Ce nom de site n'est pas valide.");
		// Test slug is unique, so name is unique
		this.slug = Normalizer.normalize(this.site.getName(), Normalizer.Form.NFD).replaceAll("[\u0300-\u036F]", "");
		this.slug = this.slug.replaceAll("\\W", "_").replaceAll("_{1,}","_").toLowerCase();
		// Request user id from database with mail address as filter (one Integer result expected... Else rejecting)
		Map<String,Object> parameters = new HashMap<>();
		parameters.put("slug", this.slug);
		parameters.put("type", "SITE");
		int siteId = 0;
		try {
			siteId = siteDao.getIdFromNamedQuery("Reference.getIdFromSlug", parameters);
		} catch (Exception e) {/* if it's a dao error it was tracked anyway */}
		DLOG.log(Level.DEBUG , "Id trouvé : " + siteId);
		if ( siteId > 0 )
			throw new FormException("Un site existe déjà avec un nom similaire.");
	}
		
	private void validateCountry() throws FormException {
		if ( this.site.getCountry() == null ) throw new FormException("Ce nom de pays n'est pas valide.");
		if ( ! this.site.getCountry().matches("[- éèêëàâùûôîï\\w]{3,}") )
			throw new FormException("Ce nom de pays n'est pas valide.");
	}

	private void validateDepartment() throws FormException {
		if ( this.site.getDepartment() == null ) throw new FormException("La saisie du département n'est pas valide.");
		if ( ! this.site.getDepartment().matches("[- éèêëàâùûôîï()\\w]{3,}") )
			throw new FormException("La saisie du département n'est pas valide.");
	}

	private void validatePathsNumber() throws FormException {
		if ( this.site.getPathsNumber() == null ) throw new FormException("Le nombre de voies n'est pas valide.");
		if ( this.site.getPathsNumber() <= 0 )
			throw new FormException("Le nombre de voies doit être supérieur à 0.");
	}


	private void validateOrientation() throws FormException {
		if ( this.site.getOrientation() == null ) throw new FormException("La saisie de l'orientation n'est pas valide.");
		if ( ! this.site.getOrientation().matches("[- \\w]{3,}") )
			throw new FormException("La saisie de l'orientation n'est pas valide.");
	}

	private void validateType() throws FormException {
		if ( ! (this.site.isBlock() || this.site.isCliff() || this.site.isWall() ) ) 
			throw new FormException("Aucun type d'escalade n'est sélectionné.");
	}

	private void validateMinHeight() throws FormException {
		if ( this.site.getMinHeight() == null ) throw new FormException("La hauteur de voie minimum n'est pas valide.");
		if ( this.site.getMinHeight() <= 0  || this.site.getMinHeight() > 1000)
			throw new FormException("La hauteur de voie minimum (en mètres) doit être comprise entre 1 et 1000.");
	}


	private void validateMaxHeight() throws FormException {
		if ( this.site.getMaxHeight() == null ) throw new FormException("La hauteur de voie maximum n'est pas valide.");
		if ( this.site.getMaxHeight() < 1  || this.site.getMaxHeight() > 3000)
			throw new FormException("La hauteur de voie maximum (en mètres) doit être comprise entre 1 et 3000.");
		int minHeight = 0;
		if ( this.site.getMinHeight() != null ) minHeight = this.site.getMinHeight();
		if ( minHeight > this.site.getMaxHeight() )
			throw new FormException("La hauteur de voie maximum doit être supérieure au minimum.");
	}


	private void validateCotationMin() throws FormException {
		if ( this.site.getCotationMin() == null ) throw new FormException("La cotation minimum n'est pas valide.");
		if ( this.site.getCotationMin().getId() <= 0 || this.site.getCotationMin().getId() > 30)
			throw new FormException("La cotation minimum n'est pas valide.");
	}


	private void validateCotationMax() throws FormException {
		if ( this.site.getCotationMax() == null ) throw new FormException("La cotation maximum n'est pas valide.");
		if ( this.site.getCotationMax().getId() <= 0 || this.site.getCotationMax().getId() > 30)
			throw new FormException("La cotation maximum n'est pas valide.");
		int minCotationId = 0;
		if ( this.site.getCotationMin() != null ) minCotationId = this.site.getCotationMin().getId();
		if ( minCotationId > this.site.getCotationMax().getId() )
			throw new FormException("La cotation maximum doit être supérieure à la cotation minimum.");
	}

	private void validateFileWhenCreatingSite() throws FormException {
		String rawName = "";
		String rawType = "jpg";
		String filePath = System.getProperty("catalina.base") + UPLOAD_PATH + "/site";
		// Test Slug because filename and slug must match 
		if ( this.slug.isEmpty() ) 
			throw new FormException("Le fichier ne peut pas être sauvagardé car le nom du site est invalide.");
		// Test uploded file
		try {
			rawName = getFileName(uploadFile);
		} catch (IOException e) {
			DLOG.log(Level.ERROR, e.getMessage());
			throw new FormException("Le fichier est invalide.");
		}
		if ( rawName == null ) throw new FormException("Le fichier est invalide.");
		DLOG.log(Level.ERROR, "Nom de fichier dans le part : " + rawName);
		if ( rawName.isEmpty() ) throw new FormException("Le fichier est invalide.");
		// Test file type
		if ( ! rawType.matches("^[jJ][pP][eE]?[gG]$") )  throw new FormException("Ce type de fichier est invalide.");
		// Writing file as tmpfile
		try {
	    	writeUploadFile(uploadFile, filePath, this.tmpFilename, UPLOAD_BUFFER_SIZE);			
		} catch (Exception e) {
			DLOG.log(Level.ERROR, e.getMessage());
			throw new FormException("L'envoi du fichier a échoué.");
		}
	}

	private void validateFileWhenUpdatingSite() throws FormException {
		String rawName = "";
		String rawType = "jpg";
		String filePath = System.getProperty("catalina.base") + UPLOAD_PATH + "/site";
		// Test Slug because filename and slug must match 
		if ( this.slug.isEmpty() ) 
			throw new FormException("Le fichier ne peut pas être sauvagardé car le nom du site est invalide.");
		// Test uploded file
		try {
			rawName = getFileName(uploadFile);
		} catch (IOException e) {
			DLOG.log(Level.ERROR, e.getMessage());
			return;
		}
		if ( rawName == null ) return;
		DLOG.log(Level.DEBUG, "Nom de fichier dans le part : " + rawName);
		if ( rawName.isEmpty() ) return;
		this.updatingFile = true;
		// Test file type
		if ( ! rawType.matches("^[jJ][pP][eE]?[gG]$") )  throw new FormException("Ce type de fichier est invalide.");
		// Writing file as tmpfile
		try {
	    	writeUploadFile(uploadFile, filePath, this.tmpFilename, UPLOAD_BUFFER_SIZE);			
		} catch (Exception e) {
			DLOG.log(Level.ERROR, e.getMessage());
			throw new FormException("L'envoie du fichier a échoué.");
		}
	}

	private void validateSummary() throws FormException {
		// Test not null
		if ( this.site.getSummary() == null ) throw new FormException("Saisissez un résumé.");
		// Test contains more than 4 chars (word, " " or - are accepted
		if ( this.site.getSummary().length() < 20 || this.site.getSummary().length() > 150 )
			throw new FormException("Le résumé doit contenir entre 20 et 150 caractères.");		
	}
	
	private void validateContent() throws FormException {
		// Test not null
		if ( this.site.getContent() == null ) throw new FormException("Saisissez un contenu.");
		// Test contains more than 4 chars (word, " " or - are accepted
		if ( this.site.getContent().length() < 20 )
			throw new FormException("Le contenu doit contenir au minimum 20 caractère.");		
	}
 
	/**
	 * @return the errors
	 */
	public Map<String, String> getErrors() {
		return this.errors;
	}

	/**
	 * @return the site
	 */
	public Site getSite() {
		return this.site;
	}	
	
	/**
	 * Method to get help content for formular bullets
	 * 
	 * @return help contents 
	 */
	public Map<String,String> getHelp() {
		if ( this.help == null ) {
			this.help = new HashMap<>();
			this.help.put("name", "Le nom doit contenir au moins 3 caractères parmi lettres, chiffres, espace ou tiret.");
			this.help.put("country", "Le pays doit contenir au moins 3 caractères parmi lettres, chiffres, espace ou tiret.");
			this.help.put("department", "Le departement doit contenir au moins 3 caractères parmi lettres, chiffres, espace ou tiret.");
			this.help.put("pathsNumber", "Le nombre de voies doit être supérieur à 0.");
			this.help.put("orientation", "L'orientation doit contenir au moins 3 caractères parmi lettres, espace ou tiret.");
			this.help.put("type", "Choisir un ou plusieurs type d'escalade");
			this.help.put("minHeight", "La hauteur de voie minimum (en mètres) doit être comprise entre 1 et 1000.");
			this.help.put("maxHeight", "La hauteur de voie maximum (en mètres) doit être comprise entre 1 et 3000, et supérieur à la hauteur minimum.");
			this.help.put("cotationMin", "Choisir une cotation dans la liste.");
			this.help.put("cotationMax", "Choisir dans la liste, une cotation supérieure à la cotation minimum.");
			this.help.put("image", "Les photos sont re-dimensionnées en 1280x720. Choisissez donc un format compatible pour éviter toute déformation lors du redimensionnement. La taille du fichier ne doit pas dépassé 1M");
			this.help.put("summary", "Le résumé doit contenir entre 20 et 150 caractères.");
			this.help.put("content", "Le contenu doit contenir au minimum 20 caractère.");
		}
		return this.help;
	}
}
