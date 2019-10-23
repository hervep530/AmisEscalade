package com.ocherve.jcm.service.factory;

import com.ocherve.jcm.service.Delivry;
import com.ocherve.jcm.service.Parameters;

/**
 * CommentService Interface
 * 
 * @author herve_dev
 *
 */
public interface CommentService extends Service {

	/**
	 * Getting data to provide Comment form
	 * 
	 * @param parameters
	 * @return Delivry - as result of action
	 */
	Delivry getCommentForm(Parameters parameters);
	
	/**
	 * Getting data and calling instruction to delete a comment
	 * 
	 * @param parameters
	 * @return Delivry - as result of action
	 */
	Delivry deleteComment(Parameters parameters);
	
	/**
	 * Getting data from posted formular
	 * 
	 * @param parameters
	 * @return Delivry - as result of action
	 */
	Delivry postCommentForm(Parameters parameters);

}
