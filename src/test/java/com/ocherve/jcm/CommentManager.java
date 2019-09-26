package com.ocherve.jcm;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import com.ocherve.jcm.dao.DaoProxy;
import com.ocherve.jcm.dao.contract.CommentDao;
import com.ocherve.jcm.model.Comment;
import com.ocherve.jcm.model.Reference;
import com.ocherve.jcm.model.User;

/**
 * @author herve_dev
 *
 */
public class CommentManager {

	private static final Logger DLOG = LogManager.getLogger("development_file");
	private static final String[][] COMMENTS_DE_TEST = new String[][] {
		{"0", "Bonjour pourquoi le titre danse avec les loups il est romancé le topo", "3", "TOPO"},
		{"0", "Bonjour romancé... pas vraiment mais c'est bien écrit", "6", "TOPO"},
		{"0", "Et il y a des photos", "3", "TOPO"},
		{"0", "Ah oui vraiment magnifique", "6", "TOPO"},
		{"0", "En général c'est plus pour montrer le paysage ou le contexte technique", "6", "TOPO"},
		{"0", "C est varié un peu des 2 je dirais", "6", "TOPO"},
		{"1", "Bonjour, il a l'air chouette ce site. Il y a des voies accessible pour les ados", "2", "SITE"},
		{"1", "Bonjour, tout dépend s'ils pratiquent déjà...", "4", "SITE"},
		{"1", "Bien, il grimpe souvent dans les arbres dans le jardin", "2", "SITE"},
		{"1", "Ils ont déjà essayé un mur d'escalade", "4", "SITE"},
		{"1", "Non, jamais...", "2", "SITE"},
		{"1", "Je pense qu'il y a des voies pas trop compliquées, mais l'escalade reste un sport dangereux. C'est toujours bien de commencer avec du mur...", "2", "SITE"},
		{"1", "Je vais probablement suivre ton conseil. Je n'ai pas envie de ramener des blessés", "2", "SITE"},
		{"4", "Bonjour, Est ce que ce spot est beaucoup fréquenté, stp?", "0", "SITE"},
		{"4", "En fait, ça dépend de la saison... C'est plutôt calme une grande partie de l'année", "4", "SITE"},
		{"4", "Donc là, à l'automne... grimper la journée oui... mais la fête le soir, c'est plus compliqué?", "0", "SITE"},
		{"4", "Il faut voir le bon coté des choses. Cela permet d'avoir une bonne nuit de repos pour repartir le lendemain.", "4", "SITE"},
	};
	private static Integer[] ids;
	private static CommentDao dao;
	
	private static void initialization() {
		if ( dao != null ) return;
		Configurator.setLevel(DLOG.getName(), Level.TRACE);
		DLOG.log(Level.DEBUG, "Initialization of Comment Manager");
		dao = (CommentDao) DaoProxy.getInstance().getCommentDao();
	}
	
	/**
	 * @return comment dao
	 */
	public static CommentDao getDao() {
		return dao;
	}
	
	/**
	 * @return ids of comments created
	 */
	public static Integer[] getIds() {
		return ids;
	}

	
	/**
	 * 
	 */
	public static void createOneComment() {
		User author = UserManager.getDao().get(1);
		Integer referenceId = TopoManager.getIds()[1];
		Reference reference = TopoManager.getDao().get(referenceId);
		ids = new Integer[1];
		try {
			Comment comment = new Comment();
			comment.setReference(reference);
			comment.setContent("Hello World");
			comment.setAuthor(author);
			comment.setTsCreated(Timestamp.from(Instant.now()));
			comment.setTsModified(Timestamp.from(Instant.now()));
			logComment(comment);
			dao.create(comment);
			ids[0] = comment.getId();
		} catch (Exception e) {
			DLOG.log(Level.DEBUG, String.format("Error on creating comments"));
			//DLOG.log(Level.DEBUG, String.format(e.getMessage()));
			String trace = "";
			for (int t = 0; t < e.getStackTrace().length; t++) {
				trace += "%n" + e.getStackTrace()[t].toString();
			}
			DLOG.log(Level.DEBUG, String.format(trace));
		}


	}
		
	/**
	 * Create several comments on existent topo and site
	 */
	public static void create() {
		initialization();
		ids = new Integer[COMMENTS_DE_TEST.length];
		Integer authorId;
		User author;
		Object targetReference;
		Comment comment;
		try {
			for (int c = 0 ; c < COMMENTS_DE_TEST.length ; c++) {
				// GETTING AUTHOR - author id by default is anonymous
				authorId= 1;
				author = null;
				// If COMMENTS_DE_TEST[c][2] matches with ids produced by UserManager set authorId with it
				if  ( COMMENTS_DE_TEST[c][2].matches("^[0-9]{1,}$") ) { 
					if ( Integer.valueOf(COMMENTS_DE_TEST[c][2]) < UserManager.getIds().length )
						authorId = UserManager.getIds()[Integer.valueOf(COMMENTS_DE_TEST[c][2])];
				}
				// Get author
				author = UserManager.getDao().get(authorId);
				if (author == null ) author = UserManager.getDao().get(1);
				// GETTING TOPO OR SITE (cast AS REFERENCE). If it fails reference is null.
				targetReference = null;
				switch (COMMENTS_DE_TEST[c][3]) {
					case "TOPO" :
						targetReference = TopoManager.getDao()
								.get(TopoManager.getIds()[Integer.valueOf(COMMENTS_DE_TEST[c][0])]);
						break;
					case "SITE" :
						targetReference = SiteManager.getDao()
								.get(SiteManager.getIds()[Integer.valueOf(COMMENTS_DE_TEST[c][0])]);
						break;
					default :
				}
				// CREATING COMMENT - create comment and store id
				comment = new Comment(targetReference, COMMENTS_DE_TEST[c][1], author);
				dao.create(comment);
				ids[c] = comment.getId();			
			}
		} catch (Exception e) {
			DLOG.log(Level.DEBUG, String.format("Error on creating comments"));
			//DLOG.log(Level.DEBUG, String.format(e.getMessage()));
			String trace = "";
			for (int t = 0; t < e.getStackTrace().length; t++) {
				trace += "%n" + e.getStackTrace()[t].toString();
			}
			DLOG.log(Level.DEBUG, String.format(trace));
		}

	}
	
	/**
	 * Delete comment dedicated for test
	 */
	public static void delete() {
		initialization();
		int currentId = 0;
		try {
			for (int c = 0 ; c < ids.length ; c++) {	
				currentId = ids[c];
				if (! dao.delete(ids[c])) DLOG.log(Level.DEBUG, String.format("Error on deleting comment " + currentId));
			}
		} catch (Exception e) {
			DLOG.log(Level.DEBUG, String.format("Error on deleting comments (Id : " + currentId + ")"));
			DLOG.log(Level.DEBUG, String.format(e.getMessage()));
		}
	}

	/**
	 * Delete all comments
	 */
	public static void deleteAll() {
		initialization();
		Integer currentId = 0;
		List<Comment> comments = dao.getList();
		try {
			for (Comment comment : comments) {	
				currentId = comment.getId();
				if (! dao.delete(currentId)) DLOG.log(Level.DEBUG, String.format("Error on deleting comment " + currentId));
			}
		} catch (Exception e) {
			DLOG.log(Level.DEBUG, String.format("Error on deleting comments (Id : " + currentId + ")"));
			DLOG.log(Level.DEBUG, String.format(e.getMessage()));
		}
	}

	/**
	 * @param comment
	 */
	public static void logComment(Comment comment) {
		initialization();
		String message = "%nDisplay Comment Entity%n";
		if (comment.getId() != null) message += "Id : " + comment.getId() + "%n";
		message += "Referenced to " + comment.getReference().getType().toString() + " " +
				comment.getReference().getId() + "%n";
		message += "Content : " + comment.getContent() + "%n";
		message += "Author : " + comment.getAuthor().getUsername() + "%n";
		message += "Created At : " + comment.getTsCreated().toString() + "%n";
		message += "Modified At : " + comment.getTsModified().toString() + "%n";
		DLOG.log(Level.DEBUG, String.format(message));		
	}
	
	/**
	 * @param comments List of comments
	 * @param commentsExpected string to describe kind of list expected (where clause)
	 */
	public static void logCommentsList(List<Comment> comments, String commentsExpected) {
		initialization();
		String message = "%n Display list of " + commentsExpected + " in database%n";
		message += "Id / Topo name / Departement / Cotation Min / Cotation max / Auteur / Date creation%n";
		try {
			if ( comments != null ) {
				for (Comment comment : comments) {
					message += comment.getId() + " / ";
					message += comment.getReference().getType().toString() + " " +
							comment.getReference().getId() + " / ";
					message += comment.getContent() + " / ";
					message += comment.getAuthor().getUsername() + " / ";
					message += comment.getTsCreated().toString() + "%n";
					message += comment.getTsModified().toString() + "%n";
				}			
			}
		} catch (Exception e) {
			DLOG.log(Level.DEBUG, String.format("Error on writing comments list in log"));
			DLOG.log(Level.DEBUG, String.format(formatException(e)));
		}
		DLOG.log(Level.DEBUG, String.format(message));
	}

	/**
	 * @param reference
	 */
	public static void LogReferenceComments(Reference reference) {
		initialization();
		String message = "%n";
		try {
			message += reference.getType().toString() + " id : " + reference.getId() + "%n";
			message += "Site name : " + reference.getName() + "%n";
			message += "Summary : " + reference.getSummary() + "%n";
			message += "Modifié le : " + reference.getTsModified().toString() + "%n";
			message += "Published : " + String.valueOf(reference.isPublished()) + "%n";
			message += getComments(reference);
			DLOG.log(Level.DEBUG, String.format(message));
		} catch (Exception e) {
			DLOG.log(Level.DEBUG, String.format("Error on writing reference and comments in log"));
			DLOG.log(Level.DEBUG, String.format(formatException(e)));
		}
	}

	/**
	 * @param reference
	 * @return message
	 */
	public static String getComments (Reference reference) {
		String message = "" ;
		message += "Id / Comment Summary / Author name / Reference name / Ts Created%n";
		try {
			List<Comment> comments = reference.getComments();
			if ( comments != null ) {
				for (Comment comment : comments) {
					message += comment.getId() + " / ";
					message += comment.getContent() + " / ";
					message += comment.getAuthor().getUsername() + " / ";
					message += reference.getName() + " / ";
					message += comment.getTsCreated().toString() + "%n";
				}			
			}			
		} catch (Exception e) {
			DLOG.log(Level.DEBUG, String.format("Error on getting reference's comments"));
			DLOG.log(Level.DEBUG, String.format(formatException(e)));			
		}
		return message;
	}

	private static String formatException (Exception e) {
		String trace = "";
		for (int t = 0; t < e.getStackTrace().length; t++) {
			trace += "%n" + e.getStackTrace()[t].toString();
		}
		return trace;
	}

}
