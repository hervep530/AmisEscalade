package com.ocherve.jcm.dao.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Level;

import com.ocherve.jcm.dao.contract.TopoDao;
import com.ocherve.jcm.model.Site;
import com.ocherve.jcm.model.Topo;
import com.ocherve.jcm.model.User;
import com.ocherve.jcm.utils.JcmException;

class TopoDaoImpl extends DaoImpl implements TopoDao {

	@Override
	public Topo create(Topo topo) {
		return (Topo) super.create(Topo.class, topo);
	}

	@Override
	public Topo get(Integer id) {
		Topo topo = (Topo) super.get(Topo.class, id);
		return topo;
	}
	
	@Override
	public String getSlug(Integer id) {
		Topo topo = (Topo) super.get(Topo.class, id);
		return topo.getSlug();
	}	

	@SuppressWarnings("unchecked")
	@Override
	public List<Topo> getList() {
		List<Topo> topos = null;
		try {
			topos = (List<Topo>) getListFromNamedQuery(Topo.class, "Topo.findAll", null);
		} catch (Exception e) {
			return null;
		}
		return topos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Topo update(Integer id, Map<String, Object> fields) {
		Topo topo = null;
		try {
			topo = this.em.find(Topo.class, id);
			if ( topo != null ) {
				this.em.getTransaction().begin();
				for (String field : fields.keySet()) {
					switch (field) {
						case "name":
							topo.setName((String)fields.get(field));
							break;
						case "title" :
							topo.setTitle((String)fields.get(field));
							break;
						case "summary":
							topo.setSummary((String)fields.get(field));
							break;
						case "writer":
							topo.setWriter((String)fields.get(field));
							break;
						case "writedAt":
							topo.setWritedAt((String)fields.get(field));
							break;
						case "author":
							topo.setAuthor((User)fields.get(field));
							break;
						case "addingSite":
							topo.addSite((Site)fields.get(field));
							break;
						case "removingSite":
							topo.removeSite((Site)fields.get(field));
							break;
						case "addingSites":
							// Site ids must be given under string form "number1:number2:..."
							topo.addSites((List<Site>)fields.get(field));
							break;
						case "addingSitesGivenIds":
							// Site ids must be given under string form "number1:number2:..."
							topo.addSites((String)fields.get(field));
							break;
						case "published":
							topo.setPublished((Boolean)fields.get(field));
							break;
						case "available":
							topo.setAvailable((Boolean)fields.get(field));
							break;
						default :
					}
				}
				topo.setTsModified(Timestamp.from(Instant.now()));
				this.em.getTransaction().commit();
			}
		} catch (Exception e) {
			DLOG.log(Level.ERROR, Topo.class.getSimpleName() + " can not update object.");
			DLOG.log(Level.DEBUG, JcmException.formatStackTrace(e));
			if ( this.em.getTransaction().isActive() ) this.em.getTransaction().rollback();
		}	
		return topo;
	}

	@Override
	public Topo update(Topo topo) {
		topo.setTsModified(Timestamp.from(Instant.now()));
		return (Topo) super.update(Topo.class,topo.getId(), topo);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Topo> getToposByPublishingStatus(Boolean published) {
		List<Topo> topos = null;
		Map<String,Object> parameters = new HashMap<>();
		parameters.put("published", published);
		try {
			topos = (List<Topo>) getListFromNamedQuery(Topo.class, "Topo.findByPublishingStatus", parameters);
		} catch (Exception e) {
			return null;
		}
		return topos;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Topo> getByAuthor(Integer id) {
		List<Topo> topos = null;
		Map<String,Object> parameters = new HashMap<>();
		parameters.put("authorId", id);
		try {
			topos = (List<Topo>) getListFromNamedQuery(Topo.class, "Topo.findByAuthor", parameters);
		} catch (Exception e) {
			return null;
		}
		return topos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Topo> getBySite(Integer id) {
		List<Topo> topos = null;
		Map<String,Object> parameters = new HashMap<>();
		parameters.put("siteId", id);
		try {
			topos = (List<Topo>) getListFromNamedQuery(Topo.class, "Topo.findBySite", parameters);
		} catch (Exception e) {
			return null;
		}
		return topos;
	}
	

	@Override
	public boolean delete(Integer id) {
		return super.delete(Topo.class, id);
	}

	@Override
	public List<Topo> getToposByAvailability(Boolean available) {
		// TODO Auto-generated method stub
		return null;
	}

}
