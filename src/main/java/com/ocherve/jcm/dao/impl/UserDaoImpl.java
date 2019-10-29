package com.ocherve.jcm.dao.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Level;

import com.ocherve.jcm.dao.contract.UserDao;
import com.ocherve.jcm.model.Role;
import com.ocherve.jcm.model.User;
import com.ocherve.jcm.utils.JcmException;

/**
 * @author herve_dev
 *
 */
public class UserDaoImpl extends DaoImpl implements UserDao {

	@Override
	public User create(User user) {
		return (User) super.create(User.class, user);
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
	public User update(Integer id, Map<String,Object> fields) {
		User user = null;
		try {
			user = this.em.find(User.class, id);
			if ( user != null ) {
				this.em.getTransaction().begin();
				for (String field : fields.keySet()) {
					switch (field) {
						case "username":
							user.setUsername((String)fields.get(field));
							break;
						case "mailAddress" :
							user.setUsername((String)fields.get(field));
							break;
						case "password":
							user.setPassword((String)fields.get(field));
							break;
						case "token":
							user.setToken((String)fields.get(field));
							break;
						case "salt":
							user.setSalt((String)fields.get(field));
							break;
						case "tsAccess":
							Timestamp tsAccess = Timestamp.from(Instant.now());
							user.setTsAccess(tsAccess);
							break;
						case "role":
							user.setRole((Role)fields.get(field));
							break;
						case "roleId":
							Role role = getRole((Integer)fields.get(field));
							user.setRole(role);
							break;
						default :
					}
				}
				this.em.getTransaction().commit();
			}
		} catch (Exception e) {
			DLOG.log(Level.ERROR, User.class.getSimpleName() + " can not update object.");
			DLOG.log(Level.DEBUG, JcmException.formatStackTrace(e));
			if ( this.em.getTransaction().isActive() ) this.em.getTransaction().rollback();
		}	
		return user;
	}

	@Override
	public User update(User updatedUser) {
		return (User) super.update(User.class, updatedUser.getId(), updatedUser);
	}	
	
	@Override
	public boolean delete(Integer id) {
		return super.delete(User.class, id);
	}

	@Override
	public Role getRole(Integer id) {
		Role role = null;
		try {
			role = this.em.find(Role.class, id);
		} catch (Exception e) {
			DLOG.log(Level.ERROR, User.class.getSimpleName() + " can not update object.");
			DLOG.log(Level.DEBUG, JcmException.formatStackTrace(e));
		}
		return role;		
	}

}
