package com.ocherve.jcm.dao.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ocherve.jcm.dao.contract.TopoDao;
import com.ocherve.jcm.model.Site;
import com.ocherve.jcm.model.Topo;
import com.ocherve.jcm.model.User;

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

	@Override
	public Topo update(Integer id, Map<String, Object> fields) {
		return (Topo) super.update(Topo.class, id, fields);
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
	protected void setUpdateAttributes(Map<String,Object> fields) {
		for (String field : fields.keySet()) {
			switch (field) {
				case "name":
					((Topo) object).setName((String)fields.get(field));
					break;
				case "title" :
					((Topo) object).setTitle((String)fields.get(field));
					break;
				case "summary":
					((Topo) object).setSummary((String)fields.get(field));
					break;
				case "writer":
					((Topo) object).setWriter((String)fields.get(field));
					break;
				case "writedAt":
					((Topo) object).setWritedAt((String)fields.get(field));
					break;
				case "author":
					((Topo) object).setAuthor((User)fields.get(field));
					break;
				case "site":
					((Topo) object).setSite((Site)fields.get(field));
					break;
				case "published":
					((Topo) object).setPublished((Boolean)fields.get(field));
					break;
				default :
			}
		}
		((Topo) object).setTsModified(Timestamp.from(Instant.now()));
	}

	@Override
	public List<Topo> getToposByAvailability(Boolean available) {
		// TODO Auto-generated method stub
		return null;
	}

}
