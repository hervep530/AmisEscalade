package com.ocherve.jcm.dao.contract;

import java.util.List;
import java.util.Map;

import com.ocherve.jcm.model.Comment;
import com.ocherve.jcm.model.User;

/**
 * @author herve_dev
 *
 */
public interface CommentDao extends Dao {

	/**
	 * @param comment
	 * @return comment created
	 */
	Comment create( Comment comment );

	/**
	 * @param id
	 * @param fields
	 * @return topo
	 */
	Comment update(Integer id, Map<String, Object> fields);

	/**
	 * @param comment
	 * @return comment updated
	 */
	Comment update(Comment comment);

	/**
	 * @param id
	 * @return comment requested
	 */
	Comment get(Integer id );
		
	/**
	 * @return comments list
	 */
	List<Comment> getList();
	
	/**
	 * @param userId 
	 * @return comments list
	 */
	List<Comment> getByAuthor(Integer userId);

	/**
	 * @param author 
	 * @return comments list
	 */
	List<Comment> getByAuthor(User author);

	/**
	 * @param keyWord
	 * @return comments list
	 */
	List<Comment> searchComment(String keyWord);
	
	/**
	 * @param id
	 * @return true if delete , false if not
	 */
	boolean delete (Integer id);

}
