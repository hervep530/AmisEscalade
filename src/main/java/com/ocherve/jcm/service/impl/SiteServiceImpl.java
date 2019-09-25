package com.ocherve.jcm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Level;

import com.ocherve.jcm.dao.DaoProxy;
import com.ocherve.jcm.dao.contract.SiteDao;
import com.ocherve.jcm.form.CreateSiteForm;
import com.ocherve.jcm.form.SearchForm;
import com.ocherve.jcm.model.Cotation;
import com.ocherve.jcm.model.Site;
import com.ocherve.jcm.service.Delivry;
import com.ocherve.jcm.service.Notification;
import com.ocherve.jcm.service.NotificationType;
import com.ocherve.jcm.service.Parameters;
import com.ocherve.jcm.service.UrlException;
import com.ocherve.jcm.service.factory.SiteService;

/**
 * Implementing Service for Site component
 * 
 * @author herve_dev
 *
 */
public class SiteServiceImpl extends ServiceImpl implements SiteService {
	
	
	protected final static String SVC_DEFAULT_URL = "";
	protected final static String[][] SVC_ACTIONS = {
			{"l","/site/l/$id"},
			{"f","/site/f"},
			{"r","/site/r/$id/$slug"},
			{"c","/site/c"},
			{"u","/site/u/$id"},
			{"uac","/site/uac"},
			{"umc","/site/umc"},
			{"ut","/site/ut"},
			{"utt","/site/utt/$id"},
			{"utf","/site/utf/$id"},
			{"upt","/site/upt/$id"},
			{"upf","/site/upf/$id"},
			{"d","/site/d"}
	};
	protected final static long LIST_LIMIT = 3;
	private SiteDao siteDao;
	
	/**
	 * Constructor 
	 */
	public SiteServiceImpl() {
		super(SVC_DEFAULT_URL);
		siteDao = SiteDao.class.cast(DaoProxy.getInstance().getSiteDao());
	}
	
	public Parameters setParameters(HttpServletRequest request) {
		Parameters parameters = super.setParameters(request);

		if ( request.getMethod().contentEquals("POST") ) {
			switch (parameters.getParsedUrl().getAction()) {
				case  "f" :
					parameters.setForm(new SearchForm(request));
					break;
				case  "c" :
					parameters.setForm(new CreateSiteForm(request));
					break;
			}
		}
		return parameters;
	}
	
	@Override
	public Delivry doGetAction(Parameters parameters) {
		Delivry delivry = null;
		try {
			switch (parameters.getParsedUrl().getAction()) {
				case "l" :
					delivry = getList(parameters);
					break;
				case "f" :
					Delivry resultGetFind = getFindForm(parameters);
					if ( resultGetFind != null ) delivry = resultGetFind;
					break;
				case "r" :
					delivry = getSite(parameters);
					break;
				case "c" :
					Delivry resultGetCreate = getFindForm(parameters);
					if ( resultGetCreate != null ) delivry = resultGetCreate;
					break;
				case "u" :
					break;
				case "uac" :
					break;
				case "umc" :
					break;
				case "ut" :
					break;
				case "utt" :
					break;
				case "utf" :
					break;
				case "upt" : 
					break;
				case "upf" :
					break;
				case "d" :
					break;
				default :
			}			
		} catch (UrlException e ) {
			DLOG.log(Level.ERROR , e.getMessage());
			delivry = new Delivry();
			delivry.appendError(serviceName + "_" + parameters.getParsedUrl().getAction(), e.getMessage());
		}
		if ( delivry == null ) delivry = new Delivry();
		delivry.setParameters(parameters);
		if ( ! parameters.getNotifications().isEmpty() ) delivry.appendNotifications(parameters.getNotifications());
		if ( ! parameters.getErrors().isEmpty() ) delivry.setErrors(parameters.getErrors());
		String info = "Service " + this.serviceName + " do GetAction.";
		DLOG.log(Level.DEBUG , info);
		return delivry;
	}

	@Override
	public Delivry doPostAction(Parameters parameters) {
		Delivry delivry = null;
		try {
			switch (parameters.getParsedUrl().getAction()) {
				case "f" :
					if ( getFindForm(parameters) != null ) delivry = postFindForm(parameters);
					break;
				case "c" :
					if ( getCreateForm(parameters) != null ) delivry = postCreateForm(parameters);
					break;
				default :
			}			
		} catch (UrlException e ) {
			DLOG.log(Level.ERROR , e.getMessage());
			delivry = new Delivry();
			delivry.appendError(serviceName + "_" + parameters.getParsedUrl().getAction(), e.getMessage());
		}
		if ( delivry == null ) delivry = new Delivry();
		delivry.setParameters(parameters);
		if ( ! parameters.getNotifications().isEmpty() ) delivry.appendNotifications(parameters.getNotifications());
		if ( ! parameters.getErrors().isEmpty() ) delivry.setErrors(parameters.getErrors());
		String info = "Service " + this.serviceName + " do PostAction.";
		DLOG.log(Level.DEBUG , info);
		return delivry;
	}


	@SuppressWarnings("unchecked")
	@Override
	public Delivry getList(Parameters parameters) {
		Delivry result = new Delivry();
		List<Site> sites = null;
		long sitesCount = 0;
		long listsCount = 0;
		// Calculating the count of sites lists in database, regarding the limit LIST_LIMIT to display a list
		try {
			sitesCount = siteDao.getCountFromNamedQuery(Site.class, "Site.countAll", null);
		} catch (Exception e ) {
			throw new UrlException("Echec de la requete Site.countAll");
		}
		if ( LIST_LIMIT > 0 ) {
			listsCount = sitesCount / LIST_LIMIT ;
			if ( sitesCount % LIST_LIMIT > 0 ) listsCount = Math.round(listsCount) + 1;
		}
		// Calculating offset to extract a sublist matching with id from all sites lists regarding given list id
		long listId = 1;
		try {
			if ( ! parameters.getParsedUrl().getId().isEmpty() && Integer.valueOf(parameters.getParsedUrl().getId()) > 0 ) 
				listId = Long.valueOf(parameters.getParsedUrl().getId());
		} catch(Exception e) { /* this value is already checked with UrlChecker */ }
		long offset = (listId -1) * LIST_LIMIT;
		// Setting query name and parameters
		String queryName = "Site.findAllOrderById";
		Map<String,Object> queryParameters = new HashMap<>();
		queryParameters.put("limit", LIST_LIMIT);
		queryParameters.put("offset", offset);
		// Getting query result
		try {
			sites = (List<Site>) siteDao.getListFromNamedQuery(Site.class, queryName, queryParameters);
		} catch (Exception e ) {
			throw new UrlException("Echec de la requete " + queryName);
		}
		if (sites == null) throw new UrlException("Aucun resultat pour la requete " + queryName);
		// debug
		if ( sites.isEmpty() ) throw new UrlException("Aucun resultat pour la requete " + queryName);
		// Appending data to result and return it
		result.appendattribute("sites", sites);
		result.appendattribute("listsCount", listsCount);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Delivry postFindForm(Parameters parameters) {
		// variables
		Delivry result = new Delivry();
		List<Site> sites = null;
		String queryString = "";
		Map<String,Object> queryParameters = null;
		SearchForm form = (SearchForm) parameters.getForm();

		try {
			// Get queryString and queryParameters from form
			form.Search();
			queryString = form.getQuery();
			queryParameters = form.getQueryParameters();			
		} catch (Exception e) {
			DLOG.log(Level.ERROR, e.getMessage());
		}
		DLOG.log(Level.DEBUG, "Search Query : " + queryString);

		// Getting query result
		try {
			sites = (List<Site>) siteDao.getListFromFilteredQuery(Site.class, queryString, queryParameters);
		} catch (Exception e ) {
			DLOG.log(Level.ERROR, "Search Query : " + queryString);
			throw new UrlException("Echec de la requete de recherche");
		}
		/*
		if (sites == null) throw new UrlException("Aucun resultat pour la requete");
		if ( sites.isEmpty() ) throw new UrlException("Aucun resultat pour la requete");
		*/
		
		// Appending data to result and return it
		result.appendattribute("sites", sites);
		result.appendattribute("query", form.getQuery());

		return result;
	}

	@Override
	public Delivry getFindForm(Parameters parameters) {
		return getSiteForm(parameters);
	}

	private Delivry getSiteForm(Parameters parameters) {
		Delivry result = new Delivry();
		List<Cotation> cotations = null;
		cotations = siteDao.getCotations();
		try {
			result.appendattribute("cotations", cotations);
		} catch (Exception e ) {
			result.appendError("Site search", "Error on displaying search site formular.");
			DLOG.log(Level.ERROR, "Error on displaying search site formular Cotation references can not be reached.");
		}
		return result;
	}

	@Override
	public Delivry getSite(Parameters parameters) {
		Delivry result = new Delivry();
		Site site = null;
		try {
			site = siteDao.get(Integer.valueOf(parameters.getParsedUrl().getId()));
		} catch (Exception e ) {
			throw new UrlException("Echec de la requete sur la base");
		}
		if (site == null) throw new UrlException("Aucun site trouvé avec cet id.");
		debugSite(site);
		if ( site.getSlug() == null ) throw new UrlException("Ce site n'a pas de slug associé");
		if ( ! site.getSlug().contentEquals(parameters.getParsedUrl().getSlug()) )
			throw new UrlException("L'id et le slug fourni par l'url ne correspondent pas.");
		result.appendattribute("site", site);
		return result;
	}

	@Override
	public Delivry getCreateForm(Parameters parameters) {
		return getSiteForm(parameters);
	}

	@Override
	public Delivry postCreateForm(Parameters parameters) {
		Delivry result = new Delivry();
		CreateSiteForm createSiteForm = (CreateSiteForm) parameters.getForm();
		Site createSite = createSiteForm.createSite();
		// If errors we set result values and return it
		if ( ! createSiteForm.getErrors().isEmpty() ) {
			result.appendattribute("createSiteForm", createSiteForm);
			result.appendattribute("cotations", this.getSiteForm(parameters).getAttribute("cotations"));
			return result;
		} 
		// Else we set redirection and notification(s) to display after redirection
		String notificationLabel = "Ajout d'un nouveau site";
		String message = "Le site vient d'être créé avec succès.";
		Notification notification = new Notification(NotificationType.SUCCESS, message);
		Map<String,Notification> notifications = new HashMap<>();
		notifications.put(notificationLabel, notification);
		result.appendSession("notifications", notifications);
		/*result.appendattribute("redirect", parameters.getContextPath() + "/site/r/" +
				createSite.getId() + "/" + createSite.getSlug());*/
		result.appendattribute("redirect", parameters.getContextPath() + "/site/l/1");
		return result;
	}

	@Override
	public Delivry getUpdateForm(Parameters parameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Delivry postUpdateForm(Parameters parameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Delivry postAddComment(Parameters parameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Delivry postUpdateComment(Parameters parameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Delivry getUpdatePublishedStatus(Parameters parameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Delivry getUpdateTag(Parameters parameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Delivry getDelete(Parameters parameters) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Display site in log for debug
	 * 
	 * @param site
	 */
	private static void debugSite(Site site) {
		String message = "%n";
		message += "Site id : " + site.getType().toString() + " " + site.getId() + "%n";
		message += "Site name : " + site.getName() + "%n";
		message += "Site slug : " + site.getSlug() + "%n";
		message += "Departement : " + site.getDepartment() + "%n";
		message += "Pays : " + site.getCountry() + "%n";
		message += "Bloc : " + String.valueOf(site.isBlock()) + "%n";
		message += "Falaise : " + String.valueOf(site.isCliff()) + "%n";
		message += "Mur : " + String.valueOf(site.isWall()) + "%n";
		message += "Orientation : " + site.getOrientation() + "%n";
		message += "Hauteur mini : " + site.getMinHeight() + "%n";
		message += "Hauteur maxi : " + site.getMaxHeight() + "%n";
		message += "Nombre de voies : " + site.getPathsNumber() + "%n";
		message += "Cotation mini : " + site.getCotationMin().getLabel() + "%n";
		message += "Cotation maxi : " + site.getCotationMax().getLabel() + "%n";
		String content = "";
		if ( site.getContent() != null ) {
			content = site.getContent();
			if ( content.length() > 71) content = content.substring(0, 70);
		}
		message += "Content : " + content + "...%n";
		message += "Auteur : " + site.getAuthor().getUsername() + "%n";
		message += "Créé le : " + site.getTsCreated().toString() + "%n";
		message += "Modifié le : " + site.getTsModified().toString() + "%n";
		message += "Tagué ami : " + String.valueOf(site.isFriendTag()) + "%n";
		message += "Published : " + String.valueOf(site.isPublished()) + "%n";
		DLOG.log(Level.DEBUG, String.format(message));
	}

}
