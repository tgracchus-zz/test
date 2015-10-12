package com.test.backend.server.http;

import com.sun.net.httpserver.HttpExchange;

/**
 * Created by ulises on 7/10/15.
 */
public class Request {

    private final String method;
    private final String body;
    private final PathParams pathParams;
    private final QueryParams queryParams;

    private final HttpExchange exchange;

    public Request(String method, String body, HttpExchange exchange, PathParams pathParams, QueryParams queryParams) {
        this.method = method;
        this.exchange = exchange;
        this.pathParams = pathParams;
        this.queryParams = queryParams;
        this.body = body;
    }

    public String getMethod() {
        return method;
    }

    public PathParams getPathParams() {
        return pathParams;
    }

    public QueryParams getQueryParams() {
        return queryParams;
    }

    public String getBody() {
        return body;
    }

    protected HttpExchange getExchange() {
        return exchange;
    }
}
