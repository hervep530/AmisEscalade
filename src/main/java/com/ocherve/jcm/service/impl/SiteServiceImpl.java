package com.ocherve.jcm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Level;

import com.ocherve.jcm.dao.DaoProxy;
import com.ocherve.jcm.dao.contract.SiteDao;
import com.ocherve.jcm.model.Cotation;
import com.ocherve.jcm.model.Site;
import com.ocherve.jcm.service.Delivry;
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

	@Override
	public Delivry doGetAction(Parameters parameters) {
		Delivry delivry = new Delivry();
		try {
			switch (parameters.getParsedUrl().getAction()) {
				case "l" :
					delivry = getList(parameters);
					break;
				case "f" :
					delivry = getFindForm(parameters);
					break;
				case "r" :
					delivry = getSite(parameters);
					break;
				case "c" :
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
			delivry.appendError(serviceName + "_" + parameters.getParsedUrl().getAction(), e.getMessage());
		}
		delivry.setParameters(parameters);
		if ( ! parameters.getErrors().isEmpty() ) delivry.setErrors(parameters.getErrors());
		String info = "Service " + this.serviceName + " do GetAction.";
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

	@Override
	public Delivry postFindForm(Parameters parameters) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Delivry getFindForm(Parameters parameters) {
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
		// TODO Auto-generated method stub
		return null;
		
	}

	@Override
	public Delivry postCreateForm(Parameters parameters) {
		// TODO Auto-generated method stub
		return null;
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
		String content = site.getContent();
		if ( content.length() > 70) content = content.substring(0, 70);
		message += "Content : " + content + "...%n";
		message += "Auteur : " + site.getAuthor().getUsername() + "%n";
		message += "Créé le : " + site.getTsCreated().toString() + "%n";
		message += "Modifié le : " + site.getTsModified().toString() + "%n";
		message += "Tagué ami : " + String.valueOf(site.isFriendTag()) + "%n";
		message += "Published : " + String.valueOf(site.isPublished()) + "%n";
		DLOG.log(Level.DEBUG, String.format(message));
	}

}
