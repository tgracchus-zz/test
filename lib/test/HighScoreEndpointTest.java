package com.king.backend.test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.junit.Before;
import org.junit.Test;

import com.king.backend.app.model.UserScore;
import com.king.backend.app.model.Level;

public class HighScoreEndpointTest extends BaseCommandTest {

	protected UserScore userScore1;
	protected UserScore userScore2;
	protected UserScore userScore3;
	protected UserScore userScore4;
	protected UserScore userScore5;
	protected UserScore userScore6;
	protected UserScore userScore7;
	protected UserScore userScore8;
	protected UserScore userScore9;
	protected UserScore userScore10;
	protected UserScore userScore11;
	protected UserScore userScore12;
	protected UserScore userScore13;
	protected UserScore userScore14;
	protected UserScore userScore15;

	protected User user10;
	protected User user11;
	protected User user12;
	protected User user13;

	protected Level highScoresByLevel;
	protected ReentrantReadWriteLock lock;

	@Before
	public void setUpHighScoreCommandTest() {

		user10 = new User(10);
		user11 = new User(11);
		user12 = new User(12);
		user13 = new User(13);
		
		repository.getUsers().put(10, user10);
		repository.getUsers().put(11, user11);
		repository.getUsers().put(12, user12);
		repository.getUsers().put(13, user13);

		userScore1 = new UserScore(1, user10);
		userScore2 = new UserScore(2, user10);
		userScore3 = new UserScore(3, user10);
		userScore4 = new UserScore(4, user10);
		userScore5 = new UserScore(5, user10);
		userScore6 = new UserScore(6, user10);
		userScore7 = new UserScore(7, user10);
		userScore8 = new UserScore(8, user10);
		userScore9 = new UserScore(9, user10);
		userScore10 = new UserScore(10, user10);
		userScore11 = new UserScore(11, user10);
		userScore12 = new UserScore(12, user10);
		userScore13 = new UserScore(13, user10);
		userScore14 = new UserScore(14, user10);
		userScore15 = new UserScore(15, user10);

		highScoresByLevel = new Level(10);
		highScoresByLevel.getScores().getUserScores().add(userScore1);
		highScoresByLevel.getScores().getUserScores().add(userScore2);
		highScoresByLevel.getScores().getUserScores().add(userScore3);
		highScoresByLevel.getScores().getUserScores().add(userScore4);
		highScoresByLevel.getScores().getUserScores().add(userScore5);
		highScoresByLevel.getScores().getUserScores().add(userScore6);
		highScoresByLevel.getScores().getUserScores().add(userScore7);
		highScoresByLevel.getScores().getUserScores().add(userScore8);
		highScoresByLevel.getScores().getUserScores().add(userScore9);
		highScoresByLevel.getScores().getUserScores().add(userScore10);
		highScoresByLevel.getScores().getUserScores().add(userScore11);
		highScoresByLevel.getScores().getUserScores().add(userScore12);
		highScoresByLevel.getScores().getUserScores().add(userScore13);
		highScoresByLevel.getScores().getUserScores().add(userScore14);
		highScoresByLevel.getScores().getUserScores().add(userScore15);


		repository.getLevels().put(highScoresByLevel.getLevel(), highScoresByLevel);


	}

	@Test
	public void test() throws InterruptedException {

		BaseCommandTest.RunnableCommand command = CommandTestFactory.createHScoreCommand(context, "10");
		List<Runnable> pool = new ArrayList<Runnable>();
		pool.add(command);

		AssertConcurrent.assertConcurrent("HighScoreEndpointTest", pool, 2000);

		assertTrue(command.getAttributes().get("output") != null);

		String expectedResult =  "10=15,10=14,10=13,10=12,10=11,10=10,10=9,10=8,10=7,10=6,10=5,10=4,10=3,10=2,10=1";
		assertTrue(command.getAttributes().get("output").equals(expectedResult));

	}

}
