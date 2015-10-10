package com.king.backend.test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.king.backend.app.model.Token;
import com.king.backend.app.model.Level;

public class ScoreEndpointTest extends BaseCommandTest{
	
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
	public void setUpScoreCommandTest(){
		
		
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
		BaseCommandTest.RunnableCommand command10 = CommandTestFactory.createScoreCommand(context,sessionkeyUser10,String.valueOf(user10.getUserId()),"10","100");
		BaseCommandTest.RunnableCommand command11 = CommandTestFactory.createScoreCommand(context,sessionkeyUser10,String.valueOf(user10.getUserId()),"10","101");
		BaseCommandTest.RunnableCommand command12 = CommandTestFactory.createScoreCommand(context,sessionkeyUser10,String.valueOf(user10.getUserId()),"10","102");
		BaseCommandTest.RunnableCommand command13 = CommandTestFactory.createScoreCommand(context,sessionkeyUser10,String.valueOf(user10.getUserId()),"10","103");
		BaseCommandTest.RunnableCommand command14 = CommandTestFactory.createScoreCommand(context,sessionkeyUser10,String.valueOf(user10.getUserId()),"10","104");
		BaseCommandTest.RunnableCommand command15 = CommandTestFactory.createScoreCommand(context,sessionkeyUser10,String.valueOf(user10.getUserId()),"10","105");
		BaseCommandTest.RunnableCommand command16 = CommandTestFactory.createScoreCommand(context,sessionkeyUser10,String.valueOf(user10.getUserId()),"10","106");
		BaseCommandTest.RunnableCommand command17 = CommandTestFactory.createScoreCommand(context,sessionkeyUser10,String.valueOf(user10.getUserId()),"10","107");
		BaseCommandTest.RunnableCommand command18 = CommandTestFactory.createScoreCommand(context,sessionkeyUser10,String.valueOf(user10.getUserId()),"10","108");
		BaseCommandTest.RunnableCommand command19 = CommandTestFactory.createScoreCommand(context,sessionkeyUser10,String.valueOf(user10.getUserId()),"10","109");
		BaseCommandTest.RunnableCommand command20 = CommandTestFactory.createScoreCommand(context,sessionkeyUser10,String.valueOf(user10.getUserId()),"10","110");
		BaseCommandTest.RunnableCommand command21 = CommandTestFactory.createScoreCommand(context,sessionkeyUser10,String.valueOf(user10.getUserId()),"10","111");
		BaseCommandTest.RunnableCommand command22 = CommandTestFactory.createScoreCommand(context,sessionkeyUser10,String.valueOf(user10.getUserId()),"10","112");
		BaseCommandTest.RunnableCommand command23 = CommandTestFactory.createScoreCommand(context,sessionkeyUser10,String.valueOf(user10.getUserId()),"10","113");
		BaseCommandTest.RunnableCommand command24 = CommandTestFactory.createScoreCommand(context,sessionkeyUser10,String.valueOf(user10.getUserId()),"10","114");
	
		
		List<Runnable> pool = new ArrayList<Runnable>();
		pool.add(command10);
		pool.add(command11);
		pool.add(command12);
		pool.add(command13);
		pool.add(command14);
		pool.add(command15);
		pool.add(command16);
		pool.add(command17);
		pool.add(command18);
		pool.add(command19);
		pool.add(command20);
		pool.add(command21);
		pool.add(command22);
		pool.add(command23);
		pool.add(command24);

		
		AssertConcurrent.assertConcurrent("ScoreEndpointTest",pool,2000);
		Level highScoresByLevel = repository.getLevels().get(10);
		
		assertTrue(highScoresByLevel !=null);
		assertTrue(highScoresByLevel.getScores().getUserScores().size()==15);
		ScoreQueue scores = highScoresByLevel.getScores();
		assertTrue(scores.getUserScores().peek().getScore()==100);
		
		BaseCommandTest.RunnableCommand command25 = CommandTestFactory.createScoreCommand(context,sessionkeyUser10,String.valueOf(user10.getUserId()),"10","115");
		BaseCommandTest.RunnableCommand command26 = CommandTestFactory.createScoreCommand(context,sessionkeyUser10,String.valueOf(user10.getUserId()),"10","116");
		BaseCommandTest.RunnableCommand command27 = CommandTestFactory.createScoreCommand(context,sessionkeyUser10,String.valueOf(user10.getUserId()),"10","117");
		
		pool = new ArrayList<Runnable>();
		pool.add(command25);
		pool.add(command26);
		pool.add(command27);
		AssertConcurrent.assertConcurrent("CheckLoginEndpointTest",pool,2000);
		assertTrue(repository.getLevels().get(10).getScores().getScores().size()==15);
		scores = repository.getLevels().get(10).getScores();
		
		assertTrue(scores.getUserScores().poll().getScore()==103);
		assertTrue(scores.getUserScores().poll().getScore()==104);
		assertTrue(scores.getUserScores().poll().getScore()==105);
		assertTrue(scores.getUserScores().poll().getScore()==106);
		assertTrue(scores.getUserScores().poll().getScore()==107);
		assertTrue(scores.getUserScores().poll().getScore()==108);
		assertTrue(scores.getUserScores().poll().getScore()==109);
		assertTrue(scores.getUserScores().poll().getScore()==110);
		assertTrue(scores.getUserScores().poll().getScore()==111);
		assertTrue(scores.getUserScores().poll().getScore()==112);
		assertTrue(scores.getUserScores().poll().getScore()==113);
		assertTrue(scores.getUserScores().poll().getScore()==114);
		assertTrue(scores.getUserScores().poll().getScore()==115);
		assertTrue(scores.getUserScores().poll().getScore()==116);
		assertTrue(scores.getUserScores().poll().getScore()==117);
		assertTrue(scores.getUserScores().poll()==null);
		
		
	}


}
