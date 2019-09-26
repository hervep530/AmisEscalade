package com.ocherve.jcm;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ocherve.jcm.model.User;
import com.ocherve.jcm.model.Site;
import com.ocherve.jcm.model.Topo;
import com.ocherve.jcm.model.Comment;
import com.ocherve.jcm.model.Message;


/**
 * fake test to populate database in order to use web application
 * 
 * @author herve_dev
 *
 */
public class CreateData {

	/**
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		UserManager.create();
		SiteManager.create();
		TopoManager.create();
		CommentManager.create();
		MessageManager.create();
	}

	/**
	 * @throws Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * 
	 */
	@Test
	public void test() {
		User user = UserManager.getDao().get(1);
		List<User> users = UserManager.getDao().getList();
		List<Site> sites = SiteManager.getDao().getList();
		List<Topo> topos = TopoManager.getDao().getList();
		List<Comment> comments = CommentManager.getDao().getList();
		List<Message> messages = MessageManager.getDao().getList();
		assertNotNull(user);
		assertEquals(users.size(), 6);
		assertEquals(sites.size(), 5);
		assertEquals(topos.size(), 3);
		assertEquals(comments.size(), 17);
		assertEquals(messages.size(), 10);
	}

}
