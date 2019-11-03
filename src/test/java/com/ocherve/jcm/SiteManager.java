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

	private static final Logger DLOG = LogManager.getLogger("test_file");
	private static final String[][] SITES_DE_TEST = new String[][] {
		{"Autoire", "France", "Lot", "Lorem ipsum dolor sit amet, consectetur", "true", "false","true","false",
			"5", "25", "Est Sud-Est", "271", "5a", "8b", "1", "false",
			"/media/site/autoire.jpg",
			"Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed doeiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enimad minim veniam, quis nostrud exercitation ullamco laboris nisi utaliquip ex ea commodo consequat. Duis aute irure dolor inreprehenderit in voluptate velit esse cillum dolore eu fugiat nullapariatur. Excepteur sint occaecat cupidatat non proident, sunt inculpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed doeiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enimad minim veniam, quis nostru"},
		{"Cantobre", "France", "Aveyron", "labore et dolore magna aliqua. Ut enimad minim", "true", "false","true","false",
			"20", "40", "Sud Sud-Est Est", "55", "5c", "9a", "2", "false",
			"/media/site/cantobre.jpg",
			"labore et dolore magna aliqua. Ut enimad minim veniam, quis nostrud exercitation ullamco laboris nisi utaliquip ex ea commodo consequat. Duis aute irure dolor inreprehenderit in voluptate velit esse cillum dolore eu fugiat nullapariatur. Excepteur sint occaecat cupidatat non proident, sunt inculpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed doeiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enimad minim veniam, quis nostrud exercitation ullamco laboris nisi utaliquip ex ea commodo consequat. Duis aute irure dolor inreprehenderit in voluptate velit esse cillum dolore eu fugiat nullapariatur. Excepteur sint occaecat cupi"},
		{"Kerlouan", "France", "Finistere", "doeiusmod tempor incididunt ut labore", "true", "true","false","false",
			"1", "10", "Toutes", "1000", "3", "8a", "1", "false",
			"/media/site/kerlouan.jpg",
			"doeiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enimad minim veniam, quis nostrud exercitation ullamco laboris nisi utaliquip ex ea commodo consequat. Duis aute irure dolor inreprehenderit in voluptate velit esse cillum dolore eu fugiat nullapariatur. Excepteur sint occaecat cupidatat non proident, sunt inculpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed doeiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enimad minim veniam, quis nostrud exercitation ullamco laboris nisi utaliquip ex ea commodo consequat. Duis aute irure dolor inreprehenderit in voluptate velit esse cillum dolore eu fugiat nullapariatur. Excepteur sint occaecat cupidatat non proident, sunt inculpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed doeiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enimad minim veniam, quis nostrud exercitation ullamco laboris nisi utaliquip ex ea commodo consequat. Duis aute irure dolor inreprehenderit in voluptate velit esse cillum dolore eu fugiat nullapariatur. Excepteur sint occaecat cupidatat non proident, sunt inculpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed doeiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enimad minim veniam, quis nostrud exercitation ull"},
		{"Les Gorges du Loup", "France", "Alpes Maritimes", "adipisicing elit, sed doeiusmod tempor", "true", "false","true","false",
			"1", "45", "Ouest", "70", "6a", "9a", "1", "false",
			"/media/site/gorges_du_loup.jpg",
			"adipisicing elit, sed doeiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enimad minim veniam, quis nostrud exercitation ullamco laboris nisi utaliquip ex ea commodo consequat. Duis aute irure dolor inreprehenderit in voluptate velit esse cillum dolore eu fugiat nullapariatur. Excepteur sint occaecat cupidatat non proident, sunt inculpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed doeiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enimad minim veniam, quis nostrud exercitation ullamco laboris nisi utaliquip ex ea commodo consequat. Duis aute irure dolor inreprehenderit in voluptate velit esse cillum dolore eu fugiat nullapariatur. Excepteur sint occaecat cupidatat non proident, sunt inculpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed doeiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enimad minim veniam, quis nostrud exercitation ullamco laboris nisi utaliquip ex ea commodo consequat. Duis aute irure dolor inreprehenderit in voluptate velit esse cillum dolore eu fugiat nu"},
		{"Annot", "France", "Alpes de Haute Provence", "sed doeiusmod tempor", "true", "true","true","false",
			"5", "25", "toutes", "400", "5c", "8c+", "2", "false",
			"/media/site/annot.jpg",
			"sed doeiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enimad minim veniam, quis nostrud exercitation ullamco laboris nisi utaliquip ex ea commodo consequat. Duis aute irure dolor inreprehenderit in voluptate velit esse cillum dolore eu fugiat nullapariatur. Excepteur sint occaecat cupidatat non proident, sunt inculpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed doeiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enimad minim veniam, quis nostru"},
		{"Bionnassay", "France", "Haute-Savoie", "Excepteur sint occaecat cupidatat non", "true", "false","true","false",
			"5", "40", "Ouest - Sud Ouest", "50", "5c", "9a", "4", "false",
			"/media/site/bionnassay.jpg",
			"Excepteur sint occaecat cupidatat non proident, sunt inculpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed doeiusmod tempor incididunt ut labore et dolore"},
		{"Castillon", "France", "Alpes maritimes", "labore et dolore magna aliqua. Ut enimad", "true", "false","true","false",
			"5", "40", "Sud Ouest", "55", "6b", "8c+", "3", "false",
			"/media/site/castillon.jpg",
			"labore et dolore magna aliqua. Ut enimad minim veniam, quis nostrud exercitation ullamco laboris nisi utaliquip ex ea commodo consequat. Duis aute irure dolor inreprehenderit in voluptate velit esse cillum dolore eu fugiat nullapariatur. Excepteur sint occaecat cupidatat non proident, sunt inculpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed doeiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enimad minim veniam, quis nostrud exercitation ullamco laboris nisi utaliquip ex ea commodo consequat. Duis aute irure dolor inreprehenderit in voluptate velit esse cillum dolore eu fugiat nullapariatur. Excepteur sint occaecat cupi"},
		{"Claret", "France", "Herault", "doeiusmod tempor incididunt ut labore", "true", "true","true","false",
			"5", "30", "Sud", "100", "5c", "8c", "3", "false",
			"/media/site/claret.jpg",
			"doeiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enimad minim veniam, quis nostrud exercitation ullamco laboris nisi utaliquip ex ea commodo consequat. Duis aute irure dolor inreprehenderit in voluptate velit esse cillum dolore eu fugiat nullapariatur. Excepteur sint occaecat cupidatat non proident, sunt inculpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed doeiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enimad minim veniam, quis nostrud exercitation ullamco laboris nisi utaliquip ex ea commodo consequat. Duis aute irure dolor inreprehenderit in voluptate velit esse cillum dolore eu fugiat nullapariatur. Excepteur sint occaecat cupidatat non proident, sunt inculpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed doeiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enimad minim veniam, quis nostrud exercitation ullamco laboris nisi utaliquip ex ea commodo consequat. Duis aute irure dolor inreprehenderit in voluptate velit esse cillum dolore eu fugiat nullapariatur. Excepteur sint occaecat cupidatat non proident, sunt inculpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed doeiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enimad minim veniam, quis nostrud exercitation ull"},
		{"Krappenfels", "France", "Moselle", "adipisicing elit, sed doeiusmod", "true", "false","true","false",
			"5", "20", "Sud Ouest", "100", "4c", "7b+", "2", "false",
			"/media/site/krappenfels.jpg",
			"adipisicing elit, sed doeiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enimad minim veniam, quis nostrud exercitation ullamco laboris nisi utaliquip ex ea commodo consequat. Duis aute irure dolor inreprehenderit in voluptate velit esse cillum dolore eu fugiat nullapariatur. Excepteur sint occaecat cupidatat non proident, sunt inculpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed doeiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enimad minim veniam, quis nostrud exercitation ullamco laboris nisi utaliquip ex ea commodo consequat. Duis aute irure dolor inreprehenderit in voluptate velit esse cillum dolore eu fugiat nullapariatur. Excepteur sint occaecat cupidatat non proident, sunt inculpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed doeiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enimad minim veniam, quis nostrud exercitation ullamco laboris nisi utaliquip ex ea commodo consequat. Duis aute irure dolor inreprehenderit in voluptate velit esse cillum dolore eu fugiat nu"},
		{"La Chambotte", "France", "Savoie", "magna aliqua. Ut enimad minim veniam", "true", "false","true","false",
			"10", "90", "Ouest - Sud Ouest", "300", "4b", "8c", "4", "false",
			"/media/site/la_chambotte.jpg",
			"magna aliqua. Ut enimad minim veniam, quis nostrud exercitation ullamco laboris nisi utaliquip ex ea commodo consequat. Duis aute irure dolor inreprehenderit in voluptate velit esse cillum dolore eu fugiat nullapariatur. Excepteur sint occaecat cupidatat non proident, sunt inculpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed doeiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enimad minim veniam, quis nostrud exercitation ullamco laboris nisi utaliquip ex ea commodo consequat. Duis aute irure dolor inreprehenderit in voluptate velit esse cillum dolore eu fugiat nullapariatur. Excepteur sint occaecat cupidatat non proident, sunt inculpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor "},
		{"La Combe obscure", "France", "Vaucluse", "sed doeiusmod tempor incididunt ut labore", "true", "false","true","false",
			"5", "40", "Sud Ouest", "55", "6b", "8c+", "3", "false",
			"/media/site/la_combe_obscure.jpg",
			"sed doeiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enimad minim veniam, quis nostrud exercitation ullamco laboris nisi utaliquip ex ea commodo consequat. Duis aute irure dolor inreprehenderit in voluptate velit esse cillum dolore eu fugiat nullapariatur. Excepteur sint occaecat cupidatat non proident, sunt inculpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed doeiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enimad minim veniam, quis nostrud exercitation ullamco laboris nisi utaliquip ex ea commodo consequat. Duis aute irure dolor inreprehenderit in voluptate velit esse cillum dolore eu fugiat nullapariatur. Excepteur sint occaecat cupidatat non proident, sunt inculpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed doeiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enimad minim veniam, quis nostrud exercitation ullamco laboris nisi utaliquip ex ea commodo consequat. Duis aute irure dolor inreprehenderit in voluptate velit esse cillum dolore eu fugiat nullapariatur. Excepteur sint occaecat cupidatat non proident, sunt inculpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed doeiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enimad minim veniam, quis nostrud exercitation ullamco laboris nisi utaliquip ex ea commodo consequat. Duis aute irure dolor inreprehenderit in voluptate velit esse cillum dolore eu fugiat nullapariatur. Excepteur sint occaecat cupidatat non proident, sunt inculpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consecte"},
		{"La dent de la rancune", "France", "Auvergne", "Lorem ipsum dolor", "true", "false","true","false",
			"10", "90", "Toutes", "30", "4c", "8a", "4", "false",
			"/media/site/la_dent_de_la_rancune.jpg",
			"Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed doeiusmod tempor i"},
		{"La falaise de Cohons", "France", "Haute Marne", "elit, sed doeiusmod tempor", "true", "false","true","false",
			"3", "8", "Toutes", "100", "3", "8a", "2", "false",
			"/media/site/la_falaise_de_cohons.jpg",
			"elit, sed doeiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enimad minim veniam, quis nostrud exercitation ullamco laboris nisi utaliquip ex ea commodo consequat. Duis aute irure dolor inreprehenderit in voluptate velit esse cillum dolore eu fugiat nullapariatur. Excepteur sint occaecat cupidatat non proident, sunt inculpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipisicing elit, s"},
		{"La hottee du diable", "France", "Marne", "amet, consectetur adipisicing elit", "true", "true","false","false",
			"5", "40", "Sud Ouest", "55", "3", "8a", "2", "false",
			"/media/site/la_hottee_du_diable.jpg",
			"amet, consectetur adipisicing elit, sed doeiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enimad minim veniam, quis nostrud exercitation ullamco laboris nisi utaliquip ex ea commodo consequat. Duis aute irure dolor inreprehenderit in voluptate velit esse cillum dolore eu fugiat nullapariatur. Excepteur sint occaecat cupidatat non proident, sunt inculpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed doeiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enimad minim veniam, quis nostrud exercitation ullamco laboris nisi utaliquip ex ea commodo consequat. Duis aute irure dolor inreprehenderit in voluptate velit esse cillum dolore eu fugiat nullapariatur. Excepteur sint occaecat cupidatat non proident, sunt inculpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed doeiusmod tempor incididunt ut labore et dolore magna al"},
		{"La roche Bernard", "France", "Haute Marne", "aliqua. Ut enimad minim veniam, quis", "true", "false","true","false",
			"10", "15", "Sud Ouest", "37", "4b", "7b+", "0", "false",
			"/media/site/la_roche_bernard.jpg",
			"aliqua. Ut enimad minim veniam, quis nostrud exercitation ullamco laboris nisi utaliquip ex ea commodo consequat. Duis aute irure dolor inreprehenderit in voluptate velit esse cillum dolore eu fugiat nullapariatur. Excepteur sint occaecat cupidatat non proident, sunt inculpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed doeiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enimad minim veniam, quis nostrud exercitation ullamco laboris nisi utaliquip ex ea commodo consequat. Duis aute irure dolor inreprehenderit in voluptate velit esse cillum dolore eu fugiat nullapariatur. Excepteur sint occaecat cupidatat non proident, sunt inculpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed doeiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enimad minim veniam, quis nostrud exercitation ullamco laboris nisi utaliquip ex ea commodo consequat. Duis aute irure dolor inreprehenderit in voluptate velit esse cillum dolore eu fugiat nullapariatur. Excepteur sint occaecat cupidatat non proident, sunt inculpa qui officia deserunt molli"},
		{"La Turbie", "France", "Auvergne", "sed doeiusmod tempor incididunt ut", "true", "false","true","false",
			"5", "120", "Toutes", "400", "4", "8c", "0", "false",
			"/media/site/la_turbie.jpg",
			"sed doeiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enimad minim veniam, quis nostrud exercitation ullamco laboris nisi utaliquip ex ea commodo consequat. Duis aute irure dolor inreprehenderit in voluptate velit esse cillum dolore eu fugiat nullapariatur. Excepteur sint occaecat cupidatat non proident, sunt inculpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed doeiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enimad minim veniam, quis nostru"},
		{"Le Caroux", "France", "Herault", "Excepteur sint occaecat cupidatat", "true", "false","true","false",
			"5", "350", "Toutes", "400", "3", "7c", "4", "false",
			"/media/site/le_caroux.jpg",
			"Excepteur sint occaecat cupidatat non proident, sunt inculpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed doeiusmod tempor incididunt ut labore et dolore"},
		{"Medonnet", "France", "Haute-Savoie", "magna aliqua. Ut enimad minim", "false", "true","false","false",
			"1", "5", "Toutes", "250", "4", "8a", "3", "false",
			"/media/site/medonnet.jpg",
			"magna aliqua. Ut enimad minim veniam, quis nostrud exercitation ullamco laboris nisi utaliquip ex ea commodo consequat. Duis aute irure dolor inreprehenderit in voluptate velit esse cillum dolore eu fugiat nullapariatur. Excepteur sint occaecat cupidatat non proident, sunt inculpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed doeiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enimad minim veniam, quis nostrud exercitation ullamco laboris nisi utaliquip ex ea commodo consequat. Duis aute irure dolor inreprehenderit in voluptate velit esse cillum dolore eu fugiat nullapariatur. Excepteur sint occaecat cupidatat non proident, sunt inculpa qui officia deserunt mollit anim id est laborum. Lorem ipsum dolor "}
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
		try {
			for (int u = 0 ; u < SITES_DE_TEST.length ; u++) {
				Integer authorId = UserManager.getIds()[Integer.valueOf(SITES_DE_TEST[u][14])];
				Site site = new Site(SITES_DE_TEST[u][0], SITES_DE_TEST[u][1], SITES_DE_TEST[u][2],
					SITES_DE_TEST[u][3], Boolean.valueOf(SITES_DE_TEST[u][4]), Boolean.valueOf(SITES_DE_TEST[u][5]),
					Boolean.valueOf(SITES_DE_TEST[u][6]), Boolean.valueOf(SITES_DE_TEST[u][7]), Integer.valueOf(SITES_DE_TEST[u][8]),
					Integer.valueOf(SITES_DE_TEST[u][9]), SITES_DE_TEST[u][10], Integer.valueOf(SITES_DE_TEST[u][11]),
					Cotation.valueOf(SITES_DE_TEST[u][12]), Cotation.valueOf(SITES_DE_TEST[u][13]), 
					UserManager.getDao().get(authorId), Boolean.valueOf(SITES_DE_TEST[u][7]));
				//Integer.valueOf(SITES_DE_TEST[u][14])
				site.setContent(SITES_DE_TEST[u][17]);
				
				dao.create(site);
				ids[u] = site.getId();
			}			
		} catch (Exception e) {
			DLOG.log(Level.DEBUG, String.format("Error on creating sites"));
			//DLOG.log(Level.DEBUG, JcmException.formatStackTrace(e));
		}		
	}
	
	/**
	 * Delete site dedicated for test
	 */
	public static void delete() {
		initialization();
		try {
			for (int u = 0 ; u < ids.length ; u++) {			
				dao.delete(ids[u]);
			}
		} catch (Exception e) {
			DLOG.log(Level.DEBUG, String.format("Error on deleting site"));
			DLOG.log(Level.DEBUG, String.format(e.getMessage()));
		}
	}

	/**
	 * Delete sites
	 */
	public static void deleteAll() {
		initialization();
		List<Site> sites= dao.getList();
		try {
			for (Site site : sites) {			
				dao.delete(site.getId());
			}
		} catch (Exception e) {
			DLOG.log(Level.DEBUG, String.format("Error on deleting site"));
			DLOG.log(Level.DEBUG, String.format(e.getMessage()));
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
		if (sites == null ) {
			DLOG.log(Level.ERROR, "Sites list is null. Nothing can be logged.");
			return;
		}
		initialization();
		String message = "%n Display list of " + siteExpected + " in database%n";
		message += "Id / Site name / Departement / Cotation Min / Cotation max / Auteur / Date creation%n";
		
		for (Site site : sites) {
			message += site.getType().toString() + " " + site.getId() + " / ";
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

