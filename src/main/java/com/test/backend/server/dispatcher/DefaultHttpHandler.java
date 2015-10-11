package com.test.backend.server.dispatcher;

import com.test.backend.server.endpoint.Endpoint;
import com.test.backend.server.endpoint.Endpoints;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.test.backend.server.http.*;

import java.io.IOException;
import java.util.Optional;

public class DefaultHttpHandler implements HttpHandler {

    private final Endpoints endpoints;
    private final DefaultRequestBuilder requestBuilder;
    private final ResponseBuilder responseBuilder;
    private final HttpResponseSender httpResponseSender;

    public DefaultHttpHandler(Endpoints endpoints, DefaultRequestBuilder requestBuilder, ResponseBuilder responseBuilder, HttpResponseSender httpResponseSender) {
        this.endpoints = endpoints;
        this.requestBuilder = requestBuilder;
        this.responseBuilder = responseBuilder;
        this.httpResponseSender = httpResponseSender;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        Response response = null;

        try {
            Optional<Endpoint> matchingEndpoints = endpoints.findEndpoint(exchange.getRequestURI(), exchange.getRequestMethod());

            if (matchingEndpoints.isPresent()) {

                Request request = requestBuilder.newRequest(exchange);
                response = matchingEndpoints.get().call(request);

            } else {
                httpResponseSender.sendResourceNotFoundResponse(exchange);
            }


        } finally {
            httpResponseSender.sendResponse(response);
        }

    }

}
