package com.ocherve.jcm.form;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.mindrot.jbcrypt.BCrypt;

import com.ocherve.jcm.dao.DaoProxy;
import com.ocherve.jcm.dao.contract.UserDao;
import com.ocherve.jcm.model.User;

/**
 * @author herve_dev
 *
 */
public class ConnexionForm {

    private static final Logger DLOG = LogManager.getLogger("development_file");
    private static final Level DLOGLEVEL = Level.TRACE;

	/**
	 * Nom du champ de formulaire pour le mail
	 */
	public final static String MAIL_FIELD = "mailAddress";
	
	/**
	 * Nom de champ de formulaire pour le mot de passe
	 */
	public final static String PASSWD_FIELD = "password";
	private UserDao userDao;
	
	private String mailHelp;
	private String passwdHelp;
	
	private String mailAddress;
	private String password;
	private String result = "";
	private int userId = 0;
	private Map<String,String> errors;

	/**
	 * constructor  without argument - used to send help to jsp
	 */
	public ConnexionForm() {
		this.mailHelp = "L'adresse mail doit être de la forme mon_nom@domaine.ext où le nom accepte _ - et .";
		this.passwdHelp = "Le mot de passe doit contenir entre 8 et 16 caractères sans caractères spéciaux avec au moins 2 minuscules, 2 majuscules et 2 chiffres";		
	}
	
	/**
	 * @param request
	 */
	public ConnexionForm(HttpServletRequest request) {
		this.mailHelp = "L'adresse mail doit être de la forme mon_nom@domaine.ext où le nom accepte _ - et .";
		this.passwdHelp = "Le mot de passe doit contenir entre 8 et 16 caractères sans caractères spéciaux avec au moins 2 minuscules, 2 majuscules et 2 chiffres";		
		Configurator.setLevel(DLOG.getName(), DLOGLEVEL);
		userDao = (UserDao) DaoProxy.getInstance().getUserDao();
		this.mailAddress = getValeurChamp(request, MAIL_FIELD);
		this.password = getValeurChamp(request, PASSWD_FIELD);
		this.errors = new HashMap<>();
	}
	
	/**
	 * @return user
	 */
	public User connectUser() {
		User user = new User();
		// Validating email pattern
		try { this.validateEmailPattern(); } catch (FormException e ) { this.errors.put("mailPattern", e.getMessage()); }
		// Validating email address / user is valid
		try { this.validateEmail(); } catch (FormException e ) { this.errors.put("mailAddress", e.getMessage()); }
		// Validating password pattern
		try { this.validatePasswordPattern(); } catch (FormException e ) { this.errors.put("passwdPattern", e.getMessage()); }
		// Validating password
		try { 
			if ( errors.isEmpty() ) user = this.validatePassword();
		} catch (FormException e ) { this.errors.put("password", e.getMessage()); }
		
		if ( errors.isEmpty() ) {
			DLOG.log(Level.DEBUG, "User / Password validated");
			result = "Succès de la connexion.";
		} else {
			for (String error : this.errors.keySet()) {
				DLOG.log(Level.DEBUG , error + " : " + this.errors.get(error));				
			}
			result = "Echec de la connexion.";
		}
		
		return user;
	}
	
	
	/**
	 * @throws FormException
	 */
	private void validateEmailPattern() throws FormException {

		// Rejecting empty mail
		if ( this.mailAddress.isEmpty() ) 
			throw new FormException("Merci de saisir une adresse email.");

		// Rejecting invalid pattern
		if ( ! this.mailAddress.matches("[\\w\\-]+(\\.\\w+)*@\\w+(\\.\\w+)+") ) 
			throw new FormException("Merci de saisir une adresse email valide."); 

	}
	
	/**
	 * @throws FormException
	 */
	private void validateEmail() throws FormException {

		// Request user id from database with mail address as filter (one Integer result expected... Else rejecting)
		Map<String,Object> parameters = new HashMap<>();
		parameters.put("mailAddress", this.mailAddress);
		try {
			this.userId = userDao.getIdFromNamedQuery("User.findUserIdByMail", parameters);
		} catch (Exception e) {/* if it's a dao error it was tracked anyway */}
		DLOG.log(Level.DEBUG , "Id trouvé : " + this.userId);				
		if ( this.userId < 2 )
			throw new FormException("Identifiant ou mot de passe invalide.");

	}
	
	/**
	 * @throws FormException
	 */
	private void validatePasswordPattern() throws FormException {
		
		// Rejecting empty password
		if ( this.password.isEmpty()  ) 
			throw new FormException("Merci de saisir votre mot de passe");

		// Rejecting password with wrong length
		if ( this.password.trim().length() < 5 )
			throw new FormException("La regle de mot de passe n'autorise pas moins de 5 caractère. Merci de le saisir à nouveau.");

	}
	
	/**
	 * @return user
	 * @throws FormException
	 */
	private User validatePassword() throws FormException {
		
		// Compare password with user password get from database given id from formular
		User userControl = this.userDao.get(this.userId);
		try {
			if ( ! BCrypt.checkpw(this.password, userControl.getPassword()) )
				throw new FormException("Identifiant ou mot de passe invalide.");
		} catch (Exception e) {
			throw new FormException("Identifiant ou mot de passe invalide.");
		}
		return userControl;

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
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	private static String getValeurChamp( HttpServletRequest request, String nomChamp ) {
	    String valeur = (String) request.getParameter( nomChamp );
	    if ( valeur == null ) valeur = "";
        return valeur.trim();
	}

	/**
	 * @return the mailHelp
	 */
	public String getMailHelp() {
		return mailHelp;
	}

	/**
	 * @return the passwdHelp
	 */
	public String getPasswdHelp() {
		return passwdHelp;
	}

	
}
