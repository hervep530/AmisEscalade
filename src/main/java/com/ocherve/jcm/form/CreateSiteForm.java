package com.ocherve.jcm.form;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import com.ocherve.jcm.model.User;
import com.ocherve.jcm.dao.DaoProxy;
import com.ocherve.jcm.dao.contract.SiteDao;
import com.ocherve.jcm.model.Site;

/**
 * @author herve_dev
 *
 */
public class CreateSiteForm {
	
    private static final Logger DLOG = LogManager.getLogger("development_file");
    private static final Level DLOGLEVEL = Level.TRACE;
	
	private SiteDao siteDao;
	private Site site;
	private Map<String,String> errors;

	
	/**
	 * Constructor without arguments
	 */
	public CreateSiteForm() {
	}
	
	/**
	 * Constructor using request to instanciate class
	 * 
	 * @param request
	 */
	public CreateSiteForm(HttpServletRequest request) {
		Configurator.setLevel(DLOG.getName(), DLOGLEVEL);
		siteDao = (SiteDao) DaoProxy.getInstance().getSiteDao();
		errors = new HashMap<>();
		
		site = new Site();
		try {
			site.setAuthor((User) request.getSession().getAttribute("sessionUser"));
			site.setName(request.getParameter("name"));
			site.setCountry(request.getParameter("country"));
			site.setDepartment(request.getParameter("department"));
			site.setPathsNumber(Integer.valueOf(request.getParameter("pathsNumber")));
			site.setOrientation(request.getParameter("orientation"));
			site.setBlock(Boolean.valueOf(request.getParameter("block")));
			site.setCliff(Boolean.valueOf(request.getParameter("cliff")));
			site.setWall(Boolean.valueOf(request.getParameter("wall")));
			if ( request.getParameter("minHeight") != null ) 
				site.setMinHeight(Integer.valueOf(request.getParameter("minHeight")));
			if ( request.getParameter("maxHeight") != null ) 
				site.setMaxHeight(Integer.valueOf(request.getParameter("maxHeight")));
			if ( request.getParameter("cotationMin") != null ) 
				site.setCotationMin(siteDao.getCotation(Integer.valueOf(request.getParameter("cotationMin"))));
			if ( request.getParameter("cotationMax") != null ) 
				site.setCotationMax(siteDao.getCotation(Integer.valueOf(request.getParameter("cotationMax"))));
			if ( request.getParameter("summary") != null ) site.setSummary(request.getParameter("summary"));
			if ( request.getParameter("content") != null ) site.setContent(request.getParameter("content"));
			site.setPublished(true);
			site.setFriendTag(false);
			site.setType("SITE");
			site.setTsCreated(Timestamp.from(Instant.now()));
			site.setTsModified(Timestamp.from(Instant.now()));
		} catch (Exception e) {
			DLOG.log(Level.ERROR, "Site can not be instanciated from Formular");
			DLOG.log(Level.ERROR, e.getMessage());
		}
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
		try { validateMinHeight() ; } catch (FormException e) { errors.put("minHeight", e.getMessage()); }
		try { validateMaxHeight() ; } catch (FormException e) { errors.put("maxHeight", e.getMessage()); }
		try { validateCotationMin() ; } catch (FormException e) { errors.put("cotationMin", e.getMessage()); }
		try { validateCotationMax() ; } catch (FormException e) { errors.put("cotationMax", e.getMessage()); }
		if ( ! errors.isEmpty() ) return site;
		try { siteDao.create(site); } catch (Exception e) {
			DLOG.log(Level.ERROR, "SiteDao : creating site failed");
			errors.put("creationSite","La création du site a échoué.");
		}
		return site;
	}
	
	private void validateName() throws FormException {
		return;
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


	private void validateMinHeight() throws FormException {
		if ( site.getMinHeight() == null ) throw new FormException("La hauteur de voie minimum n'est pas valide.");
		if ( site.getMinHeight() <= 0  || site.getMinHeight() > 1000)
			throw new FormException("La hauteur de voie minimum (en mètres) doit être comprise entre 1 et 1000.");
	}


	private void validateMaxHeight() throws FormException {
		if ( site.getMaxHeight() == null ) throw new FormException("La hauteur de voie maximum n'est pas valide.");
		if ( site.getMaxHeight() < 10  || site.getMaxHeight() > 3000)
			throw new FormException("La hauteur de voie maximum (en mètres) doit être comprise entre 10 et 3000.");
		if ( site.getMinHeight() > site.getMaxHeight() )
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
		if ( site.getCotationMin().getId() > site.getCotationMax().getId() )
			throw new FormException("La cotation maximum doit être supérieure à la cotation minimum.");
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
