package com.king.backend.test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class LoginEndpointTest extends BaseCommandTest{

	protected Map<String, String> attributes;
	
	@Before
	public void setUpCommandTestEnvironment(){
		
	}
	
	@Test
	public void test() throws InterruptedException {

		List<Runnable> pool = new ArrayList<Runnable>();
		pool.add(CommandTestFactory.createLoginCommand(context,"10"));
		pool.add(CommandTestFactory.createLoginCommand(context,"11"));
		pool.add(CommandTestFactory.createLoginCommand(context,"10"));
		pool.add(CommandTestFactory.createLoginCommand(context,"11"));
		pool.add(CommandTestFactory.createLoginCommand(context,"12"));
		pool.add(CommandTestFactory.createLoginCommand(context,"13"));

	
		AssertConcurrent.assertConcurrent("LoginEndpointTest",pool,2000);
		
		assertTrue(this.repository.getUsers().size()==4);
		assertTrue(this.repository.getAccessTokens().size()==6);
	}


}
