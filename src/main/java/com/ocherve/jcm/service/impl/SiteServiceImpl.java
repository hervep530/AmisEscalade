package com.ocherve.jcm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Level;

import com.ocherve.jcm.dao.DaoProxy;
import com.ocherve.jcm.dao.contract.SiteDao;
import com.ocherve.jcm.form.CommentForm;
import com.ocherve.jcm.form.CreateSiteForm;
import com.ocherve.jcm.form.SearchForm;
import com.ocherve.jcm.model.Comment;
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
			{"c","/site/c/$id/$slug"},
			{"u","/site/u/$id/$slug"},
			{"uac","/site/uac/$id/$slug"},
			{"umc","/site/umc/$id/$slug"},
			{"ut","/site/ut/$id/$slug"},
			{"utt","/site/utt/$id/$slug"},
			{"utf","/site/utf/$id/$slug"},
			{"upt","/site/upt/$id/$slug"},
			{"upf","/site/upf/$id/$slug"},
			{"d","/site/d/$id/$slug"}
	};
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
				case  "uac" :
					parameters.setForm(new CommentForm(request));
					break;
			}
		}
		return parameters;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Delivry getList(Parameters parameters) {
		this.delivry = new Delivry();
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

		// Appending data to result and return it
		this.delivry.appendattribute("sites", sites);
		this.delivry.appendattribute("listsCount", listsCount);
		this.appendMandatoryAttributesToDelivry(parameters);

		return this.delivry;
	}

	@Override
	public Delivry getSite(Parameters parameters) {
		this.delivry = new Delivry();
		Site site = null;
		
		// Getting site from dao
		try {
			site = siteDao.get(Integer.valueOf(parameters.getParsedUrl().getId()));
		} catch (Exception e ) {
			throw new UrlException("Echec de la requete sur la base");
		}
		
		// Testing if url (slug), and site is valid
		if (site == null) throw new UrlException("Aucun site trouvé avec cet id.");
		if ( site.getSlug() == null ) throw new UrlException("Ce site n'a pas de slug associé");
		if ( ! site.getSlug().contentEquals(parameters.getParsedUrl().getSlug()) )
			throw new UrlException("L'id et le slug fourni par l'url ne correspondent pas.");
		
		// Appending data to delivry and return it
		this.delivry.appendattribute("site", site);
		this.appendMandatoryAttributesToDelivry(parameters);

		return this.delivry;
	}

	@Override
	public Delivry getCreateForm(Parameters parameters) {
		return getSiteForm(parameters);
	}
	
	@Override
	public Delivry getUpdateForm(Parameters parameters) {
		this.delivry = new Delivry();
		this.appendMandatoryAttributesToDelivry(parameters);
		return this.delivry;
	}

	@Override
	public Delivry getFindForm(Parameters parameters) {
		return getSiteForm(parameters);
	}

	private Delivry getSiteForm(Parameters parameters) {
		this.delivry = new Delivry();
		List<Cotation> cotations = null;
		
		// Getting cotation (needed by form), and append it to delivry
		cotations = siteDao.getCotations();
		try {
			this.delivry.appendattribute("cotations", cotations);
		} catch (Exception e ) {
			this.delivry.appendError("Site search", "Error on displaying search site formular.");
			DLOG.log(Level.ERROR, "Error on displaying search site formular Cotation references can not be reached.");
		}
		
		// Finalize delivry and return it
		this.appendMandatoryAttributesToDelivry(parameters);
		return this.delivry;
	}


	
	@Override
	public Delivry putFriendTag(Parameters parameters) {
		// Initializing variables
		this.delivry = new Delivry();
		Map<String,Object> fields = new HashMap<>();
		Boolean friendTag = false;
		String notificationLabel = "Tag ami";
		Site updatedSite = null;

		// Updating Site with dao
		try {
			// set flag regarding url and call UserDao to update tag
			if ( parameters.getParsedUrl().getAction().contentEquals("utt") ) friendTag = true;
			fields.put("friendTag", friendTag);
			updatedSite = siteDao.update(Integer.valueOf(parameters.getParsedUrl().getId()), fields);			
		} catch (Exception e) {
			// Append deferred error notification and redirect on sites list
			String message = "Echec lors de l'attribution du tag ami";
			Notification notification = new Notification(NotificationType.ERROR, message);
			this.delivry.appendSessionNotification(notificationLabel, notification);
			this.delivry.appendattribute("redirect", parameters.getContextPath() + "site/l/1");
			return this.delivry;		
		}
		
		// append site in delivry
		this.delivry.appendattribute("site", updatedSite);
		// append deferred notification in delivry
		String message = "Vous avez attribué le tag Ami à ce site.";
		if ( ! friendTag ) message = "Vous avez retiré le tag Ami à ce site";
		Notification notification = new Notification(NotificationType.SUCCESS, message);
		this.delivry.appendSessionNotification(notificationLabel, notification);
		// Set Redirection
		this.delivry.appendattribute("redirect", parameters.getContextPath() + "/site/r/" + updatedSite.getId() +
				"/" + updatedSite.getSlug());
		// Finalizing delivry and return it
		this.appendMandatoryAttributesToDelivry(parameters);
		return this.delivry;
	}
	
	@Override
	public Delivry putPublishedStatus(Parameters parameters) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	@Override
	public Delivry delete(Parameters parameters) {
		// TODO Auto-generated method stub
		return null;
	}


	
	@Override
	public Delivry postCreateForm(Parameters parameters) {
		this.delivry = new Delivry();

		//Getting form and calling createSite
		CreateSiteForm createSiteForm = (CreateSiteForm) parameters.getForm();
		@SuppressWarnings("unused")
		Site createSite = createSiteForm.createSite();
		// If errors we set result values and return it
		if ( ! createSiteForm.getErrors().isEmpty() ) {
			// If errors return form and cotations do display form containing data and errors
			this.delivry.appendattribute("createSiteForm", createSiteForm);
			this.delivry.appendattribute("cotations", siteDao.getCotations());
			this.appendMandatoryAttributesToDelivry(parameters);
			DLOG.log(Level.TRACE, "CreateSiteFom - Errors on createSite() : delivry return form, cotations and parameters.");
			return this.delivry;
		} 

		// Appending deferred notification
		String notificationLabel = "Ajout d'un nouveau site";
		String message = "Le site vient d'être créé avec succès.";
		Notification notification = new Notification(NotificationType.SUCCESS, message);
		this.delivry.appendSessionNotification(notificationLabel, notification);
		// Appending redirection and finalizing delivry
		this.delivry.appendattribute("redirect", parameters.getContextPath() + "/site/l/1");
		this.appendMandatoryAttributesToDelivry(parameters);
		
		return this.delivry;
	}

	@Override
	public Delivry postUpdateForm(Parameters parameters) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Delivry postFindForm(Parameters parameters) {
		// variables
		this.delivry = new Delivry();
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
		
		// Appending data to delivry and return it
		this.delivry.appendattribute("sites", sites);
		this.delivry.appendattribute("query", form.getQuery());
		this.appendMandatoryAttributesToDelivry(parameters);

		return this.delivry;
	}

	@Override
	public Delivry postAddCommentForm(Parameters parameters) {
		this.delivry = new Delivry();
		CommentForm commentForm = (CommentForm) parameters.getForm();
		Comment comment = commentForm.createComment();
		String redirection = parameters.getContextPath();
		// If errors we set result values and return it
		if ( ! commentForm.getErrors().isEmpty() ) {
			DLOG.log(Level.ERROR, commentForm.getErrors().keySet().toString());
			if ( commentForm.getErrors().containsKey("internal")) {
				// Internal error - we redirect with notification
				Notification notification = new Notification(NotificationType.ERROR, 
					"Une erreur interne s'est produite. Le commentaire n'a pas pu être créé.");
				this.delivry.appendSessionNotification("Nouveau commentaire", notification);
				redirection += "/site/l/1";
				this.delivry.appendattribute("redirect", redirection);
			} else {
				// content error - we forward error toward formular inside site content
				Notification notification = new Notification(NotificationType.ERROR, 
						"Le commentaire n'a pas pu être créé car le contenu n'est pas valide.");
				this.delivry.appendNotification("Nouveau commentaire", notification);
				this.delivry.appendattribute("commentForm", commentForm);
				this.delivry.appendattribute("site", (Site) commentForm.getReference());
			}
			this.appendMandatoryAttributesToDelivry(parameters);
			return this.delivry;				
		} 
		// Else we set redirection and notification(s) to display after redirection
		Notification notification = new Notification(NotificationType.SUCCESS, "Votre commentaire est ajouté.");
		this.delivry.appendSessionNotification("Nouveau commentaire", notification);
		redirection += "/site/r/" + comment.getReference().getId() + "/" + comment.getReference().getSlug();
		this.delivry.appendattribute("redirect", redirection);
		DLOG.log(Level.DEBUG, this.delivry.getAttribute("redirect").toString());
		
		// Finalize delivry and return it
		this.appendMandatoryAttributesToDelivry(parameters);
		return this.delivry;
	}

	/**
	 * Display site in log for debug
	 * 
	 * @param site
	 */
	@SuppressWarnings("unused")
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
