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
public class CreateSiteForm extends Form {
	
	private SiteDao siteDao;
	private Site site;
	private String slug;
	private Part uploadFile;

	
	/**
	 * Constructor without arguments
	 */
	public CreateSiteForm() {
		super();
	}
	
	/**
	 * Constructor using request to instanciate class
	 * 
	 * @param request
	 */
	public CreateSiteForm(HttpServletRequest request) {
		super();
		this.request = request;
		this.slug = "";
		siteDao = (SiteDao) DaoProxy.getInstance().getSiteDao();
		// creating a null cotation in order to return it to jsp when formular is wrong
		Cotation nullCotation = new Cotation();
		nullCotation.setId(0);
		nullCotation.setLabel("null");
		
		site = new Site();
		
		try {
			// Hidden field createSiteControl tell us if it's possible to use getParameter with multipart or not
			if ( this.request.getParameter("partMethod") == null ) partMethod = true;
			// Setting author from session
			site.setAuthor((User) request.getSession().getAttribute("sessionUser"));
			// Getting field necessary to build site object
			site.setName(getInputTextValue("name"));
			site.setCountry(getInputTextValue("country"));
			site.setDepartment(getInputTextValue("department"));
			site.setPathsNumber(getInputIntegerValue("pathsNumber"));
			site.setOrientation(getInputTextValue("orientation"));
			site.setBlock(getCheckBoxValue("block"));
			site.setCliff(getCheckBoxValue("cliff"));
			site.setWall(getCheckBoxValue("wall"));
			site.setMinHeight(getInputIntegerValue("minHeight"));
			site.setMaxHeight(getInputIntegerValue("maxHeight"));
			// cotationMin : set to nullCotation by default & if field not empty get cotation with siteDao
			site.setCotationMin(nullCotation);
			Integer cotationMin = getInputIntegerValue("cotationMin");
			if ( cotationMin > 0 ) site.setCotationMin(siteDao.getCotation(cotationMin));
			// cotationMax : set to nullCotation by default & if field not empty get cotation with siteDao
			site.setCotationMax(nullCotation);
			Integer cotationMax = getInputIntegerValue("cotationMax");
			if ( cotationMax > 0 ) site.setCotationMax(siteDao.getCotation(cotationMax));
			// Upload file
			@SuppressWarnings("unused")
			String fileDescription = getInputTextValue("description");  // not use - just to keep it in mind
			uploadFile = getRawPart("uploadFile");
			// summary and content
			site.setSummary(getInputTextValue("summary"));
			site.setContent(getInputTextValue("content"));
			// Giving default value for other site attributes
			site.setPublished(true);
			site.setFriendTag(false);
			site.setType("SITE");
			site.setTsCreated(Timestamp.from(Instant.now()));
			site.setTsModified(Timestamp.from(Instant.now()));
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
		try { validateName(); } catch (FormException e ) { errors.put("name",e.getMessage()); }
		DLOG.log(Level.DEBUG, "Author : " + this.site.getAuthor().getUsername());
		// validate country
		try { validateCountry() ; } catch (FormException e) { errors.put("country", e.getMessage()); }
		try { validateDepartment() ; } catch (FormException e) { errors.put("department", e.getMessage()); }
		try { validatePathsNumber() ; } catch (FormException e) { errors.put("pathsNumber", e.getMessage()); }
		try { validateOrientation() ; } catch (FormException e) { errors.put("orientation", e.getMessage()); }
		try { validateType() ; } catch (FormException e) { errors.put("type", e.getMessage()); }
		try { validateMinHeight() ; } catch (FormException e) { errors.put("minHeight", e.getMessage()); }
		try { validateMaxHeight() ; } catch (FormException e) { errors.put("maxHeight", e.getMessage()); }
		try { validateCotationMin() ; } catch (FormException e) { errors.put("cotationMin", e.getMessage()); }
		try { validateCotationMax() ; } catch (FormException e) { errors.put("cotationMax", e.getMessage()); }
		try { validateFile() ; } catch (FormException e) { errors.put("file", e.getMessage()); }
		try { validateSummary() ; } catch (FormException e) { errors.put("summary", e.getMessage()); }
		try { validateContent() ; } catch (FormException e) { errors.put("content", e.getMessage()); }
		if ( ! errors.isEmpty() ) return site;
		Integer siteId = 0;
		try { 
			siteId = siteDao.create(site).getId(); 
			siteDao.refresh(Site.class, siteId);
		} catch (Exception e) {
			DLOG.log(Level.ERROR, "SiteDao : creating site failed");
			errors.put("creationSite","La création du site a échoué.");
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
		if ( site.getName() == null ) throw new FormException("Ce nom de site n'est pas valide.");
		// Test contains more than 4 chars (word, " " or - are accepted
		if ( ! site.getName().matches("\\w[- \\w]{2,}\\w") )
			throw new FormException("Ce nom de site n'est pas valide.");
		// Test slug is unique, so name is unique
		this.slug = Normalizer.normalize(site.getName(), Normalizer.Form.NFD).replaceAll("[\u0300-\u036F]", "");
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
		if ( site.getCountry() == null ) throw new FormException("Ce nom de pays n'est pas valide.");
		if ( ! site.getCountry().matches("[- \\w]{3,}") )
			throw new FormException("Ce nom de pays n'est pas valide.");
	}

	private void validateDepartment() throws FormException {
		if ( site.getDepartment() == null ) throw new FormException("La saisie du département n'est pas valide.");
		if ( ! site.getDepartment().matches("[- ()\\w]{3,}") )
			throw new FormException("La saisie du département n'est pas valide.");
	}

	private void validatePathsNumber() throws FormException {
		if ( site.getPathsNumber() == null ) throw new FormException("Le nombre de voies n'est pas valide.");
		if ( site.getPathsNumber() <= 0 )
			throw new FormException("Le nombre de voies doit être supérieur à 0.");
	}


	private void validateOrientation() throws FormException {
		if ( site.getOrientation() == null ) throw new FormException("La saisie de l'orientation n'est pas valide.");
		if ( ! site.getOrientation().matches("[- \\w]{3,}") )
			throw new FormException("La saisie de l'orientation n'est pas valide.");
	}

	private void validateType() throws FormException {
		if ( ! (site.isBlock() || site.isCliff() || site.isWall() ) ) 
			throw new FormException("Aucun type d'escalade n'est sélectionné.");
	}

	private void validateMinHeight() throws FormException {
		if ( site.getMinHeight() == null ) throw new FormException("La hauteur de voie minimum n'est pas valide.");
		if ( site.getMinHeight() <= 0  || site.getMinHeight() > 1000)
			throw new FormException("La hauteur de voie minimum (en mètres) doit être comprise entre 1 et 1000.");
	}


	private void validateMaxHeight() throws FormException {
		if ( site.getMaxHeight() == null ) throw new FormException("La hauteur de voie maximum n'est pas valide.");
		if ( site.getMaxHeight() < 10  || site.getMaxHeight() > 3000)
			throw new FormException("La hauteur de voie maximum (en mètres) doit être comprise entre 10 et 3000.");
		int minHeight = 0;
		if ( site.getMinHeight() != null ) minHeight = site.getMinHeight();
		if ( minHeight > site.getMaxHeight() )
			throw new FormException("La hauteur de voie maximum doit être supérieure au minimum.");
	}


	private void validateCotationMin() throws FormException {
		if ( site.getCotationMin() == null ) throw new FormException("La cotation minimum n'est pas valide.");
		if ( site.getCotationMin().getId() <= 0 || site.getCotationMin().getId() > 30)
			throw new FormException("La cotation minimum n'est pas valide.");
	}


	private void validateCotationMax() throws FormException {
		if ( site.getCotationMax() == null ) throw new FormException("La cotation maximum n'est pas valide.");
		if ( site.getCotationMax().getId() <= 0 || site.getCotationMax().getId() > 30)
			throw new FormException("La cotation maximum n'est pas valide.");
		int minCotationId = 0;
		if ( site.getCotationMin() != null ) minCotationId = site.getCotationMin().getId();
		if ( minCotationId > site.getCotationMax().getId() )
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
		if ( site.getSummary() == null ) throw new FormException("Saisissez un résumé.");
		// Test contains more than 4 chars (word, " " or - are accepted
		if ( site.getSummary().length() < 20 || site.getSummary().length() > 150 )
			throw new FormException("Le résumé doit contenir entre 20 et 150 caractères.");		
	}
	
	private void validateContent() throws FormException {
		// Test not null
		if ( site.getContent() == null ) throw new FormException("Saisissez un contenu.");
		// Test contains more than 4 chars (word, " " or - are accepted
		if ( site.getContent().length() < 20 )
			throw new FormException("Le contenu doit contenir au minimum 20 caractère.");		
	}
 
	/**
	 * @return the errors
	 */
	public Map<String, String> getErrors() {
		return errors;
	}

	/**
	 * @return the site
	 */
	public Site getSite() {
		return site;
	}
	
	

}
