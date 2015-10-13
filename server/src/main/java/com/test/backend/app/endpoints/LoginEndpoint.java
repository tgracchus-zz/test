package com.test.backend.app.endpoints;


import com.test.backend.app.services.LoginService;

import com.test.backend.app.model.Token;
import com.test.backend.app.model.User;
import com.test.backend.server.endpoint.Endpoint;
import com.test.backend.server.endpoint.RequestParser;
import com.test.backend.server.http.*;

public class LoginEndpoint extends Endpoint<User> {


    private final static String LOGIN_REGEX = ".*/login";
    private final LoginService loginService;


    public LoginEndpoint(ResponseBuilder responseBuilder,
                         RequestParser<User> requestParser, LoginService loginService) {
        super(LOGIN_REGEX, responseBuilder, HttpMethod.GET, requestParser);
        this.loginService = loginService;
    }

    @Override
    public Response doCall(Request request, User user) throws Exception {
        Token token = loginService.login(user);
        return newResponse(HttpStatus.OK, request, token.getToken().toString());
    }


}

