package com.king.backend.server.http;

import com.sun.net.httpserver.HttpExchange;

/**
 * Created by ulises on 7/10/15.
 */
public class Response {

    private final Request request;
    private final HttpStatus status;
    private final String body;

    public Response(HttpStatus status, Request request, String body) {
        this.request = request;
        this.status = status;
        this.body = body;
    }

    protected HttpExchange getExchange() {
        return request.getExchange();
    }

    public String getBody() {
        return body;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
