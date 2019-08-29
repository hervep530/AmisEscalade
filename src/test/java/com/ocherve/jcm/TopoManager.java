package com.ocherve.jcm;

import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import com.ocherve.jcm.dao.DaoProxy;
import com.ocherve.jcm.dao.contract.TopoDao;
import com.ocherve.jcm.model.Site;
import com.ocherve.jcm.model.Topo;
import com.ocherve.jcm.model.User;

/**
 * Service to create, delete Topo and store id created in static object dedicated for tests
 * 
 * @author herve_dev
 *
 */
public class TopoManager {
	
	private static final Logger DLOG = LogManager.getLogger("development_file");
	private static final String[][] TOPOS_DE_TEST = new String[][] {
		{"Danse avec les loups", "Topos sur les Gorges du loup dans les Alpes Maritimes", "true",
			"Manouel Desurvi", "30/08/2008", "null", "3"},
		{"Ni dans le Cantal ni en octobre", "Topos sur le site de Cantobre dans l'Aveyron", "true",
				"Nicolas Cantalou", "12/10/2013", "3", "1"},
		{"Medonnet", "Topos sur Medonnet en Haute-Savoie", "true",
					"Marcel Reblochon", "05/03/2018", "0", "4"}
	};
	private static Integer[] ids;
	private static TopoDao dao;
	
	private static void initialization() {
		if ( dao != null ) return;
		Configurator.setLevel(DLOG.getName(), Level.TRACE);
		DLOG.log(Level.DEBUG, "Initialization of service Topo Test");
		dao = (TopoDao) DaoProxy.getInstance().getTopoDao();
	}

	/**
	 * Create topos for tests
	 */
	public static void create() {
		initialization();
		ids = new Integer[TOPOS_DE_TEST.length];
		Integer authorId;
		User author;
		Site site;
		try {
			for (int u = 0 ; u < TOPOS_DE_TEST.length ; u++) {
				// author id by default is anonymous
				authorId= 1;
				// If TOPOS_DE_TEST[u][5] matches with ids produced by UserManager set authorId with it
				if  ( TOPOS_DE_TEST[u][5].matches("^[0-9]{1,}$") ) { 
					if ( Integer.valueOf(TOPOS_DE_TEST[u][5]) < UserManager.getIds().length )
						authorId = UserManager.getIds()[Integer.valueOf(TOPOS_DE_TEST[u][5])];
				}
				// Get author
				author = UserManager.getDao().get(authorId);
				// site by default is null
				site = null;
				// If TOPOS_DE_TEST[u][6] matches with ids produced by SiteManager get site from it
				if  ( TOPOS_DE_TEST[u][6].matches("^[0-9]{1,6}$") ) { 
					if ( Integer.valueOf(TOPOS_DE_TEST[u][6]) < SiteManager.getIds().length )
						site = SiteManager.getDao().get(SiteManager.getIds()[Integer.valueOf(TOPOS_DE_TEST[u][6])]);
				}
				// Instanciate topo with all attributes
				Topo topo = new Topo(TOPOS_DE_TEST[u][0], TOPOS_DE_TEST[u][0], TOPOS_DE_TEST[u][1],
						TOPOS_DE_TEST[u][3], TOPOS_DE_TEST[u][4],
						Boolean.valueOf(TOPOS_DE_TEST[u][2]), author, site);
				// Create topo and store id
				dao.create(topo);
				ids[u] = topo.getId();
			}			
		} catch (Exception e) {
			DLOG.log(Level.DEBUG, String.format("Error on creating topos"));
			DLOG.log(Level.DEBUG, String.format(e.getMessage()));
		}		
	}
	
	/**
	 * Delete topo dedicated for test
	 */
	public static void delete() {
		initialization();
		try {
			for (int u = 0 ; u < ids.length ; u++) {			
				dao.delete(ids[u]);
			}
		} catch (Exception e) {
			DLOG.log(Level.DEBUG, String.format("Error on deleting topos"));
			DLOG.log(Level.DEBUG, String.format(e.getMessage()));
		}
	}
	
	/**
	 * @return ids of topo created as Integer array
	 */
	protected static Integer[] getIds() {
		return ids;
	}
	
	protected static TopoDao getDao() {
		initialization();
		return dao;
	}	
	
	
	/**
	 * @param topos List of topo
	 * @param topoExpected string to describe kind of list expected (where clause)
	 */
	public static void LogTopoList(List<Topo> topos, String topoExpected) {
		initialization();
		String message = "%n Display list of " + topoExpected + " in database%n";
		message += "Id / Topo name / Departement / Cotation Min / Cotation max / Auteur / Date creation%n";
		if ( topos != null ) {
			for (Topo topo : topos) {
				// message += topo.getType().toString() + " " + topo.getId() + " / ";
				message += topo.getType().toString() + " " + topo.getId() + " / ";
				message += topo.getName() + " / ";
				message += topo.getSummary() + " / ";
				message += topo.getWriter() + " / ";
				message += topo.getAuthor().getUsername() + " / ";
				message += topo.getTsCreated().toString() + "%n";
			}			
		}

		DLOG.log(Level.DEBUG, String.format(message));
	}

	/**
	 * Display condensed list of topo in log for debug
	 * @param id valid index of Integer array ids
	 */
	public static void logTopo (Integer id) {
		initialization();
		Topo topo = dao.get(id);
		logTopo(topo);
	}

	/**
	 * @param topo
	 */
	public static void logTopo (Topo topo) {
		initialization();
		String message = "%n";
		message += "Topo id : " + topo.getType().toString() + " " + topo.getId() + "%n";
		message += "Topo name : " + topo.getName() + "%n";
		message += "Orientation : " + topo.getTitle() + "%n";
		message += "Summary : " + topo.getSummary() + "%n";
		message += "Writer : " + topo.getWriter() + "%n";
		message += "Date de publication : " + topo.getWritedAt().toString() + "%n";
		message += "Auteur : " + topo.getAuthor().getUsername() + "%n";
		message += "Créé le : " + topo.getTsCreated().toString() + "%n";
		message += "Modifié le : " + topo.getTsModified().toString() + "%n";
		message += "Published : " + topo.isPublished().toString() + "%n";
		DLOG.log(Level.DEBUG, String.format(message));
	}

}
