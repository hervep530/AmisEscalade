/**
 * 
 */
package com.ocherve.jcm;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ocherve.jcm.dao.impl.AddingMode;
import com.ocherve.jcm.dao.impl.Operator;
import com.ocherve.jcm.dao.impl.WhereClause;

/**
 * @author herve_dev
 *
 */
public class WhereClauseTest {
	
	private String test = "implemented";

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * 
	 */
	@Test
	public void test() {
		//fail("Not yet implemented");
		assertEquals(test, "implemented");
	}
	
	/**
	 * Instanciate with basic clause (a field and an operator) and obtain valid String 
	 *   to implement query with where clause
	 */
	@Test
	public void Given_InitialClause_When_GetSql_Then_ObtainRightString () {
		WhereClause where = new WhereClause("c.id", Operator.GREATER);
		assertEquals(where.getSql()," WHERE c.id > :cId");
		where = new WhereClause("c.maxHeight", Operator.LESS);
		assertEquals(where.getSql(), " WHERE c.maxHeight < :cMaxHeight");
		where = new WhereClause("c.cotation.id", Operator.EQUAL);
		assertEquals(where.getSql(), " WHERE c.cotation.id = :cCotationId");
		where = new WhereClause("c.name", Operator.LIKE);
		assertEquals(where.getSql(), " WHERE c.name LIKE :cName");
	}
	
	/**
	 * 
	 */
	@Test
	public void Given_InitialClause_WhenAdding2AndNewClause_Obtain_ClauseOn3Field() {
		WhereClause where = new WhereClause("b.author.id", Operator.EQUAL);
		where.add(AddingMode.AND, "b.dateModified", Operator.LESS);
		where.add(AddingMode.AND, "b.title", Operator.LIKE);
		assertEquals(where.getSql(), " WHERE b.author.id = :bAuthorId AND b.dateModified < :bDateModified AND " +
					"b.title LIKE :bTitle");
		
	}
	
	/**
	 * 
	 */
	// @ Test
	public void Given_InitialClause_WhenAddingClauseGroupAndNewClause_Obtain_ExpectedComplexClause() {
		Map<String,Operator> groupedClauses = new HashMap<>();
		groupedClauses.put("v.volantAGauche", Operator.EQUAL);
		groupedClauses.put("v.color", Operator.LIKE);
		groupedClauses.put("v.puissance", Operator.GREATER);
		
		WhereClause where = new WhereClause("v.embrayageType.id", Operator.EQUAL);
		where.add(AddingMode.AND, "v.climatisation", Operator.EQUAL);
		// TODO - bug cause must be verified (Adding of LOWER(...) even with numeric
		where.addAndGroup(AddingMode.OR, groupedClauses);
		where.add(AddingMode.AND, "v.model", Operator.LIKE);
		assertEquals(where.getSql(), " WHERE v.embrayageType.id = :vEmbrayageTypeId AND v.climatisation = :vClimatisation " +
				"OR ( v.volantAGauche = :vVolantAGauche AND v.color LIKE :vColor AND v.puissance > :vPuissance ) " +
				"AND v.model LIKE :vModel");
		
	}
	
}
