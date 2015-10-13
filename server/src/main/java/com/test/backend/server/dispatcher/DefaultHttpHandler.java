package com.test.backend.server.dispatcher;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.test.backend.server.endpoint.Endpoint;
import com.test.backend.server.endpoint.Endpoints;
import com.test.backend.server.http.*;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DefaultHttpHandler implements HttpHandler {

    private final Endpoints endpoints;
    private final DefaultRequestBuilder requestBuilder;
    private final ResponseBuilder responseBuilder;
    private final HttpResponseSender httpResponseSender;

    private final Logger log = Logger.getLogger(DefaultHttpHandler.class.getName());

    public DefaultHttpHandler(Endpoints endpoints, DefaultRequestBuilder requestBuilder,
            ResponseBuilder responseBuilder, HttpResponseSender httpResponseSender) {
        this.endpoints = endpoints;
        this.requestBuilder = requestBuilder;
        this.responseBuilder = responseBuilder;
        this.httpResponseSender = httpResponseSender;
    }

    @Override public void handle(HttpExchange exchange) throws IOException {

        Response response = null;

        try {
            Optional<Endpoint> matchingEndpoints =
                    endpoints.findEndpoint(exchange.getRequestURI(), exchange.getRequestMethod());

            if (matchingEndpoints.isPresent()) {

                Request request = requestBuilder.newRequest(exchange);
                response = matchingEndpoints.get().call(request);

            } else {
                httpResponseSender.sendResourceNotFoundResponse(exchange);
            }

        } catch (Exception e) {
            log.log(Level.ALL, e.getLocalizedMessage(), e);
        } finally {
            httpResponseSender.sendResponse(response);
        }

    }

}
