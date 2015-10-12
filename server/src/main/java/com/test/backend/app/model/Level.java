package com.test.backend.app.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Level {

    private final int level;

    private final int numberOfScoresByLevel;

    private final List<UserScore> scores;

    private final ReentrantReadWriteLock lock;
    private final ReentrantReadWriteLock.ReadLock readLock;
    private final ReentrantReadWriteLock.WriteLock writeLock;

    private final Comparator<UserScore> userScoreComparator;

    public Level(int level, int numberOfScoresByLevel) {
        super();
        this.level = level;
        this.lock = new ReentrantReadWriteLock();
        this.readLock = this.lock.readLock();
        this.writeLock = this.lock.writeLock();
        this.scores = new ArrayList<>(numberOfScoresByLevel);
        this.numberOfScoresByLevel = numberOfScoresByLevel;
        this.userScoreComparator = new Comparator<UserScore>() {
            @Override
            public int compare(UserScore o1, UserScore o2) {
                return o2.compareTo(o1);
            }
        };
    }

    public long getLevel() {
        return level;
    }


    public void updateUserHighScore(UserScore userScore) {

        readLock.lock();
        try {
            //Check condition
            if (needsUpdate(userScore)) {
                //Upgrade lock
                readLock.unlock();
                writeLock.lock();
                try {
                    //Recheck condition
                    if (needsUpdate(userScore)) {
                        updateScores(userScore);
                    }
                } finally {
                    readLock.lock();
                    writeLock.unlock();

                }

            }


        } finally {
            readLock.unlock();
        }


    }

    private void updateScores(UserScore userScore) {
        scores.add(userScore);
        scores.sort(this.userScoreComparator);
        if (isOverflowed()) {
            scores.remove(scores.size()-1);
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
        readLock.lock();
        try {
            return Collections.unmodifiableList(new ArrayList<>(scores));
        } finally {
            readLock.unlock();
        }

    }
}
