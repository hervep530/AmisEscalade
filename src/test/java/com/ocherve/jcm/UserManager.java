package com.ocherve.jcm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import com.ocherve.jcm.dao.DaoProxy;
import com.ocherve.jcm.dao.contract.UserDao;
import com.ocherve.jcm.model.Role;
import com.ocherve.jcm.model.User;

/**
 * Service to create, delete User and store id created in static object dedicated for tests

 * @author herve_dev
 *
 */
public class UserManager {

	private static final Logger DLOG = LogManager.getLogger("development_file");
	private static final String[][] USERS_DE_TEST = new String[][] {
		{"herve.oc@amiescalade.fr", "hervesca", "paSS13word", "salt", "","2"},
		{"aurelie.lavant@amiescalade.fr", "alavantie", "paSS12word", "salt", "","3"},
		{"julien.lami@amiescalade.fr", "lamijiii", "paSS13word", "salt", "","4"},
		{"christine.chappard@amiescalade.fr", "chachristi", "paSS14word", "salt", "","2"},
		{"solene.marsault@amiescalade.fr", "solemarsa", "PaSS13worD", "salt", "","2"}
	};
	private static Integer[] ids;
	private static UserDao dao;

	private static void initialization() {
		if ( dao != null ) return;
		Configurator.setLevel(DLOG.getName(), Level.TRACE);
		DLOG.log(Level.DEBUG, "Initialization of User Manager");
		dao = (UserDao) DaoProxy.getInstance().getUserDao();
	}

	/**
	 * Create users for tests
	 */
	public static void create() {
		initialization();
		ids = new Integer[USERS_DE_TEST.length];
		UserDao dao = (UserDao) DaoProxy.getInstance().getUserDao();
		
		for (int u = 0 ; u < USERS_DE_TEST.length ; u++) {
			User user = new User(USERS_DE_TEST[u][0], USERS_DE_TEST[u][1], USERS_DE_TEST[u][2],
					USERS_DE_TEST[u][3], USERS_DE_TEST[u][4]);		
			Role role = dao.getRole(Integer.valueOf(USERS_DE_TEST[u][5]));
			user.setRole(role);
			
			dao.create(user);
			ids[u] = user.getId();
		}
		
	}
	
	/**
	 * Delete site dedicated for test
	 */
	public static void delete() {
		initialization();
		for (int u = 0 ; u < ids.length ; u++) {			
			dao.delete(ids[u]);
		}
	}
	
	/**
	 * Delete all users but keep anonymous with id 1
	 */
	public static void deleteAllNotAnonymous() {
		initialization();
		Map <String,Object> parameters = new HashMap<>();
		parameters.put("idMin", 2);
		try {
			List<User> users = dao.getListFromNamedQuery("User.FindUserIdGreaterThan", parameters);
			for (User user : users) {			
				dao.delete(user.getId());
			}		
		} catch (Exception e) {
			DLOG.log(Level.DEBUG, String.format("Error on deleting topos"));
			DLOG.log(Level.DEBUG, String.format(e.getMessage()));			
		}
	}
	
	/**
	 * @return ids of site created as Integer array
	 */
	public static Integer[] getIds() {
		return ids;
	}
	
	/**
	 * @return UserDao
	 */
	public static UserDao getDao() {
		initialization();
		return dao;
	}

	/**
	 * @param users List of site
	 * @param userExpected string to describe kind of list expected (where clause)
	 */
	public static void LogUserList(List<User> users, String userExpected) {
		initialization();
		String message = "%n Display list of " + userExpected + " in database%n";
		message += "Id / Username / Mail Address / Password / Salt / Token / Role%n";
		
		for (User user : users) {
			message += user.getId() + " / ";
			message += user.getUsername() + " / ";
			message += user.getMailAddress() + " / ";
			message += user.getPassword() + " / ";
			message += user.getSalt() + " / ";
			message += user.getRole().getName() + "%n";
		}
		DLOG.log(Level.DEBUG, String.format(message));
	}

	/**
	 * Display condensed list of site in log for debug
	 * @param id 
	 */
	public static void logUser (Integer id) {
		initialization();
		User user = dao.get(id);
		logUser(user);
	}

	/**
	 * @param user
	 */
	public static void logUser (User user) {
		initialization();
		String message = "%n";
		message += "User id : " + user.getId() + "%n";
		message += "Username : " + user.getUsername() + "%n";
		message += "Mail address : " + user.getMailAddress() + "%n";
		message += "Password : " + user.getPassword() + "%n";
		message += "Salt : " + user.getSalt() + "%n";
		message += "Role : " + user.getRole().getName() + "%n";
		message += "Access sites : " + user.getRole().getSiteAccess().getName() + "%n";
		message += "Access topos : " + user.getRole().getTopoAccess().getName() + "%n";
		message += "Access comments : " + user.getRole().getCommentAccess().getName() + "%n";
		message += "Access messages : " + user.getRole().getMessageAccess().getName() + "%n";
		message += "Access utilisateurs : " + user.getRole().getUserAccess().getName() + "%n";
		message += "Access medias : " + user.getRole().getMediaAccess().getName() + "%n";
		DLOG.log(Level.DEBUG, String.format(message));
	}


}
