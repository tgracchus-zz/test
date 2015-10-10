package com.king.backend.app.requestparser;

import com.king.backend.server.endpoint.RequestParser;
import com.king.backend.server.endpoint.RequestParserException;
import com.king.backend.server.http.Request;

import java.util.List;
import java.util.UUID;

/**
 * Created by ulises on 10/10/15.
 */
public class ScoreRequestParser implements RequestParser<ScoreRequest> {

    @Override
    public ScoreRequest parse(Request request) throws RequestParserException {

        List<String> pathParams = request.getPathParams().getVariables();
        List<String> sessionkey = request.getQueryParams().getParam("sessionkey");
        if (sessionkey == null || sessionkey.size() > 1 || pathParams.size() < 1) {
            throw new RequestParserException("Please check level id and sessionkey query parameter");
        } else {
            int levelId = Integer.valueOf(pathParams.get(0));
            UUID token = UUID.fromString(sessionkey.get(0));
            int score = Integer.valueOf(request.getBody());
            return new ScoreRequest(levelId, score, token);

        }
    }
}
