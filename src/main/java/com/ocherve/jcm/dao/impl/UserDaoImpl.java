package com.ocherve.jcm.dao.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;


import com.ocherve.jcm.dao.contract.UserDao;
import com.ocherve.jcm.model.Role;
import com.ocherve.jcm.model.User;

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
		return (User) super.update(User.class, id, fields);
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
		daoInit();
		Role role = em.find(Role.class, id);
		return role;		
	}

	//	protected Object setEntityProperties(Class<User> user, Map<String,String> fields) {
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
