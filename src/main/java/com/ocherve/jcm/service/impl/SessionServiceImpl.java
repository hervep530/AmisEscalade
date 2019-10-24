package com.ocherve.jcm.service.impl;

import javax.servlet.http.HttpServletRequest;


import com.ocherve.jcm.model.User;
import com.ocherve.jcm.form.ConnexionForm;
import com.ocherve.jcm.form.InscriptionForm;
import com.ocherve.jcm.service.Delivry;
import com.ocherve.jcm.service.Notification;
import com.ocherve.jcm.service.NotificationType;
import com.ocherve.jcm.service.Parameters;
import com.ocherve.jcm.service.factory.SessionService;

/**
 * @author herve_dev
 *
 * Service call by Session servlet to set action Parameters and process action (or aborting)
 * 
 */
public class SessionServiceImpl extends ServiceImpl implements SessionService {
	
	protected final static String SVC_DEFAULT_URL = "session/moncompte";
	protected final static String[][] SVC_ACTIONS = {
			{"connexion","/session/connexion/$id/$slug"},	
			{"deconnexion","/session/deconnexion/$id/$slug"},	
			{"inscription","/session/inscription/$id/$slug"},	
			{"pass","/session/pass/$id/$slug"},	
			{"d","/session/d/$id/$slug"}
	};

	/**
	 * Service instanciation 
	 * The "super()" instruction is called to herit of ServiceImpl instanciation. All other instruction will
	 * customize this constructor
	 */
	public SessionServiceImpl() {
		super(SVC_DEFAULT_URL);
	}
	
	public Parameters setParameters(HttpServletRequest request) {
		Parameters parameters = super.setParameters(request);
		
		if ( request.getMethod().contentEquals("POST")) {
			switch (parameters.getParsedUrl().getAction()) {
				case  "connexion" :
					parameters.setForm(new ConnexionForm(request));
					break;
				case  "inscription" :
					parameters.setForm(new InscriptionForm(request));
					break;
			}
		}
		return parameters;
	}

/*	
	@Override
	public Delivry doGetAction(Parameters parameters) {
		Delivry delivry = new Delivry();
		try {
			switch (parameters.getParsedUrl().getAction()) {
				case "deconnexion" :
					delivry = getDeconnexion(parameters);
					break;
				case "pass" :
					break;
				case "d" :
					break;
				default :
			}			
		} catch (UrlException e ) {
			DLOG.log(Level.ERROR , e.getMessage());
			delivry.appendError(serviceName + "_" + parameters.getParsedUrl().getAction(), e.getMessage());
		}
		if ( delivry == null ) delivry = new Delivry();
		delivry.setParameters(parameters);
		if ( ! parameters.getNotifications().isEmpty() ) delivry.appendNotifications(parameters.getNotifications());
		if ( ! parameters.getErrors().isEmpty() ) delivry.setErrors(parameters.getErrors());
		String info = "Service " + this.serviceName + " do GetAction.";
		DLOG.log(Level.DEBUG , info);
		return delivry;
	}
*/
	
	@Override
	public Delivry getDeconnexion(Parameters parameters) {
		this.delivry = new Delivry();
		
		// will reset session - value of resetSession doesn't matter... always operate
		this.delivry.appendSession("resetSession", "yes");
		// Setting redirection and notification(s)
		String message = "Vous êtes maintenant déconnecté.";
		Notification notification = new Notification(NotificationType.SUCCESS, message);
		this.delivry.appendSessionNotification("Déconnexion", notification);
		this.delivry.appendattribute("redirect", parameters.getContextPath());
		this.appendMandatoryAttributesToDelivry(parameters);
		
		return this.delivry;
	}

	@Override
	public Delivry getConnexionForm(Parameters parameters) {
		this.delivry = new Delivry();
		// get Connexion Form stored in parameters and call connectUser()
		this.delivry.appendattribute("connexionForm", new ConnexionForm());
		this.appendMandatoryAttributesToDelivry(parameters);

		return this.delivry;
	}

	@Override
	public Delivry getInscriptionForm(Parameters parameters) {
		this.delivry = new Delivry();
		this.delivry.appendattribute("inscriptionForm", new InscriptionForm());
		this.appendMandatoryAttributesToDelivry(parameters);

		return this.delivry;
	}

	@Override
	public Delivry postConnexionForm(Parameters parameters) { 
		this.delivry = new Delivry();
		// get Connexion Form stored in parameters and call connectUser()
		ConnexionForm connexionForm = (ConnexionForm) parameters.getForm();
		User user = connexionForm.connectUser();
		if ( ! connexionForm.getErrors().isEmpty() ) {
			// if errors, we will forward delivry to formular with errors embedded in connexionForm
			String message = "Identifiant ou mot de passe invalide.";
			Notification notification = new Notification(NotificationType.ERROR, message);
			this.delivry.appendNotification("Connexion", notification);
			delivry.appendattribute("connexionForm", connexionForm);
			this.appendMandatoryAttributesToDelivry(parameters);
			
			return this.delivry;
		} 
		
		// if Success, user is stored in session and we will send in the next httpRequest (redirection)
		this.delivry.appendSession("sessionUser", user.getSessionInstance());
		// Settin redirection and notification(s)
		String message = "Bonjour " + user.getUsername() + " et bienvenue!";
		Notification notification = new Notification(NotificationType.SUCCESS, message);
		this.delivry.appendSessionNotification("Connexion", notification);
		this.delivry.appendattribute("redirect", parameters.getContextPath());
		this.appendMandatoryAttributesToDelivry(parameters);

		return this.delivry;
	}
	
	@Override
	public Delivry postInscriptionForm(Parameters parameters) {
		this.delivry = new Delivry();

		// get Connexion Form stored in parameters and call connectUser()
		InscriptionForm inscriptionForm = (InscriptionForm) parameters.getForm();
		@SuppressWarnings("unused")
		User inscriptionUser = inscriptionForm.createUser();
		if ( ! inscriptionForm.getErrors().isEmpty() ) {
			// if errors, we will forward delivry to formular with errors embedded in connexionForm
			this.delivry.appendattribute("inscriptionForm", inscriptionForm);
			this.appendMandatoryAttributesToDelivry(parameters);
			
			return this.delivry;
		} 
		
		// Setting redirection and notification(s)
		String message = "Votre compte est créé. Vous pouvez maintenant vous connecter.";
		Notification notification = new Notification(NotificationType.SUCCESS, message);
		this.delivry.appendSessionNotification("Creation de compte", notification);
		String urlConnexion = "/session/connexion/0/786775566A7674776D7541724E58766B";
		this.delivry.appendattribute("redirect", parameters.getContextPath() + urlConnexion);
		this.appendMandatoryAttributesToDelivry(parameters);
		
		return this.delivry;
		
	}
	
}
