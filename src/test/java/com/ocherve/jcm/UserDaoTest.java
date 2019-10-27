package com.ocherve.jcm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

import com.ocherve.jcm.dao.DaoProxy;
import com.ocherve.jcm.dao.contract.UserDao;
import com.ocherve.jcm.model.Role;
import com.ocherve.jcm.model.User;

/**
 * @author herve_dev
 *
 */
public class UserDaoTest {

	private static final Logger DLOG = LogManager.getLogger("test_file");
	private static Integer id;
	private static Integer[] userIds;
	private static User userControl;
	private static UserDao userDao;
	private static Map<String,Object> fields;
	private static Map<String,Object> parameters;

	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Configurator.setLevel(DLOG.getName(), Level.TRACE);
		DLOG.log(Level.DEBUG, String.format("%n=============%nDEBUT DU TEST%n--------------%n%n"));
		id = null;
		userControl = null;
		fields = new HashMap<>();
		parameters = new HashMap<>();
		userDao = (UserDao) DaoProxy.getInstance().getUserDao();
		
		assertNull(id);
		UserManager.create();

		/* Debug after create*/
		List<User> users = userDao.getList();
		UserManager.LogUserList(users, "all users after create");
		parameters.clear();
		parameters.put("roleIdMini", 2);
		users = userDao.getListFromNamedQuery("User.findUserAtLeastGrantedTo", parameters);
		UserManager.LogUserList(users, "users at least granted to role " + userDao.getRole(3).getName());
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		userIds = UserManager.getIds();
		/* deleting users */
		UserManager.delete();
		List<User> users = userDao.getList();
		UserManager.LogUserList(users, "all users after delete");
		/* Test if users deleted */
		assertNull(userDao.get(id));
		for (int u = 0 ; u < userIds.length ; u++) {			
			assertNull(userDao.get(userIds[u]));
		}
		DLOG.log(Level.DEBUG, String.format("%nFIN DU TEST%n=============%n%n"));
	}

	/**
	 * Add, Display and Delete User
	 */
	@Test
	public void testCreateAndRemove() {

		createFirst();
		UserManager.logUser(id);

		/* Test userControl after creating user*/
		assertNotNull(id);
		assertNotNull(userControl);
		assertTrue(userControl.getRole().getId() ==  3);
		assertEquals(userControl.getUsername(), "captaine");
		
		/* Test user when deleting */
		assertTrue(userDao.delete(id));
		assertNull(userDao.get(id));
	}

	/**
	 * Add, Display and Delete User
	 */
	@Test
	public void testChangePassword5eUser() {
		/* get 5th id and set paramters for update and update matching user */
		id = UserManager.getIds()[4];
		fields.clear();
		fields.put("password", "STR0nGER0mot0de0passe");
		userDao.update(id, fields);
		/* get user again from dao and log it for debug */
		userControl = userDao.get(id);
		UserManager.logUser(userControl);

		/* Test userControl after creating user*/
		assertNotNull(userControl);
		assertEquals(userControl.getUsername(), "solemarsa");
		assertTrue(BCrypt.checkpw("STR0nGER0mot0de0passe", userControl.getPassword()));		
	}

	/**
	 * 
	 */
	@Test
	public void testChangeUserRoleAndUpdate2ndUser() {
		/* get second id and matching user from dao */
		id = UserManager.getIds()[1];
		User registredUser = userDao.get(id);
		/* get role with id 4 from dao, affect it to user and modify user with dao */
		Role role = userDao.getRole(4);
		registredUser.setRole(role);
		userDao.update(registredUser);
		/* Get user again from dao and log it for debug */
		userControl = userDao.get(id);
		UserManager.logUser(userControl);

		/* Test userControl after creating user*/
		assertNotNull(userControl);
		assertEquals(userControl.getMailAddress(), "aurelie.lavant@amiescalade.fr");
		assertEquals(userControl.getRole().getName(),  "MANAGER");		
	}


	
	/**
	 * 
	 */
	private void createFirst() {
		User user = new User("jack.spar@amiescalade.fr", "captaine", "owowow", "seldetortugas", "blackcoffre");		
		Role role = userDao.getRole(3);
		user.setRole(role);
		
		/* Insert user in database and getting id (not null expected) */
		userDao.create(user);
		id = user.getId();

		/* Get user in database from id*/
		if (id != null)
			userControl = userDao.get(id);
		
	}
	
}
