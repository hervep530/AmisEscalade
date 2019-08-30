package com.ocherve.jcm.dao.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ocherve.jcm.dao.contract.CommentDao;
import com.ocherve.jcm.model.Comment;
import com.ocherve.jcm.model.User;

/**
 * @author herve_dev
 *
 */
class CommentDaoImpl extends DaoImpl implements CommentDao {

	@Override
	public Comment create(Comment comment) {
		return (Comment) super.create(Comment.class, comment);
	}

	@Override
	public Comment update(Integer id, Map<String, Object> fields) {
		return (Comment) super.update(Comment.class, id, fields);
	}

	@Override
	public Comment update(Comment comment) {
		comment.setTsModified(Timestamp.from(Instant.now()));
		return (Comment) super.update(Comment.class,comment.getId(), comment);
	}

	@Override
	public Comment get(Integer id) {
		Comment comment = (Comment) super.get(Comment.class, id);
		return comment;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> getList() {
		List<Comment> comments = null;
		try {
			comments = (List<Comment>) getListFromNamedQuery(Comment.class, "Comment.findAll", null);
		} catch (Exception e) {
			return null;
		}
		return comments;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> getByAuthor(Integer id) {
		List<Comment> comments = null;
		Map<String,Object> parameters = new HashMap<>();
		parameters.put("authorId", id);
		try {
			comments = (List<Comment>) getListFromNamedQuery(Comment.class, "Comment.findByAuthorId", parameters);
		} catch (Exception e) {
			return null;
		}
		return comments;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> getByAuthor(User author) {
		List<Comment> comments = null;
		Map<String,Object> parameters = new HashMap<>();
		parameters.put("author", author);
		try {
			comments = (List<Comment>) getListFromNamedQuery(Comment.class, "Comment.findByAuthor", parameters);
		} catch (Exception e) {
			return null;
		}
		return comments;
	}

	@Override
	public List<Comment> searchComment(String keyWord) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Integer id) {
		return delete(Comment.class, id);
	}

	@Override
	protected void setUpdateAttributes(Map<String,Object> fields) {
		for (String field : fields.keySet()) {
			switch (field) {
				case "content":
					((Comment) object).setContent((String)fields.get(field));
					break;
				case "user" :
					((Comment) object).setAuthor((User)fields.get(field));
					break;
				default :
			}
		}
		((Comment) object).setTsModified(Timestamp.from(Instant.now()));

	}

}
