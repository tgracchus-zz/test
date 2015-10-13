package com.test.backend.app.smodel;

import com.test.backend.app.model.UserScore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ulises on 10/10/15.
 */
public class SHighScoresByLevel {

    private final Map<Integer, SLevel> levels;

    private final int numberOfScoresByLevel;

    public SHighScoresByLevel(int numberOfScoresByLevel) {
        this.levels = new HashMap<>();
        this.numberOfScoresByLevel = numberOfScoresByLevel;
    }

    public void updateUserHighScore(int level, UserScore userScore) {

        SLevel highScoresBySLevel = this.findLevel(level);
        highScoresBySLevel.updateUserHighScore(userScore);

    }

    public List<UserScore> findHighScoreBy(int level) {
        SLevel highScoresBySLevel = this.findLevel(level);
        return highScoresBySLevel.findHighScores();
    }

    private SLevel findLevel(int level) {
        SLevel highScoresBySLevel = levels.get(level);
        if (highScoresBySLevel == null) {
            SLevel newHighScoresBySLevel = new SLevel(level, numberOfScoresByLevel);
            levels.put(level, newHighScoresBySLevel);
            highScoresBySLevel = newHighScoresBySLevel;

        }
        return highScoresBySLevel;

    }

}
