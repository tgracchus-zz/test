package com.test.backend.app.services;



import com.test.backend.app.model.UserScore;

import java.util.List;

/**
 * Created by ulises on 10/10/15.
 */
public interface ScoreService {

    UserScore updateUserHighScore(int level, int score,int userId);

    List<UserScore> findHighScoreBy(int level);


}
