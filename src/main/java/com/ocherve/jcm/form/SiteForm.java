package com.ocherve.jcm.form;

import java.io.File;
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

	
	/**
	 * Constructor without arguments
	 */
	public SiteForm() {
		super();
	}
	
	/**
	 * Constructor using request to instanciate class
	 * 
	 * @param request
	 */
	public SiteForm(HttpServletRequest request) {
		super();
		this.request = request;
		this.slug = "";
		this.siteDao = (SiteDao) DaoProxy.getInstance().getSiteDao();
		// creating a null cotation in order to return it to jsp when formular is wrong
		Cotation nullCotation = new Cotation();
		nullCotation.setId(0);
		nullCotation.setLabel("null");
		
		this.site = new Site();
		
		try {
			// Hidden field createSiteControl tell us if it's possible to use getParameter with multipart or not
			if ( this.request.getParameter("partMethod") == null ) partMethod = true;
			// Setting author from session
			this.site.setAuthor((User) request.getSession().getAttribute("sessionUser"));
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
			this.site.setPublished(true);
			this.site.setFriendTag(false);
			this.site.setType("SITE");
			this.site.setTsCreated(Timestamp.from(Instant.now()));
			this.site.setTsModified(Timestamp.from(Instant.now()));
		} catch (Exception e) {
			DLOG.log(Level.ERROR, "Site can not be instanciated from Formular");
			DLOG.log(Level.ERROR, e.getMessage());
		}
		this.request = null;
	}
	
	/**
	 * @return site instanciate from formular
	 */
	public Site createSite() {
		try { validateName(); } catch (FormException e ) { this.errors.put("name",e.getMessage()); }
		DLOG.log(Level.DEBUG, "Author : " + this.site.getAuthor().getUsername());
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
		try { validateFile() ; } catch (FormException e) { this.errors.put("file", e.getMessage()); }
		try { validateSummary() ; } catch (FormException e) { this.errors.put("summary", e.getMessage()); }
		try { validateContent() ; } catch (FormException e) { this.errors.put("content", e.getMessage()); }
		if ( ! this.errors.isEmpty() ) return this.site;
		Integer siteId = 0;
		try { 
			siteId = this.siteDao.create(this.site).getId(); 
			this.siteDao.refresh(Site.class, siteId);
		} catch (Exception e) {
			DLOG.log(Level.ERROR, "SiteDao : creating site failed");
			this.errors.put("creationSite","La création du site a échoué.");
		}
		try {
			if ( siteId < 1 ) removeFile();
		} catch (FormException e) {
			errors.put("file", e.getMessage());
		}
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
		if ( ! this.site.getCountry().matches("[- \\w]{3,}") )
			throw new FormException("Ce nom de pays n'est pas valide.");
	}

	private void validateDepartment() throws FormException {
		if ( this.site.getDepartment() == null ) throw new FormException("La saisie du département n'est pas valide.");
		if ( ! this.site.getDepartment().matches("[- ()\\w]{3,}") )
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

	private void validateFile() throws FormException {
		String rawName = "";
		String rawType = "jpg";
		String filePath = UPLOAD_PATH + "/site";
		String fileName = this.slug + ".jpg";
		if ( this.slug.isEmpty() ) 
			throw new FormException("Le fichier ne peut pas être sauvagardé car le nom du site est invalide.");
		try {
	        // On vérifie qu'on a bien reçu un fichier
			rawName = getFileName(uploadFile);
		} catch (IOException e) {
			DLOG.log(Level.ERROR, e.getMessage());
			throw new FormException("Le fichier est invalide.");
		}
		if ( rawName == null ) throw new FormException("Le fichier est invalide.");
		DLOG.log(Level.ERROR, "Nom de fichier dans le part : " + rawName);
		if ( rawName.isEmpty() ) throw new FormException("Le fichier est invalide.");
		//if ( rawType == null ) throw new FormException("Ce type de fichier n'est pas reconnu.");
		if ( ! rawType.matches("^[jJ][pP][eE]?[gG]$") )  throw new FormException("Ce type de fichier est invalide.");
		try {
	    	// On écrit définitivement le fichier sur le disque
	    	writeUploadFile(uploadFile, filePath, fileName, UPLOAD_BUFFER_SIZE);			
		} catch (Exception e) {
			DLOG.log(Level.ERROR, e.getMessage());
			throw new FormException("L'envoie du fichier a échoué.");
		}
	}

	private void removeFile() throws FormException {
		String filePath = UPLOAD_PATH + "/site";
		String fileName = this.slug + ".jpg";
		try {
			File file = new File(filePath + "/" + fileName);
			file.delete();
		} catch (Exception ignore) {}
		throw new FormException("La création de site à échoué. Il faut ré-envoyer le fichier.");			
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
	
	

}
