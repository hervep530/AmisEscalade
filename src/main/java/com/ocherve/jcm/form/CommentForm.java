package com.ocherve.jcm.form;

import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Level;

import com.ocherve.jcm.dao.DaoProxy;
import com.ocherve.jcm.dao.contract.CommentDao;
import com.ocherve.jcm.dao.contract.SiteDao;
import com.ocherve.jcm.dao.contract.UserDao;
import com.ocherve.jcm.model.Comment;
import com.ocherve.jcm.model.Reference;
import com.ocherve.jcm.model.User;

/**
 * @author herve_dev
 *
 */
public class AddCommentForm extends Form {


	/**
	 * Field name for  the reference id
	 */
	public final static String REFERENCE_ID_FIELD = "commentReferenceId";

	/**
	 * Field name for  the reference type
	 */
	public final static String REFERENCE_TYPE_FIELD = "commentReferenceType";
	
	/**
	 * Field name for content
	 */
	public final static String CONTENT_FIELD = "commentContent";

	private CommentDao commentDao;
	private SiteDao siteDao;
	private UserDao userDao;
	
	private Reference reference;
	private User author;
	private String content;
	private Comment comment;

    
    /**
     * Constructor
     */
	public AddCommentForm() {
		super();
	}
	
    /**
     * Constructor
     * @param request 
     */
    public AddCommentForm(HttpServletRequest request) {
    	super();
		commentDao = (CommentDao) DaoProxy.getInstance().getCommentDao();
		userDao = (UserDao) DaoProxy.getInstance().getUserDao();
		siteDao = (SiteDao) DaoProxy.getInstance().getSiteDao();
		this.request = request;
		// partMethod = false;
		
		try {
			author = userDao.get(((User) request.getSession().getAttribute("sessionUser")).getId());
			reference = (Reference) siteDao.get(getInputIntegerValue(REFERENCE_ID_FIELD));
			content = getInputTextValue(CONTENT_FIELD);
		} catch (Exception e) {
			DLOG.log(Level.ERROR, e.getMessage());
		}
		
    }
    
    /**
     * create comment (validate field and persist with CommentDao)
     * @return comment
     */
    public Comment createComment() {
    	try {
    		validateContent();
    	} catch (FormException e) {
    		DLOG.log(Level.ERROR, "Content Error : " + this.content);
    		this.errors.put("content", e.getMessage());
    	}

    	try {
    		validateAuthor();
    	} catch (FormException e) {
    		this.errors.put("author", "Author invalid.");
    		this.errors.put("internal", "Unexpected error.");
    	}

    	try {
    		validateReference();
    	} catch (FormException e) {
    		this.errors.put("reference", "Reference invalid.");
    		this.errors.put("internal", "Unexpected error.");
    	}
    	
    	if ( ! this.errors.isEmpty() ) return null;

    	try {
    		comment = new Comment(reference, content, author);
        	comment = commentDao.create(comment);    			
    	} catch (Exception e ) {
    		for ( Entry<String,String> error : errors.entrySet() ) {
    	   		this.errors.put("internal", "Unexpected error.");
        		DLOG.log(Level.ERROR, "Add comment - " + error.getKey() + " : " + error.getValue());
    		}
    	}
    	
    	return comment;
    }
    
    /**
     * Checking content not empty
     */
    private void validateContent() throws FormException {
    	String message = "Le contenu du commentaire est invalide";
    	if ( content == null ) throw new FormException(message);
    	if ( content.length() < 3 ) throw new FormException(message);
    	
    }
    
    /**
     * Checking author not null
     */
    private void validateAuthor() throws FormException {
    	if ( author == null ) throw new FormException("This comment has no author.");
    	if ( author.getRole().getId() < 2 ) throw new FormException("This user can't access to comment creation.");
    	if ( author.getUsername() == null ) throw new FormException("Username not found.");
    }
    
    /**
     * Checking reference not null
     */
    private void validateReference() throws FormException {
    	if ( reference == null ) throw new FormException("This comment has not valid reference.");
    	if ( reference.getId() == null ) throw new FormException("This comment has not valid reference.");
    	if ( reference.getId() < 1 ) throw new FormException("This comment has not valid reference.");
    	if ( reference.getSlug() == null ) throw new FormException("Reference for this comment has no valid title.");
    	if ( reference.getSlug().length() < 1 ) throw new FormException("Reference for this comment has no valid title.");
    }
    
	/**
	 * @param comment
	 */
	@SuppressWarnings("unused")
	private static void logComment(Comment comment) {
		String message = "%nDisplay Comment Entity%n";
		try {
			if (comment.getId() != null) message += "Id : " + comment.getId() + "%n";
			message += "Referenced to " + comment.getReference().getType().toString() + " " +
					comment.getReference().getId() + "%n";
			message += "Content : " + comment.getContent() + "%n";
			message += "Author : " + comment.getAuthor().getUsername() + "%n";
			message += "Created At : " + comment.getTsCreated().toString() + "%n";
			message += "Modified At : " + comment.getTsModified().toString() + "%n";
		} catch (Exception e) {
			message += e.getMessage() + "%n";
		}
		DLOG.log(Level.DEBUG, String.format(message));		
	}

	/**
	 * @return the reference
	 */
	public Reference getReference() {
		return reference;
	}

	/**
	 * @return the author
	 */
	public User getAuthor() {
		return author;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @return the comment
	 */
	public Comment getComment() {
		return comment;
	}
    
}
