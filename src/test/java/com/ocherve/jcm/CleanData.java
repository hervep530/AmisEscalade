package com.ocherve.jcm;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ocherve.jcm.model.Comment;
import com.ocherve.jcm.model.Message;
import com.ocherve.jcm.model.Site;
import com.ocherve.jcm.model.Topo;
import com.ocherve.jcm.model.User;

/**
 * Call all Manager with deleteAll methods
 * 
 * @author herve_dev
 *
 */
public class CleanData {

	/**
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MessageManager.deleteAll();
		CommentManager.deleteAll();
		TopoManager.deleteAll();
		SiteManager.deleteAll();
		UserManager.deleteAllNotAnonymous();
	}

	/**
	 * @throws Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * Test than all was deleted
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
		assertEquals(users.size(), 1);
		assertEquals(sites.size(), 0);
		assertEquals(topos.size(), 0);
		assertEquals(comments.size(), 0);
		assertEquals(messages.size(), 0);
	}

}
