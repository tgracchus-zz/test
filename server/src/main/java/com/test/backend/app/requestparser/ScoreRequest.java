package com.test.backend.app.requestparser;

import java.util.UUID;

/**
 * Created by ulises on 10/10/15.
 */
public class ScoreRequest {

    private final int levelId;
    private final int score;
    private final UUID tokenId;

    public ScoreRequest(int levelId, int score, UUID tokenId) {
        this.levelId = levelId;
        this.score = score;
        this.tokenId = tokenId;
    }

    public int getLevelId() {
        return levelId;
    }

    public int getScore() {
        return score;
    }

    public UUID getTokenId() {
        return tokenId;
    }
}
