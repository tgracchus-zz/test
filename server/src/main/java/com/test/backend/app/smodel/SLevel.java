package com.test.backend.app.smodel;

import com.test.backend.app.model.UserScore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SLevel {

    private final int level;

    private final int numberOfScoresByLevel;

    private final List<UserScore> scores;

    private final Comparator<UserScore> userScoreComparator;

    public SLevel(int level, int numberOfScoresByLevel) {
        super();
        this.level = level;
        this.scores = new ArrayList<>(numberOfScoresByLevel);
        this.numberOfScoresByLevel = numberOfScoresByLevel;
        this.userScoreComparator = new Comparator<UserScore>() {

            @Override public int compare(UserScore o1, UserScore o2) {
                return o2.compareTo(o1);
            }
        };
    }

    public long getLevel() {
        return level;
    }

    public void updateUserHighScore(UserScore userScore) {

        //Recheck condition
        if (needsUpdate(userScore)) {
            updateScores(userScore);

        }

    }

    private void updateScores(UserScore userScore) {
        scores.add(userScore);
        scores.sort(this.userScoreComparator);
        if (isOverflowed()) {
            scores.remove(scores.size() - 1);
        }
    }

    private boolean needsUpdate(UserScore userScore) {
        return !isFull() || userScore.compareTo(scores.get(0)) > 0;
    }

    private boolean isFull() {
        return scores.size() >= numberOfScoresByLevel;
    }

    private boolean isOverflowed() {
        return scores.size() > numberOfScoresByLevel;
    }

    public List<UserScore> findHighScores() {

        return Collections.unmodifiableList(new ArrayList<>(scores));

    }
}
