package com.king.backend.app.model;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by ulises on 10/10/15.
 */
public class HighScoresByLevel {

    private final ConcurrentMap<Integer, Level> levels;

    private final int numberOfScoresByLevel;

    public HighScoresByLevel(int numberOfScoresByLevel) {
        this.levels = new ConcurrentHashMap<>();
        this.numberOfScoresByLevel = numberOfScoresByLevel;
    }


    public void updateUserHighScore(int level, UserScore userScore) {

        Level highScoresByLevel = this.findLevel(level);
        highScoresByLevel.updateUserHighScore(userScore);

    }

    public List<UserScore> findHighScoreBy(int level) {
        Level highScoresByLevel = this.findLevel(level);
        return highScoresByLevel.findHighScores();
    }


    private Level findLevel(int level) {
        Level highScoresByLevel = levels.get(level);
        if (highScoresByLevel == null) {
            Level newHighScoresByLevel = new Level(level, numberOfScoresByLevel);
            highScoresByLevel = levels.putIfAbsent(level, newHighScoresByLevel);
            if (highScoresByLevel == null) {
                highScoresByLevel = newHighScoresByLevel;
            }
        }
        return highScoresByLevel;

    }


}
