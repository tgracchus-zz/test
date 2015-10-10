package com.king.backend.test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class AllCommandsTest extends BaseCommandTest {

	@Test
	public void test() throws InterruptedException {

		BaseCommandTest.RunnableCommand login = CommandTestFactory.createLoginCommand(context, "10");
		BaseCommandTest.RunnableCommand login2 = CommandTestFactory.createLoginCommand(context, "10");

		List<Runnable> pool = new ArrayList<Runnable>();
		pool.add(login);
		pool.add(login2);
		AssertConcurrent.assertConcurrent("login", pool, 2000);

		BaseCommandTest.RunnableCommand checkLogin = CommandTestFactory.createCheckLoginCommand(context, login.getAttributes().get("sessionkey"));
		pool = new ArrayList<Runnable>();
		pool.add(checkLogin);
		AssertConcurrent.assertConcurrent("checkLogin", pool, 2000);

		BaseCommandTest.RunnableCommand score1 = CommandTestFactory.createScoreCommand(context, login.getAttributes().get("sessionkey"), "10", "10", "110");
		BaseCommandTest.RunnableCommand score2 = CommandTestFactory.createScoreCommand(context, login2.getAttributes().get("sessionkey"), "10", "10", "111");
		BaseCommandTest.RunnableCommand score3 = CommandTestFactory.createScoreCommand(context, login.getAttributes().get("sessionkey"), "10", "10", "112");
		BaseCommandTest.RunnableCommand score4 = CommandTestFactory.createScoreCommand(context, login.getAttributes().get("sessionkey"), "10", "10", "113");
		BaseCommandTest.RunnableCommand score5 = CommandTestFactory.createScoreCommand(context, login.getAttributes().get("sessionkey"), "10", "10", "114");
		BaseCommandTest.RunnableCommand score6 = CommandTestFactory.createScoreCommand(context, login.getAttributes().get("sessionkey"), "10", "10", "115");
		BaseCommandTest.RunnableCommand score7 = CommandTestFactory.createScoreCommand(context, login.getAttributes().get("sessionkey"), "10", "10", "116");
		BaseCommandTest.RunnableCommand score8 = CommandTestFactory.createScoreCommand(context, login.getAttributes().get("sessionkey"), "10", "10", "117");
		BaseCommandTest.RunnableCommand score9 = CommandTestFactory.createScoreCommand(context, login.getAttributes().get("sessionkey"), "10", "10", "118");
		BaseCommandTest.RunnableCommand score10 = CommandTestFactory.createScoreCommand(context, login2.getAttributes().get("sessionkey"), "10", "10", "119");
		BaseCommandTest.RunnableCommand score11 = CommandTestFactory.createScoreCommand(context, login.getAttributes().get("sessionkey"), "10", "10", "120");
		BaseCommandTest.RunnableCommand score12 = CommandTestFactory.createScoreCommand(context, login2.getAttributes().get("sessionkey"), "10", "10", "121");
		BaseCommandTest.RunnableCommand score13 = CommandTestFactory.createScoreCommand(context, login.getAttributes().get("sessionkey"), "10", "10", "122");
		BaseCommandTest.RunnableCommand score14 = CommandTestFactory.createScoreCommand(context, login.getAttributes().get("sessionkey"), "10", "10", "123");
		BaseCommandTest.RunnableCommand score15 = CommandTestFactory.createScoreCommand(context, login.getAttributes().get("sessionkey"), "10", "10", "124");
		BaseCommandTest.RunnableCommand score16 = CommandTestFactory.createScoreCommand(context, login.getAttributes().get("sessionkey"), "10", "10", "125");
		BaseCommandTest.RunnableCommand score17 = CommandTestFactory.createScoreCommand(context, login.getAttributes().get("sessionkey"), "10", "10", "126");
		
		BaseCommandTest.RunnableCommand hscore = CommandTestFactory.createHScoreCommand(context, "10");

		BaseCommandTest.RunnableCommand login3 = CommandTestFactory.createLoginCommand(context, "10");
		
		
		pool = new ArrayList<Runnable>();
		pool.add(score1);
		pool.add(hscore);
		AssertConcurrent.assertConcurrent("scores", pool, 2000);
		
		
		pool = new ArrayList<Runnable>();
		pool.add(score11);
		pool.add(score12);
		pool.add(score13);
		pool.add(score14);
		pool.add(score15);
		pool.add(login3);
		pool.add(hscore);
		pool.add(score2);
		pool.add(score3);
		pool.add(score4);
		pool.add(score5);
		pool.add(score6);
		pool.add(score7);
		pool.add(score8);
		pool.add(score9);
		pool.add(score10);
		pool.add(score16);
		pool.add(score17);
		
		AssertConcurrent.assertConcurrent("all", pool, 2000);
		
		assertTrue(repository.getLevels().get(10).getScores().getScores().size()==15);
		ScoreQueue scores = repository.getLevels().get(10).getScores();
		
		assertTrue(scores.getUserScores().poll().getScore()==112);
		assertTrue(scores.getUserScores().poll().getScore()==113);
		assertTrue(scores.getUserScores().poll().getScore()==114);
		assertTrue(scores.getUserScores().poll().getScore()==115);
		assertTrue(scores.getUserScores().poll().getScore()==116);
		assertTrue(scores.getUserScores().poll().getScore()==117);
		assertTrue(scores.getUserScores().poll().getScore()==118);
		assertTrue(scores.getUserScores().poll().getScore()==119);
		assertTrue(scores.getUserScores().poll().getScore()==120);
		assertTrue(scores.getUserScores().poll().getScore()==121);
		assertTrue(scores.getUserScores().poll().getScore()==122);
		assertTrue(scores.getUserScores().poll().getScore()==123);
		assertTrue(scores.getUserScores().poll().getScore()==124);
		assertTrue(scores.getUserScores().poll().getScore()==125);
		assertTrue(scores.getUserScores().poll().getScore()==126);
		assertTrue(scores.getUserScores().poll()==null);
		
		

	}
}
