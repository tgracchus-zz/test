package com.king.backend.app.model;

public class UserScore implements Comparable<UserScore> {

    private final int score;
    private final int userId;

    public UserScore(int score, int userId) {
        super();
        this.score = score;
        this.userId = userId;
    }

    public int getScore() {
        return score;
    }

    public int getUserId() {
        return userId;
    }


    @Override
    public int compareTo(UserScore o) {
        return this.score - o.score;
    }


}


