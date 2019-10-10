package com.ocherve.jcm.form;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.Normalizer;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.logging.log4j.Level;

import com.ocherve.jcm.dao.DaoProxy;
import com.ocherve.jcm.dao.contract.SiteDao;
import com.ocherve.jcm.dao.contract.TopoDao;
import com.ocherve.jcm.model.Site;
import com.ocherve.jcm.model.Topo;
import com.ocherve.jcm.model.User;

/**
 * @author herve_dev
 *
 */
public class TopoForm extends Form {
	
	private TopoDao topoDao;
	private SiteDao siteDao;
	private Topo topo;
	private String slug;
	private boolean updatingName = false;
	private Part uploadFile;
	private Map<String,String> selectedIds;

	
	/**
	 * Constructor without arguments
	 */
	public TopoForm() {
		super();
	}
	
	/**
	 * Constructor using request to instanciate class
	 * 
	 * @param request
	 */
	public TopoForm(HttpServletRequest request) {
		super();
		this.request = request;
		this.selectedIds = new HashMap<>();
		this.slug = "";
		
		this.topoDao = (TopoDao) DaoProxy.getInstance().getTopoDao();
		this.siteDao = (SiteDao) DaoProxy.getInstance().getSiteDao();
		// creating a null cotation in order to return it to jsp when formular is wrong
		
		
		try {
			// Hidden field createSiteControl tell us if it's possible to use getParameter with multipart or not
			if ( this.request.getParameter("partMethod") == null ) this.partMethod = true;
			// instanciating topo or getting it for update
			Integer topoId = 0;
			if ( getInputTextValue("topoId").matches("^[0-9]{1,}$") ) 
				topoId = Integer.valueOf(getInputTextValue("topoId"));
			if ( topoId > 0 )
				this.topo = topoDao.get(topoId);
			else	
				this.topo = new Topo();
			// Setting author from session
			if ( topoId == 0 ) this.topo.setAuthor((User) request.getSession().getAttribute("sessionUser"));
			// Getting field necessary to build site object
			this.topo.setTitle(getInputTextValue("title"));
			if ( topoId > 0 ) this.slug = this.topo.getSlug();
			if ( ! topo.getName().contentEquals(topo.getTitle()) ) this.updatingName = true;
			this.topo.setName(getInputTextValue("title"));
			this.topo.setWriter(getInputTextValue("writer"));
			this.topo.setWritedAt(getInputTextValue("writedAt"));
			this.selectedIds = getMultiSelectValues("sites");
			// Upload file
			@SuppressWarnings("unused")
			String fileDescription = getInputTextValue("description");  // not use - just to keep it in mind
			this.uploadFile = getRawPart("uploadFile");
			// summary and content
			this.topo.setSummary(getInputTextValue("summary"));
			this.topo.setContent(getInputTextValue("content"));
			// Giving default value for other site attributes
			if ( topoId == 0 ) this.topo.setPublished(true);
			if ( topoId == 0 ) this.topo.setType("TOPO");
			if ( topoId == 0 ) this.topo.setTsCreated(Timestamp.from(Instant.now()));
			this.topo.setTsModified(Timestamp.from(Instant.now()));
		} catch (Exception e) {
			DLOG.log(Level.ERROR, "Site can not be instanciated from Formular");
			DLOG.log(Level.ERROR, e.getMessage());
		}
		this.request = null;
	}
	
	/**
	 * Constructor using request to instanciate class
	 * 
	 * @param topoId
	 */
	public TopoForm(Integer topoId) {
		super();
		if (topoId == null) {
			DLOG.log(Level.ERROR, "Topo form can not be instanciated - topoId is null.");
			return;
		}
		this.selectedIds = new HashMap<>();
		this.topoDao = (TopoDao) DaoProxy.getInstance().getTopoDao();
		this.siteDao = (SiteDao) DaoProxy.getInstance().getSiteDao();
		// Getting topo from id, then setting Topo form and selectedId
		try {
			this.topo = topoDao.get(topoId);
			this.slug = topo.getSlug();
			if ( this.topo.getSites() != null ) {
				for (Site site : this.topo.getSites()) {
					this.selectedIds.put(String.valueOf(site.getId()), " selected");
				}				
			}
		} catch (Exception e) {
			DLOG.log(Level.ERROR, "Topo form can not be instanciated - topoId : " + topoId);
			DLOG.log(Level.ERROR, e.getMessage());
		}
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
		try { validateFile() ; } catch (FormException e) { this.errors.put("file", e.getMessage()); }
		try { validateSummary() ; } catch (FormException e) { this.errors.put("summary", e.getMessage()); }
		try { validateContent() ; } catch (FormException e) { this.errors.put("content", e.getMessage()); }
		if ( ! this.errors.isEmpty() ) return topo;
		try { validateSites() ; } catch (FormException e) { this.errors.put("sites", e.getMessage()); }
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

	/**
	 * @return site instanciate from formular
	 */
	public Topo updateTopo() {
		//try { validateName(); } catch (FormException e ) { this.errors.put("name",e.getMessage()); }
		try { validateTitle() ; } catch (FormException e) { this.errors.put("title", e.getMessage()); }
		try { validateWriter() ; } catch (FormException e) { this.errors.put("writer", e.getMessage()); }
		try { validateWritedAt() ; } catch (FormException e) { this.errors.put("writedAt", e.getMessage()); }
		//try { validateFile() ; } catch (FormException e) { this.errors.put("file", e.getMessage()); }
		try { validateSummary() ; } catch (FormException e) { this.errors.put("summary", e.getMessage()); }
		try { validateContent() ; } catch (FormException e) { this.errors.put("content", e.getMessage()); }
		if ( ! this.errors.isEmpty() ) return topo;
		try { validateSites() ; } catch (FormException e) { this.errors.put("sites", e.getMessage()); }
		if ( ! this.errors.isEmpty() ) return topo;
		Integer controlId = 0;
		try { 
			controlId = this.topoDao.update(this.topo).getId();
			//if ( controlId > 0 ) this.topoDao.refresh(Topo.class, topoId);
		} catch (Exception e) {
			DLOG.log(Level.ERROR, "SiteDao : creating site failed");
			this.errors.put("creationSite","La création du site a échoué.");
		}
		try {
			if ( controlId < 1 ) removeFile();
		} catch (FormException e) {
			this.errors.put("file", e.getMessage());
		}
		return this.topo;
	}

	/**
	 * Validate topo name
	 * 
	 * @throws FormException
	 */
	private void validateName() throws FormException {
		// Test not null
		if ( this.topo.getName() == null ) throw new FormException("Ce nom de topo n'est pas valide.");
		// Test contains more than 4 chars (word, " " or - are accepted
		if ( ! this.topo.getName().matches("\\w[- \\w]{2,}\\w") )
			throw new FormException("Ce nom de topo n'est pas valide.");
		// Test slug is unique, so name is unique
		DLOG.log(Level.DEBUG, "Slug form / topo : " + this.slug + " / " + this.topo.getSlug());
		if ( ! this.slug.contentEquals(this.topo.getSlug()) ) {
			// Request user id from database with mail address as filter (one Integer result expected... Else rejecting)
			this.slug = Normalizer.normalize(topo.getName(), Normalizer.Form.NFD).replaceAll("[\u0300-\u036F]", "");
			this.slug = this.slug.replaceAll("\\W", "_").replaceAll("_{1,}","_").toLowerCase();
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
	}
		
	/**
	 * Validate topo title
	 * 
	 * @throws FormException
	 */
	private void validateTitle() throws FormException {
		if ( this.topo.getTitle() == null ) throw new FormException("Le titre du topo est invalide.");
		if ( ! this.topo.getTitle().matches("[- \\w]{3,}") )
			throw new FormException("Ce titre de topo n'est pas valide.");
		// Test not null
		if ( this.topo.getName() == null ) throw new FormException("Ce nom de topo n'est pas valide.");
		// Test contains more than 4 chars (word, " " or - are accepted
		if ( ! this.topo.getName().matches("\\w[- \\w]{2,}\\w") )
			throw new FormException("Ce nom de topo n'est pas valide.");
		// Test slug is unique, so name is unique
		DLOG.log(Level.DEBUG, "Slug form / topo : " + this.slug + " / " + this.topo.getSlug());
		if ( ! this.slug.contentEquals(this.topo.getSlug()) ) {
			// Request user id from database with mail address as filter (one Integer result expected... Else rejecting)
			this.slug = Normalizer.normalize(topo.getName(), Normalizer.Form.NFD).replaceAll("[\u0300-\u036F]", "");
			this.slug = this.slug.replaceAll("\\W", "_").replaceAll("_{1,}","_").toLowerCase();
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
	}

	/**
	 * Validate topo Writer
	 * 
	 * @throws FormException
	 */
	private void validateWriter() throws FormException {
		if ( this.topo.getWriter() == null ) throw new FormException("Le nom d'auteur n'est pas valide.");
		if ( ! this.topo.getWriter().matches("[- ()\\w]{3,}") )
			throw new FormException("Le nom d'auteur n'est pas valide (3 caractères minimum.");
	}

	/**
	 * Validate topo writing date
	 * 
	 * @throws FormException
	 */
	private void validateWritedAt() throws FormException {
		if ( this.topo.getWritedAt() == null ) throw new FormException("La saisie de la date de publication n'est pas valide.");
		if ( ! this.topo.getWritedAt().matches("[0-9]{1,2}/[0-9]{2}/[0-9]{4}") ) 
			throw new FormException("La date de publication ne respecte pas le format jj/mm/aaaa");
	}
	
	/**
	 * Validate Topo sites list
	 * @throws FormException
	 */
	private void validateSites() throws FormException {
		if ( this.selectedIds == null ) return;
		String key = "";
		try {
			if ( topo.getSites() != null ) {
				// when it's a new topo , getSites will return null, so we check it before
				for ( Site site : topo.getSites() ) {
					// If sites list contains a site which is now not selected we remove it
					boolean removing = ( ! this.selectedIds.containsKey( String.valueOf(site.getId())) );
					if ( removing ) topo.removeSite(site);
					DLOG.log(Level.DEBUG, "Site lié au topo - id :" + site.getId() + " ->  " + String.valueOf(removing));					
				}
			}
			// Adding site in formular to the list attached to the topo
			List<Integer> topoSitesIds = new ArrayList<Integer>();
			for ( Site siteEntry : this.topo.getSites() ) {
				topoSitesIds.add(siteEntry.getId());
			}
			for (Entry<String,String> entry : this.selectedIds.entrySet()) {
				key = entry.getKey();
				boolean adding = ( ! topoSitesIds.contains(Integer.valueOf(entry.getKey())) );
				// if linkedSite (from formular) is not attached to topo, we add it
				if ( adding) {
					Site linkedSite = siteDao.get(Integer.valueOf(entry.getKey()));
					topo.addSite(linkedSite);
				}
				DLOG.log(Level.DEBUG, "Site lié au topo - id :" + entry.getKey() + " ->  " + String.valueOf(adding));
			}			
		} catch (Exception e) {
			DLOG.log(Level.DEBUG, "AddingSite error (id :" + key + ") - " + e.getMessage() );
		}
	}

	/**
	 * Validate Image file
	 * 
	 * @throws FormException
	 */
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

	/**
	 * Remove file (needed if topo creation fails)
	 * 
	 * @throws FormException
	 */
	private void removeFile() throws FormException {
		String filePath = UPLOAD_PATH + "/topo";
		String fileName = this.slug + ".jpg";
		try {
			File file = new File(filePath + "/" + fileName);
			file.delete();
		} catch (Exception ignore) {}
		throw new FormException("La création de site à échoué. Il faut ré-envoyer le fichier.");			
	}

	/**
	 * Validate topo summary
	 * 
	 * @throws FormException
	 */
	private void validateSummary() throws FormException {
		// Test not null
		if ( this.topo.getSummary() == null ) throw new FormException("Saisissez un résumé.");
		// Test contains more than 4 chars (word, " " or - are accepted
		if ( this.topo.getSummary().length() < 20 || topo.getSummary().length() > 150 )
			throw new FormException("Le résumé doit contenir entre 20 et 150 caractères.");		
	}
	
	/**
	 * Validate topo content
	 * 
	 * @throws FormException
	 */
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
	
	/**
	 * Getter for selectedIds Map
	 * 
	 * @return ids in key Map
	 */
	public Map<String,String> getSelectedIds() {
		return this.selectedIds;
	}
	
}
