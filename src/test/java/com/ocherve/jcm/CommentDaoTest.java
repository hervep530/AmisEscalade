/**
 * 
 */
package com.ocherve.jcm;

import static org.junit.Assert.*;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * @author herve_dev
 *
 */
public class CommentDaoTest {

	private static final Logger DLOG = LogManager.getLogger("development_file");
	private static Integer id;
	//private static Site commentControl;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Configurator.setLevel(DLOG.getName(), Level.TRACE);
		DLOG.log(Level.DEBUG, String.format("%n=============%nDEBUT DU TEST%n--------------%n%n"));
		id = null;
		//commentControl = null;
		/* Call SiteManager to create all sites */ 
		assertNull(id);
		UserManager.create();
		SiteManager.create();
		TopoManager.create();
		CommentManager.createOneComment();;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		/* deleting topos */
		CommentManager.deleteOneComment();
		TopoManager.delete();
		SiteManager.delete();
		UserManager.delete();
		/* log for debug */
		CommentManager.logList(CommentManager.getDao().getList(), "all comments after deleting");
		DLOG.log(Level.DEBUG, String.format("%nFIN DU TEST%n=============%n%n"));
	}

	/**
	 * Basic test check that we created some comments even if we tested to deleted one... more than (ids.length - 1)
	 */
	@Test
	public void test() {
		assertEquals(CommentManager.getDao().getList().size(), 1);
	}

}
