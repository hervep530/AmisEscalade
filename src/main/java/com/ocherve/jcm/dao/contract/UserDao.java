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
	 * @param updatedUser
	 * @return user
	 */
	User update(User updatedUser);
	
	/**
	 * @param id
	 * @param fields
	 * @return user
	 */
	User update(Integer id, Map<String, Object> fields);
	
	/**
	 * @param id
	 * @return user requested
	 */
	User get(Integer id );

	/**
	 * @param queryName 
	 * @param parameters 
	 * @return id
	 */
	Integer getIdFromNamedQuery(String queryName, Map<String,Object> parameters);		

	/**
	 * @return user list
	 */
	List<User> getList();

	/**
	 * @param queryName 
	 * @param parameters 
	 * @return user list
	 */
	List<User> getListFromNamedQuery(String queryName, Map<String,Object> parameters);
	
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
