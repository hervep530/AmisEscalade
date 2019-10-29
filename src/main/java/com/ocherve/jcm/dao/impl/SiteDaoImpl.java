package com.ocherve.jcm.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.logging.log4j.Level;

import com.ocherve.jcm.dao.contract.SiteDao;
import com.ocherve.jcm.model.Comment;
import com.ocherve.jcm.model.Cotation;
import com.ocherve.jcm.model.Site;
import com.ocherve.jcm.utils.JcmException;

/**
 * @author herve_dev
 *
 */
class SiteDaoImpl extends DaoImpl implements SiteDao {

	@Override
	public Site create(Site site) {
		return (Site) super.create(Site.class, site);
	}

	@Override
	public Site get(Integer id) {
		Site site = (Site) super.get(Site.class, id);
		return site;
	}

	@Override
	public String getSlug(Integer id) {
		Site site = (Site) super.get(Site.class, id);
		return site.getSlug();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Site> getList() {
		List<Site> sites = null;
		try {
			sites = (List<Site>) getListFromNamedQuery(Site.class, "Site.findAll", null);
		} catch (Exception e) {
			return null;
		}
		return sites;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Site> getSitesWhereCotationMaxGreaterThan(Cotation cotationMax) {
		List<Site> sites = null;
		Map<String,Object> parameters = new HashMap<>();
		parameters.put("cotationMax", cotationMax.getId());
		try {
			sites = (List<Site>) getListFromNamedQuery(Site.class, "Site.findCotationMaxGreaterThan", parameters);
		} catch (Exception e) {
			return null;
		}
		return sites;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Site> getSitesWhereCotationMinLessThan(Cotation cotationMin) {
		List<Site> sites = null;
		Map<String,Object> parameters = new HashMap<>();
		parameters.put("cotationMin", cotationMin.getId());
		try {
			sites = (List<Site>) getListFromNamedQuery(Site.class, "Site.findCotationMinLessThan", parameters);
		} catch (Exception e) {
			return null;
		}
		return sites;
	}

	@Override
	public Site update(Integer id, Map<String, Object> fields) {
		Site site = null;
		try {
			site = this.em.find(Site.class, id);
			if ( site != null ) {
				this.em.getTransaction().begin();
				for (String field : fields.keySet()) {
					switch (field) {
						case "country":
							site.setCountry((String)fields.get(field));
							break;
						case "department" :
							site.setDepartment((String)fields.get(field));
							break;
						case "block":
							site.setBlock((Boolean)fields.get(field));
							break;
						case "cliff":
							site.setCliff((Boolean)fields.get(field));
							break;
						case "wall":
							site.setWall((Boolean)fields.get(field));
							break;
						case "minHeight":
							site.setMinHeight((Integer)fields.get(field));
							break;
						case "maxHeight":
							site.setMaxHeight((Integer)fields.get(field));
							break;
						case "pathsNumber":
							site.setPathsNumber((Integer)fields.get(field));
							break;
						case "orientation":
							site.setOrientation((String)fields.get(field));
							break;
						case "cotationMin":
							site.setCotationMin((Cotation)fields.get(field));
							break;
						case "cotationMax":
							site.setCotationMax((Cotation)fields.get(field));
							break;
						case "friendTag":
							site.setFriendTag((Boolean)fields.get(field));
							break;
						case "removeComment":
							site.removeComment((Comment)fields.get(field));
							break;
						default :
					}
				}
				this.em.getTransaction().commit();
			}
		} catch (Exception e) {
			DLOG.log(Level.ERROR, Site.class.getSimpleName() + " can not update object.");
			DLOG.log(Level.DEBUG, JcmException.formatStackTrace(e));
			if ( this.em.getTransaction().isActive() ) this.em.getTransaction().rollback();
		}
		return site;
	}

	@Override
	public Site update(Site site) {
		return (Site) super.update(Site.class,site.getId(), site);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Cotation> getCotations() {
		try {
			return (List<Cotation>) getListFromNamedQuery(Cotation.class, "Cotation.findAll", null);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Cotation getCotation(Integer id) {
		return (Cotation) super.get(Cotation.class, id);
	}

	@Override
	public Cotation getCotationByLabel(String cotationName) {
		Cotation cotation = null;
		Map<String,Object> parameters = new HashMap<>();
		parameters.put("cotationLabel", cotationName);
		try {
			@SuppressWarnings("unchecked")
			List<Cotation> cotations = (List<Cotation>) getListFromNamedQuery(Cotation.class, "Cotation.getByLabel", parameters);
			if ( cotations.size() == 1 ) cotation = cotations.get(0);
		} catch (Exception e) {
			return null;
		}
		return cotation;
	}

	@Override
	public boolean delete(Integer id) {
		return super.delete(Site.class, id);
	}
	
}
