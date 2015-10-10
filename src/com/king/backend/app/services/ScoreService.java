package com.king.backend.app.services;

import com.king.backend.app.model.UserScore;

import java.util.List;

/**
 * Created by ulises on 10/10/15.
 */
public interface ScoreService {

    UserScore updateUserHighScore(int level, int score,int userId);

    List<UserScore> findHighScoreBy(int level);


}
