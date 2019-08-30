package com.ocherve.jcm;

import static org.junit.Assert.*;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ocherve.jcm.model.Message;

/**
 * @author herve_dev
 *
 */
public class MessageDaoTest {

	private static final Logger DLOG = LogManager.getLogger("development_file");
	private static Integer id;
	private static Message messageControl;

	/**
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Configurator.setLevel(DLOG.getName(), Level.TRACE);
		DLOG.log(Level.DEBUG, String.format("%n=============%nDEBUT DU TEST%n--------------%n%n"));
		id = null;
		messageControl = null;
		/* Call UserManager to create users and MessageManager to create messages */ 
		UserManager.create();
		MessageManager.create();
		MessageManager.LogMessageList(
				MessageManager.getDao().getListFromNamedQuery("Message.findAllOrderByIdAsc"),
				"all messages created");
	}

	/**
	 * @throws Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		/* deleting messages and users created */
		MessageManager.delete();
		UserManager.delete();
		MessageManager.LogMessageList(
				MessageManager.getDao().getListFromNamedQuery("Message.findAllOrderByIdAsc"),
				"all messages deleted");
		DLOG.log(Level.DEBUG, String.format("%nFIN DU TEST%n=============%n%n"));
	}

	/**
	 * 
	 */
	@Test
	public void test() {
		assertTrue(MessageManager.getDao().getList().size() > 0);
	}

}
