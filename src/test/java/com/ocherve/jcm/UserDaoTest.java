package com.ocherve.jcm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ocherve.jcm.dao.DaoProxy;
import com.ocherve.jcm.dao.contract.UserDao;
import com.ocherve.jcm.model.Role;
import com.ocherve.jcm.model.User;

/**
 * @author herve_dev
 *
 */
public class UserDaoTest {

	private static Integer id;
	private static User user;
	private static User userControl;
	private static UserDao userDao;

	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		id = null;
		user = null;
		userControl = null;
		userDao = (UserDao) DaoProxy.getInstance().getUserDao();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * Add, Display and Delete User
	 */
	@Test
	public void test() {
		//fail("Not yet implemented");
		assertNull(id);
		createFirst();
		displayUserControl();
		userQueryByMailAddress();
		userQueryByRangeRoleId();
		removeUserCreated();

	}

	/**
	 * 
	 */
	private void createFirst() {
		user = new User("herve.pineau@laferme.fr", "hervep", "passwd", "seldeguerande", "jetetienstumetiens");
		Role role = userDao.getRole(2);
		user.setRole(role);
		userDao.create(user);
		id = user.getId();
		if (id != null)
			userControl = userDao.get(user.getId());
		assertNotNull(userControl);
		assertEquals(userControl.getUsername(), "hervep");
	}
	
	/**
	 * 
	 */
	private void removeUserCreated() {
		assertTrue(userDao.delete(id));
		assertNull(userDao.get(id));
	}
	
	private void userQueryByMailAddress() {
		Map<String,String> clauses = new HashMap<>();
		clauses.put("mailAddress", " like :'herve.pineau@laferme.fr'");
		List<User> users = userDao.getFromFilteredQuery(clauses);
		System.out.println(String.format("%n%nDisplay salt for user with mail address \"herve.pineau@laferme.fr\"%n%n"));
		if ( users == null ) return;
		for (User user : users) {
			System.out.println(String.format("Salt : " + user.getSalt() + "%n" ));
		}
	}

	private void userQueryByRangeRoleId() {
		Map<String,String> clauses = new HashMap<>();
		clauses.put("role.siteAccess.id", " > 2");
		List<User> users = userDao.getFromFilteredQuery(clauses);
		System.out.println(String.format("%n%nDisplay salt for user with siteAccess > 2 %n%n"));
		if ( users == null ) return;
		for (User user : users) {
			System.out.println(String.format("Mail : " + user.getMailAddress() + "%n" ));
			System.out.println(String.format("Role id : " + user.getRole().getId() + "%n" ));
		}
	}

	private void displayUserControl() {
		String message = "%n";
		message += "User id : " + userControl.getId() + "%n";
		message += "Username : " + userControl.getUsername() + "%n";
		message += "Mail address : " + userControl.getMailAddress() + "%n";
		message += "Password : " + userControl.getPassword() + "%n";
		message += "Salt : " + userControl.getSalt() + "%n";
		message += "Role : " + userControl.getRole().getName() + "%n";
		message += "Access sites : " + userControl.getRole().getSiteAccess().getName() + "%n";
		message += "Access topos : " + userControl.getRole().getTopoAccess().getName() + "%n";
		message += "Access comments : " + userControl.getRole().getCommentAccess().getName() + "%n";
		message += "Access messages : " + userControl.getRole().getMessageAccess().getName() + "%n";
		message += "Access utilisateurs : " + userControl.getRole().getUserAccess().getName() + "%n";
		message += "Access medias : " + userControl.getRole().getMediaAccess().getName() + "%n";
		System.out.println(String.format(message));		
	}
}
