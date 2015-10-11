package com.king.backend.test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.king.backend.app.model.Token;

public class CheckLoginEndpointTest extends BaseCommandTest{
	
	protected String sessionkeyUser10;
	protected String sessionkeyUser11;
	protected String sessionkeyUser12;
	protected String sessionkeyUser13;
	protected String sessionkeyUser14;
	
	protected Token asessionkeyUser10;
	protected Token asessionkeyUser11;
	protected Token asessionkeyUser12;
	protected Token asessionkeyUser13;
	protected Token asessionkeyUser14;

	
	protected User user10;
	protected User user11;
	protected User user12;
	protected User user13;
	
	
	@Before
	public void setUpCheckLoginCommandTest(){
		
		user10 = new User(10);
		user11 = new User(11);
		user12 = new User(12);
		user13 = new User(13);		
		repository.getUsers().put(10, user10);
		repository.getUsers().put(11, user11);
		repository.getUsers().put(12, user12);
		repository.getUsers().put(13, user13);
		
		sessionkeyUser10 = UUID.randomUUID().toString();
		sessionkeyUser11 = UUID.randomUUID().toString();
		sessionkeyUser12 = UUID.randomUUID().toString();
		sessionkeyUser13 = UUID.randomUUID().toString();
		sessionkeyUser14 = UUID.randomUUID().toString();		
		asessionkeyUser10 = new Token(sessionkeyUser10,user10);
		asessionkeyUser11 = new Token(sessionkeyUser11,user11);
		asessionkeyUser12 = new Token(sessionkeyUser12,user12);
		asessionkeyUser13 = new Token(sessionkeyUser13,user13);
		asessionkeyUser14 = new Token(sessionkeyUser14,user10);
		repository.getAccessTokens().put(sessionkeyUser10,asessionkeyUser10);
		repository.getAccessTokens().put(sessionkeyUser11,asessionkeyUser11);
		repository.getAccessTokens().put(sessionkeyUser12,asessionkeyUser12);
		repository.getAccessTokens().put(sessionkeyUser13,asessionkeyUser13);
		repository.getAccessTokens().put(sessionkeyUser14,asessionkeyUser14);
		
		

	}
	
	@Test
	public void test() throws InterruptedException {

		BaseCommandTest.RunnableCommand command10 = CommandTestFactory.createCheckLoginCommand(context,sessionkeyUser10);
		BaseCommandTest.RunnableCommand command11 = CommandTestFactory.createCheckLoginCommand(context,sessionkeyUser11);
		BaseCommandTest.RunnableCommand command12 = CommandTestFactory.createCheckLoginCommand(context,sessionkeyUser12);
		BaseCommandTest.RunnableCommand command13 = CommandTestFactory.createCheckLoginCommand(context,sessionkeyUser13);
		
		List<Runnable> pool = new ArrayList<Runnable>();
		pool.add(command10);
		pool.add(command11);
		pool.add(command12);
		pool.add(command13);
	
	
		AssertConcurrent.assertConcurrent("CheckLoginEndpointTest",pool,2000);

		assertTrue(command10.getAttributes().containsKey("userId"));
		assertTrue(command10.getAttributes().get("userId").equals("10"));
		
		assertTrue(command11.getAttributes().containsKey("userId"));
		assertTrue(command11.getAttributes().get("userId").equals("11"));
		
		assertTrue(command12.getAttributes().containsKey("userId"));
		assertTrue(command12.getAttributes().get("userId").equals("12"));
		
		assertTrue(command13.getAttributes().containsKey("userId"));
		assertTrue(command13.getAttributes().get("userId").equals("13"));
		
		
	}
	


}
