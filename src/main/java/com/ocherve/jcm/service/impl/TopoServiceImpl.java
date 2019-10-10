package com.ocherve.jcm.service.impl;

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
				case  "u" :
					parameters.setForm(new TopoForm(request));
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
			throw new UrlException("Echec de la requete Topo.countAll");
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
			throw new UrlException("Echec de la requete " + queryName);
		}
		if (topos == null) throw new UrlException("Aucun resultat pour la requete " + queryName);
		// debug
		if ( topos.isEmpty() ) throw new UrlException("Aucun resultat pour la requete " + queryName);
		// Appending data to result and return it
		this.delivry.appendattribute("topos", topos);
		this.delivry.appendattribute("listsCount", listsCount);
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
			this.delivry.appendError("Site search", "Error on displaying search site formular.");
			DLOG.log(Level.ERROR, "Error on displaying search site formular Cotation references can not be reached.");
		}
		this.appendMandatoryAttributesToDelivry(parameters);
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
		// TODO Auto-generated method stub
		this.delivry = new Delivry();
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
		// TODO Auto-generated method stub
		this.delivry = new Delivry();
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
