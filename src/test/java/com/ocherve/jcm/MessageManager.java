package com.ocherve.jcm;

import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import com.ocherve.jcm.dao.DaoProxy;
import com.ocherve.jcm.dao.contract.MessageDao;
import com.ocherve.jcm.model.Message;
import com.ocherve.jcm.model.User;

/**
 * @author herve_dev
 *
 */
public class MessageManager {

	private static final Logger DLOG = LogManager.getLogger("test_file");
	private static final String[][] MESSAGES_DE_TEST = new String[][] {
		{"0", "1", "Demande de prêt du topo Grimpe en Auvergne", "Bonjour, il a l'air bien ce topo. Peux tu me le prêter s'il te plait", null},
		{"1", "0", "Demande de prêt du topo Grimpe en Auvergne", "Pas de soucis, comme tu as vu, il est dispo ;-). Par contre j'en aurai besoin dans une vingtaine de jours...", "0"},
		{"0", "1", "Demande de prêt du topo Grimpe en Auvergne", "Ok, je le consulterai sans attendre.", "1"},
		{"1", "0", "Demande de prêt du topo Grimpe en Auvergne", "Donc on fait comme ça", "2"},
		{"0", "1", "Demande de prêt du topo Grimpe en Auvergne", "Merci", "3"},
		{"3", "0", "Demande de prêt du topo Falaise de marne", "Bonjour, il est bien diponible ton topo?", null},
		{"0", "3", "Demande de prêt du topo Falaise de marne", "Bonjour. Eh non... Je plaisante. En effet il est dispo.", "5"},
		{"3", "0", "Demande de prêt du topo Falaise de marne", "Est ce que tu peux me le prêter s'il te plait? Je vais faire un séjour en Haute-Marne, donc j'aimerais préparer le terrain.", "6"},
		{"0", "3", "Demande de prêt du topo Falaise de marne", "Est ce que ça te va, si je te le prête à partir de la fin de semaine?", "7"},
		{"3", "0", "Demande de prêt du topo Falaise de marne", "oui, ça serait cool :-)", "8"}
	};
	private static Integer[] ids;
	private static MessageDao dao;

	private static void initialization() {
		if ( dao != null ) return;
		Configurator.setLevel(DLOG.getName(), Level.TRACE);
		DLOG.log(Level.DEBUG, "Initialization of Message Manager");
		dao = (MessageDao) DaoProxy.getInstance().getMessageDao();		
	}
	
	/**
	 * @return the ids
	 */
	public static Integer[] getIds() {
		return ids;
	}

	/**
	 * @return the dao
	 */
	public static MessageDao getDao() {
		return dao;
	}

	/**
	 * 
	 */
	public static void create() {
		initialization();
		User sender = null;
		User receiver = null;
		Message parent = null;
		Message message = null;
		ids = new Integer[MESSAGES_DE_TEST.length];
		try {
			for (int u = 0 ; u < MESSAGES_DE_TEST.length ; u++) {
				sender = UserManager.getDao().get(UserManager.getIds()[Integer.valueOf(MESSAGES_DE_TEST[u][0])]);
				receiver = UserManager.getDao().get(UserManager.getIds()[Integer.valueOf(MESSAGES_DE_TEST[u][1])]);
				parent = null;
				if ( MESSAGES_DE_TEST[u][4] != null ) {
					if ( Integer.valueOf(MESSAGES_DE_TEST[u][4]) < u) 
						parent = dao.get(ids[Integer.valueOf(MESSAGES_DE_TEST[u][4])]);
				}
				message = new Message(sender, receiver, MESSAGES_DE_TEST[u][2], MESSAGES_DE_TEST[u][3], parent);
				dao.create(message);
				ids[u] = message.getId();
			}
		} catch (Exception e) {
			DLOG.log(Level.DEBUG, String.format("Error on creating messages"));
			DLOG.log(Level.DEBUG, String.format(formatException(e)));
		}
		DLOG.log(Level.DEBUG, String.format("End of create Message"));
	}

	
	/**
	 * 
	 */
	protected static void createOne() {
		initialization();
		try {
			ids = new Integer[1];
			User sender = UserManager.getDao().get(UserManager.getIds()[0]);
			User receiver = UserManager.getDao().get(UserManager.getIds()[2]);
			Message message = new Message();
			message.setSender(sender);
			message.setReceiver(receiver);
			message.setTitle("Test de messagerie");
			message.setContent("Envoi d'un message privé");
			dao.create(message);
			ids[0] = message.getId();
		} catch (Exception e) {
			DLOG.log(Level.DEBUG, String.format("Error on creating messages"));
			DLOG.log(Level.DEBUG, String.format(e.getMessage()));
		}
	}

	/**
	 * 
	 */
	protected static void deleteOne() {
		initialization();
		try {
			if (! dao.delete(ids[0]))
				DLOG.log(Level.DEBUG, String.format("Error on deleting message " + ids[0]));
		} catch (Exception e) {
			DLOG.log(Level.DEBUG, String.format("Error on deleting message " + ids[0]));
			DLOG.log(Level.DEBUG, String.format(e.getMessage()));
		}
	}

	/**
	 * Delete all messages created by MessageManager
	 */
	public static void delete() {
		initialization();
		Integer currentId = 0;
		boolean deleted = false;
		try {
			for (int u = ids.length -1 ; u >= 0 ; u--) {
				currentId = Integer.valueOf(ids[u]);
				deleted = dao.delete(Integer.valueOf(ids[u]));
				if (! deleted) DLOG.log(Level.DEBUG, String.format("Error on deleting message " + currentId ));
			}
		} catch (Exception e) {
			DLOG.log(Level.DEBUG, String.format("Error on deleting messages (Id : " + currentId + ")"));
			DLOG.log(Level.DEBUG, String.format(formatException(e)));
		}		
		DLOG.log(Level.DEBUG, String.format("End of MessageManager.delete()"));
	}
	
	/**
	 * Delete all messages in database
	 */
	public static void deleteAll() {
		initialization();
		List<Message> messages = dao.getListFromNamedQuery("Message.findAllOrderByIdDesc");
		Integer currentId = 0;
		try {
			for (Message message : messages) {
				currentId = message.getId();
				boolean deleted = dao.delete(currentId);
				if (! deleted) DLOG.log(Level.DEBUG, String.format("Error on deleting message " + currentId ));
			}
		} catch (Exception e) {
			DLOG.log(Level.DEBUG, String.format("Error on deleting messages (Id : " + currentId + ")"));
			DLOG.log(Level.DEBUG, String.format(formatException(e)));
		}		
		DLOG.log(Level.DEBUG, String.format("End of MessageManager.deleteAll()"));
	}	
	
	/**
	 * @param messages List of message
	 * @param messageExpected string to describe kind of list expected (where clause)
	 */
	public static void LogMessageList(List<Message> messages, String messageExpected) {
		initialization();
		String logMessage = "%n Display list of " + messageExpected + " in database%n";
		logMessage += "(discussionId/parentId - id) sender -> receiver : title - content%n";
		try {
			if ( messages != null ) {
				for (Message message : messages) {
					logMessage += "(" + String.valueOf(message.getDiscussionId()) + "/";
					String parentId = "null";
					if ( message.getParent() != null ) parentId = String.valueOf(message.getParent().getId());
					logMessage += parentId + " - ";
					logMessage += message.getId() + ") ";
					logMessage += message.getSender().getUsername() + " -> ";
					logMessage += message.getReceiver().getUsername() + " : ";
					logMessage += message.getTitle() + " - ";
					String text = message.getContent();
					if (text.length() > 70) text = message.getContent().substring(0,70);
					logMessage += text + "%n";
				}			
			}						
		} catch (Exception e) {
			DLOG.log(Level.DEBUG, String.format(formatException(e)));
		}
		DLOG.log(Level.DEBUG, String.format(logMessage));
	}

	private static String formatException (Exception e) {
		String trace = "";
		for (int t = 0; t < e.getStackTrace().length; t++) {
			trace += "%n" + e.getStackTrace()[t].toString();
		}
		return trace;
	}
	

}
