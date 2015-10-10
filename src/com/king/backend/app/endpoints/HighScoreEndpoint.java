package com.king.backend.app.endpoints;

import com.king.backend.app.model.UserScore;
import com.king.backend.app.services.ScoreService;
import com.king.backend.server.endpoint.Endpoint;
import com.king.backend.server.endpoint.RequestParser;
import com.king.backend.server.http.*;

import java.util.List;
import java.util.logging.Logger;

public class HighScoreEndpoint extends Endpoint<Integer> {


    private final static String HIGH_SCORE_REGEX = ".*/highscorelist";

    private final ScoreService scoreService;

    public HighScoreEndpoint(ResponseBuilder responseBuilder, RequestParser requestParser, ScoreService scoreService) {
        super(HIGH_SCORE_REGEX, responseBuilder, HttpMethod.GET, requestParser);
        this.scoreService = scoreService;
    }

    @Override
    public Response doCall(Request request, Integer level) throws Exception {
        List<UserScore> userScores = scoreService.findHighScoreBy(level.intValue());


        String csvScores =
                userScores.stream()
                        .map(userScore -> userScore.getUserId() + "=" + userScore.getScore())
                        .reduce((csv, str) -> csv + "," + str)
                        .get();

        return newResponse(HttpStatus.OK, request, csvScores);
    }
}
