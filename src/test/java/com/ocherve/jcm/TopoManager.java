package com.ocherve.jcm;

import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import com.ocherve.jcm.dao.DaoProxy;
import com.ocherve.jcm.dao.contract.TopoDao;
import com.ocherve.jcm.model.Topo;
import com.ocherve.jcm.model.User;

/**
 * Service to create, delete Topo and store id created in static object dedicated for tests
 * 
 * @author herve_dev
 *
 */
public class TopoManager {
	
	private static final Logger DLOG = LogManager.getLogger("test_file");
	private static final String[][] TOPOS_DE_TEST = new String[][] {
		{"Grimpe en auvergne", "exercitation ullamco laboris nisi utaliquip ex ea commodo", "true",
			"Benoit Cantalou", "30/08/2008", "null", "3",
			"labore et dolore magna aliqua. Ut enimad minim veniam, quis nostrud exercitation ullamco laboris nis"},
		{"Tout l aveyron", "dolore magna aliqua. Ut enimad minim veniam, quis", "true",
			"Manouel Desurvi", "12/10/2013", "3", "1",
			"aliqua. Ut enimad minim veniam, quis nostrud exercitation"},
		{"Falaises de Marne", "incididunt ut labore et dolore magna aliqua", "true",
			"Marcel Reblochon", "05/03/2018", "0", "4",
			"tempor incididunt ut labore et dolore magna aliqua. Ut enimad minim veniam, quis nostrud exercitation ullamco laboris nisi utaliquip ex ea commodo consequat. Duis aute irure dolor inreprehenderit in voluptate velit"}
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
		try {
			for (int u = 0 ; u < TOPOS_DE_TEST.length ; u++) {
				// AUTHOR : id by default is anonymous
				authorId= 1;
				// If TOPOS_DE_TEST[u][5] matches with ids produced by UserManager set authorId with it
				if  ( TOPOS_DE_TEST[u][5].matches("^[0-9]{1,}$") ) { 
					if ( Integer.valueOf(TOPOS_DE_TEST[u][5]) < UserManager.getIds().length )
						authorId = UserManager.getIds()[Integer.valueOf(TOPOS_DE_TEST[u][5])];
				}
				// Get author
				author = UserManager.getDao().get(authorId);
				// SITE : create Integer array of ids 
				Integer[] siteIds = null;
				if ( TOPOS_DE_TEST[u][6].matches("^[0-9]{1,}(:[0-9]{1,})*$") ) {
					String[] split = TOPOS_DE_TEST[u][6].split(":");
					siteIds = new Integer[split.length];
					for ( int s = 0; s < split.length; s++ ) {
						siteIds[s] = SiteManager.getIds()[Integer.valueOf(split[s])]; 
					}
				}
				// Instanciate topo with all attributes
				Topo topo = new Topo(TOPOS_DE_TEST[u][0], TOPOS_DE_TEST[u][0], TOPOS_DE_TEST[u][1],
						TOPOS_DE_TEST[u][3], TOPOS_DE_TEST[u][4],
						Boolean.valueOf(TOPOS_DE_TEST[u][2]), author, siteIds);
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
	 * Delete all topo in database
	 */
	public static void deleteAll() {
		initialization();
		List<Topo> topos = dao.getList();
		try {
			for (Topo topo : topos) {			
				dao.delete(topo.getId());
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
		message += "Id / Topo name / Summary / Writer / Auteur / Date creation%n";
		try {
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
		} catch (Exception e) {
			DLOG.log(Level.DEBUG, String.format("Error on creating list topos log"));
			//DLOG.log(Level.DEBUG, String.format(e.getMessage()));			
		}
		DLOG.log(Level.DEBUG, String.format(message));
	}

	/**
	 * Display condensed list of topo in log for debug
	 * @param id valid index of Integer array ids
	 */
	public static void logTopo (Integer id) {
		initialization();
		try {
			Topo topo = dao.get(id);
			logTopo(topo);			
		} catch (Exception e) {
			DLOG.log(Level.DEBUG, String.format("Error before log generation, on getting topo from id " + id ));
			//DLOG.log(Level.DEBUG, String.format(e.getMessage()));			
		}
	}

	/**
	 * @param topo
	 */
	public static void logTopo (Topo topo) {
		initialization();
		String message = "%n";
		try {
			message += "Topo id : " + topo.getType().toString() + " " + topo.getId() + "%n";
			message += "Topo name : " + topo.getName() + "%n";
			message += "Orientation : " + topo.getTitle() + "%n";
			message += "Summary : " + topo.getSummary() + "%n";
			message += "Writer : " + topo.getWriter() + "%n";
			message += "Date de publication : " + topo.getWritedAt().toString() + "%n";
			message += "Auteur : " + topo.getAuthor().getUsername() + "%n";
			message += "Créé le : " + topo.getTsCreated().toString() + "%n";
			message += "Modifié le : " + topo.getTsModified().toString() + "%n";
			message += "Published : " + String.valueOf(topo.isPublished()) + "%n";
			DLOG.log(Level.DEBUG, String.format(message));			
		} catch (Exception e) {
			DLOG.log(Level.DEBUG, String.format("Error on creating topo entity log message"));
			//DLOG.log(Level.DEBUG, String.format(e.getMessage()));
		}
	}

}
