package com.ocherve.jcm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ocherve.jcm.model.Site;
import com.ocherve.jcm.model.Topo;
import com.ocherve.jcm.model.User;

/**
 * @author herve_dev
 *
 */
public class TopoDaoTest {

	private static final Logger DLOG = LogManager.getLogger("development_file");
	private static Integer id;
	private static Topo topoControl;

	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Configurator.setLevel(DLOG.getName(), Level.TRACE);
		DLOG.log(Level.DEBUG, String.format("%n=============%nDEBUT DU TEST%n--------------%n%n"));
		id = null;
		topoControl = null;
		/* Call TopoManager to create all topos */ 
		assertNull(id);
		UserManager.create();
		SiteManager.create();
		TopoManager.create();
		TopoManager.LogTopoList(TopoManager.getDao().getList(), "all topos created");
		topoControl = TopoManager.getDao().get(TopoManager.getIds()[2]);
		TopoManager.logTopo(topoControl);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		Integer[] topoIds = TopoManager.getIds();
		/* deleting topos */
		TopoManager.delete();
		SiteManager.delete();
		UserManager.delete();
		/* Test if topos deleted */
		for (int u = 0 ; u < topoIds.length ; u++) {			
			assertNull(TopoManager.getDao().get(topoIds[u]));
		}
		TopoManager.LogTopoList(TopoManager.getDao().getList(), "all topos after deleting");
		DLOG.log(Level.DEBUG, String.format("%nFIN DU TEST%n=============%n%n"));
	}
	
	/**
	 * 
	 */
	@Test
	public void testDaoNotNull() {
		assertEquals(TopoManager.getIds().length,3);
		assertNotNull(TopoManager.getDao());
	}
	
	/**
	 * 
	 */
	@Test
	public void Given3ToposInserted_When_CheckHowManyTopoAreAvailable_Then_ResultIs2() {
		assertEquals(TopoManager.getDao().getToposByAvailability(true).size(), 2);		
	}

	/**
	 * 
	 */
	@Test
	public void Given2ndTopo_When_ChangingAuthorToAnonyous_Then_AuthorUsernameEqualsAnonymous() {
		/* Get 2nd topo, user Anonymous, keep oldAuthor and update author as anonymous */
		Topo topo = TopoManager.getDao().get(TopoManager.getIds()[1]);
		User oldAuthor = topo.getAuthor();
		topo.setAuthor(UserManager.getDao().get(1));
		TopoManager.getDao().update(topo);
		/* get topo again with topoControl and test author.username */
		TopoManager.LogTopoList(TopoManager.getDao().getByAuthor(1), "all topos with anonymous author");
		topoControl = TopoManager.getDao().get(TopoManager.getIds()[1]);
		assertEquals(topoControl.getAuthor().getUsername(), "anonymous");
		assertEquals(TopoManager.getDao().getByAuthor(1).size(), 2);
		/* Get 2nd topo again, and update it with old author */
		topo = TopoManager.getDao().get(TopoManager.getIds()[1]);
		topo.setAuthor(oldAuthor);
		TopoManager.getDao().update(topo);
		/* get topo again with topoControl and test author.username */
		topoControl = TopoManager.getDao().get(TopoManager.getIds()[1]);
		assertEquals(topoControl.getAuthor().getUsername(), "chachristi");
		assertEquals(TopoManager.getDao().getByAuthor(1).size(), 1);
	}
	
	/**
	 * 
	 */
	@Test
	public void Given3ToposPublished_When_UnpublishedTheFirstOne_Then_CountOfPublishedToposEqualsTo2() {
		/* Test count of topo with published status = 3 */
		assertEquals(TopoManager.getDao().getToposByPublishingStatus(true).size(), 3);
		/* update published status to false for 1rst topo, get it from dao with topoControl and keep last modified date */
		Map<String,Object> fields = new HashMap<>();
		fields.put("published", false);
		TopoManager.getDao().update(TopoManager.getIds()[0], fields);
		topoControl = TopoManager.getDao().get(TopoManager.getIds()[0]);
		Timestamp lastModified = topoControl.getTsModified();
		/* Log topoControl for debug, test count of published status and published status in topoControl */
		TopoManager.logTopo(topoControl);
		assertEquals(TopoManager.getDao().getToposByPublishingStatus(true).size(), 2);
		assertEquals(topoControl.isPublished(), false);
		/* after 60s Modified published status again... come back to true*/
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		topoControl = null;
		fields.put("published", true);
		TopoManager.getDao().update(TopoManager.getIds()[0], fields);
		topoControl = TopoManager.getDao().get(TopoManager.getIds()[0]);
		TopoManager.logTopo(topoControl);
		assertEquals(TopoManager.getDao().getToposByPublishingStatus(true).size(), 3);
		assertEquals(topoControl.isPublished(), true);
		assertTrue( topoControl.getTsModified().after(lastModified ));
	}
	
	/**
	 * 
	 */
	@Test
	public void GivenTopoWithSiteCantobre_When_ChangeSiteToAutoire_Then_SiteNameEqualsAutoire() {
		/* Get 2nd topo, user Anonymous, keep oldAuthor and update author as anonymous */
		Topo topo = TopoManager.getDao().get(TopoManager.getIds()[1]);
		Timestamp lastModified = topo.getTsModified();
		Site autoire = SiteManager.getDao().get(SiteManager.getIds()[0]);
		topo.setTitle("Lot en spot");
		topo.setName("Lot en spot");
		topo.setSummary("Topo sur le site d'Autoire dans le lot");
		topo.setSite(autoire);
		TopoManager.getDao().update(topo);
		/* get topo again with topoControl and test author.username */
		topoControl = TopoManager.getDao().get(TopoManager.getIds()[1]);
		TopoManager.logTopo(topoControl);
		assertEquals(topoControl.getSite().getName(), "Autoire");
		assertEquals(topoControl.getTitle(), "Lot en spot");
		assertTrue( topoControl.getTsModified().after(lastModified));
	}
	
}
