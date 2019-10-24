/**
 * 
 */
package com.ocherve.jcm.dao.hibernate.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.hibernate.SessionFactory;

import com.ocherve.jcm.dao.contract.UserDao;
import com.ocherve.jcm.model.Role;
import com.ocherve.jcm.model.User;
import com.ocherve.jcm.utils.JcmException;

/**
 * @author herve_dev
 *
 */
public class UserDaoHibernateImpl extends DaoHibernateImpl implements UserDao {
	
	/**
	 * Constructor with shared session factory
	 * 
	 * @param sessionFactory
	 */
	public UserDaoHibernateImpl(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public User create(User user) {
		return (User) super.create(User.class, user);
	}

	@Override
	public User update(User updatedUser) {
		return (User) super.update(User.class, updatedUser.getId(), updatedUser);
	}

	@Override
	public User update(Integer id, Map<String, Object> fields) {
		return (User) super.update(User.class, id, fields);
	}

	@Override
	public User get(Integer id) {
		User user = (User) super.get(User.class, id);
		return user;
	}

	@Override
	public Integer getIdFromNamedQuery(String queryName, Map<String, Object> parameters) {
		try {
			return ((Integer) super.getColumnsFromNamedQuery(Integer.class, queryName, parameters));
		} catch (Exception e) {
			return 0;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getList() {
		List<User> users = null;
		try {
			//users = (List<User>) super.getList(User.class);
			users = (List<User>) super.getListFromNamedQuery(User.class, "User.findAll", null);
		} catch (Exception e) {
			return null;
		}
		return users;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getListFromNamedQuery(String queryName, Map<String, Object> parameters) {
		List<User> users = null;
		try {
			users = (List<User>) super.getListFromNamedQuery(User.class, queryName, parameters);
		} catch (Exception e) {
			return null;
		}
		return users;
	}

	@Override
	public boolean delete(Integer id) {
		return super.delete(User.class, id);
	}

	@Override
	public Role getRole(Integer id) {
		// Opening session and trying to get object
		if ( ! daoInit() ) return null ;
		Role role = null;
		this.session = this.sessionFactory.openSession(); 
		try { 
			// Getting object
			role = this.session.get(Role.class, id);
		} catch ( Exception e ) {
			DLOG.log(Level.ERROR, Role.class.getSimpleName() + " can not get role with id." + id);
			DLOG.log(Level.DEBUG, String.format(e.getMessage() + JcmException.formatStackTrace(e)));
		} finally { 
			// closing session
		    if ( this.session.isOpen() ) this.session.close(); 
		} 
		// Closing factory and return object
		this.sessionFactory.close();
		return role;
	}

	@Override
	protected void setUpdateAttributes(Map<String,Object> fields) {
		for (String field : fields.keySet()) {
			switch (field) {
				case "username":
					((User) object).setUsername((String)fields.get(field));
					break;
				case "mailAddress" :
					((User) object).setUsername((String)fields.get(field));
					break;
				case "password":
					((User) object).setPassword((String)fields.get(field));
					break;
				case "token":
					((User) object).setToken((String)fields.get(field));
					break;
				case "salt":
					((User) object).setSalt((String)fields.get(field));
					break;
				case "tsAccess":
					Timestamp tsAccess = Timestamp.from(Instant.now());
					((User) object).setTsAccess(tsAccess);
					break;
				case "role":
					((User) object).setRole((Role)fields.get(field));
					break;
				case "roleId":
					Role role = getRole((Integer)fields.get(field));
					((User) object).setRole(role);
					break;
				default :
			}
		}
	}

}
