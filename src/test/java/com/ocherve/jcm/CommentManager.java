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
import com.ocherve.jcm.utils.JcmException;

/**
 * @author herve_dev
 *
 */
public class CommentManager {

	private static final Logger DLOG = LogManager.getLogger("test_file");
	private static final String[][] COMMENTS_DE_TEST = new String[][] {
		{"3", "sed doeiusmod tempor incididunt ut labore et", "1", "SITE"},
		{"3", "Ut enimad minim veniam, quis nostrud exercitation ullamco laboris nisi utaliquip ex ea commodo", "0", "SITE"},
		{"3", "incididunt ut labore et dolore magna aliqua. Ut enimad minim veniam, quis nostrud exercitation", "1", "SITE"},
		{"3", "labore et dolore magna aliqua. Ut enimad minim veniam, quis nostrud exercitation ullamco laboris", "0", "SITE"},
		{"3", "mollit anim id est laborum. Lorem ipsum dolor", "1", "SITE"},
		{"3", "enimad minim veniam, quis nostrud exercitation ullamco laboris nisi utaliquip", "0", "SITE"},
		{"1", "Ut enimad minim veniam, quis nostrud exercitation ullamco laboris", "2", "SITE"},
		{"1", "enimad minim veniam, quis nostrud exercitation ullamco laboris nisi utaliquip", "3", "SITE"},
		{"1", "mollit anim id est laborum. Lorem ipsum dolor", "2", "SITE"},
		{"1", "enimad minim veniam, quis nostrud exercitation ullamco laboris nisi utaliquip", "3", "SITE"},
		{"1", "incididunt ut labore et dolore magna aliqua", "2", "SITE"},
		{"2", "Ut enimad minim veniam, quis nostrud exercitation ullamco laboris nisi utaliquip ex ea commodo", "0", "SITE"},
		{"2", "quis nostrud exercitation ullamco laboris ?", "4", "SITE"},
		{"2", "enimad minim veniam, quis nostrud exercitation ullamco laboris nisi utaliquip", "0", "SITE"},
		{"2", "mollit anim id est laborum. Lorem ipsum dolor", "4", "SITE"},
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
		}


	}
		
	/**
	 * Create several comments on existent topo and site
	 */
	public static void createDeMerde() {
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
	
	public static void create() {
		initialization();
		ids = new Integer[COMMENTS_DE_TEST.length];
		Integer entityId;
		User author;
		Reference targetReference;
		Comment comment;
		
		try {
			for (int c = 0; c < COMMENTS_DE_TEST.length; c++) {
				targetReference = null;
				comment = null;
				author = null;
				// Get reference
				entityId = 0 ;
				if ( COMMENTS_DE_TEST[c][3].contentEquals("SITE") ) {
					entityId = SiteManager.getIds()[Integer.valueOf(COMMENTS_DE_TEST[c][0])];
					targetReference = SiteManager.getDao().get(entityId);
				} else { 
					entityId = TopoManager.getIds()[Integer.valueOf(COMMENTS_DE_TEST[c][0])];
					targetReference = TopoManager.getDao().get(entityId);
				}
				if ( targetReference == null ) { 
					DLOG.log(Level.ERROR, "Target reference is null for id " + entityId);
				} else {
					DLOG.log(Level.DEBUG, "ref id / name : " + targetReference.getType() + " " + targetReference.getId() + " / " + targetReference.getName());					
				}
				// Get author
				entityId = 0 ;
				entityId = UserManager.getIds()[Integer.valueOf(COMMENTS_DE_TEST[c][2])];
				author = UserManager.getDao().get(entityId);
				if ( author == null ) { 
					DLOG.log(Level.ERROR, "Author is null for id " + entityId);
				} else {
					DLOG.log(Level.DEBUG, "Author id / username : " + author.getId() + " / " + author.getUsername());
				}
				comment = new Comment(targetReference, COMMENTS_DE_TEST[c][1], author);
				ids[c] = dao.create(comment).getId(); 
			}
		} catch ( Exception e ) {
			DLOG.log(Level.ERROR, "Cannot create comments");
			DLOG.log(Level.ERROR, JcmException.formatStackTrace(e));
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
