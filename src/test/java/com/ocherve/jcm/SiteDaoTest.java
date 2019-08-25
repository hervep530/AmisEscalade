package com.ocherve.jcm;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ocherve.jcm.model.Cotation;
import com.ocherve.jcm.model.Site;

/**
 * @author herve_dev
 *
 */
public class SiteDaoTest {

		private static final Logger DLOG = LogManager.getLogger("development_file");
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
			SiteManager.create();
			siteControl = SiteManager.getSiteDao().get(SiteManager.getIds()[3]);
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
			/* Test if users deleted */
			for (int u = 0 ; u < siteIds.length ; u++) {			
				assertNull(SiteManager.getSiteDao().get(siteIds[u]));
			}
			SiteManager.LogSiteList(SiteManager.getSiteDao().getList(), "all sites created");
			DLOG.log(Level.DEBUG, String.format("%nFIN DU TEST%n=============%n%n"));
		}
		
		/**
		 * 
		 */
		@Test
		public void testDaoNotNull() {
			assertNotNull(SiteManager.getSiteDao());
		}

		/**
		 * 
		 */
		@Test
		public void Given5SitesInserted_When_CheckCountOfAllSite_Then_ResultIs5() {
			/* Log list of all sites for debug */
			List<Site> sites = SiteManager.getSiteDao().getList();
			SiteManager.LogSiteList(sites, "all sites created");
			assertEquals(sites.size(), SiteManager.getIds().length);
		}

		/**
		 * 
		 */
		@Test
		public void Given5SitesInserted_When_CheckHowManyAsCotationMaxGreaterThan_Then_ResultIs3() {
			Cotation cotationMax = Cotation.valueOf("8a"); 
			List<Site> sites = (List<Site>) SiteManager.getSiteDao().getSitesWhereCotationMaxGreaterThan(cotationMax);
			SiteManager.LogSiteList(sites, "sites with min cotation > 8a");
			assertEquals(sites.size(), 3);
		}

		/**
		 * 
		 */
		@Test
		public void Given5SitesInserted_When_CheckHowManyAsCotationMinLessThan_Then_ResultIs2() {
			Cotation cotationMin = Cotation.valueOf("4"); 
			List<Site> sites = (List<Site>) SiteManager.getSiteDao().getSitesWhereCotationMinLessThan(cotationMin);
			SiteManager.LogSiteList(sites, "sites with min cotation <=4");
			assertEquals(sites.size(), 2);
		}
		 

		/**
		 * Add, Display and Delete User
		@Test
		 */
		public void testUpdateSite4() {

			//createFirst();
			//debugSiteControl();

			/* Test userControl after creating user*/
			assertNotNull(id);
			assertNotNull(siteControl);
			//assertTrue(siteControl.getAuthor().getId() ==  3);
			//assertEquals(siteControl.getSlug(), "captaine");
			
			/* Test user when deleting */
			assertTrue(SiteManager.getSiteDao().delete(id));
			assertNull(SiteManager.getSiteDao().get(id));
		}

				

}
