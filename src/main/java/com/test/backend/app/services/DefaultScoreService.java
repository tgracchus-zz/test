package com.test.backend.app.services;

import com.test.backend.app.model.HighScoresByLevel;
import com.test.backend.app.model.UserScore;

import java.util.List;

/**
 * Created by ulises on 10/10/15.
 */
public class DefaultScoreService implements ScoreService {

    private final HighScoresByLevel highScoresByLevel;

    public DefaultScoreService(int numberOfScoresByLevel) {
        this.highScoresByLevel = new HighScoresByLevel(numberOfScoresByLevel);
    }

    @Override
    public UserScore updateUserHighScore(int level, int score, int userId) {
        UserScore userScore = new UserScore(score, userId);
        highScoresByLevel.updateUserHighScore(level, userScore);
        return userScore;
    }

    @Override
    public List<UserScore> findHighScoreBy(int level) {
        return highScoresByLevel.findHighScoreBy(level);
    }
}
