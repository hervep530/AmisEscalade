package com.ocherve.jcm.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Level;

import com.ocherve.jcm.model.User;
import com.ocherve.jcm.form.ConnexionForm;
import com.ocherve.jcm.service.Delivry;
import com.ocherve.jcm.service.Parameters;
import com.ocherve.jcm.service.UrlException;
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
			{"connexion","/session/connexion"},	
			{"deconnexion","/session/deconnexion"},	
			{"inscription","/session/inscription"},	
			{"pass","/session/pass"},	
			{"d","/session/d"}
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
		if ( request.getMethod().contentEquals("POST") && 
						parameters.getParsedUrl().getAction().contentEquals("connexion") ) {
			ConnexionForm form = new ConnexionForm(request);
			parameters.setForm(form);
		}		
		return parameters;
	}

	@Override
	public Delivry doGetAction(Parameters parameters) {
		Delivry delivry = new Delivry();
		try {
			switch (parameters.getParsedUrl().getAction()) {
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
		delivry.setParameters(parameters);
		if ( ! parameters.getErrors().isEmpty() ) delivry.setErrors(parameters.getErrors());
		String info = "Service " + this.serviceName + " do GetAction.";
		DLOG.log(Level.DEBUG , info);
		return delivry;
	}

	public Delivry doPostAction(Parameters parameters) {
		Delivry delivry = new Delivry();
		try {
			switch (parameters.getParsedUrl().getAction()) {
				case "connexion" :
					ConnexionForm form = (ConnexionForm) parameters.getForm();
					User user = form.connectUser();
					if ( ! form.getErrors().isEmpty() ) {
						delivry.appendattribute("formError", form);
					} 
					delivry.appendSession("sessionUser", user);
					break;
				case "inscription" :
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
	
}
