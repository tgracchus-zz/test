package com.king.backend.server.http;

import com.sun.net.httpserver.HttpExchange;

/**
 * Created by ulises on 10/10/15.
 */
public interface RequestBuilder {
    Request newRequest(HttpExchange exchange);
}
