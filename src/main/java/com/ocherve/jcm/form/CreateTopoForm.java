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

import com.ocherve.jcm.dao.DaoProxy;
import com.ocherve.jcm.dao.contract.TopoDao;
import com.ocherve.jcm.model.Topo;
import com.ocherve.jcm.model.User;

/**
 * @author herve_dev
 *
 */
public class CreateTopoForm extends Form {
	
	private TopoDao topoDao;
	private Topo topo;
	private String slug;
	private Part uploadFile;
	private Integer[] linkedSites;

	
	/**
	 * Constructor without arguments
	 */
	public CreateTopoForm() {
		super();
	}
	
	/**
	 * Constructor using request to instanciate class
	 * 
	 * @param request
	 */
	public CreateTopoForm(HttpServletRequest request) {
		super();
		this.request = request;
		this.slug = "";
		
		this.topoDao = (TopoDao) DaoProxy.getInstance().getTopoDao();
		// creating a null cotation in order to return it to jsp when formular is wrong
		
		this.topo = new Topo();
		
		try {
			// Hidden field createSiteControl tell us if it's possible to use getParameter with multipart or not
			if ( this.request.getParameter("partMethod") == null ) this.partMethod = true;
			// Setting author from session
			this.topo.setAuthor((User) request.getSession().getAttribute("sessionUser"));
			// Getting field necessary to build site object
			this.topo.setTitle(getInputTextValue("title"));
			this.topo.setName(getInputTextValue("title"));
			this.topo.setWriter(getInputTextValue("writer"));
			this.topo.setWritedAt(getInputTextValue("writedAt"));
			//this.linkedSites = getMultiSelectIntegersValue("sites");
			// Upload file
			@SuppressWarnings("unused")
			String fileDescription = getInputTextValue("description");  // not use - just to keep it in mind
			this.uploadFile = getRawPart("uploadFile");
			// summary and content
			this.topo.setSummary(getInputTextValue("summary"));
			this.topo.setContent(getInputTextValue("content"));
			// Giving default value for other site attributes
			this.topo.setPublished(true);
			this.topo.setType("SITE");
			this.topo.setTsCreated(Timestamp.from(Instant.now()));
			this.topo.setTsModified(Timestamp.from(Instant.now()));
		} catch (Exception e) {
			DLOG.log(Level.ERROR, "Site can not be instanciated from Formular");
			DLOG.log(Level.ERROR, e.getMessage());
		}
		this.request = null;
	}
	
	/**
	 * @return site instanciate from formular
	 */
	public Topo createTopo() {
		try { validateName(); } catch (FormException e ) { this.errors.put("name",e.getMessage()); }
		DLOG.log(Level.DEBUG, "Author : " + this.topo.getAuthor().getUsername());
		// validate country
		try { validateTitle() ; } catch (FormException e) { this.errors.put("title", e.getMessage()); }
		try { validateWriter() ; } catch (FormException e) { this.errors.put("writer", e.getMessage()); }
		try { validateWritedAt() ; } catch (FormException e) { this.errors.put("writedAt", e.getMessage()); }
		try { validateSites() ; } catch (FormException e) { this.errors.put("cotationMax", e.getMessage()); }
		try { validateFile() ; } catch (FormException e) { this.errors.put("file", e.getMessage()); }
		try { validateSummary() ; } catch (FormException e) { this.errors.put("summary", e.getMessage()); }
		try { validateContent() ; } catch (FormException e) { this.errors.put("content", e.getMessage()); }
		if ( ! this.errors.isEmpty() ) return topo;
		Integer topoId = 0;
		try { 
			topoId = this.topoDao.create(this.topo).getId(); 
			this.topoDao.refresh(Topo.class, topoId);
		} catch (Exception e) {
			DLOG.log(Level.ERROR, "SiteDao : creating site failed");
			this.errors.put("creationSite","La création du site a échoué.");
		}
		try {
			if ( topoId < 1 ) removeFile();
		} catch (FormException e) {
			this.errors.put("file", e.getMessage());
		}
		return this.topo;
	}
	
	private void validateName() throws FormException {
		// Test not null
		if ( this.topo.getName() == null ) throw new FormException("Ce nom de topo n'est pas valide.");
		// Test contains more than 4 chars (word, " " or - are accepted
		if ( ! this.topo.getName().matches("\\w[- \\w]{2,}\\w") )
			throw new FormException("Ce nom de topo n'est pas valide.");
		// Test slug is unique, so name is unique
		this.slug = Normalizer.normalize(topo.getName(), Normalizer.Form.NFD).replaceAll("[\u0300-\u036F]", "");
		this.slug = this.slug.replaceAll("\\W", "_").replaceAll("_{1,}","_").toLowerCase();
		// Request user id from database with mail address as filter (one Integer result expected... Else rejecting)
		Map<String,Object> parameters = new HashMap<>();
		parameters.put("slug", this.slug);
		parameters.put("type", "TOPO");
		int siteId = 0;
		try {
			siteId = this.topoDao.getIdFromNamedQuery("Reference.getIdFromSlug", parameters);
		} catch (Exception e) {/* if it's a dao error it was tracked anyway */}
		DLOG.log(Level.DEBUG , "Id trouvé : " + siteId);
		if ( siteId > 0 )
			throw new FormException("Un site existe déjà avec un nom similaire.");
	}
		
	private void validateTitle() throws FormException {
		if ( this.topo.getTitle() == null ) throw new FormException("Le titre du topo est invalide.");
		if ( ! this.topo.getTitle().matches("[- \\w]{3,}") )
			throw new FormException("Ce titre de topo n'est pas valide.");
	}

	private void validateWriter() throws FormException {
		if ( this.topo.getWriter() == null ) throw new FormException("Le nom d'auteur n'est pas valide.");
		if ( ! this.topo.getWriter().matches("[- ()\\w]{3,}") )
			throw new FormException("Le nom d'auteur n'est pas valide (3 caractères minimum.");
	}

	private void validateWritedAt() throws FormException {
		if ( this.topo.getWritedAt() == null ) throw new FormException("La saisie de la date de publication n'est pas valide.");
		if ( ! this.topo.getWritedAt().matches("[0-9]{1,2}/[0-9]{2}/[0-9]{4}") ) 
			throw new FormException("La date de publication ne respecte pas le format jj/mm/aaaa");
	}

	private void validateSites() throws FormException {
		/*
		for (Integer siteId : this.linkedSites) {
			DLOG.log(Level.DEBUG, "Site lié au topo - id :" + siteId);
		}
		if ( topo.getCotationMax() == null ) throw new FormException("La cotation maximum n'est pas valide.");
		if ( topo.getCotationMax().getId() <= 0 || topo.getCotationMax().getId() > 30)
			throw new FormException("La cotation maximum n'est pas valide.");
		int minCotationId = 0;
		if ( topo.getCotationMin() != null ) minCotationId = topo.getCotationMin().getId();
		if ( minCotationId > topo.getCotationMax().getId() )
			throw new FormException("La cotation maximum doit être supérieure à la cotation minimum.");
		*/
	}

	private void validateFile() throws FormException {
		String rawName = "";
		String rawType = "jpg";
		String filePath = UPLOAD_PATH + "/topo";
		String fileName = this.slug + ".jpg";
		if ( this.slug.isEmpty() ) 
			throw new FormException("Le fichier ne peut pas être sauvagardé car le nom du topo est invalide.");
		try {
	        // On vérifie qu'on a bien reçu un fichier
			rawName = getFileName(uploadFile);
		} catch (IOException e) {
			DLOG.log(Level.ERROR, e.getMessage());
			throw new FormException("Le fichier est invalide.");
		}
		if ( rawName == null ) throw new FormException("Le fichier est invalide.");
		DLOG.log(Level.DEBUG, "Nom de fichier dans le part : " + rawName);
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
		String filePath = UPLOAD_PATH + "/topo";
		String fileName = this.slug + ".jpg";
		try {
			File file = new File(filePath + "/" + fileName);
			file.delete();
		} catch (Exception ignore) {}
		throw new FormException("La création de site à échoué. Il faut ré-envoyer le fichier.");			
	}

	private void validateSummary() throws FormException {
		// Test not null
		if ( this.topo.getSummary() == null ) throw new FormException("Saisissez un résumé.");
		// Test contains more than 4 chars (word, " " or - are accepted
		if ( this.topo.getSummary().length() < 20 || topo.getSummary().length() > 150 )
			throw new FormException("Le résumé doit contenir entre 20 et 150 caractères.");		
	}
	
	private void validateContent() throws FormException {
		// Test not null
		if ( this.topo.getContent() == null ) throw new FormException("Saisissez un contenu.");
		// Test contains more than 4 chars (word, " " or - are accepted
		if ( this.topo.getContent().length() < 20 )
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
	public Topo getTopo() {
		return this.topo;
	}
	
	

}
