package com.king.backend;

import com.king.backend.app.endpoints.HighScoreEndpoint;
import com.king.backend.app.endpoints.LoginEndpoint;
import com.king.backend.app.endpoints.ScoreEndpoint;
import com.king.backend.app.requestparser.HighScoreRequestParser;
import com.king.backend.app.requestparser.LoginRequestParser;
import com.king.backend.app.requestparser.ScoreRequest;
import com.king.backend.app.requestparser.ScoreRequestParser;
import com.king.backend.app.model.User;
import com.king.backend.app.services.DefaultLoginService;
import com.king.backend.app.services.DefaultScoreService;
import com.king.backend.app.services.LoginService;
import com.king.backend.app.services.ScoreService;
import com.king.backend.server.KingBackEndServer;
import com.king.backend.server.dispatcher.DefaultHttpHandler;
import com.king.backend.server.endpoint.Endpoints;
import com.king.backend.server.endpoint.RequestParser;
import com.king.backend.server.http.DefaultRequestBuilder;
import com.king.backend.server.http.DefaultResponseBuilder;
import com.king.backend.server.http.HttpResponseSender;
import com.king.backend.server.http.ResponseBuilder;
import com.sun.net.httpserver.HttpHandler;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.Executors;

public class Main {

    private static KingBackEndServer server;


    //In milliseconds: So 10 minutes
    private static long DEFAULT_TOKEN_EXPIRATION = 1000 * 60 * 10;

    private static int SCORES_PER_LEVEL = 15;

    public static void main(String[] args) throws IllegalArgumentException {

        //Services
        LoginService loginService = new DefaultLoginService(DEFAULT_TOKEN_EXPIRATION, Executors.newSingleThreadExecutor());
        ScoreService scoreService = new DefaultScoreService(SCORES_PER_LEVEL);

        ResponseBuilder responseBuilder = new DefaultResponseBuilder();

        RequestParser<User> loginRequestParser = new LoginRequestParser();
        RequestParser<ScoreRequest> scoreRequestParser = new ScoreRequestParser();
        RequestParser<Integer> highScoreRequestParser = new HighScoreRequestParser();

        Endpoints endpoints = new Endpoints( //
                Arrays.asList( //
                        new ScoreEndpoint(responseBuilder, scoreRequestParser, scoreService, loginService), //
                        new LoginEndpoint(responseBuilder, loginRequestParser, loginService), //
                        new HighScoreEndpoint(responseBuilder, highScoreRequestParser, scoreService)//
                )//
        );

        HttpHandler httpHandler = new DefaultHttpHandler(endpoints, new DefaultRequestBuilder(), responseBuilder,
                new HttpResponseSender());


        server = new KingBackEndServer.KingBackEndServerBuilder().
                address("0.0.0.0").port(8080) //
                .connectionThreads(300).workingThreads(50)//
                .httpHandler("/", httpHandler)
                .keepAliveTime(60L) //
                .build(); //


        server.start();
        Scanner user_input = null;
        try {
            user_input = new Scanner(System.in);

            System.out.println("Press Enter to stop the server");
            user_input.nextLine();

        } finally {
            if (user_input != null) {
                user_input.close();
            }
            server.stop(1);
            System.out.println("Main Stopped");
        }
    }


}
