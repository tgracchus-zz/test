package com.test.backend.app.sservices;

import com.test.backend.app.model.UserScore;
import com.test.backend.app.services.ScoreService;
import com.test.backend.app.smodel.SHighScoresByLevel;

import java.util.List;

/**
 * Created by ulises on 10/10/15.
 */
public class SDefaultScoreService implements ScoreService {

    private final SHighScoresByLevel highScoresByLevel;

    public SDefaultScoreService(int numberOfScoresByLevel) {
        this.highScoresByLevel = new SHighScoresByLevel(numberOfScoresByLevel);
    }

    @Override public UserScore updateUserHighScore(int level, int score, int userId) {
        UserScore userScore = new UserScore(score, userId);
        highScoresByLevel.updateUserHighScore(level, userScore);
        return userScore;
    }

    @Override public List<UserScore> findHighScoreBy(int level) {
        return highScoresByLevel.findHighScoreBy(level);
    }
}
