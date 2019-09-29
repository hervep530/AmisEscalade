package com.ocherve.jcm.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ocherve.jcm.dao.contract.SiteDao;
import com.ocherve.jcm.model.Cotation;
import com.ocherve.jcm.model.Site;

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
/*
	@Override
	public Integer getIdFromNamedQuery(String queryName, Map<String, Object> parameters) {
		try {
			return ((Integer) super.getColumnsFromNamedQuery(Integer.class, queryName, parameters));
		} catch (Exception e) {
			return 0;
		}
	}
*/
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
		return (Site) super.update(Site.class, id, fields);
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
	
	@Override
	protected void setUpdateAttributes(Map<String,Object> fields) {
		for (String field : fields.keySet()) {
			switch (field) {
				case "country":
					((Site) object).setCountry((String)fields.get(field));
					break;
				case "department" :
					((Site) object).setDepartment((String)fields.get(field));
					break;
				case "block":
					((Site) object).setBlock((Boolean)fields.get(field));
					break;
				case "cliff":
					((Site) object).setCliff((Boolean)fields.get(field));
					break;
				case "wall":
					((Site) object).setWall((Boolean)fields.get(field));
					break;
				case "minHeight":
					((Site) object).setMinHeight((Integer)fields.get(field));
					break;
				case "maxHeight":
					((Site) object).setMaxHeight((Integer)fields.get(field));
					break;
				case "pathsNumber":
					((Site) object).setPathsNumber((Integer)fields.get(field));
					break;
				case "orientation":
					((Site) object).setOrientation((String)fields.get(field));
					break;
					
				case "cotationMin":
					((Site) object).setCotationMin((Cotation)fields.get(field));
					break;
				case "cotationMax":
					((Site) object).setCotationMax((Cotation)fields.get(field));
					break;

				case "friendTag":
					((Site) object).setFriendTag((Boolean)fields.get(field));
					break;
				case "roleId":
					/*
					Role role = getRole((Integer)fields.get(field));
					((User) object).setRole(role);
					break;
					*/
				default :
			}
		}
	}

}
