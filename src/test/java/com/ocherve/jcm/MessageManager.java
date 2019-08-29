package com.ocherve.jcm;

import java.sql.Timestamp;
import java.time.Instant;

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

	private static final Logger DLOG = LogManager.getLogger("development_file");
	private static final String[][] MESSAGES_DE_TEST = new String[][] {
		{""}
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
			message.setTsSent(Timestamp.from(Instant.now()));
			ids[0] = dao.create(message).getId();
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

}
