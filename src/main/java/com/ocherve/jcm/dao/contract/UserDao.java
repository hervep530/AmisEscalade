package com.ocherve.jcm.dao.contract;

import java.util.List;
import java.util.Map;

import com.ocherve.jcm.model.Role;
import com.ocherve.jcm.model.User;


/**
 * @author herve_dev
 *
 */
public interface UserDao extends Dao {

	/**
	 * @param user
	 * @return user created
	 */
	User create( User user );
	
	
	/**
	 * @param id
	 * @return user requested
	 */
	User get(Integer id );
	
	/**
	 * @param attributes
	 * @param clauses
	 * @return list of user
	 */
	List<User> getFromFilteredQuery(Map<String,String> clauses);
	
	/**
	 * @return user list
	 */
	List<User> getList();
	
	/**
	 * @param id
	 * @return true if delete , false if not
	 */
	boolean delete (Integer id);
	
	/**
	 * @param id
	 * @return role
	 */
	Role getRole(Integer id);
	
}
