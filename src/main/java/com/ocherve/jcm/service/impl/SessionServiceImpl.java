package com.ocherve.jcm.service.impl;

import java.util.HashMap;
import java.util.Map;

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
		Delivry delivry = new Delivry();
		
		// will reset session - value of resetSession doesn't matter... always operate
		delivry.appendSession("resetSession", "yes");
		// Setting redirection and notification(s)
		String notificationLabel = "Déconnexion";
		String message = "Vous êtes maintenant déconnecté.";
		Notification notification = new Notification(NotificationType.SUCCESS, message);
		Map<String,Notification> notifications = new HashMap<>();
		notifications.put(notificationLabel, notification);
		delivry.appendSession("notifications", notifications);
		delivry.appendattribute("redirect", parameters.getContextPath());
		
		return delivry;
	}

/*
	public Delivry doPostAction(Parameters parameters) {
		Delivry delivry = new Delivry();
		try {
			switch (parameters.getParsedUrl().getAction()) {
				case "connexion" :
					delivry = postConnexionForm(parameters);
					break;
				case "inscription" :
					delivry = postInscriptionForm(parameters);
					break;
				case "pass" :
					break;
				default :
			}			
		} catch (UrlException e ) {
			DLOG.log(Level.ERROR , e.getMessage());
			delivry.appendError(serviceName + "_" + parameters.getParsedUrl().getAction(), e.getMessage());
		}
		delivry.setParameters(parameters);
		if ( ! parameters.getErrors().isEmpty() ) delivry.setErrors(parameters.getErrors());
		String info = "Service " + this.serviceName + " do GetAction.";
		DLOG.log(Level.DEBUG , info);
		return delivry;
	}
*/

	@Override
	public Delivry postConnexionForm(Parameters parameters) { 
		Delivry delivry = new Delivry();
		// get Connexion Form stored in parameters and call connectUser()
		ConnexionForm connexionForm = (ConnexionForm) parameters.getForm();
		User user = connexionForm.connectUser();
		if ( ! connexionForm.getErrors().isEmpty() ) {
			// if errors, we will forward delivry to formular with errors embedded in connexionForm
			delivry.setErrors(connexionForm.getErrors());
			delivry.appendattribute("connexionForm", connexionForm);
			
			return delivry;
		} 
		
		// if Success, user is stored in session and we will send in the next httpRequest (redirection)
		delivry.appendSession("sessionUser", user.getSessionInstance());
		// Settin redirection and notification(s)
		String notificationLabel = "Connexion";
		String message = "Bonjour " + user.getUsername() + " et bienvenue!";
		Notification notification = new Notification(NotificationType.SUCCESS, message);
		delivry.appendSessionNotification(notificationLabel, notification);
		delivry.appendattribute("redirect", parameters.getContextPath());
		
		return delivry;
	}
	
	@Override
	public Delivry postInscriptionForm(Parameters parameters) {
		Delivry delivry = new Delivry();

		// get Connexion Form stored in parameters and call connectUser()
		InscriptionForm inscriptionForm = (InscriptionForm) parameters.getForm();
		@SuppressWarnings("unused")
		User inscriptionUser = inscriptionForm.createUser();
		if ( ! inscriptionForm.getErrors().isEmpty() ) {
			// if errors, we will forward delivry to formular with errors embedded in connexionForm
			delivry.setErrors(inscriptionForm.getErrors());
			delivry.appendattribute("inscriptionForm", inscriptionForm);
			
			return delivry;
		} 
		
		// Setting redirection and notification(s)
		String notificationLabel = "Creation de compte";
		String message = "Votre compte est créé. Vous pouvez maintenant vous connecter.";
		Notification notification = new Notification(NotificationType.SUCCESS, message);
		Map<String,Notification> notifications = new HashMap<>();
		notifications.put(notificationLabel, notification);
		delivry.appendSession("notifications", notifications);
		String urlConnexion = "/session/connexion/0/786775566A7674776D7541724E58766B";
		delivry.appendattribute("redirect", parameters.getContextPath() + urlConnexion);
		
		return delivry;
		
	}
	
}
