package com.king.backend.server.endpoint;

import com.king.backend.server.http.Request;

/**
 * Created by ulises on 10/10/15.
 */
public interface RequestParser<T> {

    T parse(Request request) throws RequestParserException;
}
