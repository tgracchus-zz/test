package com.test.backend.app.endpoints;

import com.test.backend.app.requestparser.ScoreRequest;
import com.test.backend.app.model.User;
import com.test.backend.app.services.LoginService;
import com.test.backend.app.services.ScoreService;
import com.test.backend.server.endpoint.Endpoint;
import com.test.backend.server.endpoint.RequestParser;
import com.test.backend.server.http.*;

public class ScoreEndpoint extends Endpoint<ScoreRequest> {

    private final static String SCORE_REGEX = ".*/score";

    private final ScoreService scoreService;
    private final LoginService loginService;


    public ScoreEndpoint(ResponseBuilder responseBuilder, RequestParser<ScoreRequest> requestParser,
                         ScoreService scoreService, LoginService loginService) {
        super(SCORE_REGEX, responseBuilder, HttpMethod.POST, requestParser);
        this.scoreService = scoreService;
        this.loginService = loginService;
    }

    @Override
    public Response doCall(Request request, ScoreRequest scoreRequest) throws Exception {

        User user = loginService.findUserByTokenId(scoreRequest.getTokenId());
        if (user == null) {
            return newResponse(HttpStatus.NOT_AUTORIZED, request, scoreRequest.getTokenId().toString());
        }

        scoreService.updateUserHighScore(scoreRequest.getLevelId(), scoreRequest.getScore(), user.getUserId());

        return newResponse(HttpStatus.OK, request, "");
    }
}
