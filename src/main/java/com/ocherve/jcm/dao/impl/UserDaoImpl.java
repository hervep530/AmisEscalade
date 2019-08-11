package com.ocherve.jcm.dao.impl;

import java.util.List;

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
	public List<User> getList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Integer id) {
		return super.delete(User.class, id);
	}

	@Override
	public Role getRole(Integer id) {
		init();
		Role role = em.find(Role.class, id);
		return role;		
	}

}
