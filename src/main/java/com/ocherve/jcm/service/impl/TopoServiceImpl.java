package com.ocherve.jcm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Level;

import com.ocherve.jcm.dao.DaoProxy;
import com.ocherve.jcm.dao.contract.SiteDao;
import com.ocherve.jcm.dao.contract.TopoDao;
import com.ocherve.jcm.form.TopoForm;
import com.ocherve.jcm.model.Site;
import com.ocherve.jcm.model.Topo;
import com.ocherve.jcm.service.Delivry;
import com.ocherve.jcm.service.Notification;
import com.ocherve.jcm.service.NotificationType;
import com.ocherve.jcm.service.Parameters;
import com.ocherve.jcm.service.UrlException;
import com.ocherve.jcm.service.factory.TopoService;

/**
 * TopoService Implementation 
 * 
 * @author herve_dev
 *
 */
public class TopoServiceImpl extends ServiceImpl implements TopoService {

	protected final static String SVC_DEFAULT_URL = "";
	protected final static String[][] SVC_ACTIONS = {
			{"l","/topo/l/$id"},
			{"h","/topo/h/$id/$slug"},
			{"r","/topo/r/$id/$slug"},
			{"c","/topo/c/$id/$slug"},
			{"u","/topo/u/$id/$slug"},
			{"uat","/topo/uat/$id/$slug"},
			{"uaf","/topo/uaf/$id/$slug"},
			{"upt","/topo/upt/$id/$slug"},
			{"upf","/topo/upf/$id/$slug"},
			{"d","/topo/d/$id/$slug"}
	};
	private TopoDao topoDao;
	private SiteDao siteDao;

	/**
	 * Constructor 
	 */
	public TopoServiceImpl() {
		super(SVC_DEFAULT_URL);
		topoDao = (TopoDao) DaoProxy.getInstance().getTopoDao();
		siteDao = (SiteDao) DaoProxy.getInstance().getSiteDao();
	}
	
	public Parameters setParameters(HttpServletRequest request) {
		Parameters parameters = super.setParameters(request);
		// Overloading set parameters with creating and adding new form to parameters
		if ( request.getMethod().contentEquals("POST") ) {
			switch (parameters.getParsedUrl().getAction()) {
				case  "c" :
					parameters.setForm(new TopoForm(request,false));
					break;
				case  "u" :
					parameters.setForm(new TopoForm(request,true));
					break;
			}
		}
		return parameters;
	}


	@SuppressWarnings("unchecked")
	@Override
	public Delivry getPublishedList(Parameters parameters) {
		this.delivry = new Delivry();
		List<Topo> topos = null;
		long toposCount = 0;
		long listsCount = 0;
		Map<String,Object> queryParameters = new HashMap<>();
		queryParameters.put("published", true);
		
		// Calculating the count of sites lists in database, regarding the limit LIST_LIMIT to display a list
		try {
			toposCount = topoDao.getCountFromNamedQuery(Topo.class, "Topo.countByPublishingStatus", queryParameters);
		} catch (Exception e ) {
			//throw new UrlException("Echec de la requete " + queryName);
			DLOG.log(Level.DEBUG, "Error on executing query Topo.countByPublishingStatus");
		}
		if ( LIST_LIMIT > 0 ) {
			listsCount = toposCount / LIST_LIMIT ;
			if ( toposCount % LIST_LIMIT > 0 ) listsCount = Math.round(listsCount) + 1;
		}
		// Calculating offset to extract a sublist matching with id from all sites lists regarding given list id
		long listId = 1;
		try {
			if ( ! parameters.getParsedUrl().getId().isEmpty() && Integer.valueOf(parameters.getParsedUrl().getId()) > 0 ) 
				listId = Long.valueOf(parameters.getParsedUrl().getId());
		} catch(Exception e) { /* this value is already checked with UrlChecker */ }
		long offset = (listId -1) * LIST_LIMIT;
		// Setting query name and parameters
		String queryName = "Topo.findByPublishingStatusOrderByIdDesc";
		queryParameters.put("limit", LIST_LIMIT);
		queryParameters.put("offset", offset);
		// Getting query result
		try {
			topos = (List<Topo>) topoDao.getListFromNamedQuery(Topo.class, queryName, queryParameters);
		} catch (Exception e ) {
			//throw new UrlException("Echec de la requete " + queryName);
			DLOG.log(Level.DEBUG, "Error on executing query Topo.countByPublishingStatus");
		}
		if (topos == null) topos = new ArrayList<Topo>();

		// Appending data to result and return it
		this.delivry.appendattribute("topos", topos);
		this.delivry.appendattribute("title", "Liste des topos");
		this.delivry.appendattribute("listsCount", listsCount);
		this.appendMandatoryAttributesToDelivry(parameters);
		
		return this.delivry;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Delivry getMyList(Parameters parameters) {
		this.delivry = new Delivry();
		List<Topo> topos = null;
		Map<String,Object> queryParameters = new HashMap<>();
		
		// Setting query name and parameters
		String queryName = "Topo.findByAuthor";
		int authorId = Integer.valueOf(parameters.getParsedUrl().getId());
		queryParameters.put("authorId", authorId);
		// Getting query result
		try {
			topos = (List<Topo>) topoDao.getListFromNamedQuery(Topo.class, queryName, queryParameters);
		} catch (Exception e ) {
			//throw new UrlException("Echec de la requete " + queryName);
			DLOG.log(Level.DEBUG, "Error on executing query " + queryName);
		}
		if (topos == null) topos = new ArrayList<Topo>();
		// Appending data to result and return it
		this.delivry.appendattribute("topos", topos);
		this.delivry.appendattribute("title", "Liste de mes topos");
		this.appendMandatoryAttributesToDelivry(parameters);
		
		return this.delivry;
	}

	@Override
	public Delivry getTopo(Parameters parameters) {
		// Initializing variables
		this.delivry = new Delivry();
		Topo topo = null;
		// Getting topo using topoDao
		try {
			topo = topoDao.get(Integer.valueOf(parameters.getParsedUrl().getId()));
		} catch (Exception e ) {
			throw new UrlException("Echec de la requete sur la base");
		}
		// Testing topo - if Ok, append to delivry - else throw UrlException
		if (topo == null) throw new UrlException("Aucun topo trouvé avec cet id.");
		if ( topo.getSlug() == null ) throw new UrlException("Ce site n'a pas de slug associé");
		if ( ! topo.getSlug().contentEquals(parameters.getParsedUrl().getSlug()) )
			throw new UrlException("L'id et le slug fourni par l'url ne correspondent pas.");
		delivry.appendattribute("topo", topo);
		this.delivry.appendattribute("title", "Topo - " + topo.getName());
		this.appendMandatoryAttributesToDelivry(parameters);

		return delivry;
	}

	@Override
	public Delivry getCreateForm(Parameters parameters) {
		this.delivry = new Delivry();
		List<Site> sites = null;
		sites = siteDao.getList();
		try {
			this.delivry.appendattribute("sites", sites);
		} catch (Exception e ) {
			this.delivry.appendError("Creating topo", "Error on displaying topo formular.");
			DLOG.log(Level.ERROR, "Error on displaying topo formular.");
		}
		this.appendMandatoryAttributesToDelivry(parameters);
		this.delivry.appendattribute("topoForm", new TopoForm());
		this.delivry.appendattribute("title", "Création d'un nouveau topo");
		return this.delivry;
	}

	@Override
	public Delivry getUpdateForm(Parameters parameters) {
		this.delivry = new Delivry();
		List<Site> sites = null;
		TopoForm topoForm = null;
		try {
			topoForm = new TopoForm(Integer.valueOf(parameters.getParsedUrl().getId()));
			this.delivry.appendattribute("topoForm", topoForm);
			sites = siteDao.getList();
			this.delivry.appendattribute("title", "Mise à jour du topo - " + topoForm.getTopo().getName());
			this.delivry.appendattribute("sites", sites);
		} catch (Exception e ) {
			this.delivry.appendError("Site search", "Error on displaying search site formular.");
			DLOG.log(Level.ERROR, "Error on displaying search site formular Cotation references can not be reached.");
		}
		this.appendMandatoryAttributesToDelivry(parameters);
		return this.delivry;
	}

	@Override
	public Delivry putAvailability(Parameters parameters) {
		DLOG.log(Level.DEBUG, "Putting availability property...");
		// Initializing variables
		this.delivry = new Delivry();
		Map<String,Object> fields = new HashMap<>();
		Boolean available = true;
		String notificationLabel = "Disponibilite Topo";
		Topo updatedTopo = null;

		// Updating Site with dao
		try {
			// set flag regarding url and call UserDao to update tag
			if ( parameters.getParsedUrl().getAction().contentEquals("uaf") ) available = false;
			fields.put("available", available);
			DLOG.log(Level.DEBUG, "Updating available to " + (available ? "disponible" : "réservé"));
			updatedTopo = topoDao.update(Integer.valueOf(parameters.getParsedUrl().getId()), fields);			
		} catch (Exception e) {
			// Append deferred error notification and redirect on sites list
			String message = "Echec lors de la modification de disponibilité du topo";
			Notification notification = new Notification(NotificationType.ERROR, message);
			this.delivry.appendSessionNotification(notificationLabel, notification);
			this.delivry.appendattribute("redirect", parameters.getContextPath() + "topo/l/1");
			return this.delivry;		
		}
		
		// append site in delivry
		this.delivry.appendattribute("topo", updatedTopo);
		// append deferred notification in delivry
		String message = "Vous avez changé le statut du topo : " + (available ? "disponible" : "réservé") + ".";
		DLOG.log(Level.DEBUG, "Appending deferred notification : " + message);
		Notification notification = new Notification(NotificationType.SUCCESS, message);
		this.delivry.appendSessionNotification(notificationLabel, notification);
		// Set Redirection
		this.delivry.appendattribute("redirect", parameters.getContextPath() + "/topo/r/" + updatedTopo.getId() +
				"/" + updatedTopo.getSlug());
		// Finalizing delivry and return it
		this.appendMandatoryAttributesToDelivry(parameters);
		return this.delivry;
	}

	@Override
	public Delivry putPublishStatus(Parameters parameters) {
		// TODO Auto-generated method stub
		this.delivry = new Delivry();
		this.appendMandatoryAttributesToDelivry(parameters);
		return this.delivry;
	}

	@Override
	public Delivry delete(Parameters parameters) {
		this.delivry = new Delivry();
		Boolean deleted = false;
		String topoName = "";
		// default failure notification
		Notification notification = new Notification(NotificationType.ERROR, 
				"Une erreur interne s'est produite. Le topo n'a pas pu être supprimé.");
		// Trying to delete
		try {
			Integer topoId = 0;
			// Get topo and topo id
			topoId = Integer.valueOf(parameters.getParsedUrl().getId());
			topoName = topoDao.get(topoId).getName();
			// delete comment and refresh lazy parent (site)... more than lazy...
			deleted = topoDao.delete(topoId);
			// topoDao.refresh(Site.class, siteId);
		} catch (Exception ignore) {/* Already traced in Dao */}
		// If deleting successfull, notification is modified
		if ( deleted ) notification = new Notification(NotificationType.SUCCESS, "Le topo " + topoName + " est supprimé.");
		// Append deferred notification, redirection and mandatory attributes from parameters to delivry
		this.delivry.appendSessionNotification("Suppression d'un Topo", notification);
		this.delivry.appendattribute("redirect", parameters.getContextPath() + "/topo/l/1");
		this.appendMandatoryAttributesToDelivry(parameters);
		return this.delivry;
	}

	@Override
	public Delivry postCreateForm(Parameters parameters) {
		this.delivry = new Delivry();
		// Getting form and apply createTopo method
		TopoForm topoForm = (TopoForm) parameters.getForm();
		@SuppressWarnings("unused")
		Topo createTopo = topoForm.createTopo();
		// if errors we forward form (containing errors) and sites list in delivry to display form again
		if ( ! topoForm.getErrors().isEmpty() ) {
			try {
				List<Site> sites = this.siteDao.getList();
				this.delivry.appendattribute("topoForm", topoForm);
				this.delivry.appendattribute("title", "Création d'un nouveau topo");
				this.delivry.appendattribute("sites", sites);
			} catch (Exception ignore) {}
			this.appendMandatoryAttributesToDelivry(parameters);
			return this.delivry;
		} 
		// Else...
		String notificationLabel = "Ajout d'un nouveau topo";
		String message = "Le topo vient d'être créé avec succès.";
		// Append notifications to delivry
		Notification notification = new Notification(NotificationType.SUCCESS, message);
		Map<String,Notification> notifications = new HashMap<>();
		notifications.put(notificationLabel, notification);
		this.delivry.appendSession("notifications", notifications);
		// Append redirection and mandatory attributes from parameters to delivry
		this.delivry.appendattribute("redirect", parameters.getContextPath() + "/topo/l/1");
		this.appendMandatoryAttributesToDelivry(parameters);
		return this.delivry;
	}

	@Override
	public Delivry postUpdateForm(Parameters parameters) {
		this.delivry = new Delivry();
		// Getting form and apply createTopo method
		TopoForm topoForm = (TopoForm) parameters.getForm();
		@SuppressWarnings("unused")
		Topo updatedTopo = topoForm.updateTopo();
		// if errors we forward form (containing errors) and sites list in delivry to display form again
		if ( ! topoForm.getErrors().isEmpty() ) {
			for ( Entry<String,String> error : topoForm.getErrors().entrySet() ) {
				DLOG.log(Level.ERROR, error.getKey() + " : " + error.getValue());
			}
			try {
				List<Site> sites = this.siteDao.getList();
				this.delivry.appendattribute("topoForm", topoForm);
				this.delivry.appendattribute("title", "Mise à jour du topo - " + topoForm.getTopo().getName());
				this.delivry.appendattribute("sites", sites);
			} catch (Exception ignore) {}
			this.appendMandatoryAttributesToDelivry(parameters);
			return this.delivry;
		} 
		// Else...
		String notificationLabel = "Mise à jour du topo " + updatedTopo.getName();
		String message = "Le topo vient d'être modifié avec succès.";
		// Append notifications to delivry
		Notification notification = new Notification(NotificationType.SUCCESS, message);
		Map<String,Notification> notifications = new HashMap<>();
		notifications.put(notificationLabel, notification);
		this.delivry.appendSession("notifications", notifications);
		// Append redirection and mandatory attributes from parameters to delivry
		this.delivry.appendattribute("redirect", parameters.getContextPath() + "/topo/l/1");
		this.appendMandatoryAttributesToDelivry(parameters);
		return this.delivry;
	}

}
