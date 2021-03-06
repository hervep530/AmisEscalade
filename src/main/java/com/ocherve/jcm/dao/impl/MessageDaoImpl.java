package com.ocherve.jcm.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Level;

import com.ocherve.jcm.dao.contract.MessageDao;
import com.ocherve.jcm.model.Message;
import com.ocherve.jcm.utils.JcmException;

/**
 * @author herve_dev
 *
 */
class MessageDaoImpl extends DaoImpl implements MessageDao {

	@Override
	public Message create(Message message) {
		return (Message) create(Message.class, message);
	}

	@Override
	public Message update(Integer id, Map<String, Object> fields) {
		Message message = null;
		try {
			message = this.em.find(Message.class, id);
			if ( message != null ) {
				this.em.getTransaction().begin();
				for (String field : fields.keySet()) {
					switch (field) {
						case "content":
							message.setContent((String)fields.get(field));
							break;
						case "discussionMasked":
							message.setDiscussionMasked((Integer)fields.get(field));
							break;
						default :
							DLOG.log(Level.ERROR, "Field " + field + " is not defined in method setUpdateAttributes.");
					}
				}
				this.em.getTransaction().commit();
			}
		} catch (Exception e) {
			DLOG.log(Level.ERROR, Message.class.getSimpleName() + " can not update object.");
			DLOG.log(Level.DEBUG, JcmException.formatStackTrace(e));
			if ( this.em.getTransaction().isActive() ) this.em.getTransaction().rollback();
		}	
		return message;
	}

	@Override
	public Message update(Message message) {
		return (Message) this.update(Message.class, message.getId(), message);
	}

	@Override
	public Message get(Integer id) {
		return (Message) this.get(Message.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Message> getList() {
		try {
			return (List<Message>) getListFromNamedQuery(Message.class, "Message.findAll", null);			
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Message> getListFromNamedQuery(String queryName) {
		try {
			return (List<Message>) getListFromNamedQuery(Message.class, queryName, null);			
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Message> getListFromNamedQueryWithParameters(String queryName, Map<String, Object> fields) {
		try {
			return (List<Message>) getListFromNamedQuery(Message.class, queryName, fields);			
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Message> getListFromNamedQueryAndIdParameter(String namedQuery, Integer id) {
		try {
			return (List<Message>) getListFromNamedQueryAndIdParameter(Message.class, namedQuery, id);			
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean delete(Integer id) {
		return delete(Message.class, id);
	}

}
