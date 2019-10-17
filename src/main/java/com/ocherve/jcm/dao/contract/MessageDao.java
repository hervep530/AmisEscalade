package com.ocherve.jcm.dao.contract;

import java.util.List;
import java.util.Map;

import com.ocherve.jcm.model.Message;

/**
 * @author herve_dev
 *
 * DAO specific Interface for Message module
 */
public interface MessageDao extends Dao{


	/**
	 * @param message
	 * @return message created
	 */
	Message create( Message message );

	/**
	 * @param id
	 * @param fields
	 * @return message
	 */
	Message update(Integer id, Map<String, Object> fields);

	/**
	 * @param message
	 * @return message updated
	 */
	Message update(Message message);

	/**
	 * @param id
	 * @return message requested
	 */
	Message get(Integer id );
		
	/**
	 * @return message list
	 */
	List<Message> getList();
	
	/**
	 * @param namedQuery 
	 * @param id 
	 * @return message list
	 */
	List<Message> getListFromNamedQueryAndIdParameter(String namedQuery, Integer id);
	
	/**
	 * @param queryName 
	 * @param fields 
	 * @return message list
	 */
	List<Message> getListFromNamedQueryWithParameters(String queryName, Map<String,Object> fields);	
	
	/**
	 * @param id
	 * @return true if delete , false if not
	 */
	boolean delete (Integer id);

	/**
	 * @param queryName
	 * @return list of Messages
	 */
	List<Message> getListFromNamedQuery(String queryName);
	
}
