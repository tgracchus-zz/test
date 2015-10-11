package com.test.backend.app.endpoints;

import com.test.backend.app.model.UserScore;
import com.test.backend.app.services.ScoreService;
import com.test.backend.server.endpoint.Endpoint;
import com.test.backend.server.endpoint.RequestParser;
import com.test.backend.server.http.*;

import java.util.List;

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
