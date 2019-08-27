package com.ocherve.jcm;

import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import com.ocherve.jcm.dao.DaoProxy;
import com.ocherve.jcm.dao.contract.SiteDao;
import com.ocherve.jcm.model.Cotation;
import com.ocherve.jcm.model.Site;

/**
 * Service to create, delete Site and store id created in static object dedicated for tests
 * 
 * @author herve_dev
 *
 */
public class SiteManager {

	private static final Logger DLOG = LogManager.getLogger("development_file");
	private static final String[][] SITES_DE_TEST = new String[][] {
		{"Autoire", "France", "Lot", "Autoire est un spot du lot", "true", "false","true","false",
			"5", "25", "Est / Sud-Est", "271", "5a", "8b", "1", "false"},
		{"Cantobre", "France", "Aveyron", "Cantobre est un spot de l'aveyron", "true", "false","true","false",
				"20", "40", "Sud / Sud-Est / Est", "55", "5c", "9a", "1", "false"},
		{"Kerlouan", "France", "Finistere", "Kerlouan est un spot du finistère", "true", "true","false","false",
					"0", "10", "Toutes", "1000", "3", "8a", "1", "false"},
		{"Les Gorges du Loup", "France", "Alpes Maritimes", "Les Gorges du loup sont un port des Alpes Maritimes", "true", "false","true","false",
						"0", "45", "Ouest", "70", "6a", "9a", "1", "false"},
		{"Medonnet", "France", "Haute-Savoie", "Medonnet est un spot de Haute-Savoie", "false", "true","false","false",
							"0", "5", "Toutes", "250", "4", "8a", "1", "false"}
	};
	private static Integer[] ids;
	private static SiteDao dao;
	
	private static void initialization() {
		if ( dao != null ) return;
		Configurator.setLevel(DLOG.getName(), Level.TRACE);
		DLOG.log(Level.DEBUG, "Initialization of Site Manager");
		dao = (SiteDao) DaoProxy.getInstance().getSiteDao();
	}

	/**
	 * Create sites for tests
	 */
	public static void create() {
		initialization();
		ids = new Integer[SITES_DE_TEST.length];
		UserManager.logUser(1);
		
		for (int u = 0 ; u < SITES_DE_TEST.length ; u++) {
			Site site = new Site(SITES_DE_TEST[u][0], SITES_DE_TEST[u][1], SITES_DE_TEST[u][2],
				SITES_DE_TEST[u][3], Boolean.valueOf(SITES_DE_TEST[u][4]), Boolean.valueOf(SITES_DE_TEST[u][5]),
				Boolean.valueOf(SITES_DE_TEST[u][6]), Boolean.valueOf(SITES_DE_TEST[u][7]), Integer.valueOf(SITES_DE_TEST[u][8]),
				Integer.valueOf(SITES_DE_TEST[u][9]), SITES_DE_TEST[u][10], Integer.valueOf(SITES_DE_TEST[u][11]),
				Cotation.valueOf(SITES_DE_TEST[u][12]), Cotation.valueOf(SITES_DE_TEST[u][13]), 
				UserManager.getDao().get(1), Boolean.valueOf(SITES_DE_TEST[u][7]));
			//Integer.valueOf(SITES_DE_TEST[u][14])
			
			dao.create(site);
			ids[u] = site.getId();
		}
		
	}
	
	/**
	 * Delete site dedicated for test
	 */
	public static void delete() {
		initialization();
		for (int u = 0 ; u < ids.length ; u++) {			
			dao.delete(ids[u]);
		}
	}
	
	/**
	 * @return ids of site created as Integer array
	 */
	protected static Integer[] getIds() {
		return ids;
	}
	
	protected static SiteDao getDao() {
		initialization();
		return dao;
	}	
	
	/**
	 * @param sites List of site
	 * @param siteExpected string to describe kind of list expected (where clause)
	 */
	public static void LogSiteList(List<Site> sites, String siteExpected) {
		initialization();
		String message = "%n Display list of " + siteExpected + " in database%n";
		message += "Id / Site name / Departement / Cotation Min / Cotation max / Auteur / Date creation%n";
		
		for (Site site : sites) {
			message += site.getId() + " / ";
			message += site.getName() + " / ";
			message += site.getDepartment() + " / ";
			message += site.getCotationMin().getLabel() + " / ";
			message += site.getCotationMax().getLabel() + " / ";
			message += site.getAuthor().getUsername() + " / ";
			message += site.getTsCreated().toString() + "%n";
		}
		DLOG.log(Level.DEBUG, String.format(message));
	}

	/**
	 * Display condensed list of site in log for debug
	 * @param id valid index of Integer array ids
	 */
	public static void logSite (Integer id) {
		initialization();
		Site site = dao.get(id);
		logSite(site);
	}

	/**
	 * @param site
	 */
	public static void logSite (Site site) {
		initialization();
		String message = "%n";
		message += "Site id : " + site.getId() + "%n";
		message += "Site name : " + site.getName() + "%n";
		message += "Site slug : " + site.getSlug() + "%n";
		message += "Departement : " + site.getDepartment() + "%n";
		message += "Pays : " + site.getCountry() + "%n";
		message += "Bloc : " + site.isBlock().toString() + "%n";
		message += "Falaise : " + site.isCliff().toString() + "%n";
		message += "Mur : " + site.isWall().toString() + "%n";
		message += "Orientation : " + site.getOrientation() + "%n";
		message += "Hauteur mini : " + site.getMinHeight() + "%n";
		message += "Hauteur maxi : " + site.getMaxHeight() + "%n";
		message += "Nombre de voies : " + site.getPathsNumber() + "%n";
		message += "Cotation mini : " + site.getCotationMin().getLabel() + "%n";
		message += "Cotation maxi : " + site.getCotationMax().getLabel() + "%n";
		message += "Auteur : " + site.getAuthor().getUsername() + "%n";
		message += "Créé le : " + site.getTsCreated().toString() + "%n";
		message += "Modifié le : " + site.getTsModified().toString() + "%n";
		message += "Tagué ami : " + site.isFriendTag().toString() + "%n";
		message += "Published : " + site.isPublished().toString() + "%n";
		DLOG.log(Level.DEBUG, String.format(message));
	}


	
}

