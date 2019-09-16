package com.ocherve.jcm.form;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import com.ocherve.jcm.dao.DaoException;
import com.ocherve.jcm.dao.DaoProxy;
import com.ocherve.jcm.dao.contract.UserDao;
import com.ocherve.jcm.model.User;

/**
 * @author herve_dev
 *
 */
public class InscriptionForm {

    private static final Logger DLOG = LogManager.getLogger("development_file");
    private static final Level DLOGLEVEL = Level.TRACE;

	/**
	 * Field name for email
	 */
	public final static String MAIL_FIELD = "mailAddress";
	/**
	 * Field name for username
	 */
	public final static String NAME_FIELD = "username";
	/**
	 * Field name for password
	 */
	public final static String PASSWD_FIELD = "password";
	/**
	 * Field name for confirm password
	 */
	public final static String CONFIRM_FIELD = "confirmPassword";
	
	private UserDao userDao;

	private String mailAddress = "";
	private String username = "";
	private String password = "";
	private String confirmPassword = "";
	private String result = "";
	private int userId = 0;
	private Map<String,String> errors;
	
	/**
	 * @param request
	 */
	public InscriptionForm(HttpServletRequest request) {
		Configurator.setLevel(DLOG.getName(), DLOGLEVEL);
		userDao = (UserDao) DaoProxy.getInstance().getUserDao();
		this.mailAddress = getFieldValue(request, MAIL_FIELD);
		this.username = getFieldValue(request, NAME_FIELD);
		this.password = getFieldValue(request, PASSWD_FIELD);
		this.confirmPassword = getFieldValue(request, CONFIRM_FIELD);
		this.errors = new HashMap<>();
	}
	
	/**
	 * @return user
	 */
	public User createUser() {
		
		User user = null;
		
		try {
			this.validateEmail();
		} catch (FormException e ) {
			this.errors.put("mailAddress", e.getMessage());
		}
		
		try {
			this.validateUsername();
		} catch (FormException e ) {
			this.errors.put("username", e.getMessage());
		}
		
		try {
			this.validatePassword();
		} catch (FormException e ) {
			this.errors.put("password", e.getMessage());
		}

		try {
			if ( errors.isEmpty() ) {
				user = new User(this.mailAddress, this.username, this.password, 2);
				userDao.create(user);
			}
		} catch (DaoException e) {
			this.errors.put("UserDao", e.getMessage());
		}
		
		if ( errors.isEmpty() ) {
			result = "Le compte est maintenant créé.";
		} else {
			for (String error : this.errors.keySet()) {
				DLOG.log(Level.DEBUG , error + " : " + this.errors.get(error));				
			}
			result = "La creation de compte a échoué.";
		}
		
		return user;
	}
	
	/**
	 * When createUser fails, keeps only mailAddress and username (used by formular) and set all over to null
	 * 
	 */
	public void cleanBeforeBackToFormular() {
		userDao = null;
		password = null;
		confirmPassword = null;
		result = null;
		errors = null;
	}
	
	/**
	 * @throws FormException
	 */
	private void validateEmail() throws FormException {

		// Rejecting empty mail
		if ( this.mailAddress.isEmpty() ) 
			throw new FormException("Merci de saisir une adresse email.");

		// Rejecting invalid pattern
		if ( ! this.mailAddress.matches("[\\w\\-]+(\\.\\w+)*@\\w+(\\.\\w+)+") ) 
			throw new FormException("Merci de saisir une adresse email valide."); 

		// Request user id from database with mail address as filter (one Integer result expected... Else rejecting)
		Map<String,Object> parameters = new HashMap<>();
		parameters.put("mailAddress", this.mailAddress);
		try {
			this.userId = userDao.getIdFromNamedQuery("User.findUserIdByMail", parameters);
		} catch (Exception e) { /* if it's a dao error it was tracked anyway */ }
		DLOG.log(Level.DEBUG , "Id trouvé : " + this.userId);				
		DLOG.log(Level.DEBUG , "Id trouvé : " + this.userId);				
		if ( this.userId > 0 )
			throw new FormException("Vous avez déjà créé un compte.");

	}

	/**
	 * @throws FormException
	 */
	private void validateUsername() throws FormException {

		// Rejecting empty mail
		if ( this.username.isEmpty() ) 
			throw new FormException("Merci de saisir un nom d'utilisateur.");

		// Rejecting invalid pattern
		if ( ! this.username.matches("\\w{8,16}") ) 
			throw new FormException("Merci de saisir un nom d'utilisateur valide."); 

		// Request user id from database with mail address as filter (one Integer result expected... Else rejecting)
		Map<String,Object> parameters = new HashMap<>();
		parameters.put("username", this.username);
		try {
			this.userId = userDao.getIdFromNamedQuery("User.findUserIdByUsername", parameters);
		} catch (Exception e) { /* if it's a dao error it was tracked anyway */ }
		DLOG.log(Level.DEBUG , "Id trouvé : " + this.userId);				
		if ( this.userId > 0 )
			throw new FormException("Ce nom d'utilisateur est déjà utilisé.");
	}

	/**
	 * @throws FormException
	 */
	private void validatePassword() throws FormException {
		
		// Rejecting empty password
		if ( this.password.isEmpty()  ) 
			throw new FormException("Merci de saisir votre mot de passe");

		// Rejecting password with wrong length
		if ( this.password.trim().length() < 8 )
			throw new FormException("La regle de mot de passe n'autorise pas moins de 5 caractère. Merci de le saisir à nouveau.");

		// Rejecting password if not containing at least 8 char and at most 32 char
		if ( ! this.password.trim().matches("\\w{8,32}") )
			throw new FormException("Le mot de passe ne doit contenir que des chiffres ou des lettres. Merci de le saisir à nouveau.");

		// Rejecting password without 2 lowercase
		if ( ! this.password.trim().matches("\\w*[a-z]\\w*[a-z]\\w*") )
			throw new FormException("Le mot de passe doit contenir au moins deux minuscules. Merci de le saisir à nouveau.");

		// Rejecting password without 2 uppercase
		if ( ! this.password.trim().matches("\\w*[A-Z]\\w*[A-Z]\\w*") )
			throw new FormException("Le mot de passe doit contenir au moins deux majuscules. Merci de le saisir à nouveau.");

		// Rejecting password without 2 digit
		if ( ! this.password.trim().matches("\\w*[0-9]\\w*[0-9]\\w*") )
			throw new FormException("Le mot de passe doit contenir au moins deux chiffres. Merci de le saisir à nouveau.");

		// Rejecting different passwords
		if ( ! this.confirmPassword.trim().contentEquals(this.password.trim()) )
			throw new FormException("Les mots de passe ne sont pas identiques. Merci de le saisir à nouveau.");
	}
	
	/**
	 * @return result
	 */
	public String getResultat() {
		return result;
	}

	/**
	 * @return errors
	 */
	public Map<String, String> getErrors() {
		return errors;
	}

	/**
	 * @return the mailAddress
	 */
	public String getMailAddress() {
		return mailAddress;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	private static String getFieldValue( HttpServletRequest request, String nomChamp ) {
	    String valeur = (String) request.getParameter( nomChamp );
	    if ( valeur == null ) valeur = "";
        return valeur.trim();
	}


}
