package com.ocherve.jcm.model;

import com.ocherve.jcm.dao.DaoProxy;
import com.ocherve.jcm.dao.contract.SiteDao;

import java.io.Serializable;

import javax.persistence.*;


	/**
	 * The persistent class for the jcm_cotation database table.
	 * 
	 */
	@Entity
	@Table(name="jcm_cotation")
	@NamedQueries({
		@NamedQuery(name="Cotation.findAll", query="SELECT j FROM Cotation j"),
		@NamedQuery(name="Cotation.getByLabel", query="SELECT j FROM Cotation j WHERE j.label like :cotationLabel")
	})
	public class Cotation implements Serializable {
		private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		private Integer id;

		@Column(unique = true)
		private String label;
		
/*
		//bi-directional many-to-one association to JcmSite
		@OneToMany(mappedBy="cotationMin")
		private List<Site> sitesWithCotationMin;

		//bi-directional many-to-one association to JcmSite
		@OneToMany(mappedBy="cotationMax")
		private List<Site> sitesWithCotationMax;
*/
		
		/**
		 * Constructor
		 */
		public Cotation() {
		}

		/**
		 * Constructor
		 * @param id 
		 * @param label 
		 */
		@SuppressWarnings("unused")
		private Cotation(Integer id, String label) {
			this.id = id;
			this.label = label;
		}


		/**
		 * @return id
		 */
		public Integer getId() {
			return this.id;
		}

		/**
		 * @param id
		 */
		public void setId(Integer id) {
			this.id = id;
		}

		/**
		 * @return label
		 */
		public String getLabel() {
			return this.label;
		}

		/**
		 * @param label
		 */
		public void setLabel(String label) {
			this.label = label;
		}

		/**
		 * @return site with a min cotation 
		public List<Site> getSitesWithCotationMin() {
			return this.sitesWithCotationMin;
		}
		 */

		/**
		 * @param sitesWithCotationMin
		public void setSitesWithCotationMin(List<Site> sitesWithCotationMin) {
			this.sitesWithCotationMin = sitesWithCotationMin;
		}
		 */

		/**
		 * @param site
		 * @return site added to the list
		public Site addSiteWithCotationMin(Site site) {
			getSitesWithCotationMin().add(site);
			site.setCotationMin(this);

			return site;
		}
		 */

		/**
		 * @param site
		 * @return site removed from the list
		public Site removeSiteWithCotationMin(Site site) {
			getSitesWithCotationMin().remove(site);
			site.setCotationMin(null);

			return site;
		}
		 */

		/**
		 * @return site with a min cotation 
		public List<Site> getSitesWithCotationMax() {
			return this.sitesWithCotationMax;
		}
		 */

		/**
		 * @param sitesWithCotationMax
		public void setSitesWithCotationMax(List<Site> sitesWithCotationMax) {
			this.sitesWithCotationMax = sitesWithCotationMax;
		}
		 */

		/**
		 * @param site
		 * @return site added to the list
		public Site addSiteWithCotationMax(Site site) {
			getSitesWithCotationMax().add(site);
			site.setCotationMax(this);

			return site;
		}
		 */

		/**
		 * @param site
		 * @return site removed from the list
		public Site removeSiteWithCotationMax(Site site) {
			getSitesWithCotationMax().remove(site);
			site.setCotationMax(null);

			return site;
		}
		 */
		
		/**
		 * @param cotationName
		 * @return this cotation
		 */
		public static Cotation valueOf(String cotationName) {
			try {
				return ((SiteDao) DaoProxy.getInstance().getSiteDao()).getCotationByLabel(cotationName);
			} catch (Exception e) {
				
			}
			return null;
		}

}
