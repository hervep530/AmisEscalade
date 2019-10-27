package com.ocherve.jcm;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ocherve.jcm.model.Cotation;
import com.ocherve.jcm.model.Site;
import com.ocherve.jcm.model.User;

/**
 * @author herve_dev
 *
 */
public class SiteDaoTest {

		private static final Logger DLOG = LogManager.getLogger("test_file");
		private static Integer id;
		private static Site siteControl;

		
		/**
		 * @throws java.lang.Exception
		 */
		@BeforeClass
		public static void setUpBeforeClass() throws Exception {
			Configurator.setLevel(DLOG.getName(), Level.TRACE);
			DLOG.log(Level.DEBUG, String.format("%n=============%nDEBUT DU TEST%n--------------%n%n"));
			id = null;
			siteControl = null;
			/* Call SiteManager to create all sites */ 
			assertNull(id);
			UserManager.create();
			SiteManager.create();
			siteControl = SiteManager.getDao().get(SiteManager.getIds()[3]);
			SiteManager.logSite(siteControl);
		}

		/**
		 * @throws java.lang.Exception
		 */
		@AfterClass
		public static void tearDownAfterClass() throws Exception {
			Integer[] siteIds = SiteManager.getIds();
			/* deleting sites */
			SiteManager.delete();
			UserManager.delete();
			/* Test if users deleted */
			for (int u = 0 ; u < siteIds.length ; u++) {			
				assertNull(SiteManager.getDao().get(siteIds[u]));
			}
			SiteManager.LogSiteList(SiteManager.getDao().getList(), "all sites created");
			DLOG.log(Level.DEBUG, String.format("%nFIN DU TEST%n=============%n%n"));
		}
		
		/**
		 * 
		 */
		@Test
		public void testDaoNotNull() {
			assertNotNull(SiteManager.getDao());
		}

		/**
		 * 
		 */
		@Test
		public void Given5SitesInserted_When_CheckCountOfAllSite_Then_ResultIs5() {
			/* Log list of all sites for debug */
			List<Site> sites = SiteManager.getDao().getList();
			SiteManager.LogSiteList(sites, "all sites created");
			assertEquals(sites.size(), SiteManager.getIds().length);
		}

		/**
		 * 
		 */
		@Test
		public void Given5SitesInserted_When_CheckHowManyAsCotationMaxGreaterThan_Then_ResultIs3() {
			Cotation cotationMax = Cotation.valueOf("8a"); 
			List<Site> sites = (List<Site>) SiteManager.getDao().getSitesWhereCotationMaxGreaterThan(cotationMax);
			SiteManager.LogSiteList(sites, "sites with min cotation > 8a");
			assertEquals(sites.size(), 3);
		}

		/**
		 * 
		 */
		@Test
		public void Given5SitesInserted_When_CheckHowManyAsCotationMinLessThan_Then_ResultIs2() {
			Cotation cotationMin = Cotation.valueOf("4"); 
			List<Site> sites = (List<Site>) SiteManager.getDao().getSitesWhereCotationMinLessThan(cotationMin);
			SiteManager.LogSiteList(sites, "sites with min cotation <=4");
			assertEquals(sites.size(), 2);
		}
		 

		/**
		 * Test update author with update(site)
		 */
		@Test
		public void testUpdate4thSiteReplaceUserAnonymousBy2ndUserCreated() {
			/* get id from SiteManager and idAuthor from UserManager */
			id = SiteManager.getIds()[3];
			Integer idAuthor = UserManager.getIds()[1];
			/* Get site and new author , modify author in site and update it with siteDao */
			Site site = SiteManager.getDao().get(id); 
			User newAuthor = UserManager.getDao().get(idAuthor);
			site.setAuthor(newAuthor);
			SiteManager.getDao().update(site);
			/* Get user again from dao and log it for debug */
			siteControl = SiteManager.getDao().get(id);
			SiteManager.logSite(siteControl);

			/* Test userControl after creating user*/
			assertNotNull(siteControl);
			assertEquals(siteControl.getName(),  "Les Gorges du Loup");		
			assertEquals(siteControl.getAuthor().getUsername(), "alavant");			
		}

		/**
		 * Test update cotationMax with update(id, parameters)
		 */
		@Test
		public void testUpdate2ndSiteReplaceCotationMaxby8c() {
			Map<String,Object> parameters = new HashMap<>();
			/* get id from SiteManager, get cotation, set parameter cotationMax, and update */
			id = SiteManager.getIds()[1];
			Cotation newCotation = Cotation.valueOf("8c");
			parameters.put("cotationMax", newCotation);
			SiteManager.getDao().update(id,parameters);
			
			/* Get user again from dao and log it for debug */
			siteControl = SiteManager.getDao().get(id);
			SiteManager.logSite(siteControl);

			/* Test userControl after creating user*/
			assertNotNull(siteControl);
			assertEquals(siteControl.getName(),  "Cantobre");		
			assertEquals(siteControl.getCotationMax().getLabel(), "8c");			
		}

				

}
