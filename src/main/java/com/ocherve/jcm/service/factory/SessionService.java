package com.ocherve.jcm.service.factory;

import com.ocherve.jcm.service.Delivry;
import com.ocherve.jcm.service.Parameters;

/**
 * @author herve_dev
 * 
 * Session Service Interface : dedicated to all features about users
 * suscribing, connexion, account,...
 *
 */
public interface SessionService extends Service {
	
	/**
	 * Calling Session service method to delete connexion information in session
	 * 
	 * @param parameters
	 * @return Delivry as result of action
	 */
	Delivry getDeconnexion(Parameters parameters);
	
	/**
	 * Calling Session service method to get empty form in delivry (needed for help message)
	 * 
	 * @param parameters
	 * @return Delivry as result of action
	 */
	Delivry getConnexionForm(Parameters parameters);
	
	/**
	 * Calling Session service method to get empty form in delivry (needed for help message)
	 * 
	 * @param parameters
	 * @return Delivry as result of action
	 */
	Delivry getInscriptionForm(Parameters parameters);
	
	/**
	 * Getting form data, trying to connect user, and return result in delivry
	 * 
	 * @param parameters
	 * @return Delivry as result of action
	 */
	Delivry postConnexionForm(Parameters parameters);
	
	/**
	 * Getting form data, trying to register user, and return result in delivry
	 * 
	 * @param parameters
	 * @return Delivry as result of action
	 */
	Delivry postInscriptionForm(Parameters parameters);
	
}
